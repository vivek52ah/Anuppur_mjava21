package com.anuppur.service.impl;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.Work;

@Repository
public class WorkRepositoryCustomImpl {

    @PersistenceContext
    private EntityManager entityManager;

    
    public Page<Work> findAllByDynamicFilters(Pageable pageable, String workName, String workType, 
                                              String financialYear, String divisionName, String districtName, 
                                              Integer workSubTypeIdInt, String workStatusName, 
                                              String implementationAgency) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Work> query = cb.createQuery(Work.class);
        Root<Work> work = query.from(Work.class);
        
        List<Predicate> predicates = new ArrayList<>();

        // Only add conditions if parameters are not null or empty
        if (workName != null && !workName.isEmpty()) {
            predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
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


	public Page<Work> findAllByStatusNotDeleted(Pageable pageable, String workName, String workType,
			String financialYear, String divisionName, String districtName, Integer workSubTypeIdInt,
			String workStatusName, String implementationAgency) {
		 CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	        CriteriaQuery<Work> query = cb.createQuery(Work.class);
	        Root<Work> work = query.from(Work.class);
	        
	        List<Predicate> predicates = new ArrayList<>();

	        // Only add conditions if parameters are not null or empty
	        if (workName != null && !workName.isEmpty()) {
	            predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
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
	
	public Page<Work> fetchAllWorksByAgency(Pageable pageable, String workName, String workType,
	        String financialYear, String agency, String divisionName, String districtName, 
	        String workStatusName, Integer workSubTypeIdInt, String implementationAgency) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
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
	        Integer workSubTypeIdInt, String workStatusName, String implementationAgency) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
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

	
	public Page<Work> fetchAllWorksByDistrict(Pageable pageable, String workName, String workType,
	        String financialYear, String districtCode, String agency, String divisionName, 
	        String districtName, Integer workSubTypeIdInt, String workStatusName, 
	        String implementationAgency) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
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

	public Page<Work> fetchAllWorksByDivision(Pageable pageable, String workName, String workType,
	        String financialYear, Long divisionCode, String divisionName, String districtName, 
	        Integer workSubTypeIdInt, String workStatusName, String implementationAgency) {
	    
	    CriteriaBuilder cb = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Work> query = cb.createQuery(Work.class);
	    Root<Work> work = query.from(Work.class);
	    
	    List<Predicate> predicates = new ArrayList<>();

	    if (workName != null && !workName.isEmpty()) {
	        predicates.add(cb.like(cb.lower(work.get("workName")), "%" + workName.toLowerCase() + "%"));
	    }
	    if (workType != null && !workType.isEmpty()) {
	        predicates.add(cb.equal(work.get("workType"), workType));
	    }
	    if (financialYear != null && !financialYear.isEmpty()) {
	        predicates.add(cb.equal(work.get("financialYear"), financialYear));
	    }
	    if (divisionCode != null ) {
	        predicates.add(cb.equal(work.get("divisionCode"), divisionCode));
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

		
}
