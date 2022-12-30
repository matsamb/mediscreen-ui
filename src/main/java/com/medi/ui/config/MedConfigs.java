package com.medi.ui.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("medconfigs")
@RefreshScope
public class MedConfigs {

	private String patienturl;
	private String historicurl;
	
	public String getPatienturl() {
		return patienturl;
	}
	
	public void setPatienturl(String url) {
		this.patienturl = url;
	}
	
	public String getHistoricurl() {
		return historicurl;
	}
	
	public void setHistoricurl(String url) {
		this.historicurl = url;
	}
	
}
