/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Kleur;
import Domein.Speler;
import java.util.ArrayList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
public class EndOfGamePane extends RightPaneBlueprint
{

    private MainScherm mainScherm;
    private HBox mainBox;
    private Label mededelingLabel;

    public EndOfGamePane(MainScherm mainScherm, Pane container)
    {
        super(mainScherm, mainScherm.getController().getSpelers().get(0), container, 1, 1);
        this.mainScherm = mainScherm;
        init();

    }

    private void init()
    {
        setBackgroundImage("img/ConsoleScherm.png");
        initTop();
        initNamen();
        initSpelers();
        initActions();
    }

    private void initActions()
    {
        /*
    bevestig.setOnAction((event) ->
            {

            //  mainScherm.beeindigKoopHut(stapel, this, true);
            });
         */
    }

    private void initTop()
    {
        mainBox = new HBox();
        DeelPaneel mededelingDeelPaneel = new DeelPaneel(mainScherm, 0.85, 0.16, vbox, true);
        mededelingLabel = new Label("Speler" + (speler.index() + 1) + " heeft gewonnen!");
        Button bevestig = new Button("Bevestig");
        mededelingDeelPaneel.addLabel(mededelingLabel, 1);
        mededelingLabel.setAlignment(Pos.CENTER);
        mededelingLabel.setStyle(style);
        mededelingDeelPaneel.setFontTracking(mededelingLabel, 43);

        addChild(mededelingDeelPaneel);

        vbox.widthProperty().addListener(e ->
            {
            VBox.setMargin(mededelingDeelPaneel, new Insets(heightProperty().multiply(0.1).doubleValue(), widthProperty().multiply(0.06).doubleValue(), 0, widthProperty().multiply(0.06).doubleValue()));
            });
        mainBox.prefWidthProperty().bind(vbox.widthProperty());
        mainBox.minWidthProperty().bind(vbox.widthProperty());
        mainBox.maxWidthProperty().bind(vbox.widthProperty());
        mainBox.prefHeightProperty().bind(vbox.heightProperty().subtract(mededelingDeelPaneel.heightProperty()));
        mainBox.minHeightProperty().bind(vbox.heightProperty().subtract(mededelingDeelPaneel.heightProperty()));
        mainBox.maxHeightProperty().bind(vbox.heightProperty().subtract(mededelingDeelPaneel.heightProperty()));
    }

    private void initNamen()
    {
        addChild((getNamenPaneel(mainScherm.getController().getAantalSpelers())));
    }

    private void initSpelers()
    {
        ArrayList<Speler> orderedWinnaars = mainScherm.getController().getOrderedWinnaars();
        for (int i = 0; i < orderedWinnaars.size(); i++)
            {
            if (i == 0)
                {
                switch (orderedWinnaars.get(i).index())
                    {
                    case 0:
                        mededelingLabel.setStyle("-fx-text-fill:red;");
                        break;
                    case 1:
                        mededelingLabel.setStyle("-fx-text-fill:blue;");
                        break;
                    case 2:
                        mededelingLabel.setStyle("-fx-text-fill:yellow;");
                        break;
                    case 3:
                        mededelingLabel.setStyle("-fx-text-fill:green;");
                        break;
                    }
                mededelingLabel.setText("Speler" + (orderedWinnaars.get(i).index() + 1) + " heeft gewonnen!");
                }
            addChild(getSpelerPaneel(orderedWinnaars.get(i), orderedWinnaars.size()));
            }
    }

    private HBox getNamenPaneel(int aantal)
    {

        double widthDiv = 0.22;
        double fontSize = 23;
        String style = "-fx-text-fill:red;" + "-fx-border-color: black;-fx-border-insets: 0;-fx-border-width: 1;-fx-border-style: solid;";
        HBox spelerPaneel = new HBox();
        Label spelerLabel = new Label("Speler: ");
        Label penaltyLabel = new Label("Penalty: ");
        Label GrondstoffenLabel = new Label("Punten grondstoffen: ");
        Label huttenLabel = new Label("Punten: hutten ");
        Label totaalLabel = new Label("Totaal: ");

        DeelPaneel spelerDeelPaneel = new DeelPaneel(mainScherm, 0.15, 0.14, vbox, true);
        DeelPaneel puntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel huttenPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel grondstoffenPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel totaalPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);

        //spelerDeelPaneel.setStyle("-fx-background-color:blue;");
        //huttenPuntenDeelPaneel.setStyle("-fx-background-color:red;");
        //grondstoffenPuntenDeelPaneel.setStyle("-fx-background-color:purple;");
        //totaalPuntenDeelPaneel.setStyle("-fx-background-color:orange;");
        spelerLabel.setAlignment(Pos.CENTER);
        GrondstoffenLabel.setAlignment(Pos.CENTER);
        penaltyLabel.setAlignment(Pos.CENTER);
        huttenLabel.setAlignment(Pos.CENTER);
        totaalLabel.setAlignment(Pos.CENTER);

        spelerLabel.setStyle(style);
        GrondstoffenLabel.setStyle(style);
        huttenPuntenDeelPaneel.setStyle(style);
        penaltyLabel.setStyle(style);
        huttenLabel.setStyle(style);
        totaalLabel.setStyle(style);

        spelerDeelPaneel.addLabel(spelerLabel, 1);
        puntenDeelPaneel.addLabel(penaltyLabel, 1);
        huttenPuntenDeelPaneel.addLabel(huttenLabel, 1);
        grondstoffenPuntenDeelPaneel.addLabel(GrondstoffenLabel, 1);
        totaalPuntenDeelPaneel.addLabel(totaalLabel, 1);

        spelerDeelPaneel.setFontTracking(spelerLabel, 25.5);
        puntenDeelPaneel.setFontTracking(penaltyLabel, 25.5);
        huttenPuntenDeelPaneel.setFontTracking(huttenLabel, fontSize);
        grondstoffenPuntenDeelPaneel.setFontTracking(GrondstoffenLabel, fontSize);
        totaalPuntenDeelPaneel.setFontTracking(totaalLabel, fontSize);

        spelerPaneel.getChildren().add(spelerDeelPaneel);
        spelerPaneel.getChildren().add(puntenDeelPaneel);
        spelerPaneel.getChildren().add(huttenPuntenDeelPaneel);
        spelerPaneel.getChildren().add(grondstoffenPuntenDeelPaneel);
        spelerPaneel.getChildren().add(totaalPuntenDeelPaneel);

        return spelerPaneel;
    }

    private HBox getSpelerPaneel(Speler speler, int aantal)
    {
        String kleur = "";
        String style = "";
        if (speler.getKleur() == Kleur.ROOD)
            {
            kleur = "Rood";
            style = "-fx-text-fill: red;";
            }
        if (speler.getKleur() == Kleur.BLAUW)
            {
            kleur = "Blauw";
            style = "-fx-text-fill: blue;";
            }
        if (speler.getKleur() == Kleur.GEEL)
            {
            kleur = "Geel";
            style = "-fx-text-fill: #8e800c;";
            }
        if (speler.getKleur() == Kleur.GROEN)
            {
            kleur = "Groen";
            style = "-fx-text-fill: green;";
            }
        style += "-fx-border-color: black;-fx-border-insets: 0;-fx-border-width: 1;-fx-border-style: solid;";
        double widthDiv = 0.22;
        double fontSize = 30;
        HBox spelerPaneel = new HBox();
        Label spelerLabel = new Label("" + kleur);
        Label resourcesPuntenLabel = new Label("" + speler.getWaardeVanResources());
        Label huttenPuntenLabel = new Label("" + speler.berekenPuntenVanHutten());
        Label penaltyLabel = new Label("" + speler.getTotaalPenalty());
        Label totaalPuntenLabel = new Label("" + ((speler.getPunten()) + speler.getWaardeVanResources()));

        DeelPaneel spelerDeelPaneel = new DeelPaneel(mainScherm, 0.15, 0.14, vbox, true);
        DeelPaneel puntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel huttenPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel grondstoffenPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);
        DeelPaneel totaalPuntenDeelPaneel = new DeelPaneel(mainScherm, widthDiv, 0.14, vbox, true);

        // spelerDeelPaneel.setStyle("-fx-background-color:blue;");
        // huttenPuntenDeelPaneel.setStyle("-fx-background-color:red;");
        //  grondstoffenPuntenDeelPaneel.setStyle("-fx-background-color:purple;");
        // totaalPuntenDeelPaneel.setStyle("-fx-background-color:orange;");
        spelerLabel.setAlignment(Pos.CENTER);
        penaltyLabel.setAlignment(Pos.CENTER);
        resourcesPuntenLabel.setAlignment(Pos.CENTER);
        huttenPuntenLabel.setAlignment(Pos.CENTER);
        totaalPuntenLabel.setAlignment(Pos.CENTER);

        spelerLabel.setStyle(style);
        penaltyLabel.setStyle(style);
        resourcesPuntenLabel.setStyle(style);
        huttenPuntenDeelPaneel.setStyle(style);
        huttenPuntenLabel.setStyle(style);
        totaalPuntenLabel.setStyle(style);

        spelerDeelPaneel.addLabel(spelerLabel, 1);
        puntenDeelPaneel.addLabel(penaltyLabel, 1);
        huttenPuntenDeelPaneel.addLabel(huttenPuntenLabel, 1);
        grondstoffenPuntenDeelPaneel.addLabel(resourcesPuntenLabel, 1);
        totaalPuntenDeelPaneel.addLabel(totaalPuntenLabel, 1);

        spelerDeelPaneel.setFontTracking(spelerLabel, 25.5);
        puntenDeelPaneel.setFontTracking(penaltyLabel, 25.5);
        huttenPuntenDeelPaneel.setFontTracking(huttenPuntenLabel, fontSize);
        grondstoffenPuntenDeelPaneel.setFontTracking(resourcesPuntenLabel, fontSize);
        totaalPuntenDeelPaneel.setFontTracking(totaalPuntenLabel, fontSize);

        spelerPaneel.getChildren().add(spelerDeelPaneel);
        spelerPaneel.getChildren().add(puntenDeelPaneel);
        spelerPaneel.getChildren().add(huttenPuntenDeelPaneel);
        spelerPaneel.getChildren().add(grondstoffenPuntenDeelPaneel);
        spelerPaneel.getChildren().add(totaalPuntenDeelPaneel);

        return spelerPaneel;
    }

}
