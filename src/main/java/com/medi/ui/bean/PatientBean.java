package com.medi.ui.bean;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientBean implements Cloneable {

	// "family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside
	// St&phone=100-222-3333"

	private Integer patientId;
//	@Nonnull
	private String family;
//	@Nonnull
	private String given;
//	@Nonnull
	private Object dob;
//	@Nonnull
	private String sex;
//	@Nonnull
	private String address;
//	@Nonnull
	private String phone;

	public PatientBean(String string) {
		this.family = string;
	}
	
	public PatientBean(PatientBeanView patientBeanView/*, String dob2*/) {
		this.dob = LocalDate.parse(patientBeanView.getDob());
		this.patientId = patientBeanView.getPatientId();
		this.family = patientBeanView.getFamily();
		this.given = patientBeanView.getGiven();
		this.sex = patientBeanView.getSex();
		this.address = patientBeanView.getAddress();
		this.phone = patientBeanView.getPhone();
}

	public Object clone() {
		PatientBean copy = null;
		try {
			copy = (PatientBean) super.clone();
		} catch (CloneNotSupportedException c) {
			c.printStackTrace();
		}
		return copy;
	}

}
