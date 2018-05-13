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
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Pair;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.RightPaneBlueprint;

/**
 *
 * @author kenzo
 */
public class SavePane extends RightPaneBlueprint {

    private MainScherm mainScherm;
    private PersistentieController pc;
    private DomeinController dc;
    private Stage stage;

    private Label lblSave;
    private Button btnNewSave;
    private ArrayList<String> saves;
    private ArrayList<Button> existingSavesButtons;
    private VBox vBox;
    private PauzePane pauzePane;
    private Background buttonBG;

    private ImageView backgroundImg;

    SavePane(MainScherm mainScherm, PauzePane pauzePane, DomeinController dc) {
        super(mainScherm, mainScherm.getController().getSpelers().get(0), pauzePane, 1, 0.77);
        this.mainScherm = mainScherm;
        this.pauzePane = pauzePane;
        this.pc = new PersistentieController();
        this.dc = dc;
        lblSave = new Label("Spel opslaan:");
        btnNewSave = new Button("New Save");
        saves = new ArrayList<String>();
        existingSavesButtons = new ArrayList<Button>();

        loadExistingSaveButtons();
        existingSavesButtons.add(new Button("fuck dit"));
        existingSavesButtons.add(new Button("fuck dit hard"));
        existingSavesButtons.add(new Button("fuck dit hard & droog"));
        
        backgroundImg = mainScherm.getImageView("img/PauzeMenuBg.png");

        vBox = new VBox();

        setLayout();
        setStyleSheets();
        setActions();

        this.getChildren().add(backgroundImg);

        vBox.getChildren().add(lblSave);
        vBox.getChildren().add(btnNewSave);

        for (Button b : existingSavesButtons) {
            vBox.getChildren().add(b);
            b.setBackground(buttonBG);
        }

        this.getChildren().add(vBox);
    }

    private void setLayout() {
        backgroundImg.fitWidthProperty().bind(this.widthProperty());
        backgroundImg.fitHeightProperty().bind(this.heightProperty());

        vBox.prefWidthProperty().bind(widthProperty());
        vBox.prefHeightProperty().bind(heightProperty());
        vBox.setAlignment(Pos.CENTER);

        vBox.setSpacing(10);

        lblSave.setFont(new Font("Arial", 30));

        for (Button b : existingSavesButtons) {
            b.prefWidthProperty().bind(btnNewSave.widthProperty());
            b.prefHeightProperty().bind(btnNewSave.heightProperty());
        }
    }

    private void setStyleSheets() {
        
        lblSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/newSavePane.css").toExternalForm());
        btnNewSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/MenuButtons.css").toExternalForm());
        btnNewSave.setId("saveButtons");
        btnNewSave.prefWidthProperty().bind(widthProperty().multiply(0.65));

        for (Button b : existingSavesButtons) {
            b.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/MenuButtons.css").toExternalForm());
            b.setId("saveButtons");
            
        }
    }

    private void setActions() {
        System.out.println("set actions called");
        btnNewSave.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("NEW SAVEPANE OPENED!");
                pauzePane.openNewSavePane(saves);
            }

        });
    }

    private void loadExistingSaveButtons() {
        ArrayList<Pair> temp = pc.getSaveNamesWithRoundNr();

        for (int i = 0; i < temp.size(); ++i) {
            String strSaveName = temp.get(i).getKey().toString();
            saves.add(strSaveName);

            
            Button tempButton = new Button("Save " + (i + 1) + " - " + "Naam: " + strSaveName + " - " + "Ronde: " + temp.get(i).getValue().toString());

            tempButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    pc.saveOverride(strSaveName, dc);
                    mainScherm.printLine("Overschrijven " + strSaveName + " successvol.");
                }
            });

            existingSavesButtons.add(tempButton);
        }
    }
}
