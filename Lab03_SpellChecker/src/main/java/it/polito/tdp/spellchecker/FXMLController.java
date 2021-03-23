package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class FXMLController {

	private Dictionary model ;
	
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLanguage;

    @FXML
    private TextField txtUser;

    @FXML
    private TextField txtWrong;

    @FXML
    private Label txtErrors;

    @FXML
    private Label txtTime;

    @FXML
    void btnClear(ActionEvent event) {
    	txtUser.clear();
    	txtWrong.setText("");
    	txtErrors.setText("The text contains ... errors");
    	txtTime.setText("");
    }

    @FXML
    void btnSpell(ActionEvent event) {
    	
    	//CARICO IL VOCABOLARIO SCELTO
    	String lang = boxLanguage.getValue();
    	String nomeFile = lang+".txt";
    	//String nomeFile = "English.txt";
    	model.loadDictionary(nomeFile,lang); //passo la lingua di cui caricare il vocabolario
    	
    	//Passo FRASE SCRITTA minuscola e senza punteggiatura
    	String input = txtUser.getText().toLowerCase();
    	input.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]\"]", "");
    	
    	
    	String inputArray []= input.split(" ");
    	// ***************************************** LINKED o ARRAY *************************************
    	List <String> inputList=new LinkedList <String>(); 
    	for(String si: inputArray)
    		inputList.add(si);
    	
    	
    	//TEMPO per TESTARE ERRORI con RICERCA LINEARE
    	double start = System.nanoTime()*Math.pow(10, -6);
    	List <RichWord> writtenWords = model.spellCheckTextLinear(inputList);
    	double end = System.nanoTime()*Math.pow(10, -6);
    	double difference1= end-start;
    	
    	
    	//TEMPO per TESTARE ERRORI con RICERCA DICOTOMICA
    	double start2 = System.nanoTime()*Math.pow(10, -6);
    	writtenWords = model.spellCheckTextDichotomic(inputList);
    	double end2 = System.nanoTime()*Math.pow(10, -6);
    	double difference2= end2-start2;
    	
    	
    	// MODIFICO VIEW
    	
    	// parole errate in stringa
    	String wrong = model.wrongWords(writtenWords);
    	//numero di errori
    	int errors = model.numberErrors(writtenWords);
    	
    	txtWrong.setText(wrong);
    	txtErrors.setText("The text contains "+errors+" errors");
    	txtTime.setText("Spell check with LINEAR SEARCH completed in "+difference1+" seconds\n"
    			+ "Spell check with DICHOTOMIC SEARCH completed in "+difference2+" seconds");
    }
    
    public void setModel(Dictionary model2) {
    	this.model = model2 ;
    	String languages []= {"English","Italian"};
    	boxLanguage.getItems().addAll(languages);
    }

    @FXML
    void initialize() {
        assert boxLanguage != null : "fx:id=\"boxLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtUser != null : "fx:id=\"txtUser\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtWrong != null : "fx:id=\"txtWrong\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtErrors != null : "fx:id=\"txtErrors\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtTime != null : "fx:id=\"txtTime\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}



