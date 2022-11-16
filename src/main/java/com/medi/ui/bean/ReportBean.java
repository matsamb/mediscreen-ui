package com.medi.ui.bean;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

//import com.medi.historic.diabetestatus.DiabeteStatusEnum;

@Data
@NoArgsConstructor
public class ReportBean {

	private String id;//string of user Id for instance
	//@Transient
	private Integer patientId;
	//private String family;
	//private String given;
	//private LocalDate dob;
	//S@Field("datetime")
	private Date date;
	//private DiabeteStatus diabeteStatus;
	private String comment;

	
}
