package com.medi.ui.bean;

import java.time.LocalDate;
import java.util.UUID;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PatientBean implements Cloneable{

	//"family=TestNone&given=Test&dob=1966-12-31&sex=F&address=1 Brookside St&phone=100-222-3333"
	
	private UUID patientId;
//	@Nonnull
	private String family;
//	@Nonnull
	private String given;
//	@Nonnull
	private LocalDate dob;
//	@Nonnull
	private String sex;
//	@Nonnull
	private String address;
//	@Nonnull
	private String phone;
	
	public PatientBean(String string) {
		this.family = string;
	}
	
	public Object clone() {
		PatientBean copy = null;
		try {
			copy = (PatientBean)super.clone();
		}catch(CloneNotSupportedException c){
			c.printStackTrace();
		}
		return copy;
	}
	
}
