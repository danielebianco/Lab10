package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import it.polito.tdp.porto.model.Paper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	private Model model;
	
	@FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<Author> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	
    	Author a = this.boxPrimo.getValue();
    	if(a == null) {
    		txtResult.appendText("Errore: selezionare un autore!\n");
    		return ;
    	}
    	List<Author> coautori = model.trovaCoAutori(a);
    	
    	// non-coautori di "a"
    	List<Author> nonCoautori = model.getAutori();
    	nonCoautori.removeAll(coautori);
    	nonCoautori.remove(a);
    	    	
    	this.txtResult.appendText("I coautori di " + a.toString() + " sono: \n");
    	for(Author au : coautori) {
    		txtResult.appendText("- "+ au.toString() + "\n");
    	}
    	
    	txtResult.appendText("\n\n");
    	
    	this.boxSecondo.getItems().clear();
    	this.boxSecondo.getItems().addAll(nonCoautori);
    	this.boxSecondo.setDisable(false);
    	
    }

    @FXML
    void handleSequenza(ActionEvent event) {
    	
    	Author a1 = this.boxPrimo.getValue();
    	Author a2 = this.boxSecondo.getValue();
    	
    	if(a1 == null || a2 == null) {
    		txtResult.appendText("Errore: selezionare gli autori!\n");
    		return ;
    	}
    	
    	List<Paper> articoli = model.sequenzaArticoli(a1, a2);
    	
    	this.txtResult.appendText("La lista di articoli che collega " + a1.toString() + " e " +
    			a2.toString() + " è:\n\n");
    	for(Paper pr : articoli) {
    		txtResult.appendText("- " + pr.getTitle() + "\n");
    	}
    	
    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";
        this.boxSecondo.setDisable(true);
    }

	public void setModel(Model model) {
		this.model = model;
		this.boxPrimo.getItems().addAll(model.getAutori());
		this.boxSecondo.getItems().clear();
	}
	
	public Model getModel() {
		return model;
	}
	
}
