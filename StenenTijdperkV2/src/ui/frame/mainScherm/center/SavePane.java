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
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author kenzo
 */
public class SavePane extends RightPaneBlueprint
{
    private MainScherm mainScherm;
    private PersistentieController pc;
    private DomeinController dc;
    private Button btnNewSave;
    private ArrayList<String> saves;
    private ArrayList<Button> existingSavesButtons;
    private VBox vBox;
    private PauzePane pauzePane;

    private ImageView backgroundImg;

    SavePane(MainScherm mainScherm, PauzePane pauzePane, DomeinController dc)
    {
        super(mainScherm, mainScherm.getController().getSpelers().get(0), pauzePane, 1, 0.77);
        this.mainScherm = mainScherm;
        this.pauzePane = pauzePane;
        this.pc = new PersistentieController();
        this.dc = dc;
        btnNewSave = new Button("New Save");
        saves = new ArrayList<String>();
        existingSavesButtons = new ArrayList<Button>();

        loadExistingSaveButtons();

        backgroundImg = mainScherm.getImageView("img/PauzeMenuBg.png");

        vBox = new VBox();

        setLayout();
        setStyleSheets();
        setActions();

        this.getChildren().add(backgroundImg);

        vBox.getChildren().add(btnNewSave);

        for (Button b : existingSavesButtons)
            {
            vBox.getChildren().add(b);
            }

        this.getChildren().add(vBox);
    }

    private void setLayout()
    {
        backgroundImg.fitWidthProperty().bind(this.widthProperty());
        backgroundImg.fitHeightProperty().bind(this.heightProperty());

        vBox.prefWidthProperty().bind(widthProperty());
        vBox.prefHeightProperty().bind(heightProperty());
        vBox.setAlignment(Pos.CENTER);

        btnNewSave.prefWidthProperty().bind(widthProperty().multiply(0.65));

        for (Button b : existingSavesButtons)
            {
            b.prefWidthProperty().bind(btnNewSave.widthProperty());
            }
    }

    private void setStyleSheets()
    {
        btnNewSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());

        for (Button b : existingSavesButtons)
            {
            b.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
            }
    }
    
    private void setActions()
    {
        btnNewSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                pc.saveNew("Test07", dc);
                pauzePane.toggleSavePane();
            }
        });
    }

    private void loadExistingSaveButtons(){
        ArrayList<Pair> temp = pc.getSaveNamesWithRoundNr();
        
        for(int i = 0; i < temp.size(); ++i)
        {
            saves.add(temp.get(i).getKey().toString());
            existingSavesButtons.add(new Button("Save " + (i + 1) + " - " + "Naam: " + temp.get(i).getKey().toString() + " - " + "Ronde: " + temp.get(i).getValue().toString()));
        }
    }
}
