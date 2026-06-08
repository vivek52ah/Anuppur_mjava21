package com.anuppur.service.impl;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.Work;
import com.anuppur.entity.DmRemarks;
import com.anuppur.entity.DepartmentRemarks;
import com.anuppur.entity.Block;

@Repository
public class WorkRepositoryCustomImpl {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Page<Work> findAllByDynamicFilters(Pageable pageable, String workName, String workType, 
                                              String financialYear, String divisionName, String districtName, 
                                              Integer workSubTypeIdInt, String workStatusName, 
                                              String implementationAgency, List<Long> blockId, String workNameFilter, String departmentRemark) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Work> query = cb.createQuery(Work.class);
        Root<Work> work = query.from(Work.class);
        
        List<Predicate> predicates = new ArrayList<>();

        // Only add conditions if parameters are not null or empty
        if (workName != null && !workName.isEmpty()) {
            predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
        }
        
        // Filter: workNameFilter (partial match on work name)
        if (workNameFilter != null && !workNameFilter.isEmpty()) {
            predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
        }
        
        // Filter: blockId (multiple values)
        if (blockId != null && !blockId.isEmpty()) {
            predicates.add(work.get("blockId").in(blockId));
        }
        
        if (workType != null && !workType.isEmpty()) {
            predicates.add(cb.equal(work.get("workType"), workType));
        }
        if (financialYear != null && !financialYear.isEmpty()) {
            predicates.add(cb.equal(work.get("financialYear"), financialYear));
        }
        if (divisionName != null && !divisionName.isEmpty()) {
            predicates.add(cb.equal(work.get("divisionName"), divisionName));
        }
        if (districtName != null && !districtName.isEmpty()) {
            predicates.add(cb.equal(work.get("districtName"), districtName));
        }
        if (workSubTypeIdInt != null) {
            predicates.add(cb.equal(work.get("workSubtypeId"), workSubTypeIdInt));
        }
        if (workStatusName != null && !workStatusName.isEmpty()) {
            predicates.add(cb.equal(work.get("workStatus"), workStatusName));
        }
        if (implementationAgency != null && !implementationAgency.isEmpty()) {
            predicates.add(cb.equal(work.get("implementationAgency"), implementationAgency));
        }

        // Filter: Department Remarks
        Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
        if (dmRemarksPredicate != null) {
            predicates.add(dmRemarksPredicate);
        }

        // Condition 1: Only Active status
        predicates.add(cb.equal(work.get("status"), "Active"));

        // Condition 2: (financial_year IS NOT NULL or scheme IS NOT NULL or work_head IS NOT NULL)
        Predicate financialSchemeWorkHeadCondition = cb.or(
            cb.isNotNull(work.get("financialYear")),
            cb.isNotNull(work.get("scheme")),
            cb.isNotNull(work.get("workHead"))
        );

        // Condition 3: (fund_by_state IS NOT NULL or fund_by_others IS NOT NULL or fund_by_nhm IS NOT NULL or fund_by_ecrp2 IS NOT NULL)
        Predicate fundCondition = cb.or(
            cb.isNotNull(work.get("fundByState")),
            cb.isNotNull(work.get("fundByOthers")),
            cb.isNotNull(work.get("fundByNhm")),
            cb.isNotNull(work.get("fundByEcrp2"))
        );

        // Combine Conditions 2 and 3 using OR
        Predicate additionalCondition = cb.or(financialSchemeWorkHeadCondition, fundCondition);
        predicates.add(additionalCondition);

        // Apply the predicates
        query.where(predicates.toArray(new Predicate[0]));

        // Create a TypedQuery with pagination
        TypedQuery<Work> typedQuery = entityManager.createQuery(query);
        int totalRows = typedQuery.getResultList().size();

        // Apply pagination
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<Work> resultList = typedQuery.getResultList();

        return new PageImpl<>(resultList, pageable, totalRows);
    }


    
    public Page<Work> findAllByStatusNotDeleted(Pageable pageable,
                String workNo, List<Long> workTypeId, List<Long> financialYear,
                Long districtId, List<Long> workStatusId, List<Long> agency,
                List<Long> workPriority, List<Long> financialHead, List<Long> vidhanSabha,
                String workNameFilter, List<Long> blockId, String departmentRemark) {
        return findAllByStatusNotDeleted(pageable, workNo, workTypeId, financialYear, districtId,
                workStatusId, agency, workPriority, financialHead, vidhanSabha,
                workNameFilter, blockId, departmentRemark, null);
    }

    public Page<Work> findAllByStatusNotDeleted(Pageable pageable,
                String workNo, List<Long> workTypeId, List<Long> financialYear,
                Long districtId, List<Long> workStatusId, List<Long> agency,
                List<Long> workPriority, List<Long> financialHead, List<Long> vidhanSabha,
                String workNameFilter, List<Long> blockId, String departmentRemark,
                List<Long> departmentList) {

            CriteriaBuilder cb = entityManager.getCriteriaBuilder();

            // Main query
            CriteriaQuery<Work> query = cb.createQuery(Work.class);
            Root<Work> work = query.from(Work.class);

            List<Predicate> predicates = new ArrayList<>();

            // Filter: workName (partial match)
            if (workNo != null && !workNo.isEmpty()) {
                predicates.add(cb.like(cb.lower(work.get("workNo")), "%" + workNo.toLowerCase() + "%"));
            }

            // Filter: workNameFilter (partial match on work name)
            if (workNameFilter != null && !workNameFilter.isEmpty()) {
                predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
            }

            // Filter: blockId (multiple values)
            if (blockId != null && !blockId.isEmpty()) {
                predicates.add(work.get("blockId").in(blockId));
            }

            // Filter: Department Remarks
            Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
            if (dmRemarksPredicate != null) {
                predicates.add(dmRemarksPredicate);
            }

            // Filter: Department (by department master ID via DepartmentRemarks)
            Predicate deptPredicate = addDepartmentFilter(cb, query, work, departmentList);
            if (deptPredicate != null) {
                predicates.add(deptPredicate);
            }

            // Multiple values filters (List)
            if (workTypeId != null && !workTypeId.isEmpty()) {
                predicates.add(work.get("workType").in(workTypeId));
            }
            if (financialYear != null && !financialYear.isEmpty()) {
                predicates.add(work.get("financialYear").in(financialYear));
            }
            if (workStatusId != null && !workStatusId.isEmpty()) {
                predicates.add(work.get("workStatus").in(workStatusId));
            }
            if (agency != null && !agency.isEmpty()) {
                predicates.add(work.get("implementationAgency").in(agency));
            }
            if (workPriority != null && !workPriority.isEmpty()) {
                predicates.add(work.get("workPriorityId").in(workPriority));
            }
            if (financialHead != null && !financialHead.isEmpty()) {
                predicates.add(work.get("financialHeadId").in(financialHead));
            }
            if (vidhanSabha != null && !vidhanSabha.isEmpty()) {
                predicates.add(work.get("vidhanSabhaId").in(vidhanSabha));
            }

            // Single value filter
            if (districtId != null) {
                predicates.add(cb.equal(work.get("districtId"), districtId));
            }

            // Always only active records
            predicates.add(cb.equal(work.get("status"), "Active"));

            query.where(predicates.toArray(new Predicate[0]));
            query.orderBy(cb.desc(work.get("id"))); // sort by ID descending

            // Execute query with pagination
            TypedQuery<Work> typedQuery = entityManager.createQuery(query);
            typedQuery.setFirstResult((int) pageable.getOffset());
            typedQuery.setMaxResults(pageable.getPageSize());
            List<Work> resultList = typedQuery.getResultList();

            // Count query
            CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
            Root<Work> countRoot = countQuery.from(Work.class);
            List<Predicate> countPredicates = new ArrayList<>();

            if (workNo != null && !workNo.isEmpty()) 
                countPredicates.add(cb.like(cb.lower(countRoot.get("workNo")), "%" + workNo.toLowerCase() + "%"));
            if (workNameFilter != null && !workNameFilter.isEmpty()) {
                countPredicates.add(cb.like(cb.lower(countRoot.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
            }
            if (blockId != null && !blockId.isEmpty()) {
                countPredicates.add(countRoot.get("blockId").in(blockId));
            }

            // Filter: Department Remarks for count
            Predicate dmRemarksCountPredicate = addDepartmentRemarksFilter(cb, countQuery, countRoot, departmentRemark);
            if (dmRemarksCountPredicate != null) {
                countPredicates.add(dmRemarksCountPredicate);
            }

            // Filter: Department for count
            Predicate deptCountPredicate = addDepartmentFilter(cb, countQuery, countRoot, departmentList);
            if (deptCountPredicate != null) {
                countPredicates.add(deptCountPredicate);
            }

            if (workTypeId != null && !workTypeId.isEmpty()) {
                countPredicates.add(countRoot.get("workType").in(workTypeId));
            }
            if (financialYear != null && !financialYear.isEmpty()) {
                countPredicates.add(countRoot.get("financialYear").in(financialYear));
            }
            if (workStatusId != null && !workStatusId.isEmpty()) {
                countPredicates.add(countRoot.get("workStatus").in(workStatusId));
            }
            if (agency != null && !agency.isEmpty()) {
                countPredicates.add(countRoot.get("implementationAgency").in(agency));
            }
            if (workPriority != null && !workPriority.isEmpty()) {
                countPredicates.add(countRoot.get("workPriorityId").in(workPriority));
            }
            if (financialHead != null && !financialHead.isEmpty()) {
                countPredicates.add(countRoot.get("financialHeadId").in(financialHead));
            }
            if (vidhanSabha != null && !vidhanSabha.isEmpty()) {
                countPredicates.add(countRoot.get("vidhanSabhaId").in(vidhanSabha));
            }
            if (districtId != null) {
                countPredicates.add(cb.equal(countRoot.get("districtId"), districtId));
            }
            countPredicates.add(cb.equal(countRoot.get("status"), "Active"));

            countQuery.select(cb.count(countRoot))
                    .where(countPredicates.toArray(new Predicate[0]));
            Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

            return new PageImpl<>(resultList, pageable, totalRows);
        }



    
    
    
	
	public Page<Work> fetchAllWorksByAgency(Pageable pageable, String workName, String workType,
	        String financialYear, String agency, String divisionName, String districtName, 
	        String workStatusName, Integer workSubTypeIdInt, String implementationAgency,
	        String workNameFilter, List<Long> blockId, String departmentRemark) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
	    }
	    
	    // Filter: workNameFilter (partial match on work name)
	    if (workNameFilter != null && !workNameFilter.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
	    }
	    
	    // Filter: blockId (multiple values)
	    if (blockId != null && !blockId.isEmpty()) {
	        predicates.add(work.get("blockId").in(blockId));
	    }
	    
	    if (workType != null && !workType.isEmpty()) {
	        predicates.add(cb.equal(work.get("workType"), workType));
	    }
	    if (financialYear != null && !financialYear.isEmpty()) {
	        predicates.add(cb.equal(work.get("financialYear"), financialYear));
	    }
	    if (agency != null && !agency.isEmpty()) {
	        predicates.add(cb.equal(work.get("implementationAgency"), agency));
	    }
	    if (divisionName != null && !divisionName.isEmpty()) {
	        predicates.add(cb.equal(work.get("divisionName"), divisionName));
	    }
	    if (districtName != null && !districtName.isEmpty()) {
	        predicates.add(cb.equal(work.get("districtName"), districtName));
	    }
	    if (workSubTypeIdInt != null) {
	        predicates.add(cb.equal(work.get("workSubtypeId"), workSubTypeIdInt));
	    }
	    if (workStatusName != null && !workStatusName.isEmpty()) {
	        predicates.add(cb.equal(work.get("workStatus"), workStatusName));
	    }
	    if (implementationAgency != null && !implementationAgency.isEmpty()) {
	        predicates.add(cb.equal(work.get("implementationAgency"), implementationAgency));
	    }

	    // Filter: Department Remarks
	    Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
	    if (dmRemarksPredicate != null) {
	        predicates.add(dmRemarksPredicate);
	    }

	    predicates.add(cb.equal(work.get("status"), "Active"));

	    Predicate financialSchemeWorkHeadCondition = cb.or(
	        cb.isNotNull(work.get("financialYear")),
	        cb.isNotNull(work.get("scheme")),
	        cb.isNotNull(work.get("workHead"))
	    );
	    
	    Predicate fundCondition = cb.or(
	        cb.isNotNull(work.get("fundByState")),
	        cb.isNotNull(work.get("fundByOthers")),
	        cb.isNotNull(work.get("fundByNhm")),
	        cb.isNotNull(work.get("fundByEcrp2"))
	    );
	    
	    Predicate additionalCondition = cb.or(financialSchemeWorkHeadCondition, fundCondition);
	    predicates.add(additionalCondition);

	    query.where(predicates.toArray(new Predicate[0]));

	    TypedQuery<Work> typedQuery = entityManager.createQuery(query);
	    int totalRows = typedQuery.getResultList().size();

	    typedQuery.setFirstResult((int) pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    List<Work> resultList = typedQuery.getResultList();

	    return new PageImpl<>(resultList, pageable, totalRows);
	}
	
	
	public Page<Work> fetchAllDeptDistrict(Pageable pageable, String workName, String workType,
	        String financialYear, String districtCode, String divisionName, String districtName, 
	        Integer workSubTypeIdInt, String workStatusName, String implementationAgency,
	        String workNameFilter, List<Long> blockId, String departmentRemark) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
	    }
	    
	    // Filter: workNameFilter (partial match on work name)
	    if (workNameFilter != null && !workNameFilter.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
	    }
	    
	    // Filter: blockId (multiple values)
	    if (blockId != null && !blockId.isEmpty()) {
	        predicates.add(work.get("blockId").in(blockId));
	    }
	    
	    if (workType != null && !workType.isEmpty()) {
	        predicates.add(cb.equal(work.get("workType"), workType));
	    }
	    if (financialYear != null && !financialYear.isEmpty()) {
	        predicates.add(cb.equal(work.get("financialYear"), financialYear));
	    }
	    if (districtCode != null && !districtCode.isEmpty()) {
	        predicates.add(cb.equal(work.get("districtCode"), districtCode));
	    }
	    if (divisionName != null && !divisionName.isEmpty()) {
	        predicates.add(cb.equal(work.get("divisionName"), divisionName));
	    }
	    if (districtName != null && !districtName.isEmpty()) {
	        predicates.add(cb.equal(work.get("districtName"), districtName));
	    }
	    if (workSubTypeIdInt != null) {
	        predicates.add(cb.equal(work.get("workSubtypeId"), workSubTypeIdInt));
	    }
	    if (workStatusName != null && !workStatusName.isEmpty()) {
	        predicates.add(cb.equal(work.get("workStatus"), workStatusName));
	    }
	    if (implementationAgency != null && !implementationAgency.isEmpty()) {
	        predicates.add(cb.equal(work.get("implementationAgency"), implementationAgency));
	    }

	    // Filter: Department Remarks
	    Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
	    if (dmRemarksPredicate != null) {
	        predicates.add(dmRemarksPredicate);
	    }

	    predicates.add(cb.equal(work.get("status"), "Active"));

	    Predicate financialSchemeWorkHeadCondition = cb.or(
	        cb.isNotNull(work.get("financialYear")),
	        cb.isNotNull(work.get("scheme")),
	        cb.isNotNull(work.get("workHead"))
	    );
	    
	    Predicate fundCondition = cb.or(
	        cb.isNotNull(work.get("fundByState")),
	        cb.isNotNull(work.get("fundByOthers")),
	        cb.isNotNull(work.get("fundByNhm")),
	        cb.isNotNull(work.get("fundByEcrp2"))
	    );
	    
	    Predicate additionalCondition = cb.or(financialSchemeWorkHeadCondition, fundCondition);
	    predicates.add(additionalCondition);

	    query.where(predicates.toArray(new Predicate[0]));

	    TypedQuery<Work> typedQuery = entityManager.createQuery(query);
	    int totalRows = typedQuery.getResultList().size();

	    typedQuery.setFirstResult((int) pageable.getOffset());
	    typedQuery.setMaxResults(pageable.getPageSize());

	    List<Work> resultList = typedQuery.getResultList();

	    return new PageImpl<>(resultList, pageable, totalRows);
	}

	
	public Page<Work> fetchAllWorksByDistrict(Pageable pageable, String workNo, List<Long> workTypeList,
		        List<Long> fyList, String districtCode, List<Long> agencyList, Long divisionIds, Long districtIds,
		        Integer workSubTypeIdInt, List<Long> statusList, List<Long> priorityList, List<Long> headList,
		        List<Long> vsList, String departmentRemark, List<Long> blockIdList, String workNameFilter) {

		    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		    // Main query
		    CriteriaQuery<Work> query = cb.createQuery(Work.class);
		    Root<Work> work = query.from(Work.class);

		    List<Predicate> predicates = new ArrayList<>();

		    // Filters
		    if (workNo != null && !workNo.isEmpty()) {
		        predicates.add(cb.like(cb.lower(work.get("workNo")), "%" + workNo.toLowerCase() + "%"));
		    }
		    
		    // Filter: workNameFilter (partial match on work name)
		    if (workNameFilter != null && !workNameFilter.isEmpty()) {
		        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
		    }
		    
		    if (workTypeList != null && !workTypeList.isEmpty()) {
		        predicates.add(work.get("workType").in(workTypeList));
		    }
		    if (fyList != null && !fyList.isEmpty()) {
		        predicates.add(work.get("financialYear").in(fyList));
		    }
		    if (districtCode != null && !districtCode.isEmpty()) {
		        predicates.add(cb.equal(work.get("districtCode"), districtCode));
		    }
		    if (agencyList != null && !agencyList.isEmpty()) {
		        predicates.add(work.get("implementationAgency").in(agencyList));
		    }
		    if (divisionIds != null) {
		        predicates.add(cb.equal(work.get("divisionId"), divisionIds));
		    }
		    if (districtIds != null) {
		        predicates.add(cb.equal(work.get("districtId"), districtIds));
		    }
		    if (workSubTypeIdInt != null) {
		        predicates.add(cb.equal(work.get("workSubtypeId"), workSubTypeIdInt));
		    }
		    if (statusList != null && !statusList.isEmpty()) {
		        predicates.add(work.get("workStatus").in(statusList));
		    }
		    if (priorityList != null && !priorityList.isEmpty()) {
		        predicates.add(work.get("workPriorityId").in(priorityList));
		    }
		    if (headList != null && !headList.isEmpty()) {
		        predicates.add(work.get("financialHeadId").in(headList));
		    }
		    if (vsList != null && !vsList.isEmpty()) {
		        predicates.add(work.get("vidhanSabhaId").in(vsList));
		    }

		    // Filter: blockId (multiple values)
		    if (blockIdList != null && !blockIdList.isEmpty()) {
		        predicates.add(work.get("blockId").in(blockIdList));
		    }

		    // Filter: Department Remarks
		    Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
		    if (dmRemarksPredicate != null) {
		        predicates.add(dmRemarksPredicate);
		    }

		    // Always Active
		    predicates.add(cb.equal(work.get("status"), "Active"));

		    query.where(predicates.toArray(new Predicate[0]));
		    query.orderBy(cb.desc(work.get("id"))); // sort by ID descending

		    // Execute main query with pagination
		    TypedQuery<Work> typedQuery = entityManager.createQuery(query);
		    typedQuery.setFirstResult((int) pageable.getOffset());
		    typedQuery.setMaxResults(pageable.getPageSize());
		    List<Work> resultList = typedQuery.getResultList();

		    // Count query
		    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		    Root<Work> countRoot = countQuery.from(Work.class);

		    List<Predicate> countPredicates = new ArrayList<>();
		    if (workNo != null && !workNo.isEmpty()) 
		        countPredicates.add(cb.like(cb.lower(countRoot.get("workNo")), "%" + workNo.toLowerCase() + "%"));
		    
		    // Filter: workNameFilter for count
		    if (workNameFilter != null && !workNameFilter.isEmpty()) {
		        countPredicates.add(cb.like(cb.lower(countRoot.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
		    }
		    
		    if (workTypeList != null && !workTypeList.isEmpty()) countPredicates.add(countRoot.get("workType").in(workTypeList));
		    if (fyList != null && !fyList.isEmpty()) countPredicates.add(countRoot.get("financialYear").in(fyList));
		    if (districtCode != null && !districtCode.isEmpty()) countPredicates.add(cb.equal(countRoot.get("districtCode"), districtCode));
		    if (agencyList != null && !agencyList.isEmpty()) countPredicates.add(countRoot.get("implementationAgency").in(agencyList));
		    if (divisionIds != null) countPredicates.add(cb.equal(countRoot.get("divisionId"), divisionIds));
		    if (districtIds != null) countPredicates.add(cb.equal(countRoot.get("districtId"), districtIds));
		    if (workSubTypeIdInt != null) countPredicates.add(cb.equal(countRoot.get("workSubtypeId"), workSubTypeIdInt));
		    if (statusList != null && !statusList.isEmpty()) countPredicates.add(countRoot.get("workStatus").in(statusList));
		    if (priorityList != null && !priorityList.isEmpty()) countPredicates.add(countRoot.get("workPriorityId").in(priorityList));
		    if (headList != null && !headList.isEmpty()) countPredicates.add(countRoot.get("financialHeadId").in(headList));
		    if (vsList != null && !vsList.isEmpty()) countPredicates.add(countRoot.get("vidhanSabhaId").in(vsList));

		    // Filter: blockId for count
		    if (blockIdList != null && !blockIdList.isEmpty()) {
		        countPredicates.add(countRoot.get("blockId").in(blockIdList));
		    }

		    // Filter: Department Remarks for count
		    Predicate dmRemarksCountPredicate = addDepartmentRemarksFilter(cb, countQuery, countRoot, departmentRemark);
		    if (dmRemarksCountPredicate != null) {
		        countPredicates.add(dmRemarksCountPredicate);
		    }

		    countPredicates.add(cb.equal(countRoot.get("status"), "Active"));

		    countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
		    Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

		    return new PageImpl<>(resultList, pageable, totalRows);
		}



	
	
	public Page<Work> fetchAllWorksByDivision(Pageable pageable, String workNo, List<Long> workTypeList,
		        List<Long> fyList, Long districtIds, List<Long> statusList, List<Long> agencyList,
		        String username, List<Long> priorityList, List<Long> headList, List<Long> vsList, Long divisionId, String departmentRemark, List<Long> blockIdList, String workNameFilter) {

		    CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		    // Main query
		    CriteriaQuery<Work> query = cb.createQuery(Work.class);
		    Root<Work> work = query.from(Work.class);

		    List<Predicate> predicates = new ArrayList<>();

		    // Filters
		    if (workNo != null && !workNo.isEmpty()) {
		        predicates.add(cb.like(cb.lower(work.get("workNo")), "%" + workNo.toLowerCase() + "%"));
		    }
		    
		    // Filter: workNameFilter (partial match on work name)
		    if (workNameFilter != null && !workNameFilter.isEmpty()) {
		        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
		    }
		    
		    if (workTypeList != null && !workTypeList.isEmpty()) {
		        predicates.add(work.get("workType").in(workTypeList));
		    }
		    if (fyList != null && !fyList.isEmpty()) {
		        predicates.add(work.get("financialYear").in(fyList));
		    }
		    if (districtIds != null) {
		        predicates.add(cb.equal(work.get("districtId"), districtIds));
		    }
		    if (statusList != null && !statusList.isEmpty()) {
		        predicates.add(work.get("workStatus").in(statusList));
		    }
		    if (agencyList != null && !agencyList.isEmpty()) {
		        predicates.add(work.get("implementationAgency").in(agencyList));
		    }
		    if (username != null && !username.isEmpty()) {
		        predicates.add(cb.equal(work.get("createdBy"), username));
		    }
		    if (priorityList != null && !priorityList.isEmpty()) {
		        predicates.add(work.get("workPriorityId").in(priorityList));
		    }
		    if (headList != null && !headList.isEmpty()) {
		        predicates.add(work.get("financialHeadId").in(headList));
		    }
		    if (vsList != null && !vsList.isEmpty()) {
		        predicates.add(work.get("vidhanSabhaId").in(vsList));
		    }
		    // Add division filter for ROLE_DEPARTMENT
		    if (divisionId != null) {
		        predicates.add(cb.equal(work.get("divisionCode"), divisionId));
		    }

		    // Filter: blockId (multiple values)
		    if (blockIdList != null && !blockIdList.isEmpty()) {
		        predicates.add(work.get("blockId").in(blockIdList));
		    }

		    // Filter: Department Remarks
		    Predicate dmRemarksPredicate = addDepartmentRemarksFilter(cb, query, work, departmentRemark);
		    if (dmRemarksPredicate != null) {
		        predicates.add(dmRemarksPredicate);
		    }

		    // Always Active
		    predicates.add(cb.equal(work.get("status"), "Active"));

		    query.where(predicates.toArray(new Predicate[0]));
		    query.orderBy(cb.desc(work.get("id"))); // sort by ID descending

		    // Execute main query with pagination
		    TypedQuery<Work> typedQuery = entityManager.createQuery(query);
		    typedQuery.setFirstResult((int) pageable.getOffset());
		    typedQuery.setMaxResults(pageable.getPageSize());
		    List<Work> resultList = typedQuery.getResultList();

		    // Count query
		    CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		    Root<Work> countRoot = countQuery.from(Work.class);

		    List<Predicate> countPredicates = new ArrayList<>();
		    if (workNo != null && !workNo.isEmpty()) 
		        countPredicates.add(cb.like(cb.lower(countRoot.get("workNo")), "%" + workNo.toLowerCase() + "%"));
		    
		    // Filter: workNameFilter for count
		    if (workNameFilter != null && !workNameFilter.isEmpty()) {
		        countPredicates.add(cb.like(cb.lower(countRoot.get("workName")), "%" + workNameFilter.toLowerCase() + "%"));
		    }
		    
		    if (workTypeList != null && !workTypeList.isEmpty()) countPredicates.add(countRoot.get("workType").in(workTypeList));
		    if (fyList != null && !fyList.isEmpty()) countPredicates.add(countRoot.get("financialYear").in(fyList));
		    if (districtIds != null) countPredicates.add(cb.equal(countRoot.get("districtId"), districtIds));
		    if (statusList != null && !statusList.isEmpty()) countPredicates.add(countRoot.get("workStatus").in(statusList));
		    if (agencyList != null && !agencyList.isEmpty()) countPredicates.add(countRoot.get("implementationAgency").in(agencyList));
		    if (username != null && !username.isEmpty()) countPredicates.add(cb.equal(countRoot.get("createdBy"), username));
		    if (priorityList != null && !priorityList.isEmpty()) countPredicates.add(countRoot.get("workPriorityId").in(priorityList));
		    if (headList != null && !headList.isEmpty()) countPredicates.add(countRoot.get("financialHeadId").in(headList));
		    if (vsList != null && !vsList.isEmpty()) countPredicates.add(countRoot.get("vidhanSabhaId").in(vsList));
		    // Add division filter for ROLE_DEPARTMENT
		    if (divisionId != null) {
		        countPredicates.add(cb.equal(countRoot.get("divisionCode"), divisionId));
		    }

		    // Filter: blockId for count
		    if (blockIdList != null && !blockIdList.isEmpty()) {
		        countPredicates.add(countRoot.get("blockId").in(blockIdList));
		    }

		    // Filter: Department Remarks for count
		    Predicate dmRemarksCountPredicate = addDepartmentRemarksFilter(cb, countQuery, countRoot, departmentRemark);
		    if (dmRemarksCountPredicate != null) {
		        countPredicates.add(dmRemarksCountPredicate);
		    }

		    countPredicates.add(cb.equal(countRoot.get("status"), "Active"));

		    countQuery.select(cb.count(countRoot)).where(countPredicates.toArray(new Predicate[0]));
		    Long totalRows = entityManager.createQuery(countQuery).getSingleResult();

		    return new PageImpl<>(resultList, pageable, totalRows);
		}




	
	

	// Helper method to add department filter (by department master ID via DepartmentRemarks)
	private Predicate addDepartmentFilter(CriteriaBuilder cb, CriteriaQuery<?> query, Root<Work> work, List<Long> departmentList) {
		if (departmentList != null && !departmentList.isEmpty()) {
			Subquery<Long> deptSubquery = query.subquery(Long.class);
			Root<DepartmentRemarks> deptRoot = deptSubquery.from(DepartmentRemarks.class);
			deptSubquery.select(deptRoot.get("workId"))
				.where(
					cb.and(
						deptRoot.get("depertmentMasterId").in(departmentList),
						cb.equal(deptRoot.get("enabled"), (short) 1)
					)
				);
			return work.get("id").in(deptSubquery);
		}
		return null;
	}

	// Helper method to add department remarks filter
	private Predicate addDepartmentRemarksFilter(CriteriaBuilder cb, CriteriaQuery<?> query, Root<Work> work, String departmentRemark) {
		if (departmentRemark != null && !departmentRemark.isEmpty() && !departmentRemark.equals("")) {
			// departmentRemark now contains comma-separated department master IDs
			String[] deptIds = departmentRemark.split(",");
			List<Long> deptIdList = new ArrayList<>();
			for (String id : deptIds) {
				String trimmedId = id.trim();
				if (!trimmedId.isEmpty()) {
					try {
						deptIdList.add(Long.parseLong(trimmedId));
					} catch (NumberFormatException e) {
						// Skip invalid IDs
					}
				}
			}
			
			if (!deptIdList.isEmpty()) {
				Subquery<Long> departmentRemarksSubquery = query.subquery(Long.class);
				Root<DepartmentRemarks> departmentRemarksRoot = departmentRemarksSubquery.from(DepartmentRemarks.class);
				departmentRemarksSubquery.select(departmentRemarksRoot.get("workId"))
					.where(
						cb.and(
							departmentRemarksRoot.get("depertmentMasterId").in(deptIdList),
							cb.equal(departmentRemarksRoot.get("enabled"), (short) 1)
						)
					);
				return work.get("id").in(departmentRemarksSubquery);
			}
		}
		return null;
	}

	private Predicate addBlockNameFilter(CriteriaBuilder cb, CriteriaQuery<?> query, Root<Work> work, String blockNameFilter) {
		if (blockNameFilter != null && !blockNameFilter.isEmpty()) {
			Subquery<Long> blockSubquery = query.subquery(Long.class);
			Root<Block> blockRoot = blockSubquery.from(Block.class);
			blockSubquery.select(blockRoot.get("blockId"))
				.where(cb.like(cb.lower(blockRoot.get("blockName")), "%" + blockNameFilter.toLowerCase() + "%"));
			return work.get("blockId").in(blockSubquery);
		}
		return null;
	}
		
}

