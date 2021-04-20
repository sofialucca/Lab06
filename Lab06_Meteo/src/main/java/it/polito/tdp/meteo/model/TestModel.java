package it.polito.tdp.meteo.model;

import java.util.LinkedHashMap;
import java.util.Map;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		Map<String,Double> umidita=new LinkedHashMap<>();
		umidita=m.getUmiditaMedia(12);
		for(String s:umidita.keySet()) {
			System.out.println(s+" "+umidita.get(s));
		}

		
		System.out.println(m.trovaSequenza(5));
		

	}

}
