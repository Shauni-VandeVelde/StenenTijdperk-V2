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
import javafx.scene.layout.VBox;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author kenzo
 */
public class LoadPane extends RightPaneBlueprint
{
    private ArrayList<Button> loadButtons;
    private VBox vBox;
    private MainScherm mainScherm;
    private ImageView backgroundImg;

    LoadPane(MainScherm mainScherm, PauzePane pauzePane)
    {
        super(mainScherm, mainScherm.getController().getSpelers().get(0), pauzePane, 1, 0.77);
        this.mainScherm = mainScherm;
        loadButtons = new ArrayList<Button>();

        loadButtons.add(new Button("Save 1"));
        loadButtons.add(new Button("Save 2"));

        backgroundImg = mainScherm.getImageView("img/PauzeMenuBg.png");

        vBox = new VBox();

        setLayout();
        setStyleSheets();

        this.getChildren().add(backgroundImg);

        for (Button b : loadButtons)
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

        for (Button b : loadButtons)
            {
            b.prefWidthProperty().bind(widthProperty().multiply(0.65));
            }
    }

    private void setStyleSheets()
    {
        for (Button b : loadButtons)
            {
            b.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
            }
    }

}
