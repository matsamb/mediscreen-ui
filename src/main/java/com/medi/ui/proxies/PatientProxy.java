package com.medi.ui.proxies;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medi.ui.bean.PatientBean;

@FeignClient(name="patient", url="localhost:9004")
public interface PatientProxy {

	@GetMapping("/patient")
	ResponseEntity<PatientBean> findPatient(@RequestParam("family") String family, @RequestParam("given") String given);
	
}
