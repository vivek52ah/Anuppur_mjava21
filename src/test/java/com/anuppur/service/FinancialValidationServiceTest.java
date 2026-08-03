package com.anuppur.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.anuppur.bean.ExpensesDataBean;
import com.anuppur.bean.FinancialAgencyBean;
import com.anuppur.bean.WorkBean;
import com.anuppur.dto.FinancialExpenditureRequest;
import com.anuppur.entity.ExpensesData;
import com.anuppur.entity.Work;
import com.anuppur.entity.WorkFinancialAgency;
import com.anuppur.exception.FinancialValidationException;
import com.anuppur.repository.ExpensesDataRepository;
import com.anuppur.repository.FinancialAgencyRepository;
import com.anuppur.repository.TSASReviseWorkRepository;
import com.anuppur.repository.TSASWorkRepository;
import com.anuppur.repository.WorkRepository;

class FinancialValidationServiceTest {

    private WorkRepository workRepository;
    private TSASReviseWorkRepository reviseWorkRepository;
    private FinancialAgencyRepository financialAgencyRepository;
    private ExpensesDataRepository expensesDataRepository;
    private FinancialValidationService validation;

    @BeforeEach
    void setUp() {
        workRepository = mock(WorkRepository.class);
        financialAgencyRepository = mock(FinancialAgencyRepository.class);
        expensesDataRepository = mock(ExpensesDataRepository.class);
        reviseWorkRepository = mock(TSASReviseWorkRepository.class);
        validation = new FinancialValidationService(
                workRepository,
                mock(TSASWorkRepository.class),
                reviseWorkRepository,
                financialAgencyRepository,
                expensesDataRepository);
    }

    @Test
    void rejectsTsGreaterThanAs() {
        assertThatThrownBy(() -> validation.validateTsDoesNotExceedAs(
                new BigDecimal("100000.01"), new BigDecimal("100000.00")))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessage("TS amount cannot exceed AS amount.");
    }

    @Test
    void rejectsFinancialHeadTotalGreaterThanSanction() {
        WorkBean request = new WorkBean();
        request.setAsAmt(new BigDecimal("100000.00"));
        request.setFinancialHeads(List.of(head(60000.00), head(50000.00)));

        assertThatThrownBy(() -> validation.validateWorkFinancials(request, null))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("Financial heads total cannot exceed AS amount");
    }

    @Test
    void rejectsNegativeSingleExpenditure() {
        FinancialExpenditureRequest request = expenditure(1L, 10L, "-0.01");

        assertThatThrownBy(() -> validation.applyFinancialAgencyExpenditures(List.of(request)))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessage("Single expenditure cannot be negative.");
    }

    @Test
    void rejectsCumulativeExpenditureGreaterThanSanction() {
        Work work = work(10L, "100000.00");
        WorkFinancialAgency row = financialRow(1L, 10L, 200000.00, 95000.00);
        when(workRepository.findByIdForFinancialUpdate(10L)).thenReturn(Optional.of(work));
        when(financialAgencyRepository.findByWorkId(10L)).thenReturn(List.of(row));

        assertThatThrownBy(() -> validation.applyFinancialAgencyExpenditures(
                List.of(expenditure(1L, 10L, "5000.01"))))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("Cumulative expenditure cannot exceed AS amount 100000.00");
    }

    @Test
    void acceptsValidExpenditureAndUsesExactDecimalArithmetic() {
        Work work = work(10L, "100000.00");
        WorkFinancialAgency row = financialRow(1L, 10L, 100000.00, 99999.89);
        when(workRepository.findByIdForFinancialUpdate(10L)).thenReturn(Optional.of(work));
        when(financialAgencyRepository.findByWorkId(10L)).thenReturn(List.of(row));

        validation.applyFinancialAgencyExpenditures(
                List.of(expenditure(1L, 10L, "0.11")));

        assertThat(row.getExpenditure()).isEqualTo(100000.00);
        verify(financialAgencyRepository).saveAll(anyList());
    }

    @Test
    void cumulativeValidationUsesLatestRevisedAsAmount() {
        Work work = work(10L, "200000.00");
        WorkFinancialAgency row = financialRow(1L, 10L, 200000.00, 95000.00);
        when(workRepository.findByIdForFinancialUpdate(10L)).thenReturn(Optional.of(work));
        when(financialAgencyRepository.findByWorkId(10L)).thenReturn(List.of(row));
        when(reviseWorkRepository.findLatestActiveAmount(10L, "AS"))
                .thenReturn(new BigDecimal("100000.00"));

        assertThatThrownBy(() -> validation.applyFinancialAgencyExpenditures(
                List.of(expenditure(1L, 10L, "5000.01"))))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("Cumulative expenditure cannot exceed AS amount 100000.00");
    }

    @Test
    void rejectsDatabaseScaleAndIntegerLimitBeforeSave() {
        assertThatThrownBy(() -> validation.validateMoney(new BigDecimal("1.001"), "Amount"))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("at most 2 decimal places");
        assertThatThrownBy(() -> validation.validateMoney(new BigDecimal("100000000000.00"), "Amount"))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("database limit of 11 integer digits");
    }

    @Test
    void rejectsMonthlyExpenseWhenHistoryWouldExceedSanction() {
        Work work = work(10L, "100000.00");
        ExpensesData oldExpense = new ExpensesData();
        oldExpense.setExpensessCurrentFy(new BigDecimal("90000.00"));
        ExpensesDataBean request = new ExpensesDataBean();
        request.setWorkId(10L);
        request.setExpensessCurrentFy(new BigDecimal("10000.01"));
        when(workRepository.findByIdForFinancialUpdate(10L)).thenReturn(Optional.of(work));
        when(expensesDataRepository.findByWorkId(10L)).thenReturn(List.of(oldExpense));

        assertThatThrownBy(() -> validation.validateExpenseRequest(request))
                .isInstanceOf(FinancialValidationException.class)
                .hasMessageContaining("Cumulative expenditure cannot exceed AS amount");
    }

    private FinancialAgencyBean head(double cost) {
        FinancialAgencyBean bean = new FinancialAgencyBean();
        bean.setCost(cost);
        return bean;
    }

    private FinancialExpenditureRequest expenditure(Long rowId, Long workId, String amount) {
        FinancialExpenditureRequest request = new FinancialExpenditureRequest();
        request.setId(rowId);
        request.setWorkId(workId);
        request.setExpenditure(new BigDecimal(amount));
        return request;
    }

    private Work work(Long id, String asAmount) {
        Work work = new Work();
        work.setId(id);
        work.setAsAmt(new BigDecimal(asAmount));
        return work;
    }

    private WorkFinancialAgency financialRow(Long id, Long workId, Double cost, Double expenditure) {
        WorkFinancialAgency row = new WorkFinancialAgency();
        row.setId(id);
        row.setWorkId(workId);
        row.setCost(cost);
        row.setExpenditure(expenditure);
        return row;
    }
}
