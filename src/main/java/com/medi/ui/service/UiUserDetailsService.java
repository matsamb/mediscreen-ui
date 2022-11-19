package com.medi.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.medi.ui.bean.UiUserDetails;
import com.medi.ui.proxies.UiUserDetailsRepository;

public class UiUserDetailsService implements UserDetailsService{

	@Autowired
	private UiUserDetailsRepository uiUserDetailsRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UiUserDetails loadedUiUserDetails = uiUserDetailsRepository.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException(username+" not found"));

if(loadedUiUserDetails.getEnabled()==true) {
UserDetails currentUser = User.withUsername(loadedUiUserDetails.getUsername())
	.password(loadedUiUserDetails.getPassword())
	.authorities(new SimpleGrantedAuthority(loadedUiUserDetails.getRoles()))
	.build();

return currentUser;
}

return new UiUserDetails();	
	}

}
