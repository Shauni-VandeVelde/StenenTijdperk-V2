/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

/**
 *
 * @author kenzo
 */
public class SavePane  extends Pane{
    private Button btnNewSave;
    private ArrayList<Button> existingSavesButtons;
    private VBox vBox;
    
    private ImageView backgroundImg;
    
    SavePane(){
        btnNewSave = new Button("New Save");
        existingSavesButtons = new ArrayList<Button>();
        
        existingSavesButtons.add(new Button("Save 1"));
        existingSavesButtons.add(new Button("Save 2"));

        
        backgroundImg = new ImageView(this.getClass().getClassLoader().getResource("img/PauzeMenuBg.png").toExternalForm());
        
        vBox = new VBox();
        
        setLayout();
        setStyleSheets();
        
        this.getChildren().add(backgroundImg);

        vBox.getChildren().add(btnNewSave);
        
        for(Button b : existingSavesButtons)
            vBox.getChildren().add(b);
        
        this.getChildren().add(vBox);
    }
 
    private void setLayout(){
        backgroundImg.fitWidthProperty().bind(this.widthProperty());
        backgroundImg.fitHeightProperty().bind(this.heightProperty());
        
        vBox.prefWidthProperty().bind(widthProperty());
        vBox.prefHeightProperty().bind(heightProperty());
        vBox.setAlignment(Pos.CENTER);
        
        btnNewSave.prefWidthProperty().bind(widthProperty().multiply(0.65));
        
        for(Button b : existingSavesButtons){
            b.prefWidthProperty().bind(btnNewSave.widthProperty());
        }
    }
    
    private void setStyleSheets(){
        btnNewSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        
        for(Button b : existingSavesButtons){
            b.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        }
    }
}
