package com.medi.ui.proxies;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.medi.ui.bean.ReportBean;

@Repository
@FeignClient(name="historic", url="localhost:9002")
public interface ReportProxy {

	@GetMapping("/report")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<List<ReportBean>> findReportByPatientId(@RequestParam("patientId") Integer patientId);
	
	@PostMapping("/report/add")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<ReportBean> createReport(ReportBean reportBean);
	
	@PutMapping("/report")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<ReportBean> updateReport(@RequestParam("patientId") Integer patientId, ReportBean reportBean);
	
	@DeleteMapping("/report")
	//@Headers("Content-Type: application/Json")
	public ResponseEntity<ReportBean> deleteReportByPatientId(@RequestParam("patientId") Integer patientId, ReportBean reportBean);

	
}
