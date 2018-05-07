/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this containerlate file, choose Tools | Templates
 * and open the containerlate in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.DomeinController;
import Domein.Kleur;
import Domein.Locatie;
import Domein.Pion;
import Domein.Speler;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class CenterPane extends Pane
{

    private MainScherm mainScherm;
    private DomeinController controller;
    private PionnenLocatieDeelPane[] bosLocaties, steengroeveLocaties, leemgroeveLocaties, rivierLocaties,
            jachtLocaties, stapelLocaties, lovehutLocaties, gereedschapsmakerLocaties, akkerLocaties;
    private PionnenLocatieDeelPane arrowPane;
    private boolean arrowShown = false;
    Button bos = new Button();
    Button leemgroeve;
    Button steengroeve;
    Button rivier;
    Button jacht;
    Button akker;
    Button gereedschapsmaker;
    Button lovehut;
    private Timeline timeline;

    public CenterPane(MainScherm mainScherm)
    {

        this.mainScherm = mainScherm;
        controller = mainScherm.getController();
        init();
        setActions();
    }

    public void turnOffArrow()
    {
        timeline.stop();
        arrowPane.setVisible(false);
        arrowShown = false;
    }

    public void removeArrow()
    {
        timeline.stop();
        getChildren().remove(arrowPane);

    }

    public void toggleArrow()
    {

        if (!arrowShown)
            {

            timeline.play();

            arrowShown = true;
            }
        else
            {
            timeline.stop();
            arrowPane.setVisible(false);
            arrowShown = false;
            }
    }

    public void resetAkker()
    {
        akkerLocaties[0].reset();
    }

    public void resetGereedschapsMaker()
    {
        gereedschapsmakerLocaties[0].reset();
    }

    public void resetLovehut()
    {
        lovehutLocaties[0].reset();
        lovehutLocaties[1].reset();

    }

    public void clearPionnenVanLocatie(Locatie locatie, Speler speler)
    {
        PionnenLocatieDeelPane[] array = getLocatieDeelPane(locatie);
        for (int i = 0; i < array.length; i++)
            {
            if (array[i].hasImage())
                {
                if (array[i].getPion().getKleur() == speler.getKleur())
                    {
                    array[i].reset();
                    }
                }
            }
    }

    public void setLocatieImages(Locatie locatie, int aantal, Speler speler)
    {

        ArrayList<Pion> pionnen = new ArrayList<>();
        for (int i = 0; i < aantal; i++)
            {
            pionnen.add(new Pion(speler.getKleur(), true));
            }
        PionnenLocatieDeelPane[] temp = getLocatieDeelPane(locatie);
        setImages(temp, pionnen, speler);
    }

    private void setImages(PionnenLocatieDeelPane[] locatiePaneel, ArrayList<Pion> pionnen, Speler speler)
    {

        boolean[] allPionnen = new boolean[pionnen.size()];
        for (int i = 0; i < allPionnen.length; i++)
            {
            allPionnen[i] = false;
            }

        for (int indexAantal = 0; indexAantal < pionnen.size(); indexAantal++)
            {

            for (int locatieListIndex = 0; locatieListIndex < locatiePaneel.length; locatieListIndex++)
                {
                if (allPionnen[indexAantal] != true)
                    {
                    if (!locatiePaneel[locatieListIndex].hasImage())
                        {
                        locatiePaneel[locatieListIndex].setImage("pion" + speler.getKleur().toString() + "", pionnen.get(indexAantal));
                        pionnen.remove(pionnen.get(indexAantal));

                        allPionnen[indexAantal] = true;
                        setImages(locatiePaneel, pionnen, speler);
                        }

                    }

                }

            }

    }

    private void init()
    {

        bosLocaties = new PionnenLocatieDeelPane[7];
        steengroeveLocaties = new PionnenLocatieDeelPane[7];
        leemgroeveLocaties = new PionnenLocatieDeelPane[7];
        rivierLocaties = new PionnenLocatieDeelPane[7];
        jachtLocaties = new PionnenLocatieDeelPane[40];
        stapelLocaties = new PionnenLocatieDeelPane[1];
        akkerLocaties = new PionnenLocatieDeelPane[1];
        gereedschapsmakerLocaties = new PionnenLocatieDeelPane[1];
        lovehutLocaties = new PionnenLocatieDeelPane[2];

        ImageView spelbordBackground = new ImageView(this.getClass().getClassLoader().getResource("img/backgroundMap.png").toExternalForm());
        spelbordBackground.fitWidthProperty().bind(widthProperty());
        spelbordBackground.fitHeightProperty().bind(heightProperty());

        setStyle("-fx-background-color: red;");
        getChildren().add(spelbordBackground);
        initLocations();
        initLocatieButtons();
        getChildren().add(arrowPane);
        arrowPane.setVisible(false);
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.3), evt -> arrowPane.setVisible(!arrowPane.isVisible())));
        timeline.setCycleCount(Animation.INDEFINITE);

    }

    private void initLocatieButtons()
    {

        bos = new Button();
        leemgroeve = new Button();
        steengroeve = new Button();
        rivier = new Button();
        jacht = new Button();
        akker = new Button();
        gereedschapsmaker = new Button();
        lovehut = new Button();

        bos.setStyle("-fx-background-color:transparent;");
        leemgroeve.setStyle("-fx-background-color:transparent;");
        steengroeve.setStyle("-fx-background-color:transparent;");
        rivier.setStyle("-fx-background-color:transparent;");
        jacht.setStyle("-fx-background-color:transparent;");
        akker.setStyle("-fx-background-color:transparent;");
        gereedschapsmaker.setStyle("-fx-background-color:transparent;");
        lovehut.setStyle("-fx-background-color:transparent;");

        getChildren().add(bos);
        getChildren().add(leemgroeve);
        getChildren().add(steengroeve);
        getChildren().add(rivier);
        getChildren().add(jacht);
        getChildren().add(akker);
        getChildren().add(gereedschapsmaker);
        getChildren().add(lovehut);

        //Bindings
        gereedschapsmaker.prefWidthProperty().bind(widthProperty().multiply(0.18));
        gereedschapsmaker.minWidthProperty().bind(widthProperty().multiply(0.18));
        gereedschapsmaker.maxWidthProperty().bind(widthProperty().multiply(0.18));
        gereedschapsmaker.prefHeightProperty().bind(heightProperty().multiply(0.2));
        gereedschapsmaker.minHeightProperty().bind(heightProperty().multiply(0.2));
        gereedschapsmaker.maxHeightProperty().bind(heightProperty().multiply(0.2));
        gereedschapsmaker.layoutXProperty().bind(widthProperty().multiply(0));
        gereedschapsmaker.layoutYProperty().bind(heightProperty().multiply(0.55));

        lovehut.prefWidthProperty().bind(widthProperty().multiply(0.18));
        lovehut.minWidthProperty().bind(widthProperty().multiply(0.18));
        lovehut.maxWidthProperty().bind(widthProperty().multiply(0.18));
        lovehut.prefHeightProperty().bind(heightProperty().multiply(0.2));
        lovehut.minHeightProperty().bind(heightProperty().multiply(0.2));
        lovehut.maxHeightProperty().bind(heightProperty().multiply(0.2));
        lovehut.layoutXProperty().bind(widthProperty().multiply(0.19));
        lovehut.layoutYProperty().bind(heightProperty().multiply(0.77));

        akker.prefWidthProperty().bind(widthProperty().multiply(0.2));
        akker.minWidthProperty().bind(widthProperty().multiply(0.2));
        akker.maxWidthProperty().bind(widthProperty().multiply(0.2));
        akker.prefHeightProperty().bind(heightProperty().multiply(0.26));
        akker.minHeightProperty().bind(heightProperty().multiply(0.26));
        akker.maxHeightProperty().bind(heightProperty().multiply(0.26));
        akker.layoutXProperty().bind(widthProperty().multiply(0.345));
        akker.layoutYProperty().bind(heightProperty().multiply(0.58));

        bos.prefWidthProperty().bind(widthProperty().multiply(0.268));
        bos.minWidthProperty().bind(widthProperty().multiply(0.268));
        bos.maxWidthProperty().bind(widthProperty().multiply(0.268));
        bos.prefHeightProperty().bind(heightProperty().multiply(0.3));
        bos.minHeightProperty().bind(heightProperty().multiply(0.3));
        bos.maxHeightProperty().bind(heightProperty().multiply(0.3));
        bos.layoutXProperty().bind(widthProperty().multiply(0.29));
        bos.layoutYProperty().bind(heightProperty().multiply(0));

        leemgroeve.prefWidthProperty().bind(widthProperty().multiply(0.34));
        leemgroeve.minWidthProperty().bind(widthProperty().multiply(0.34));
        leemgroeve.maxWidthProperty().bind(widthProperty().multiply(0.34));
        leemgroeve.prefHeightProperty().bind(heightProperty().multiply(0.27));
        leemgroeve.minHeightProperty().bind(heightProperty().multiply(0.27));
        leemgroeve.maxHeightProperty().bind(heightProperty().multiply(0.27));
        leemgroeve.layoutXProperty().bind(widthProperty().multiply(0.58));
        leemgroeve.layoutYProperty().bind(heightProperty().multiply(0));

        steengroeve.prefWidthProperty().bind(widthProperty().multiply(0.38));
        steengroeve.minWidthProperty().bind(widthProperty().multiply(0.38));
        steengroeve.maxWidthProperty().bind(widthProperty().multiply(0.38));
        steengroeve.prefHeightProperty().bind(heightProperty().multiply(0.385));
        steengroeve.minHeightProperty().bind(heightProperty().multiply(0.385));
        steengroeve.maxHeightProperty().bind(heightProperty().multiply(0.385));
        steengroeve.layoutXProperty().bind(widthProperty().multiply(0.622));
        steengroeve.layoutYProperty().bind(heightProperty().multiply(0.275));

        rivier.prefWidthProperty().bind(widthProperty().multiply(0.385));
        rivier.minWidthProperty().bind(widthProperty().multiply(0.385));
        rivier.maxWidthProperty().bind(widthProperty().multiply(0.385));
        rivier.prefHeightProperty().bind(heightProperty().multiply(0.33));
        rivier.minHeightProperty().bind(heightProperty().multiply(0.33));
        rivier.maxHeightProperty().bind(heightProperty().multiply(0.33));
        rivier.layoutXProperty().bind(widthProperty().multiply(0.6145));
        rivier.layoutYProperty().bind(heightProperty().multiply(0.665));

        jacht.prefWidthProperty().bind(widthProperty().multiply(0.28));
        jacht.minWidthProperty().bind(widthProperty().multiply(0.28));
        jacht.maxWidthProperty().bind(widthProperty().multiply(0.28));
        jacht.prefHeightProperty().bind(heightProperty().multiply(0.35));
        jacht.minHeightProperty().bind(heightProperty().multiply(0.35));
        jacht.maxHeightProperty().bind(heightProperty().multiply(0.35));
        jacht.layoutXProperty().bind(widthProperty().multiply(0));
        jacht.layoutYProperty().bind(heightProperty().multiply(0));

    }

    public void setActions()
    {

        bos.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {

                if (controller.getLocatie("bos").getAantalPlaatsenVrij() > 0)
                    {

                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("bos")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("bos")));
                        }
                    }
                }
            });
        leemgroeve.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("leemgroeve").getAantalPlaatsenVrij() > 0)
                    {
                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("leemgroeve")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("leemgroeve")));
                        }
                    }
                }
            });
        steengroeve.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("steengroeve").getAantalPlaatsenVrij() > 0)
                    {
                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("steengroeve")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("steengroeve")));
                        }
                    }
                }
            });
        rivier.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("rivier").getAantalPlaatsenVrij() > 0)
                    {

                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("rivier")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("rivier")));
                        }
                    }
                }
            });
        jacht.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("jacht").getAantalPlaatsenVrij() > 0)
                    {

                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("jacht")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("jacht")));
                        }
                    }
                }
            });
        akker.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("akker").getAantalPlaatsenVrij() > 0)
                    {

                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("akker")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("akker")));
                        }

                    }
                }
            });
        gereedschapsmaker.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("gereedschapsmaker").getAantalPlaatsenVrij() > 0)
                    {
                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("gereedschapsmaker")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("gereedschapsmaker")));
                        }
                    }
                }
            });
        lovehut.setOnAction((event) ->
            {
            if (mainScherm.pionnenPlaatsen())
                {
                if (controller.getLocatie("lovehut").getAantalPlaatsenVrij() > 0)
                    {
                    if (controller.isLocatieNogNietGebruikt(controller.getLocatie("lovehut")))
                        {
                        getChildren().add(new LocatiePane(mainScherm, this, controller.getLocatie("lovehut")));
                        }
                    }
                }
            });
    }

    public void openLocationMenu(String location)
    {
        Locatie locatie = controller.getLocatie(location);

    }

    private void initLocations()
    {
        double scale = 0.05;
        arrowPane = new PionnenLocatieDeelPane(mainScherm, this, 0.12, 15.5, 4.43);
        arrowPane.setImage("arrow", new Pion(Kleur.BLAUW, true));
        bosLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 15.2, 2.6);
        bosLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 16, 3.7);
        bosLocaties[2] = new PionnenLocatieDeelPane(mainScherm, this, scale, 15.8, 1.5);
        bosLocaties[3] = new PionnenLocatieDeelPane(mainScherm, this, scale, 17, 2.4);
        bosLocaties[4] = new PionnenLocatieDeelPane(mainScherm, this, scale, 14.4, 4.3);
        bosLocaties[5] = new PionnenLocatieDeelPane(mainScherm, this, scale, 15.9, 5.8);
        bosLocaties[6] = new PionnenLocatieDeelPane(mainScherm, this, scale, 13, 5.5);

        leemgroeveLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 26.4, 5.3);
        leemgroeveLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 26.3, 1.6);
        leemgroeveLocaties[2] = new PionnenLocatieDeelPane(mainScherm, this, scale, 23.4, 2.5);
        leemgroeveLocaties[3] = new PionnenLocatieDeelPane(mainScherm, this, scale, 22.5, 4.8);
        leemgroeveLocaties[4] = new PionnenLocatieDeelPane(mainScherm, this, scale, 30.8, 0.5);
        leemgroeveLocaties[5] = new PionnenLocatieDeelPane(mainScherm, this, scale, 32.6, 1.5);
        leemgroeveLocaties[6] = new PionnenLocatieDeelPane(mainScherm, this, scale, 28, 3.9);

        steengroeveLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 29.8, 10.5);
        steengroeveLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 31.9, 10.4);
        steengroeveLocaties[2] = new PionnenLocatieDeelPane(mainScherm, this, scale, 32.8, 9.3);
        steengroeveLocaties[3] = new PionnenLocatieDeelPane(mainScherm, this, scale, 37.5, 7);
        steengroeveLocaties[4] = new PionnenLocatieDeelPane(mainScherm, this, scale, 38.4, 8.5);
        steengroeveLocaties[5] = new PionnenLocatieDeelPane(mainScherm, this, scale, 34.9, 9.4);
        steengroeveLocaties[6] = new PionnenLocatieDeelPane(mainScherm, this, scale, 36.9, 9.9);

        rivierLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 28, 14);
        rivierLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 30, 14);
        rivierLocaties[2] = new PionnenLocatieDeelPane(mainScherm, this, scale, 32, 14.3);
        rivierLocaties[3] = new PionnenLocatieDeelPane(mainScherm, this, scale, 34, 13.9);
        rivierLocaties[4] = new PionnenLocatieDeelPane(mainScherm, this, scale, 36, 14.2);
        rivierLocaties[5] = new PionnenLocatieDeelPane(mainScherm, this, scale, 37.5, 13.4);
        rivierLocaties[6] = new PionnenLocatieDeelPane(mainScherm, this, scale, 26.5, 13.2);

        lovehutLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 8.5, 17.5);
        lovehutLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 9.5, 17.5);

        akkerLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 14.8, 14.8);
        gereedschapsmakerLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3.8, 13.9);

        jachtLocaties[0] = new PionnenLocatieDeelPane(mainScherm, this, scale, 0.4, 0.6);
        jachtLocaties[1] = new PionnenLocatieDeelPane(mainScherm, this, scale, 1.5, 1.7);
        jachtLocaties[2] = new PionnenLocatieDeelPane(mainScherm, this, scale, 1.9, 0.3);
        jachtLocaties[3] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3.3, 0.2);
        jachtLocaties[4] = new PionnenLocatieDeelPane(mainScherm, this, scale, 1.0, 2.8);
        jachtLocaties[5] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3, 2.5);
        jachtLocaties[6] = new PionnenLocatieDeelPane(mainScherm, this, scale, 4.0, 1.6);
        jachtLocaties[7] = new PionnenLocatieDeelPane(mainScherm, this, scale, 4.6, 0.1);
        jachtLocaties[8] = new PionnenLocatieDeelPane(mainScherm, this, scale, 6.1, 0.3);
        jachtLocaties[9] = new PionnenLocatieDeelPane(mainScherm, this, scale, 6.6, 1.5);
        jachtLocaties[10] = new PionnenLocatieDeelPane(mainScherm, this, scale, 1.5, 4);
        jachtLocaties[11] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3, 3.8);
        jachtLocaties[12] = new PionnenLocatieDeelPane(mainScherm, this, scale, 10.2, 6.6);
        jachtLocaties[13] = new PionnenLocatieDeelPane(mainScherm, this, scale, 2.2, 5.2);
        jachtLocaties[14] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3.8, 4.85);
        jachtLocaties[15] = new PionnenLocatieDeelPane(mainScherm, this, scale, 4.8, 4);
        jachtLocaties[16] = new PionnenLocatieDeelPane(mainScherm, this, scale, 5.4, 5.2);
        jachtLocaties[17] = new PionnenLocatieDeelPane(mainScherm, this, scale, 1.8, 6.4);
        jachtLocaties[18] = new PionnenLocatieDeelPane(mainScherm, this, scale, 4.2, 6);
        jachtLocaties[19] = new PionnenLocatieDeelPane(mainScherm, this, scale, 6.8, 2.6);
        jachtLocaties[20] = new PionnenLocatieDeelPane(mainScherm, this, scale, 6.4, 3.7);
        jachtLocaties[21] = new PionnenLocatieDeelPane(mainScherm, this, scale, 6.9, 5.8);
        jachtLocaties[22] = new PionnenLocatieDeelPane(mainScherm, this, scale, 7.5, 0.7);
        jachtLocaties[23] = new PionnenLocatieDeelPane(mainScherm, this, scale, 7.9, 1.8);
        jachtLocaties[24] = new PionnenLocatieDeelPane(mainScherm, this, scale, 8.1, 2.9);
        jachtLocaties[25] = new PionnenLocatieDeelPane(mainScherm, this, scale, 7.7, 4);
        jachtLocaties[26] = new PionnenLocatieDeelPane(mainScherm, this, scale, 8.3, 5.1);
        jachtLocaties[27] = new PionnenLocatieDeelPane(mainScherm, this, scale, 9.1, 0.2);
        jachtLocaties[28] = new PionnenLocatieDeelPane(mainScherm, this, scale, 10.5, 0.6);
        jachtLocaties[29] = new PionnenLocatieDeelPane(mainScherm, this, scale, 9.4, 1.5);
        jachtLocaties[30] = new PionnenLocatieDeelPane(mainScherm, this, scale, 10.8, 1.8);
        jachtLocaties[31] = new PionnenLocatieDeelPane(mainScherm, this, scale, 10.2, 2.9);
        jachtLocaties[32] = new PionnenLocatieDeelPane(mainScherm, this, scale, 9.4, 3.9);
        jachtLocaties[33] = new PionnenLocatieDeelPane(mainScherm, this, scale, 10.7, 4.2);
        jachtLocaties[34] = new PionnenLocatieDeelPane(mainScherm, this, scale, 9.8, 5.2);
        jachtLocaties[35] = new PionnenLocatieDeelPane(mainScherm, this, scale, 2.3, 7.5);
        jachtLocaties[36] = new PionnenLocatieDeelPane(mainScherm, this, scale, 3.7, 7.1);
        jachtLocaties[37] = new PionnenLocatieDeelPane(mainScherm, this, scale, 5.8, 6.8);
        jachtLocaties[38] = new PionnenLocatieDeelPane(mainScherm, this, scale, 8.5, 6.4);
        jachtLocaties[39] = new PionnenLocatieDeelPane(mainScherm, this, scale, 7.2, 7.2);

        for (int i = 0; i < stapelLocaties.length; i++)
            {
            stapelLocaties[i] = new PionnenLocatieDeelPane(mainScherm, this, scale, 16.5, 7);
            stapelLocaties[i].setStyle("-fx-background-color:transparent;");
            }

        for (int i = 0; i < bosLocaties.length; i++)
            {
            getChildren().add(bosLocaties[i]);
            //bosLocaties[i].setImage("pionRood");

            getChildren().add(steengroeveLocaties[i]);
            //steengroeveLocaties[i].setImage("pionGeel");
            getChildren().add(leemgroeveLocaties[i]);
            //leemgroeveLocaties[i].setImage("pionBlauw");
            getChildren().add(rivierLocaties[i]);
            // rivierLocaties[i].setImage("pionGroen");

            bosLocaties[i].setStyle("-fx-background-color:transparent;");
            steengroeveLocaties[i].setStyle("-fx-background-color:transparent;");
            leemgroeveLocaties[i].setStyle("-fx-background-color:transparent;");
            rivierLocaties[i].setStyle("-fx-background-color:transparent;");

            }

        for (int i = 0; i < lovehutLocaties.length; i++)
            {
            getChildren().add(lovehutLocaties[i]);
            //lovehutLocaties[i].setImage("pionRood");
            lovehutLocaties[i].setStyle("-fx-background-color:transparent;");
            }

        for (int i = 0; i < stapelLocaties.length; i++)
            {
            // getChildren().add(stapelLocaties[i]);
            stapelLocaties[i].setStyle("-fx-background-color:transparent;");
            }

        for (int i = 0; i < jachtLocaties.length; i++)
            {
            getChildren().add(jachtLocaties[i]);
            //jachtLocaties[i].setImage("pionGroen");
            jachtLocaties[i].setStyle("-fx-background-color:transparent;");
            }

        getChildren().add(akkerLocaties[0]);
        getChildren().add(gereedschapsmakerLocaties[0]);

        // akkerLocatie.setImage("pionGroen");
        // gereedschapsmakerLocatie.setImage("pionBlauw");
    }

    public void plaatsOpLocatie(String naam, int aantal)
    {

    }

    public PionnenLocatieDeelPane[] getLocatieDeelPane(Locatie locatie)
    {

        if (locatie.getNaam().toLowerCase().contains("stapel"))
            {
            return stapelLocaties;
            }
        if (locatie == controller.getLocatie("akker"))
            {
            return akkerLocaties;
            }
        if (locatie == controller.getLocatie("gereedschapsmaker"))
            {
            return gereedschapsmakerLocaties;
            }
        if (locatie == controller.getLocatie("lovehut"))
            {
            return lovehutLocaties;
            }
        if (locatie == controller.getLocatie("jacht"))
            {
            return jachtLocaties;
            }
        if (locatie == controller.getLocatie("bos"))
            {
            return bosLocaties;
            }
        if (locatie == controller.getLocatie("steengroeve"))
            {
            return steengroeveLocaties;
            }
        if (locatie == controller.getLocatie("leemgroeve"))
            {
            return leemgroeveLocaties;
            }
        if (locatie == controller.getLocatie("rivier"))
            {
            return rivierLocaties;
            }
        return null;
    }

}
