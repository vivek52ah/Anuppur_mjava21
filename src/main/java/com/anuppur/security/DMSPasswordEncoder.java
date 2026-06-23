package com.anuppur.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import com.anuppur.exception.DMSBusinessException;
import com.anuppur.util.SHAHashingUtil;

public class DMSPasswordEncoder implements PasswordEncoder {

	private final BCryptPasswordEncoder delegate = new BCryptPasswordEncoder();

	@Override
	public String encode(CharSequence rawPassword) {
		return delegate.encode(toSha256(rawPassword));
	}

	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		if (!StringUtils.hasText(encodedPassword) || rawPassword == null) {
			return false;
		}

		String raw = rawPassword.toString();
		return delegate.matches(raw, encodedPassword)
				|| delegate.matches(toSha256(raw), encodedPassword);
	}

	private String toSha256(CharSequence rawPassword) {
		try {
			return SHAHashingUtil.encryptPassword(rawPassword.toString()).toString();
		} catch (DMSBusinessException e) {
			throw new IllegalArgumentException("Unable to hash password.", e);
		}
	}
}
