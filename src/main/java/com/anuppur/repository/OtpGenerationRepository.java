package com.anuppur.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anuppur.entity.OtpGeneration;

public interface OtpGenerationRepository extends
		JpaRepository<OtpGeneration, String> {

}
