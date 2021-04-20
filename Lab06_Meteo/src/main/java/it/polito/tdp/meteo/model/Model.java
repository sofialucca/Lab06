package it.polito.tdp.meteo.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO meteoDao;
	public Model() {
		meteoDao=new MeteoDAO();
	}

	// of course you can change the String output with what you think works best
	public Map<String,Double> getUmiditaMedia(int mese) {
		Map<String,Double> umiditaMese=new HashMap<>();
		umiditaMese.put("Genova", meteoDao.getAvgRilevamentiLocalitaMese(mese, "Genova"));
		umiditaMese.put("Milano", meteoDao.getAvgRilevamentiLocalitaMese(mese, "Milano"));
		umiditaMese.put("Torino", meteoDao.getAvgRilevamentiLocalitaMese(mese, "Torino"));
		
		return umiditaMese;
	}
	
	// of course you can change the String output with what you think works best
	public String trovaSequenza(int mese) {
		return "TODO!";
	}
	

}
