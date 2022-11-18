package com.medi.ui.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.medi.ui.bean.PatientBean;
import com.medi.ui.bean.ReportBean;
import com.medi.ui.proxies.PatientProxy;
import com.medi.ui.proxies.ReportProxy;
import com.medi.ui.service.DiagnosisService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class MediscreenRestController {

	@Autowired
	private final PatientProxy patientProxy;

	@Autowired
	private final ReportProxy reportProxy;

	@Autowired
	private DiagnosisService diagnosysService;

	MediscreenRestController(PatientProxy patientProxy, ReportProxy reportProxy, DiagnosisService diagnosysService) {
		this.diagnosysService = diagnosysService;
		this.patientProxy = patientProxy;
		this.reportProxy = reportProxy;
	}

/*	@GetMapping("/assess/{id}")
	public String getDiagnosysId(@PathVariable Integer id) {
		log.info("/assess/" + id);

		
		
		Map<String, Integer> diagnosys = diagnosysService.status(patient, report);

		log.info("diagnosys :" + diagnosys);
		String status = diagnosys.keySet().stream().collect(Collectors.toList()).get(0);

		return ResponseEntity.ok("Patient : " + familyName + " " + familyName + " (" + diagnosys.get(status)
				+ ") diabetes assessment is: " + status);

	}*/

	@GetMapping("/assess/{familyName}")
	public ResponseEntity<String> getDiagnosysFamilyName(@PathVariable String familyName) {
		log.info("/assess/" + familyName);

		PatientBean patient = patientProxy.findPatient(familyName, familyName).getBody();
		List<ReportBean> report = reportProxy.findReportByPatientId(patient.getPatientId()).getBody();

		Map<String, Integer> diagnosys = diagnosysService.status(patient, report);

		log.info("diagnosys :" + diagnosys);
		String status = diagnosys.keySet().stream().collect(Collectors.toList()).get(0);

		return ResponseEntity.ok("Patient : " + familyName + " " + familyName + " (" + diagnosys.get(status)
				+ ") diabetes assessment is: " + status);
	}

}