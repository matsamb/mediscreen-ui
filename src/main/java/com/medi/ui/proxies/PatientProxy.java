package com.medi.ui.proxies;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.medi.ui.bean.PatientBean;

import feign.Headers;
import feign.RequestTemplate;
import feign.form.ContentType;

@Repository
@FeignClient(name="patient", url="localhost:9004")
public interface PatientProxy {
	
	@GetMapping("/patient")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<PatientBean> findPatient(@RequestParam("family") String family, @RequestParam("given") String given);
	
	@PostMapping("/patient/add")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<PatientBean> createPatient(PatientBean patientBean);
	
	@PutMapping("/patient")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<PatientBean> updatePatient(@RequestParam("family") String family, @RequestParam("given") String given, PatientBean patientBean);
	
	@DeleteMapping("/patient")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<PatientBean> deletePatient(@RequestParam("family") String family, @RequestParam("given") String given);

}
