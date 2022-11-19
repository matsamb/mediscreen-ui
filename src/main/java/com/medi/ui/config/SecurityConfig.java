package com.medi.ui.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.medi.ui.service.UiUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Bean
	public UiUserDetailsService uiUserDetailsService() {
		return new UiUserDetailsService();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.csrf().disable()
			.authorizeRequests()
			.antMatchers("/admin").hasAnyRole("ADMIN")
			.antMatchers("/user").hasAnyRole("ADMIN", "USER")
			.antMatchers("/h2-console/**").permitAll()
			.anyRequest().authenticated()
			.and()
			.sessionManagement()
			.and()
			.formLogin()
			.defaultSuccessUrl("/home")
			//.and()
			//.oauth2Login()
			.and()
			.httpBasic()
			.and()
			.exceptionHandling().accessDeniedPage("/forbidden")
			.and()
			.logout()
			;

		return httpSecurity.build();

	}

	@Bean
	public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {

		return httpSecurity
				.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(uiUserDetailsService())
				.passwordEncoder(passwordEncoder)
				.and()
				.build()
				;

	}
	
}
