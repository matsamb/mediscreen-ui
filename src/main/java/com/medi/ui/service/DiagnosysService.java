package com.medi.ui.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.medi.ui.bean.PatientBean;
import com.medi.ui.bean.ReportBean;
import com.medi.ui.proxies.PatientProxy;
import com.medi.ui.proxies.ReportProxy;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class DiagnosysService {

	public Map<String, Integer> status(PatientBean patient, List<ReportBean> reportsList) {

		List<String> triggerList = List.of("Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur", "Anormal",
				"Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps");

		Map<String, Long> triggerMapWithOccurences = new HashMap<>();

		long occurence = 0l;
		for (String trigger : triggerList) {
			occurence = reportsList.stream().filter(r -> r.getComment().toLowerCase().contains(trigger.toLowerCase()))
					.count();
			if (occurence > 0) {
				triggerMapWithOccurences.put(trigger, occurence);
			}
		}
		log.info("status map :" + triggerMapWithOccurences);

		LocalDate dateOB = LocalDate.parse(patient.getDob().toString());
		log.info("status map :" + dateOB);
		log.info("status map :" + dateOB.plusYears(30).compareTo(LocalDate.now()));
		
		//positive if less than thirty : 17years old -> 13 // 33years old -> -3
		int positiveIfLessThan30 = dateOB.plusYears(30).compareTo(LocalDate.now());

		Map<String, Integer> result = new HashMap<>();
		
		if(triggerMapWithOccurences.entrySet().isEmpty()) {
			log.info("status : none");
			result.put("None", - dateOB.compareTo(LocalDate.now()));
			return result;
		}
		
		//Le dossier du patient contient deux déclencheurs et le patient est âgé de plus de 30 ans
		
		if (positiveIfLessThan30 < 0 && triggerMapWithOccurences.entrySet().size() > 1) {
			log.info("status : Borderline");
			result.put("Borderline",- dateOB.compareTo(LocalDate.now()));
			return result;
		}
		
		/*Dépend de l'âge et du sexe du patient. Si le patient est un
		homme et a moins de 30 ans, alors trois termes déclencheurs doivent être
		présents. Si le patient est une femme et a moins de 30 ans, il faudra quatre
		termes déclencheurs. Si le patient a plus de 30 ans, alors il en faudra six.*/
		
		if(patient.getSex().contentEquals("M") && positiveIfLessThan30 > 0 && triggerMapWithOccurences.entrySet().size() > 2 || 
				patient.getSex().contentEquals("F") && positiveIfLessThan30 > 0 && triggerMapWithOccurences.entrySet().size() > 3 ||
				positiveIfLessThan30 < 0 && triggerMapWithOccurences.entrySet().size() > 6) {
			log.info("status : in Danger");
			result.put("in Danger", - dateOB.compareTo(LocalDate.now()));
			return result;
		}

		/* Encore une fois, cela dépend de l'âge et du sexe. Si le patient est un homme et a 
		 * moins de 30 ans, alors cinq termes déclencheurs sont nécessaires. Si le patient est 
		 * une femme et a moins de 30 ans, il faudra sept termes déclencheurs. Si le patient a 
		 * plus de 30 ans, alors il en faudra huit ou plus*/
		
		if(patient.getSex().contentEquals("M") && positiveIfLessThan30 > 0 && triggerMapWithOccurences.entrySet().size() > 4 || 
				patient.getSex().contentEquals("F") && positiveIfLessThan30 > 0 && triggerMapWithOccurences.entrySet().size() > 6 ||
				positiveIfLessThan30 < 0 && triggerMapWithOccurences.entrySet().size() > 7) {
			log.info("status : Early onset");
			result.put("Early onset", dateOB.compareTo(LocalDate.now()));
			return result;
		}
		log.info("status : Not_Referenced");
		result.put("Not_Referenced", - dateOB.compareTo(LocalDate.now()));
		return result;
	}
}
