package com.medi.ui.proxies;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.medi.ui.bean.UiUserDetails;

public interface UiUserDetailsRepository extends JpaRepository<UiUserDetails, String>{

	Optional<UiUserDetails> findByUsername(String username);

}
