package com.anuppur.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.WorkStatus;
import com.anuppur.entity.WorkSubStatus;

@Repository
public interface WorkSubStatusRepository extends JpaRepository<WorkSubStatus, Long> {
	
	
	List<WorkSubStatus> findByWorkSubStatusId(String enabled);
	
	List<WorkSubStatus> findByEnabled(Short enabled);
	
	List<WorkSubStatus> findByEnabledAndWorkStatusId(Short isEnabled, Integer workStatusId );

	WorkSubStatus findByWorkSubStatusId(Integer workSubStatusId);

	WorkSubStatus findByWorkSubStatusId(Long workSubStatusId);

	//List<WorkSubStatus> findByEnabledAndWorkStatusIdAndWorkSubTypeId(Short enabled, Long workSubStatusid, Integer workStatusId);

	List<WorkSubStatus> findByEnabledAndWorkSubTypeIdAndWorkStatusId(Short enabled, Long workSubStatusid, Integer workStatusId);

	@Query(value = "SELECT * FROM mst_work_sub_status w " +
            "GROUP BY w.work_sub_status_name_e",
    nativeQuery = true)
List<WorkSubStatus> findAllDistinctWorkSubStatusNames();


}
