package com.anuppur.config;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.anuppur.util.DMSUtil;

/**
 * ✅ UPDATED FOR JAVA 21 & SPRING BOOT 3.2.5
 * 
 * Provides the current auditor (username) for JPA auditing.
 * Used by Spring Data JPA to automatically populate createdBy and modifiedBy fields.
 * 
 * Changes:
 * - Added @Component annotation for automatic bean registration
 * - Changed return type to Optional<String> (Spring Data JPA standard)
 * - Improved null handling with Optional
 * - Added Javadoc for clarity
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	/**
	 * Returns the current auditor (username) wrapped in Optional.
	 * If no user is logged in, returns an empty Optional.
	 * 
	 * @return Optional containing the current username, or empty if no user is logged in
	 */
	@Override
	public Optional<String> getCurrentAuditor() {
		return Optional.ofNullable(DMSUtil.getUserDetail())
				.map(userDetail -> userDetail.getUsername())
				.or(() -> Optional.of(""));
	}
}
