package com.medi.ui.controller;

import java.net.http.HttpResponse;
import java.sql.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
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

import feign.RequestTemplate;
import feign.Response;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class UserInterfaceController {

	@Autowired
	private final PatientProxy patientProxy;

	@Autowired
	private final ReportProxy reportProxy;

//	@Autowired
//	private HttpResponse response;

	UserInterfaceController(PatientProxy patientProxy, ReportProxy reportProxy) {
		this.patientProxy = patientProxy;
		this.reportProxy = reportProxy;
	}

	@GetMapping("/home")
	public String getHome() {
		log.info("getHome");
		return "home";
	}

	@GetMapping("/error")
	public String getError() {
		log.info("error ");
		return "error";
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
		// log.info(patientBean);
		PatientBean patientBean = new PatientBean(patientBeanView);
		PatientBean savePatient = new PatientBean();
		log.info(patientBean);
		if (patientProxy.createPatient(patientBean).getStatusCode() == HttpStatus.NOT_FOUND
				|| patientProxy.createPatient(patientBean).getStatusCode() == HttpStatus.BAD_REQUEST) {
			log.info("Not found");
		} else {
			log.info("Found");
			savePatient = patientProxy.createPatient(patientBean).getBody();
			log.info(savePatient);
		}

		return new ModelAndView(
				"redirect:/viewpatient?family=" + savePatient.getFamily() + "&given=" + savePatient.getGiven());

	}

	@GetMapping("/updatepatient")
	public String updatePatient(@RequestParam String patientId, @RequestParam String family, @RequestParam String given,
			Model model) {

		log.info("updatepatient get");
		model.addAttribute("family", family);
		model.addAttribute("given", given);
		model.addAttribute("patientId", patientId);

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
	public String getDeletePatient(@RequestParam String patientId, @RequestParam String family,
			@RequestParam String given, @RequestParam String dob, Model model) {
		log.info("deletepatient get " + family + " " + given);

		model.addAttribute("family", family);
		model.addAttribute("given", given);
		model.addAttribute("dob", dob);
		model.addAttribute("patientId", patientId);

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
		reportProxy.deleteReportByPatientId(integerPatientId, deleteAllReport);
		patientProxy.deletePatient(family, given);
		log.info("Patient " + family + " deleted");
		return new ModelAndView("redirect:/save");
	}

	@GetMapping("/findpatient")
	public String getFindPatient(Model model) {
		return "findpatient";
	}

	@PostMapping("/findpatient")
	public ModelAndView findPatient(String family, String given) {
		log.info(family);
		return new ModelAndView("redirect:/viewpatient?family=" + family + "&given=" + given);
	}

	@GetMapping("/viewpatient")
	public String getViewPatient(@RequestParam String family, @RequestParam String given, Model model) {

		log.info("viewpatient get");
		// log.info(patientProxy.findPatient(family, given));
		PatientBean patient = new PatientBean();
		if (patientProxy.findPatient(family, given).getStatusCode() == HttpStatus.NOT_FOUND) {
			log.info("Not found");
		} else {
			log.info("Found");
			patient = patientProxy.findPatient(family, given).getBody();
			log.info(patient);
			model.addAttribute("patient", patient);
		}

		return "viewpatient";
	}

	@GetMapping("/findreport")
	public String getFindReport(@RequestParam String patientId, @RequestParam String family, @RequestParam String given,
			@RequestParam String dob, Model model) {

		log.info("findreport get");
		log.info("findreport get " + patientId);
		// log.info("findreport get "+reportList);

		model.addAttribute("patientId", patientId);
		model.addAttribute("family", family);
		model.addAttribute("given", given);
		model.addAttribute("dob", dob);

		return "findreport";
	}

	@PostMapping("/findreport")
	public ModelAndView findReport(String patientId, String family, String given, String dob, Model model) {

		log.info("findreport post");
		log.info("findreport post " + patientId);
		model.addAttribute("patientId", patientId);
		model.addAttribute("family", family);
		model.addAttribute("given", given);
		model.addAttribute("dob", dob);
		return new ModelAndView("redirect:/viewreport?patientId=" + patientId + "&family=" + family + "&given=" + given
				+ "&dob=" + dob+"&size="+3+"&page="+1);// +"&report=");
	}

	@GetMapping("/viewreport")
	public String getViewReport(@RequestParam String patientId, @RequestParam String family, @RequestParam String given,
			@RequestParam String dob, Model model) {

		log.info("viewreport get " + patientId);
		Integer integerPatientId = Integer.parseInt(patientId);

		List<ReportBean> reportList = reportProxy.findReportByPatientId(integerPatientId).getBody();

		PagedListHolder<ReportBean> pagedReport = new PagedListHolder<>(reportList);

		Integer pageSize = 3;
		// TODO to replace with thymeleaf value
		Integer activePage = 1;

		pagedReport.setPageSize(pageSize);
		pagedReport.setPage(activePage);
		
		List<Integer> paginationRangeList = List.of(Math.max(activePage - 2, 1), 
				Math.max(activePage - 1, 1), activePage,
				Math.min(activePage + 1, pagedReport.getPageCount()),
				Math.min(activePage + 2, pagedReport.getPageCount()));
		Set<Integer> paginationRange = paginationRangeList.stream().collect(Collectors.toSet());
		
		Set<Integer> pagesSet = IntStream.rangeClosed(1, pagedReport.getPageCount()).boxed().collect(Collectors.toSet());
			

		
		model.addAttribute("activePage",activePage);
		model.addAttribute("pagedReport",pagedReport);
		model.addAttribute("paginationRange",paginationRange);
		model.addAttribute("numberOfPages",pagedReport.getPageCount());
		model.addAttribute("reportList", reportList);
		model.addAttribute("patientId", patientId);
		model.addAttribute("family", family);
		model.addAttribute("given", given);
		model.addAttribute("dob", dob);

		return "viewreport";
	}

	@GetMapping("savereport")
	public String getSaveReport() {
		return "savereport";
	}

	@GetMapping("updatereport")
	public String getUpdateReport() {
		return "updatereport";
	}

	@GetMapping("deletereport")
	public String getDeleteReport() {
		return "deletereport";
	}

}
