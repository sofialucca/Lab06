/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.meteo;

import java.net.URL;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.meteo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {

	Model model;
	
    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxMese"
    private ChoiceBox<Integer> boxMese; // Value injected by FXMLLoader

    @FXML // fx:id="btnUmidita"
    private Button btnUmidita; // Value injected by FXMLLoader

    @FXML // fx:id="btnCalcola"
    private Button btnCalcola; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    private Label labelErroreMese;
    
    @FXML
    void doCalcolaSequenza(ActionEvent event) {
    	
    	this.txtResult.clear();
    	if(boxMese.getValue()==null) {
    		labelErroreMese.setText("ERRORE: selezionare un mese");
    		return;
    	}
    	labelErroreMese.setText("");
    	int mese =boxMese.getValue();
    	List<String> citta=new LinkedList<>();
    	citta=model.trovaSequenza(mese);
    	for(int i=0;i<citta.size();i++) {
    		txtResult.appendText((i+1)+")" +citta.get(i)+"\n");
    	}
    }

    @FXML
    void doCalcolaUmidita(ActionEvent event) {
    	this.txtResult.clear();
    	if(boxMese.getValue()==null) {
    		labelErroreMese.setText("ERRORE: selezionare un mese");
    		return;
    	}
    	labelErroreMese.setText("");
    	int mese=boxMese.getValue();
    	Map<String,Double> umidita=new HashMap<>();
    	umidita=model.getUmiditaMedia(mese);

    	StringBuilder sb=new StringBuilder();
    	for(String s:umidita.keySet()) {
    		sb.append(String.format("%-8s %-10f\n", s,umidita.get(s)));
    	}
    	txtResult.appendText(sb.toString());
    	
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxMese != null : "fx:id=\"boxMese\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUmidita != null : "fx:id=\"btnUmidita\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnCalcola != null : "fx:id=\"btnCalcola\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
        assert labelErroreMese != null : "fx:id=\"labelErroreMese\" was not injected: check your FXML file 'Scene.fxml'.";
        
    }

	public void setModel(Model model) {
		this.model=model;
		for (int i=0;i<12;i++) {
			this.boxMese.getItems().add(i,i+1);
		}
    	txtResult.setStyle("-fx-font-family: monospace");
	}
}

