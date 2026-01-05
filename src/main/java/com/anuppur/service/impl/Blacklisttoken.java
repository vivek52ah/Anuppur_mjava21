package com.anuppur.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anuppur.entity.BLacklistotken;
import com.anuppur.repository.BLacklistotkenRepository;
import com.anuppur.util.JwtUtil;

@Service
public class Blacklisttoken {

	@Autowired
	private BLacklistotkenRepository   blacklistTokenRepository;
	
	@Autowired
	private JwtUtil jwt;
	
	
	public void blacklistToken(String token) {
		
        if (!blacklistTokenRepository.existsByToken(token) ) {
        	BLacklistotken blacklistToken = new BLacklistotken();
            blacklistToken.setToken(token);
            blacklistToken.setBlacklistedAt(LocalDateTime.now().toString());
          
            blacklistTokenRepository.save(blacklistToken);
        }
    }

    public boolean isTokenBlacklisted(String token) {
        return blacklistTokenRepository.existsByToken(token);
    }
	
    
	
}
