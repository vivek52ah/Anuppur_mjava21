package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.anuppur.entity.BLacklistotken;

@Repository
public interface BLacklistotkenRepository  extends JpaRepository<BLacklistotken, Long>{

	boolean existsByToken(String token);

}
