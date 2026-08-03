package com.anuppur.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.bean.FinancialAgencyBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.dto.FinancialExpenditureRequest;
import com.anuppur.entity.ExpensesData;
import com.anuppur.entity.TSASWork;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkFinancialAgency;
import com.anuppur.exception.FinancialValidationException;
import com.anuppur.repository.ExpensesDataRepository;
import com.anuppur.repository.FinancialAgencyRepository;
import com.anuppur.repository.TSASWorkRepository;
import com.anuppur.repository.TSASReviseWorkRepository;
import com.anuppur.repository.WorkRepository;

/**
 * Central server-side financial rules. The application's DECIMAL money
 * columns are DECIMAL(13,2), so requests are rejected before persistence when
 * they cannot fit that database representation.
 */
@Service
public class FinancialValidationService {

    public static final int MONEY_PRECISION = 13;
    public static final int MONEY_SCALE = 2;
    private static final int MONEY_INTEGER_DIGITS = MONEY_PRECISION - MONEY_SCALE;

    private final WorkRepository workRepository;
    private final TSASWorkRepository tsasWorkRepository;
    private final TSASReviseWorkRepository tsasReviseWorkRepository;
    private final FinancialAgencyRepository financialAgencyRepository;
    private final ExpensesDataRepository expensesDataRepository;

    public FinancialValidationService(WorkRepository workRepository, TSASWorkRepository tsasWorkRepository,
            TSASReviseWorkRepository tsasReviseWorkRepository,
            FinancialAgencyRepository financialAgencyRepository,
            ExpensesDataRepository expensesDataRepository) {
        this.workRepository = workRepository;
        this.tsasWorkRepository = tsasWorkRepository;
        this.tsasReviseWorkRepository = tsasReviseWorkRepository;
        this.financialAgencyRepository = financialAgencyRepository;
        this.expensesDataRepository = expensesDataRepository;
    }

    public BigDecimal validateWorkFinancials(WorkBean bean, Work existing) {
        if (bean == null) {
            throw invalid("Work request is required.");
        }

        BigDecimal effectiveTs = bean.getTsAmt() != null
                ? validateMoney(bean.getTsAmt(), "TS amount")
                : existing == null ? null : existing.getTsAmt();
        BigDecimal effectiveAs = bean.getAsAmt() != null
                ? validateMoney(bean.getAsAmt(), "AS amount")
                : existing == null ? null : existing.getAsAmt();

        validateTsDoesNotExceedAs(effectiveTs, effectiveAs);
        validateOptionalMoney(bean.getEstimatedAmt(), "Estimated amount");
        validateOptionalMoney(bean.getAllocatedAmount(), "Allocated amount");
        validateOptionalMoney(bean.getFundByState(), "State financial head amount");
        validateOptionalMoney(bean.getFundByNhm(), "NHM financial head amount");
        validateOptionalMoney(bean.getFundByEcrp2(), "ECRP-II financial head amount");
        validateOptionalMoney(bean.getFundByOthers(), "Other financial head amount");

        if (bean.getFinancialHeads() != null) {
            BigDecimal submittedTotal = BigDecimal.ZERO;
            for (FinancialAgencyBean head : bean.getFinancialHeads()) {
                if (head == null) {
                    throw invalid("Financial head entry cannot be null.");
                }
                BigDecimal cost = fromDouble(head.getCost(), "Financial head amount", false);
                submittedTotal = submittedTotal.add(cost);

                BigDecimal suppliedExpenditure = fromDouble(
                        head.getExpenditure(), "Financial head expenditure", true);
                if (suppliedExpenditure != null && suppliedExpenditure.compareTo(BigDecimal.ZERO) != 0) {
                    throw invalid("Expenditure cannot be changed through the normal work edit request.");
                }
            }
            validateWithinSanction(submittedTotal, effectiveAs, "Financial heads total");
        }
        return effectiveAs;
    }

    public void validateTsDoesNotExceedAs(BigDecimal tsAmount, BigDecimal asAmount) {
        validateOptionalMoney(tsAmount, "TS amount");
        validateOptionalMoney(asAmount, "AS amount");
        if (tsAmount != null && asAmount == null) {
            throw invalid("AS amount must be saved before TS amount.");
        }
        if (tsAmount != null && asAmount != null && tsAmount.compareTo(asAmount) > 0) {
            throw invalid("TS amount cannot exceed AS amount.");
        }
    }

    public void validateRevisedTsAs(Long workId, BigDecimal revisedAmount, String type) {
        if (workId == null || (!"TS".equals(type) && !"AS".equals(type))) {
            throw invalid("Valid work ID and sanction type are required.");
        }
        Work work = lockWork(workId);
        BigDecimal amount = validateMoney(revisedAmount, "Revised " + type + " amount");
        TSASWork original = tsasWorkRepository.findByWorkId(workId);

        BigDecimal effectiveTs = tsasReviseWorkRepository.findLatestActiveAmount(workId, "TS");
        if (effectiveTs == null) {
            effectiveTs = work.getTsAmt() != null
                    ? work.getTsAmt()
                    : original == null ? null : original.getTsAmt();
        }
        BigDecimal effectiveAs = tsasReviseWorkRepository.findLatestActiveAmount(workId, "AS");
        if (effectiveAs == null) {
            effectiveAs = work.getAsAmt() != null
                    ? work.getAsAmt()
                    : original == null ? null : original.getAsAmt();
        }

        if ("TS".equals(type)) {
            effectiveTs = amount;
        } else {
            effectiveAs = amount;
        }
        validateTsDoesNotExceedAs(effectiveTs, effectiveAs);

        if ("AS".equals(type)) {
            validatePersistedFinancialHeadTotal(workId, effectiveAs);
            BigDecimal cumulativeExpenses = BigDecimal.ZERO;
            for (ExpensesData expense : expensesDataRepository.findByWorkId(workId)) {
                if (expense.getExpensessCurrentFy() != null) {
                    cumulativeExpenses = cumulativeExpenses.add(
                            validateMoney(expense.getExpensessCurrentFy(), "Stored expenditure"));
                }
            }
            validateWithinSanction(cumulativeExpenses, effectiveAs, "Cumulative expenditure");
            BigDecimal agencyExpenditure = BigDecimal.ZERO;
            for (WorkFinancialAgency head : financialAgencyRepository.findByWorkId(workId)) {
                BigDecimal expenditure = fromDouble(head.getExpenditure(), "Stored expenditure", true);
                if (expenditure != null) {
                    agencyExpenditure = agencyExpenditure.add(expenditure);
                }
            }
            validateWithinSanction(agencyExpenditure, effectiveAs, "Cumulative financial-head expenditure");
        }
    }

    public void validatePersistedFinancialHeadTotal(Long workId, BigDecimal sanctionedAmount) {
        BigDecimal total = BigDecimal.ZERO;
        for (WorkFinancialAgency head : financialAgencyRepository.findByWorkId(workId)) {
            BigDecimal cost = fromDouble(head.getCost(), "Financial head amount", false);
            total = total.add(cost);
            BigDecimal expenditure = fromDouble(head.getExpenditure(), "Financial head expenditure", true);
            if (expenditure != null && expenditure.compareTo(cost) > 0) {
                throw invalid("Financial head expenditure cannot exceed its allocated amount.");
            }
        }
        validateWithinSanction(total, sanctionedAmount, "Financial heads total");
    }

    public void validatePersistedFinancialHeadTotal(Long workId) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> invalid("Work not found for ID " + workId + "."));
        validatePersistedFinancialHeadTotal(workId, sanctionedAmount(work));
    }

    /**
     * Locks the work row and validates the next monthly expenditure against the
     * complete expenditure history. Locking prevents two simultaneous requests
     * from both passing against the same old cumulative value.
     */
    public BigDecimal validateExpenseRequest(ExpensesDataBean bean) {
        if (bean == null || bean.getWorkId() == null) {
            throw invalid("Work ID is required for expenditure.");
        }
        Work work = lockWork(bean.getWorkId());
        BigDecimal sanctionedAmount = sanctionedAmount(work);

        BigDecimal current = bean.getExpensessCurrentFy() != null
                ? validateMoney(bean.getExpensessCurrentFy(), "Single expenditure")
                : validateMoney(bean.getTotalExpensess(), "Single expenditure");
        validateOptionalMoney(bean.getExpensessUptoMarch(), "Expenditure up to March");
        validateOptionalMoney(bean.getTotalExpensess(), "Total expenditure");

        BigDecimal persistedTotal = BigDecimal.ZERO;
        for (ExpensesData expense : expensesDataRepository.findByWorkId(bean.getWorkId())) {
            BigDecimal amount = expense.getExpensessCurrentFy();
            if (amount != null) {
                persistedTotal = persistedTotal.add(validateMoney(amount, "Stored expenditure"));
            }
        }
        validateWithinSanction(persistedTotal.add(current), sanctionedAmount, "Cumulative expenditure");
        return current;
    }

    public void validateCalculatedExpenseFields(Long workId, BigDecimal... values) {
        Work work = workRepository.findById(workId)
                .orElseThrow(() -> invalid("Work not found for ID " + workId + "."));
        BigDecimal sanctionedAmount = sanctionedAmount(work);
        for (BigDecimal value : values) {
            if (value != null) {
                BigDecimal validated = validateMoney(value, "Calculated expenditure");
                validateWithinSanction(validated, sanctionedAmount, "Calculated expenditure");
            }
        }
    }

    @Transactional
    public Set<Long> applyFinancialAgencyExpenditures(List<FinancialExpenditureRequest> requests) {
        if (requests == null || requests.isEmpty()) {
            throw invalid("At least one expenditure entry is required.");
        }

        Set<Long> workIds = new LinkedHashSet<>();
        Set<Long> requestRowIds = new HashSet<>();
        for (FinancialExpenditureRequest request : requests) {
            if (request == null || request.getId() == null || request.getWorkId() == null) {
                throw invalid("Financial head row ID and work ID are required.");
            }
            if (!requestRowIds.add(request.getId())) {
                throw invalid("Duplicate financial head row ID " + request.getId() + " in request.");
            }
            validateMoney(request.getExpenditure(), "Single expenditure");
            workIds.add(request.getWorkId());
        }

        List<Long> orderedWorkIds = new ArrayList<>(workIds);
        orderedWorkIds.sort(Comparator.naturalOrder());
        Map<Long, Work> lockedWorks = new HashMap<>();
        Map<Long, Map<Long, WorkFinancialAgency>> headsByWork = new HashMap<>();
        Map<Long, BigDecimal> cumulativeByWork = new HashMap<>();

        for (Long workId : orderedWorkIds) {
            Work work = lockWork(workId);
            lockedWorks.put(workId, work);
            Map<Long, WorkFinancialAgency> rowMap = new HashMap<>();
            BigDecimal cumulative = BigDecimal.ZERO;
            for (WorkFinancialAgency row : financialAgencyRepository.findByWorkId(workId)) {
                rowMap.put(row.getId(), row);
                BigDecimal stored = fromDouble(row.getExpenditure(), "Stored expenditure", true);
                if (stored != null) {
                    cumulative = cumulative.add(stored);
                }
            }
            headsByWork.put(workId, rowMap);
            cumulativeByWork.put(workId, cumulative);
        }

        List<WorkFinancialAgency> changedRows = new ArrayList<>();
        for (FinancialExpenditureRequest request : requests) {
            WorkFinancialAgency row = headsByWork.get(request.getWorkId()).get(request.getId());
            if (row == null) {
                throw invalid("Financial head row does not belong to the supplied work.");
            }

            BigDecimal increment = validateMoney(request.getExpenditure(), "Single expenditure");
            BigDecimal cost = fromDouble(row.getCost(), "Financial head amount", false);
            BigDecimal current = fromDouble(row.getExpenditure(), "Stored expenditure", true);
            if (current == null) {
                current = BigDecimal.ZERO;
            }
            BigDecimal updated = current.add(increment);
            if (updated.compareTo(cost) > 0) {
                throw invalid("Expenditure cannot exceed the financial head amount. Remaining amount is "
                        + cost.subtract(current).toPlainString() + ".");
            }

            BigDecimal cumulative = cumulativeByWork.get(request.getWorkId()).add(increment);
            validateWithinSanction(cumulative, sanctionedAmount(lockedWorks.get(request.getWorkId())),
                    "Cumulative expenditure");
            cumulativeByWork.put(request.getWorkId(), cumulative);

            row.setExpenditure(updated.doubleValue());
            changedRows.add(row);
        }
        financialAgencyRepository.saveAll(changedRows);
        return workIds;
    }

    public BigDecimal validateMoney(BigDecimal value, String fieldName) {
        if (value == null) {
            throw invalid(fieldName + " is required.");
        }
        if (value.signum() < 0) {
            throw invalid(fieldName + " cannot be negative.");
        }

        BigDecimal normalized = value.stripTrailingZeros();
        int scale = Math.max(normalized.scale(), 0);
        int integerDigits = Math.max(normalized.precision() - normalized.scale(), 0);
        if (scale > MONEY_SCALE) {
            throw invalid(fieldName + " supports at most " + MONEY_SCALE + " decimal places.");
        }
        if (integerDigits > MONEY_INTEGER_DIGITS) {
            throw invalid(fieldName + " exceeds the database limit of " + MONEY_INTEGER_DIGITS
                    + " integer digits and " + MONEY_SCALE + " decimal places.");
        }
        return value;
    }

    private void validateOptionalMoney(BigDecimal value, String fieldName) {
        if (value != null) {
            validateMoney(value, fieldName);
        }
    }

    private BigDecimal fromDouble(Double value, String fieldName, boolean optional) {
        if (value == null) {
            if (optional) {
                return null;
            }
            throw invalid(fieldName + " is required.");
        }
        if (!Double.isFinite(value)) {
            throw invalid(fieldName + " must be a finite number.");
        }
        return validateMoney(BigDecimal.valueOf(value), fieldName);
    }

    private Work lockWork(Long workId) {
        return workRepository.findByIdForFinancialUpdate(workId)
                .orElseThrow(() -> invalid("Work not found for ID " + workId + "."));
    }

    private BigDecimal sanctionedAmount(Work work) {
        BigDecimal amount = tsasReviseWorkRepository.findLatestActiveAmount(work.getId(), "AS");
        if (amount == null) {
            amount = work.getAsAmt();
        }
        if (amount == null) {
            TSASWork tsasWork = tsasWorkRepository.findByWorkId(work.getId());
            amount = tsasWork == null ? null : tsasWork.getAsAmt();
        }
        if (amount == null) {
            throw invalid("AS amount must be saved before financial expenditure.");
        }
        return validateMoney(amount, "AS amount");
    }

    private void validateWithinSanction(BigDecimal amount, BigDecimal sanctionedAmount, String fieldName) {
        if (sanctionedAmount == null) {
            if (amount.compareTo(BigDecimal.ZERO) > 0) {
                throw invalid("AS amount is required before financial amounts can be saved.");
            }
            return;
        }
        BigDecimal validatedSanction = validateMoney(sanctionedAmount, "AS amount");
        if (amount.compareTo(validatedSanction) > 0) {
            throw invalid(fieldName + " cannot exceed AS amount " + validatedSanction.toPlainString() + ".");
        }
    }

    private FinancialValidationException invalid(String message) {
        return new FinancialValidationException(message);
    }
}
