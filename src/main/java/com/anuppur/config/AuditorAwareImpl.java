package com.anuppur.config;

import org.springframework.data.domain.AuditorAware;

import com.anuppur.util.DMSUtil;

public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public String getCurrentAuditor() {
		
		String username = null;
		
		if(DMSUtil.getUserDetail()!=null)
			username = DMSUtil.getUserDetail().getUsername();
		else
			username = "";
		
		return username;
	}

}
