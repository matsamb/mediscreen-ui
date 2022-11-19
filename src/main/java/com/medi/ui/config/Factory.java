package com.medi.ui.config;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Bean;
//import org.apache.catalina.core.ApplicationContext;
//import org.apache.catalina.core.StandardContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import feign.Response;

@Configuration
public class Factory {

	@Bean
	public PasswordEncoder passwordEncoder() {
		String currentEncoder = "Bcrypt";

		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put(currentEncoder, new BCryptPasswordEncoder());

		return encoders.get(currentEncoder);

	}

}
