package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.anuppur.entity.Schemes;
import com.anuppur.entity.WorkStatus;
import com.anuppur.entity.WorkType;

public interface WorkStatusRepository  extends JpaRepository<WorkStatus, Long> {
	
	@Query("select distinct workStatusNameE from WorkStatus w where w.enabled = (:isEnabled) AND w.workType.workTypeId IS NOT NULL")
	List<String> findDistinctWorkStatusByEnabled(@Param("isEnabled")Short isEnabled);
	
	WorkStatus findByWorkStatusNameEAndWorkType(String workStatusName, WorkType workType);

	List<WorkStatus> findByWorkTypeAndEnabled(WorkType workType, Short enabled);

	List<WorkStatus> findByEnabled(Short enabled);
	
	List<WorkStatus> findByEnabledAndFlag(Short isEnabled, Long flag);

	List<WorkStatus> findBySchemesAndEnabled(Schemes findOne, Short enabled);
	
	@Query("select distinct workStatusNameE from WorkStatus w where w.enabled = (:isEnabled) AND w.schemes.id=8")
	List<String> findDistinctWorkStatusBySchemeEnabled(@Param("isEnabled")Short isEnabled);

	WorkStatus findByWorkStatusNameEAndSchemes(String workStatus, Schemes findBySchemeNameAndEnabled);
	
	WorkStatus findByWorkStatusNameE(String statusName);

	List<WorkStatus> findByWorkStatusNameEOrderByIdAsc(String statusName);
}
