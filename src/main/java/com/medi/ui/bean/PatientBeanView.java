package com.medi.ui.bean;

import java.time.LocalDate;
import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientBeanView implements Cloneable{

	private Integer patientId;
//	@Nonnull
	private String family;
//	@Nonnull
	private String given;
	private String dob;
//	@Nonnull
	private String sex;
//	@Nonnull
	private String address;
//	@Nonnull
	private String phone;

	public PatientBeanView(String string) {
		this.family = string;
	}

	public Object clone() {
		PatientBeanView copy = null;
		try {
			copy = (PatientBeanView) super.clone();
		} catch (CloneNotSupportedException c) {
			c.printStackTrace();
		}
		return copy;
	}
	
}
