package com.medi.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.medi.ui.bean.UiUserDetails;
import com.medi.ui.proxies.UiUserDetailsRepository;

@SpringBootApplication
//@EnableDiscoveryClient
@EnableFeignClients("com.medi.ui")
public class UiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(UiApplication.class, args);
	}
	
	@Autowired
	private UiUserDetailsRepository uiUserDetailsRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		
		
		UiUserDetails admin = new UiUserDetails("admin@ui.com");
		admin.setUsername("admin@ui.com");
		String adminpass = passwordEncoder.encode("admin");
		System.out.println(adminpass);
		admin.setPassword(adminpass);
		admin.setEnabled(true);
		admin.setLocked(false);
		admin.setRoles("ROLE_ADMIN");	
		
		
		UiUserDetails user = new UiUserDetails("user@ui.com");
		user.setUsername("user@ui.com");
		String userpass = passwordEncoder.encode("user");
		System.out.println(userpass);
		user.setPassword(userpass);
		user.setEnabled(true);
		user.setLocked(false);
		user.setRoles("ROLE_USER");

		uiUserDetailsRepository.save(admin);
		uiUserDetailsRepository.save(user);
		
	}

}
