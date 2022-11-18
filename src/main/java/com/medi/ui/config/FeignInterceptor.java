package com.medi.ui.config;

import org.aspectj.weaver.ast.Instanceof;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;

@Component
public class FeignInterceptor implements RequestInterceptor{

	
	@Override
	public void apply(RequestTemplate template) {
		Authentication authentication = SecurityContextHolder
				.getContext().getAuthentication();
		
		if(authentication == null) {
			template.header("Authentication","Not_Authenticated");
		}else {
			if(authentication instanceof OAuth2AuthenticationToken) {
				OAuth2AuthenticationToken userAuthenticationDetails = 
					(OAuth2AuthenticationToken)authentication.getDetails();
				template.header("Authentication", "bearer");
			}else {
			WebAuthenticationDetails userAuthenticationDetails =
					(WebAuthenticationDetails)authentication.getDetails();
				template.header("Authentication", "bearer");
			}
		}
		
	}

	
	
}
