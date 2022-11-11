package com.medi.ui.controller;

import java.time.LocalDate;

import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.AsyncRestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.medi.ui.bean.PatientBean;
//import com.medi.ui.proxies.PatientProxy;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class ClientController {

	
/*	@Autowired
	private final PatientProxy patientProxy;
	
	ClientController(PatientProxy patientProxy
			){
		this.patientProxy = patientProxy;
	}*/
	
	@GetMapping("/home")
	public String getHome() {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		return "home";
	}
	
	@GetMapping("/savepatient")
	public String getSavePatient(Model model) {
		return "savepatient";
		
	}
	
	@PostMapping("/savepatient")
	public ModelAndView savePatient( String given, Model model, BindingResult bindingResult) {
		log.info(given);
		return new ModelAndView("redirect:/patient");
		
	}
	
	@GetMapping("/patient")
	public String getPatient(Model model) {
		
		return "patient";
		
	}
	
	@GetMapping("/savereport")
	public String getSaveReport(Model model) {
		return "savereport";
		
	}
	
	@PostMapping("/savereport")
	public ModelAndView saveReport( String given/*, Model model, BindingResult bindingResult*/) {
		log.info(given);
		return new ModelAndView("redirect:/patient");
		
	}
	
	@GetMapping("/viewReport")
	public String getViewReport(Model model) {

		String family = "fraxe";
		String given = "max";
		LocalDate dob = LocalDate.now();
		String report = "hallo world report ready for patient mateman"; 
		 model.addAttribute("report",report);
		 model.addAttribute("family",family);
		 model.addAttribute("given",given);
		 model.addAttribute("dob",dob);
		return "viewReport";
	}
	
	@GetMapping("/findpatient")
	public String getFindPatient(Model model) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		//log.info(family);
		return "findpatient";
	}
	
	@PostMapping("/findpatient")
	public ModelAndView findPatient(String family) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		log.info(family);
		return new ModelAndView("redirect:/viewpatient");
	}
	
	@GetMapping("/viewpatient")
	public String getViewPatient(Model model) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		
		//log.info(family);
		PatientBean patient = new PatientBean();
		patient.setFamily("mama");
		patient.setSex("M");
		model.addAttribute("patient", patient);
		return "viewpatient";
	}
	
	
	@GetMapping("/findreport")
	public String getFindReport(Model model) {
		
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		//log.info(family);
		return "findreport";
	}
	
	@PostMapping("/findreport")
	public ModelAndView findReport(String family) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		log.info(family);
		return new ModelAndView("redirect:/home");
	}
	
	@GetMapping("/save")
	public String getSave(/*Model model*/) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		//log.info(family);
		return "save";
	}
	
	@GetMapping("/updatepatient")
	public String updatePatient(Model model) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		//log.info(family);
		return "updatepatient";
	}
	
	@PostMapping("/updatepatient")
	public ModelAndView getUpdatePatient(String family) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		log.info(family);
		return new ModelAndView("redirect:/save");
	}
	
	@GetMapping("/deletepatient")
	public String getDeletePatient(Model model) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		//log.info(family);
		return "deletepatient";
	}
	
	@PostMapping("/deletepatient")
	public ModelAndView deletePatient(String family) {
	/*	String family = "TestNone";
		String given = "Test";
		ResponseEntity<PatientBean> patient = patientProxy.findPatient(family, given);
		log.info(patient);
		log.info(patient.getBody());*/
		log.info(family);
		return new ModelAndView("redirect:/save");
	}

}
