package com.medi.ui.controller;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.medi.ui.bean.PatientBean;
import com.medi.ui.bean.PatientBeanView;
import com.medi.ui.bean.ReportBean;
import com.medi.ui.proxies.PatientProxy;
import com.medi.ui.proxies.ReportProxy;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class UserInterfaceController {

	@Autowired
	private final PatientProxy patientProxy;
		
	@Autowired
	private final ReportProxy reportProxy;

	UserInterfaceController(PatientProxy patientProxy
			, ReportProxy reportProxy){
		this.patientProxy = patientProxy;
		this.reportProxy = reportProxy;
	}

	@GetMapping("/home")
	public String getHome() {
		log.info("getHome");
		return "home";
	} 
	
	@GetMapping("/save")
	public String getSave() {
		return "save";
	}
	
	@GetMapping("/savepatient")
	public String getSavePatient(Model model) {
		log.info("savepatient get");
		
		return "savepatient";

	}

	@PostMapping("/savepatient")
	public ModelAndView savePatient(PatientBeanView patientBeanView) {
		log.info("savepatient post");
		//log.info(patientBean);
		PatientBean patientBean = new PatientBean(patientBeanView);
		log.info(patientBean);
		PatientBean savePatient = patientProxy.createPatient(patientBean).getBody();
		log.info(savePatient);
		return new ModelAndView("redirect:/viewpatient?family="+savePatient.getFamily()+"&given="+savePatient.getGiven());

	} 

	@GetMapping("/updatepatient") 
	public String updatePatient(@RequestParam String patientId, @RequestParam String family, @RequestParam String given , Model model) {

		log.info("updatepatient get");
		model.addAttribute("family",family);
		model.addAttribute("given",given); 
		model.addAttribute("patientId",patientId);
		
		return "updatepatient";
	}

	@PostMapping("/updatepatient")
	public ModelAndView getUpdatePatient(PatientBeanView patientBeanView) {

		log.info("updatepatient post");
		PatientBean patientBean = new PatientBean(patientBeanView);
		patientProxy.updatePatient(patientBeanView.getFamily(), patientBeanView.getGiven(), patientBean);
		log.info(patientBeanView);
		return new ModelAndView("redirect:/save");
	}

	@GetMapping("/deletepatient")
	public String getDeletePatient(@RequestParam String patientId, @RequestParam String family, @RequestParam String given, @RequestParam String dob,Model model) {
		log.info("deletepatient get "+ family +" "+given );

		model.addAttribute("family",family);
		model.addAttribute("given",given); 
		model.addAttribute("dob",dob);
		model.addAttribute("patientId",patientId);
		
		return "deletepatient";
	}

	@PostMapping("/deletepatient")
	public ModelAndView deletePatient(String patientId, String family, String given) {
	
		log.info("deletepatient post");
//		ResponseEntity<PatientBean> patientResponse = patientProxy.findPatient(family, given);
//		PatientBean patientToDelete = patientResponse.getBody();
		Integer integerPatientId = Integer.parseInt(patientId); 
		ReportBean deleteAllReport = new ReportBean();
		deleteAllReport.setId("deleteAll");
		deleteAllReport.setComment("deletePatient");
		reportProxy.deleteReportByPatientId(integerPatientId,deleteAllReport);
		patientProxy.deletePatient(family, given);
		log.info("Patient "+family+" deleted");
		return new ModelAndView("redirect:/save");
	}

	@GetMapping("/findpatient")
	public String getFindPatient(Model model) {
		return "findpatient";
	}

	@PostMapping("/findpatient")
	public ModelAndView findPatient(String family, String given) {
		log.info(family);
		return new ModelAndView("redirect:/viewpatient?family="+family+"&given="+given);
	}

	@GetMapping("/viewpatient")
	public String getViewPatient(@RequestParam String family, @RequestParam String given, Model model) {

		log.info(patientProxy.findPatient(family, given));

		PatientBean patient = patientProxy.findPatient(family, given).getBody();

		model.addAttribute("patient", patient);
		
		return "viewpatient";
	}
	
	@GetMapping("/findreport")
	public String getFindReport() {
		return "findreport";
	}
	
	@GetMapping("/viewreport")
	public String getViewReport() {
		return "viewreport";
	}
	
	@GetMapping("savereport")
	public String getSaveReport() {
		return "savereport";
	}

}
