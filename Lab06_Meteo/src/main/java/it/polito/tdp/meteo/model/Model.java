package it.polito.tdp.meteo.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
	private MeteoDAO meteoDao;
	private List<String> ordineCitta;
	private int costoOttimale;
	private List<Citta> partenzaRilevamentiMensili;
	
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
	public List<String> trovaSequenza(int mese) {
		
		ordineCitta = new LinkedList<>();
		costoOttimale=100*15+COST*5;
		partenzaRilevamentiMensili=new LinkedList<>();
		partenzaRilevamentiMensili.add(new Citta("Milano",this.meteoDao.getAllRilevamentiLocalitaMese(mese, "Milano")));
		partenzaRilevamentiMensili.add(new Citta("Torino",this.meteoDao.getAllRilevamentiLocalitaMese(mese, "Torino")));
		partenzaRilevamentiMensili.add(new Citta("Genova",this.meteoDao.getAllRilevamentiLocalitaMese(mese, "Genova")));
		
		List<String> parziale= new LinkedList<>();
		
		regressione(0,parziale,partenzaRilevamentiMensili,0);
		return ordineCitta;
	}

	private void regressione(int livello, List<String> parziale,List<Citta> partenza,int costo) {
		
		if(costo>costoOttimale) {
			return;
		}else if(livello==NUMERO_GIORNI_TOTALI) {
			//TODO opzione uscita
			ordineCitta=new LinkedList<>(parziale);
			costoOttimale=costo;
			return;
		}else {
			for(Citta c:partenza) {
				int nuovoCosto= costo+c.getRilevamenti().get(livello).getUmidita();
				if(c.getCounter()<NUMERO_GIORNI_CITTA_MAX) {
					if(livello==0||c.getNome().equals(parziale.get(livello-1))){
						parziale.add(c.getNome());
						c.increaseCounter();
						regressione(livello+1,parziale,partenza,nuovoCosto);
						parziale.remove(livello);
						c.setCounter(c.getCounter()-1);
					}else if(livello!=1&&!c.getNome().equals(parziale.get(livello-1))){
						int count=1;
						for(int i=livello-2;i>-1;i--) {
							if(count>=NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN) {
								parziale.add(c.getNome());
								c.increaseCounter();
								nuovoCosto+=100;
								regressione(livello+1,parziale,partenza,nuovoCosto);
								parziale.remove(livello);
								c.setCounter(c.getCounter()-1);
								break;
							}else if(parziale.get(i).equals(parziale.get(livello-1))) {
								count++;
							}else {
								break;
							}
						}
					}
					
				}
			}
		}
		
	}
	
	
}
