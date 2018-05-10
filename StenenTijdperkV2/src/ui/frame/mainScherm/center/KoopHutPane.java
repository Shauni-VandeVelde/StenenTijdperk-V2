/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Hut;
import Domein.Speler;
import Domein.Stapel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.DeelPaneel;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author NotAvailable
 */
public class KoopHutPane extends RightPaneBlueprint
{

    private Stapel stapel;

    public KoopHutPane(MainScherm mainScherm, Pane container, Speler speler, Stapel stapel)
    {
        super(mainScherm, speler, container, 1, 0.77);
        if (mainScherm.shouldPlayKoopHutSFX())
            {
            mainScherm.queueSFX("stapel", 50);
            }

        this.stapel = stapel;
        init();
    }

    private void init()
    {
        setBackgroundImage("img/ConsoleScherm.png");

        HBox hbox = new HBox();
        Label mededelingLabel = new Label("Je hebt een van je pionnen op deze stapel gezet:");
        Label vraagLabel = new Label("Wens je deze hut te kopen?");
        Button bevestig = new Button("Bevestig");
        Button weiger = new Button("Weiger");
        ImageView hut = getHut();

        DeelPaneel mededelingDeelPaneel = new DeelPaneel(mainScherm,0.85, 0.16, vbox, true);
        DeelPaneel vraagDeelPaneel = new DeelPaneel(mainScherm,0.85, 0.16, vbox, true);
        DeelPaneel bevestigDeelPaneel = new DeelPaneel(mainScherm,0.5, 0.13, vbox, true);
        DeelPaneel weigerDeelPaneel = new DeelPaneel(mainScherm,0.5, 0.13, vbox, true);
        DeelPaneel hutDeelPaneel = new DeelPaneel(mainScherm,0.25, 0.30, vbox, true);

        hutDeelPaneel.addBackgroundImage(hut);
        mededelingDeelPaneel.addLabel(mededelingLabel, 1);
        vraagDeelPaneel.addLabel(vraagLabel, 1);
        bevestigDeelPaneel.addButton(bevestig, 1, 1);
        weigerDeelPaneel.addButton(weiger, 1, 1);

        hbox.getChildren().add(bevestigDeelPaneel);
        hbox.getChildren().add(weigerDeelPaneel);

        addChild(mededelingDeelPaneel);
        addChild(hutDeelPaneel);
        addChild(vraagDeelPaneel);
        addChild(hbox);

        vbox.widthProperty().addListener(e ->
            {
            VBox.setMargin(mededelingDeelPaneel, new Insets(heightProperty().multiply(0.1).doubleValue(), widthProperty().multiply(0.06).doubleValue(), 0, widthProperty().multiply(0.06).doubleValue()));
            VBox.setMargin(vraagDeelPaneel, new Insets(heightProperty().multiply(0.02).doubleValue(), widthProperty().multiply(0.06).doubleValue(), heightProperty().multiply(0.02).doubleValue(), widthProperty().multiply(0.06).doubleValue()));
            VBox.setMargin(hutDeelPaneel, new Insets(heightProperty().multiply(0.08).doubleValue(), widthProperty().multiply(0.35).doubleValue(), heightProperty().multiply(0.019).doubleValue(), widthProperty().multiply(0.35).doubleValue()));
            });
        bevestig.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.beeindigKoopHut(stapel, this, true);
            });
        weiger.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.beeindigKoopHut(stapel, this, false);
            });

        mededelingDeelPaneel.setFontTracking(mededelingLabel, 38);
        mededelingDeelPaneel.setFontTracking(vraagLabel, 35.5);
        bevestigDeelPaneel.setFontTracking(bevestig, 35.5);
        weigerDeelPaneel.setFontTracking(weiger, 35.5);
        mededelingLabel.setAlignment(Pos.CENTER);
        vraagLabel.setAlignment(Pos.CENTER);
        mededelingLabel.setStyle(style);
        vraagLabel.setStyle(style);
        bevestig.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        weiger.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
    }

    public ImageView getHut()
    {

        ImageView image = mainScherm.getImageView(mainScherm.getUrl("pionRood"));

        if (stapel == null)
            {
            System.out.println("Stapel null");
            }
        if (stapel.getBovensteHut() == null)
            {
            System.out.println("hut null");
            }
        Hut hut = stapel.getBovensteHut();
        int h = hut.getHoutKost();
        int l = hut.getLeemKost();
        int s = hut.getSteenKost();
        int g = hut.getGoudKost();
        String path = "";
        if ((h == 0) && (l == 0) && (s == 0) && (g == 3))
            {
            path = "img/Hutten/Hut0003.png";
            }
        if ((h == 0) && (l == 0) && (s == 1) && (g == 2))
            {
            path = "img/Hutten/Hut0012.png";
            }
        if ((h == 0) && (l == 0) && (s == 2) && (g == 1))
            {
            path = "img/Hutten/Hut0021.png";
            }
        if ((h == 0) && (l == 0) && (s == 3) && (g == 0))
            {
            path = "img/Hutten/Hut0030.png";
            }

        if ((h == 0) && (l == 1) && (s == 0) && (g == 2))
            {
            path = "img/Hutten/Hut0102.png";
            }
        if ((h == 0) && (l == 1) && (s == 1) && (g == 1))
            {
            path = "img/Hutten/Hut0111.png";
            }

        if ((h == 0) && (l == 1) && (s == 2) && (g == 0))
            {
            path = "img/Hutten/Hut0120.png";
            }

        if ((h == 0) && (l == 2) && (s == 0) && (g == 1))
            {
            path = "img/Hutten/Hut0201.png";
            }
        if ((h == 0) && (l == 2) && (s == 1) && (g == 0))
            {
            path = "img/Hutten/Hut0210.png";
            }

        if ((h == 0) && (l == 3) && (s == 0) && (g == 0))
            {
            path = "img/Hutten/Hut0300.png";
            }
        if ((h == 1) && (l == 0) && (s == 0) && (g == 2))
            {
            path = "img/Hutten/Hut1002.png";
            }
        if ((h == 1) && (l == 0) && (s == 1) && (g == 1))
            {
            path = "img/Hutten/Hut2001.png";
            }
        if ((h == 1) && (l == 0) && (s == 2) && (g == 0))
            {
            path = "img/Hutten/Hut1020.png";
            }
        if ((h == 1) && (l == 1) && (s == 0) && (g == 1))
            {
            path = "img/Hutten/Hut1101.png";
            }

        if ((h == 1) && (l == 1) && (s == 1) && (g == 0))
            {
            path = "img/Hutten/Hut1110.png";
            }
        if ((h == 1) && (l == 2) && (s == 0) && (g == 0))
            {
            path = "img/Hutten/Hut1200.png";
            }
        if ((h == 2) && (l == 0) && (s == 0) && (g == 1))
            {
            path = "img/Hutten/Hut2001.png";
            }

        if ((h == 2) && (l == 0) && (s == 1) && (g == 0))
            {
            path = "img/Hutten/Hut2010.png";
            }
        if ((h == 2) && (l == 1) && (s == 0) && (g == 0))
            {
            path = "img/Hutten/Hut2100.png";
            }
        if ((h == 3) && (l == 0) && (s == 0) && (g == 0))
            {
            path = "img/Hutten/Hut3000.png";
            }
        image = mainScherm.getImageView(path);
        return image;

    }

}
