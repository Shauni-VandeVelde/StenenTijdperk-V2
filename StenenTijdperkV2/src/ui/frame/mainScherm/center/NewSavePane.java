/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.DomeinController;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.RightPaneBlueprint;

/**
 *
 * @author kenzo
 */
public class NewSavePane extends RightPaneBlueprint{
    private MainScherm mainScherm;
    private ImageView backgroundImg;
    private VBox vBox;
    
    public NewSavePane(MainScherm mainScherm, PauzePane pauzePane, DomeinController dc){
        super(mainScherm, dc.getSpelers().get(0), pauzePane, 1, 0.77);
        this.mainScherm = mainScherm;
        
        backgroundImg = mainScherm.getImageView("img/PauzeMenuBg.png");
        
        vBox = new VBox();
        
        setLayout();
    }
    
    public void setLayout(){
        backgroundImg.fitWidthProperty().bind(this.widthProperty());
        backgroundImg.fitHeightProperty().bind(this.heightProperty());
    }
}
