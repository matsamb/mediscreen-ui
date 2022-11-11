package com.medi.ui.proxies;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medi.ui.bean.PatientBean;

//import feign.RequestTemplate;
/*
@Repository
//@FeignClient(name="patient", url="localhost:9004")
public interface PatientProxy {

	@GetMapping("/patient")
	ResponseEntity<PatientBean> findPatient(@RequestParam("family") String family, @RequestParam("given") String given);
	
	
}*/
