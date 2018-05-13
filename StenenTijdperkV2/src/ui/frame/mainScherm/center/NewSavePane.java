/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.DomeinController;
import Persistentie.PersistentieController;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.RightPaneBlueprint;

/**
 *
 * @author kenzo
 */
public class NewSavePane extends RightPaneBlueprint {

    private PersistentieController pc;
    private DomeinController dc;
    private MainScherm mainScherm;
    private ImageView backgroundImg;
    private VBox vBox;
    private Label lblSaveNaam;
    private TextField saveNameField;
    private Button btnSave;
    private ArrayList<String> bestaandeSpelnamen;
    private PauzePane pauzePane;

    public NewSavePane(MainScherm mainScherm, PauzePane pauzePane, DomeinController dc, ArrayList<String> bestaandeSpelnamen) {
        super(mainScherm, dc.getSpelers().get(0), pauzePane, 1, 0.77);
        this.pc = new PersistentieController();
        this.dc = dc;
        this.mainScherm = mainScherm;
        this.bestaandeSpelnamen = bestaandeSpelnamen;
        this.pauzePane = pauzePane;

        backgroundImg = mainScherm.getImageView("img/PauzeMenuBg.png");

        vBox = new VBox();
        saveNameField = new TextField();
        lblSaveNaam = new Label("Geef een spelnaam");
        btnSave = new Button("Save");

        setActions();
        setStyleSheets();
        setLayout();

        vBox.getChildren().add(lblSaveNaam);
        vBox.getChildren().add(saveNameField);
        vBox.getChildren().add(btnSave);

        this.getChildren().add(backgroundImg);
        this.getChildren().add(vBox);
    }

    public void setLayout() {
        backgroundImg.fitWidthProperty().bind(this.widthProperty());
        backgroundImg.fitHeightProperty().bind(this.heightProperty());
        
        lblSaveNaam.setFont(new Font("Arial", 40));
        
        vBox.prefWidthProperty().bind(widthProperty());
        vBox.prefHeightProperty().bind(heightProperty());
        vBox.setAlignment(Pos.CENTER);

        vBox.setSpacing(10);
        
        saveNameField.prefWidthProperty().bind(widthProperty().multiply(0.65));
        lblSaveNaam.setFont(new Font("Arial", 30));
    }
    
        private void setStyleSheets() {
        lblSaveNaam.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/newSavePane.css").toExternalForm());
        
        saveNameField.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/newSavePane.css").toExternalForm());
        
        
        btnSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/MenuButtons.css").toExternalForm());
        

    }

    public void setActions() {
        btnSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                String givenName = saveNameField.getText();
                saveNameField.clear();
                
                if(controlleerNaamAlGebruikt(givenName)){
                    mainScherm.printLine("De naam die je gekozen hebt bestaat al!");
                }
                else if(givenName.length() <= 1){
                    mainScherm.printLine("Te weinig karakters!");
                }
                else if(givenName.length() > 20){
                    mainScherm.printLine("De naam die je ingegeven hebt is te lang! Maximum 20 karakters.");
                }
                else{
                    pc.saveNew(givenName, dc);
                    mainScherm.printLine("Het spel " + givenName + " is succesvol opgeslagen!");
                    pauzePane.open();
                }
            }
        });
    }
    
    /**
     * Returnt true als de naam al gebruikt is
     * @param naam
     * @return 
     */
    public boolean controlleerNaamAlGebruikt(String naam){
        for(String temp : bestaandeSpelnamen){
            if(naam.toLowerCase().equals(temp.toLowerCase())){
                return true;
            }
        }
        
        return false;
    }
}
