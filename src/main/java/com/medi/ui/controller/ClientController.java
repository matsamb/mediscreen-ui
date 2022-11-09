package com.medi.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medi.ui.bean.PatientBean;
import com.medi.ui.proxies.PatientProxy;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ClientController {

	@Autowired
	private final PatientProxy patientProxy;
	
	ClientController(PatientProxy patientProxy){
		this.patientProxy = patientProxy;
	}
	
	@GetMapping("/home")
	public String getHome() {
		String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());
		return "home";
	}
}
