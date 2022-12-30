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
import com.medi.ui.service.DiagnosysService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class MediscreenRestController {

	@Autowired
	private final PatientProxy patientProxy;

	@Autowired
	private final ReportProxy reportProxy;

	@Autowired
	private DiagnosysService diagnosysService;

	MediscreenRestController(PatientProxy patientProxy, ReportProxy reportProxy, DiagnosysService diagnosysService) {
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

	@GetMapping("/assess/{family}/{name}")
	public ResponseEntity<String> getDiagnosysFamilyName(@PathVariable String family, @PathVariable String name) {
		log.info("/assess/" + family + name);

		PatientBean patient = patientProxy.findPatient(family,name).getBody();
		List<ReportBean> report = reportProxy.findReportByPatientId(patient.getPatientId()).getBody();

		Map<String, Integer> diagnosys = diagnosysService.status(patient, report);

		log.info("diagnosys :" + diagnosys);
		String status = diagnosys.keySet().stream().collect(Collectors.toList()).get(0);

		return ResponseEntity.ok("Patient : " + family + " " + name + " (" + diagnosys.get(status)
				+ ") diabetes assessment is: " + status);
	}

}
