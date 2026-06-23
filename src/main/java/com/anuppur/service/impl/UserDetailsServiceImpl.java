package com.anuppur.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.anuppur.constants.DMSConstants;
import com.anuppur.entity.Role;
import com.anuppur.entity.Users;
import com.anuppur.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    
	private boolean isCaptchaSuccess;
	
	@Autowired
    private UserRepository userRepository;
	
	@Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		/*if (!isCaptchaSuccess()) {

			throw new AuthenticationServiceException("Wrong captcha text!");
		}*/
        //Users user = userRepository.findByUsernameAndStatus(username, DMFConstants.STATUS_ACTIVE);//Only active users allowed
        
        Users user = userRepository.findByUsernameAndStatusNot(username, DMSConstants.STATUS_DELETED);
        if (user == null) {
        	user = userRepository.findByEmailIdAndStatusNot(username, DMSConstants.STATUS_DELETED);
        }
        //Users user=userRepository.findByUsernameAndStatusNotContainingIgnoreCase(username, DMSConstants.STATUS_DELETED);
        
        if (user == null) {
        	//throw new AuthenticationServiceException("Invalid user name or password!");
        	throw new UsernameNotFoundException("Invalid user name or password!");
        }else if(!DMSConstants.STATUS_ACTIVE.equals(user.getStatus())){
        	throw new AuthenticationServiceException("User is not activated. Please contact admin!");
        }else if(user != null) {
        	if (!username.equals(user.getUsername()) && !username.equals(user.getEmailId())) {
        	
        		throw new UsernameNotFoundException("Invalid user details!");
        	}
        }
        
        
        List<GrantedAuthority> grantedAuthorities = buildUserAuthority(user
				.getRoles());

        return buildUserForAuthentication(user, grantedAuthorities);
    }
	
	/**
	 * builds user authority.
	 * 
	 * @param userRoles
	 *            user roles
	 * @return list of granted authority.
	 */
	private List<GrantedAuthority> buildUserAuthority(Set<Role> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		for (Role userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getRoleCode()));
		}

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(
				setAuths);

		return result;
	}
	
	/**
	 * builds user for authentication.
	 * 
	 * @param user
	 *            user.
	 * @param authorities
	 *            authorities.
	 * @return user.
	 */
	private User buildUserForAuthentication(Users user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUsername(), user.getPassword(), true, true, true,
				true, authorities);
	}
	
	public boolean isCaptchaSuccess() {
		return isCaptchaSuccess;
	}

	public void setCaptchaSuccess(boolean isCaptchaSuccess) {
		this.isCaptchaSuccess = isCaptchaSuccess;
	}

}
