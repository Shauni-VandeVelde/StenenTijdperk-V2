/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm;

import Domein.DomeinController;
import Domein.Locatie;
import Domein.Speler;
import Domein.Stapel;
import Sound.Player;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ui.CustomCursor;
import ui.frame.StartGui;
import ui.frame.mainScherm.bottom.ButtonsPane;
import ui.frame.mainScherm.bottom.Console;
import ui.frame.mainScherm.bottom.ConsolePane;
import ui.frame.mainScherm.center.CenterPane;
import ui.frame.mainScherm.center.EindeRondePane;
import ui.frame.mainScherm.center.EndOfGamePane;
import ui.frame.mainScherm.center.KoopHutPane;
import ui.frame.mainScherm.center.LocatiePane;
import ui.frame.mainScherm.center.PauzePane;
import ui.frame.mainScherm.center.StapelsPane;
import ui.frame.mainScherm.center.VoedselPane;
import ui.frame.mainScherm.right.DobbelPane;
import ui.frame.mainScherm.right.InventoryPane;


/**
 *
 * @author NotAvailable
 */
public class MainScherm extends BorderPane
{

    public static String[][] urls = new String[][]
        {
            {
            "arrow", "voedsel", "wood", "console", "dobbelbeker", "gereedschapfiche1", "gereedschapfiche2", "gereedschapfiche3", "gereedschapFiche4", "goud", "hout", "leem", "steen", "pionRood",
            "pionBlauw", "pionGeel", "pionGroen", "StapelKaarten", "stapelscherm", "inventory"
            },
            {
            "img/YellowArrow.png", "img/Voedselfiches.png", "img/TextureWoodResized.png", "img/ConsoleScherm.png", "img/DobbelSteenBeker.png", "img/GF_1.png", "img/GF_2.png", "img/GF_3.png", "img/GF_4.png", "img/Goud.png", "img/Hout.png", "img/Leem.png", "img/Steen.png", "img/PionRood2S.png", "img/PionBlauw2S.png", "img/PionGeel2S.png", "img/PionGroen2S.png", "img/StapelKaarten.png", "img/StapelPaneSchermB.png", "img/TextureInventory2.png"
            }
        };

    public static String texture = "img/TextureWoodResized.png";
    public static String textureConsole = "img/ConsoleScherm.png";
    public static String textureInv = "img/TextureInventory2.png";

    private DomeinController controller;
    public static Console console = new Console();
    private Stage stage;
    private VBox bottomVBox, centerRightInventoryMainVbox, centerLeftVbox;
    private HBox centerMainHBox;
    private Pane centerRightInventoryMainPane;
    private InventoryPane inventoryPane;
    private CenterPane spelbordPane;
    private StapelsPane stapelsPanel;
    private ButtonsPane bottomButtonsPanel;
    private ConsolePane consolePane;
    private boolean[] accept;

    private boolean first = true;
    private boolean finishRoundFirst = true;
    private boolean magPionnenPlaatsen = true;
    private boolean spelGestart = false;
    private boolean spelGedaan = false;
    private boolean isPlaying = true;
    private boolean firstDobbel = true;
    private boolean shouldPlayKoopHutSFX = true;
    private boolean shouldPlayEndOfRoundSFX = true;
    private boolean shouldPlayDobbelSFX = true;
    private boolean shouldPlayMenuSound = true;
    private boolean shouldPlayLocatieSFX = true;
    private ArrayList<Player> SFXPlayers = new ArrayList<>();
    private double masterVolume = 0.8, musicVolume = 0.38, SFXVolume = 0.7;

    public static String getUrl(String type)
    {

        String url = "";
        for (int i = 0; i < urls[0].length; i++)
            {
            if (type.trim().equalsIgnoreCase(urls[0][i]))
                {
                url = urls[1][i];
                }
            }
        return url;
    }

    public MainScherm(DomeinController controller, Stage stage)
    {

        this.controller = controller;
        this.stage = stage;

    }

    public void volgendeSpeler()
    {
        Button temp = new Button();
        if (controller.alleSpelersHebbenHunPionnenGeplaatst())
            {
            bottomButtonsPanel.toggleButtons();
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(600),
                            new KeyValue(temp.opacityProperty(), 0)));
            timeline.setOnFinished(new EventHandler<ActionEvent>()
            {
                public void handle(ActionEvent event)
                {
                    startDobbel();
                }

            });
            timeline.play();

            }
        else
            {
            controller.incrementHuidigeSpelerIndex();
            if (controller.getHuidigeSpeler().getAantalPionnenOver() == 0)
                {
                volgendeSpeler();
                }
            tabNaarHuidigeSpeler();
            bottomButtonsPanel.setHuidigeSpeler();
            }
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
    }

    public void bevestigPlaatsen(LocatiePane locatiePane)
    {
        controller.plaatsPionnenOpVeld(locatiePane.getLocatie(), locatiePane.getCurrentlySelectedNumber());
        setPionnenImages(locatiePane.getLocatie(), locatiePane.getCurrentlySelectedNumber(), controller.getHuidigeSpeler());
        getStapelsPane().updateStapels();
        volgendeSpeler();
        if (shouldPlayLocatieSFX)
            {
            queueSFX(locatiePane.getLocatie().getNaam(), -1);
            }
        FadeTransition ft = new FadeTransition(Duration.millis(150), locatiePane);
        ft.setFromValue(1);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(e -> spelbordPane.getChildren().remove(locatiePane));

    }

    public void startDobbel()
    {

        if (first)
            {
            if (shouldPlayDobbelSFX)
                {
                queueSFX("dice1", -1);
                }

            magPionnenPlaatsen = false;
            controller.setHuidigeSpelerIndex();
            first = false;
            }
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
        int spelerIndex = getVolgendeSpeler();
        if (spelerIndex != -1)
            {
            if (controller.moetNogRollen(spelerIndex))
                {
                Locatie locatie = controller.getVolgendeBezetteLocatie(spelerIndex);
                int rol = controller.getRol(spelerIndex, locatie);
                inventoryPane = null;
                setDobbelPane(new DobbelPane(this, controller.getSpelers().get(spelerIndex), centerRightInventoryMainVbox, rol, locatie));
                }
            }
        else
            {
            resetAccept();
            koopHut();
            }
    }

    public void bevestigDobbel(DobbelPane dobbel)
    {
        if (shouldPlayDobbelSFX)
            {
            queueSFX("dice2", -1);
            }

        if ((dobbel.getSpeler() != null) && (dobbel.getCurrentLocatie() != null) && (dobbel.getCurrentRol() != -1))
            {
            if (shouldPlayMenuSound)
                {
                queueSFX("menu", -1);
                }
            if (dobbel.getSpeler().getAantalBruikbaarGereedschap() == 0)
                {

                controller.geefResources(dobbel.getSpeler().index(), dobbel.getCurrentRol(), dobbel.getCurrentLocatie(),
                        0);
                }
            else
                {
                int totaalGereedschap = 0;

                boolean[] teGebruikenGereedschap = dobbel.getCurrentGereedschap();
                for (int i = 0; i < teGebruikenGereedschap.length; i++)
                    {
                    if (teGebruikenGereedschap[i] == true)
                        {
                        totaalGereedschap += dobbel.getSpeler().getGereedschapsFiches().get(i).getWaarde();
                        controller.setGereedschapGebruikt(dobbel.getSpeler(), i);
                        }
                    }
                controller.geefResources(dobbel.getSpeler().index(), dobbel.getCurrentRol(),
                        dobbel.getCurrentLocatie(),
                        totaalGereedschap);
                }
            controller.clearPionnenVanSpeler(dobbel.getSpeler().index(), dobbel.getCurrentLocatie());

            spelbordPane.clearPionnenVanLocatie(dobbel.getCurrentLocatie(), dobbel.getSpeler());
            if (getVolgendeSpeler() != -1)
                {
                startDobbel();
                }
            else
                {
                controller.setHuidigeSpelerIndex();
                resetAccept();
                HBox hbox = new HBox();
                FadeTransition ft = new FadeTransition(Duration.millis(800), hbox);
                ft.setFromValue(1);
                ft.setToValue(0.0);
                ft.setCycleCount(1);
                ft.play();
                ft.setOnFinished(e -> koopHut());

                first = true;
                }
            }
        else
            {
            System.err.println("dobbel null");
            controller.setHuidigeSpelerIndex();
            resetAccept();
            startVoeden();

            first = false;
            }

    }

    public void koopHut()
    {

        if (controller.getRondeNummer() <= 3)
            {
            spelbordPane.toggleArrow();
            }
        else
            {
            spelbordPane.removeArrow();
            }

        boolean finished = true;
        for (int i = 0; i < accept.length; i++)
            {
            if (accept[i] == false)
                {
                finished = false;
                }
            }
        if (finished)
            {
            resetAccept();
            controller.setHuidigeSpelerIndex();

            startVoeden();
            }

        else
            {
            Speler speler = controller.getHuidigeSpeler();
            if (accept[speler.index()] == false)
                {
                if (controller.getBezetteStapel(speler) != null)
                    {
                    Stapel stapel = controller.getBezetteStapel(speler);

                    if (stapel.getBovensteHut().kanHutKopen(speler))
                        {
                        CustomCursor tempCursor = new CustomCursor(this, false);
                        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
                        setKoopHutPane(new KoopHutPane(this, centerMainHBox, speler, stapel));

                        }
                    else
                        {
                        controller.clearPionnenVanSpeler(speler, stapel);
                        koopHut();
                        }
                    }
                else
                    {
                    accept[speler.index()] = true;
                    controller.incrementHuidigeSpelerIndex();
                    koopHut();
                    }
                }
            else
                {
                controller.incrementHuidigeSpelerIndex();
                koopHut();
                }

            }

    }

    public void beeindigKoopHut(Stapel stapel, KoopHutPane koopHutPane, boolean accepted)
    {

        if (accepted)
            {
            controller.verrekenStapel(stapel, koopHutPane.getSpeler());
            }
        else
            {
            controller.clearPionnenVanSpeler(koopHutPane.getSpeler(), stapel);
            controller.incrementHuidigeSpelerIndex();
            }

        koopHut();

    }

    public void startVoeden()
    {

        Speler speler = controller.getSpelers().get(controller.getHuidigeSpelerIndex());
        boolean finished = controller.voerVoedenUit(accept);
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
        if (finished)
            {

            finishRound();

            }
        else
            {
            if ((speler.getVoedseltekort() > 0) && (!accept[controller.getHuidigeSpelerIndex()]))
                {
                VoedselPane temp = new VoedselPane(this, centerMainHBox, speler);
                if (speler.getAantalGoud() + speler.getAantalHout() + speler.getAantalLeem() + speler.getAantalSteen() >= speler.getVoedseltekort())
                    {
                    setVoedselPane(temp);
                    }
                else
                    {
                    accept[controller.getHuidigeSpelerIndex()] = true;
                    controller.incrementHuidigeSpelerIndex();
                    startVoeden();
                    }
                }
            else
                {
                accept[controller.getHuidigeSpelerIndex()] = true;
                controller.incrementHuidigeSpelerIndex();

                startVoeden();

                }
            }

    }

    public void beeindigVoeden(VoedselPane v)
    {

        if (v.isInOrde())
            {
            controller.ruilHout(v.getAantalHout(), v.getSpeler());
            controller.ruilLeem(v.getAantalLeem(), v.getSpeler());
            controller.ruilSteen(v.getAantalSteen(), v.getSpeler());
            controller.ruilGoud(v.getAantalGoud(), v.getSpeler());
            }
        else
            {
            accept[v.getSpeler().index()] = true;
            }
        controller.incrementHuidigeSpelerIndex();
        startVoeden();
    }

    public void finishRound()
    {

        if (finishRoundFirst)
            {

            controller.voedPionnen();
            controller.verrekenAkker();
            controller.verrekenGereedschapsmaker();
            controller.verrekenLoveHut();
            setEindeRondePane(new EindeRondePane(this, centerMainHBox));
            finishRoundFirst = false;
            firstDobbel = true;
            }
        else
            {
            finishRoundFirst = true;
            first = true;
            System.err.println("Round " + controller.getRondeNummer() + ": " + " Finished");
            controller.resetVorigeLocaties();
            controller.resetLocaties();
            controller.resetPionnen();
            spelbordPane.resetAkker();
            spelbordPane.resetLovehut();
            spelbordPane.resetGereedschapsMaker();
            stapelsPanel.clearPionnen();
            resetAccept();

            controller.resetGebruiktGereedschap();
            stapelsPanel.updateStapels();
            spelbordPane.turnOffArrow();
            controller.incrementRondeNummer();
            controller.incrementStartSpelerNummer();
            controller.setHuidigeSpelerIndex();
            bottomButtonsPanel.update();
            magPionnenPlaatsen = true;
            tabNaarHuidigeSpeler();

            }
        if (controller.isHetSpelGedaan())
            {
            setEindScherm(new EndOfGamePane(this, centerRightInventoryMainPane));
            }

    }

    public void resetAccept()
    {
        for (int i = 0; i < accept.length; i++)
            {
            accept[i] = false;
            }
    }

    public boolean magPionnenPlaatsen()
    {
        return magPionnenPlaatsen;
    }

    public boolean isSpelGestart()
    {
        return spelGestart;
    }

    public boolean isSpelGedaan()
    {
        return spelGedaan;
    }

    private int getVolgendeSpeler()
    {
        int index;

        for (int i = 0; i < controller.getAantalSpelers(); i++)
            {
            index = controller.getHuidigeSpeler().index();

            if (controller.getVolgendeBezetteLocatie(index) != null)
                {
                return index;
                }
            controller.incrementHuidigeSpelerIndex();
            }
        return -1;
    }

    public DomeinController getController()
    {
        return controller;
    }

    //Sound related:
    public void queueSFX(String type, double volume)
    {
        Player player = new Player(this, type, volume);
        //System.out.println("type: " + type + " volume " + volume);
        SFXPlayers.add(player);

    }

    public void removeSFX(Player player)
    {
        if (player.isMusicLoop())
            {
            queueSFX("musicLoop", -1);
            }
        SFXPlayers.remove(player);
    }

    private void playMusic()
    {
        if (spelGestart)
            {
            if (!spelGedaan)
                {
                stopMusic();
                queueSFX("musicLoop", -1);
                }
            }
        else
            {

            queueSFX("mainMenu", -1);
            }
    }

    public void stopMusic()
    {
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (SFXPlayers.get(i).isMusic())
                {
                SFXPlayers.get(i).getPlayer().stop();
                }
            }
    }

    public void stopSoundEffects()
    {
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (SFXPlayers.get(i).isSoundEffect())
                {
                SFXPlayers.get(i).getPlayer().stop();
                }
            }
    }

    public void toggleMusic()
    {
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (isPlaying)
                {
                if (SFXPlayers.get(i).isMusic())
                    {
                    SFXPlayers.get(i).getPlayer().pause();
                    }
                isPlaying = false;
                }
            else
                {
                if (SFXPlayers.get(i).isMusic())
                    {
                    SFXPlayers.get(i).getPlayer().play();
                    }
                isPlaying = true;
                }
            }
    }

    public void toggleMenuSFX()
    {
        shouldPlayMenuSound = !shouldPlayMenuSound;
    }

    public void toggleDobbelSFX()
    {
        shouldPlayDobbelSFX = !shouldPlayDobbelSFX;
    }

    public void toggleKoopHutSFX()
    {
        shouldPlayKoopHutSFX = !shouldPlayKoopHutSFX;
    }

    public void toggleEndOfRoundSFX()
    {
        shouldPlayEndOfRoundSFX = !shouldPlayEndOfRoundSFX;
    }

    public void toggleLocatieSFX()
    {
        shouldPlayLocatieSFX = !shouldPlayLocatieSFX;
    }

    public void setMasterVolume(double masterVolume)
    {
        this.masterVolume = masterVolume;
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (SFXPlayers.get(i).isMusic())
                {
                SFXPlayers.get(i).getPlayer().setVolume(masterVolume * musicVolume);
                }
            if (SFXPlayers.get(i).isSoundEffect())
                {
                SFXPlayers.get(i).getPlayer().setVolume(masterVolume * SFXVolume);
                }
            }
    }

    public void setMusicVolume(double musicVolume)
    {
        this.musicVolume = musicVolume;
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (SFXPlayers.get(i).isMusic())
                {
                SFXPlayers.get(i).getPlayer().setVolume(masterVolume * musicVolume);
                }
            }
    }

    public void setSFXVolume(double SFXVolume)
    {
        this.SFXVolume = SFXVolume;
        for (int i = 0; i < SFXPlayers.size(); i++)
            {
            if (SFXPlayers.get(i).isSoundEffect())
                {
                SFXPlayers.get(i).getPlayer().setVolume(masterVolume * SFXVolume);
                }
            }
    }

    public double getMasterVolume()
    {
        return masterVolume;
    }

    public double getMusicVolume()
    {
        return musicVolume;
    }

    public double getSFXVolume()
    {
        return SFXVolume;
    }

    private File getFile(String path)
    {
        File file = null;
        String resource = path;
        URL res = getClass().getResource(resource);
        if (res.toString().startsWith("jar:"))
            {
            try
                {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1)
                    {
                    out.write(bytes, 0, read);
                    }
                file.deleteOnExit();
                }
            catch (IOException ex)
                {
                ex.printStackTrace();
                }
            }
        else
            {
            file = new File(res.getFile());
            }

        if (file != null && !file.exists())
            {
            throw new RuntimeException("Error: File " + file + " not found!");
            }
        return file;
    }

    //Gui things:
    public boolean openStartScherm()
    {
        Stage stage = new Stage();
        final ToggleGroup group = new ToggleGroup();
        VBox mainPanel = new VBox();
        TextField label = new TextField("Kies het aantal spelers: ");
        label.setEditable(false);
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        label.focusedProperty().addListener((observable, oldValue, newValue) ->
            {
            if (newValue && firstTime.get())
                {
                mainPanel.requestFocus();
                firstTime.setValue(false);
                }
            });
        HBox choicePanel = new HBox();
        Button okButton = new Button("Bevestig");
        RadioButton twee = new RadioButton("Twee Spelers");
        RadioButton drie = new RadioButton("Drie Spelers");
        RadioButton vier = new RadioButton("Vier Spelers");
        choicePanel.getChildren().add(twee);
        choicePanel.getChildren().add(drie);
        choicePanel.getChildren().add(vier);
        mainPanel.getChildren().add(label);
        mainPanel.getChildren().add(choicePanel);
        mainPanel.getChildren().add(okButton);

        Platform.runLater(() -> okButton.setPrefWidth(mainPanel.getWidth()));
        twee.setToggleGroup(group);
        twee.setSelected(true);
        drie.setToggleGroup(group);
        vier.setToggleGroup(group);
//        getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Cursor.css").toExternalForm());

        okButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {

                if (twee.isSelected())
                    {
                    controller.setAantalSpelers(2);
                    }
                if (drie.isSelected())
                    {
                    controller.setAantalSpelers(3);
                    }
                if (vier.isSelected())
                    {
                    controller.setAantalSpelers(4);
                    }

                stage.close();
            }

        });
        Scene scene = new Scene(mainPanel);
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        playMusic();
        stage.showAndWait();

        spelGestart = true;
        stopMusic();
        playMusic();
        /* Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5),
                        new KeyValue(musicPlayer.volumeProperty(), 0)));
        timeline.setOnFinished(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent event)
            {

                playMusic();
            }

        });
        timeline.play();*/
        playMusic();

        if (controller.getAantalSpelers() != 0)
            {
            return true;
            }
        else
            {
            return false;
            }
    }

    public void init()
    {

        initPanels();
        setBindings();
        tabNaarHuidigeSpeler();
        bottomButtonsPanel.update();
        try
            {
            Media media = new Media(getFile("/SFX/Akker1.mp3").toURI().toString());
            //System.out.println("Media " + media.getSource());
            }
        catch (Exception e)
            {
            e.printStackTrace();
            }
        CustomCursor tempCursor = new CustomCursor(this, false);
        ;
        Platform.runLater(() -> getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage())));
    }

    public void initCursor()
    {
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
    }

    private void initPanels()
    {
        accept = new boolean[controller.getAantalSpelers()];
        bottomVBox = new VBox();
        centerLeftVbox = new VBox();
        centerRightInventoryMainVbox = new VBox();
        centerMainHBox = new HBox();
        spelbordPane = new CenterPane(this);
        consolePane = new ConsolePane(console, stage);
        centerRightInventoryMainPane = new Pane();
        bottomButtonsPanel = new ButtonsPane(controller, console, this);
        stapelsPanel = new StapelsPane(this);

        centerLeftVbox.getChildren().add(spelbordPane);
        centerLeftVbox.getChildren().add(stapelsPanel);
        spelbordPane.setStyle("-fx-background-color:blue;");
        centerMainHBox.getChildren().add(centerLeftVbox);
        centerMainHBox.getChildren().add(centerRightInventoryMainPane);
        bottomVBox.getChildren().add(bottomButtonsPanel);
        bottomVBox.getChildren().add(consolePane);

        setCenter(centerMainHBox);
        setBottom(bottomVBox);

    }

    private void setBindings()
    {
        //Bindings for Bottom Panels
        bottomVBox.prefWidthProperty().bind(widthProperty());
        bottomVBox.minWidthProperty().bind(widthProperty());
        bottomVBox.maxWidthProperty().bind(widthProperty());
        bottomVBox.prefHeightProperty().bind(stage.heightProperty().multiply(0.23));
        bottomVBox.minHeightProperty().bind(stage.heightProperty().multiply(0.23));
        bottomVBox.maxHeightProperty().bind(stage.heightProperty().multiply(0.23));

        bottomButtonsPanel.prefWidthProperty().bind(bottomVBox.widthProperty());
        bottomButtonsPanel.minWidthProperty().bind(bottomVBox.widthProperty());
        bottomButtonsPanel.maxWidthProperty().bind(bottomVBox.widthProperty());
        bottomButtonsPanel.prefHeightProperty().bind(bottomVBox.heightProperty().multiply(0.24));
        bottomButtonsPanel.minHeightProperty().bind(bottomVBox.heightProperty().multiply(0.24));
        bottomButtonsPanel.maxHeightProperty().bind(bottomVBox.heightProperty().multiply(0.24));

        consolePane.prefWidthProperty().bind(bottomVBox.widthProperty());
        consolePane.minWidthProperty().bind(bottomVBox.widthProperty());
        consolePane.maxWidthProperty().bind(bottomVBox.widthProperty());
        consolePane.prefHeightProperty().bind(bottomVBox.heightProperty().multiply(0.76));
        consolePane.minHeightProperty().bind(bottomVBox.heightProperty().multiply(0.76));
        consolePane.maxHeightProperty().bind(bottomVBox.heightProperty().multiply(0.76));

        // Center Panels
        centerMainHBox.prefHeightProperty().bind((stage.heightProperty().multiply(0.77)));
        centerMainHBox.minHeightProperty().bind((stage.heightProperty().multiply(0.77)));
        centerMainHBox.maxHeightProperty().bind((stage.heightProperty().multiply(0.77)));
        centerMainHBox.prefWidthProperty().bind(stage.widthProperty());
        centerMainHBox.maxWidthProperty().bind(stage.widthProperty());
        centerMainHBox.minWidthProperty().bind(stage.widthProperty());

        centerLeftVbox.prefHeightProperty().bind(centerMainHBox.heightProperty());
        centerLeftVbox.minHeightProperty().bind(centerMainHBox.heightProperty());
        centerLeftVbox.maxHeightProperty().bind(centerMainHBox.heightProperty());
        centerLeftVbox.prefWidthProperty().bind(centerMainHBox.widthProperty().multiply(0.8));
        centerLeftVbox.maxWidthProperty().bind(centerMainHBox.widthProperty().multiply(0.8));
        centerLeftVbox.minWidthProperty().bind(centerMainHBox.widthProperty().multiply(0.8));

        spelbordPane.prefWidthProperty().bind(centerLeftVbox.widthProperty());
        spelbordPane.minWidthProperty().bind(centerLeftVbox.widthProperty());
        spelbordPane.maxWidthProperty().bind(centerLeftVbox.widthProperty());
        spelbordPane.prefHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.84));
        spelbordPane.minHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.84));
        spelbordPane.maxHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.84));

        stapelsPanel.prefWidthProperty().bind(centerLeftVbox.widthProperty());
        stapelsPanel.minWidthProperty().bind(centerLeftVbox.widthProperty());
        stapelsPanel.maxWidthProperty().bind(centerLeftVbox.widthProperty());
        stapelsPanel.prefHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.16));
        stapelsPanel.minHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.16));
        stapelsPanel.maxHeightProperty().bind(centerLeftVbox.heightProperty().multiply(0.16));

    }

    public void openPauzeMenu()
    {
        PauzePane p = new PauzePane(this.stage, this);

        getChildren().clear();

        setCenter(p);
        setBottom(bottomVBox);

        StartGui.isPaused = true;
        p.requestFocus();

    }

    public void closePauzeMenu()
    {
        getChildren().clear();

        setCenter(centerMainHBox);
        setBottom(bottomVBox);
        getBottomButtonsPanel().toggleButtons();
        StartGui.isPaused = false;
        centerMainHBox.requestFocus();

    }

    public void tabNaarHuidigeSpeler()
    {
        setInventoryPane(new InventoryPane(controller.getHuidigeSpeler(), this, centerRightInventoryMainVbox));
    }

    public void tabNaarSpeler(int index)
    {
        setInventoryPane(new InventoryPane(controller.getSpelers().get(index), this, centerRightInventoryMainVbox));
    }

    public void setPionnenImages(Locatie locatie, int aantal, Speler speler)
    {
        spelbordPane.setLocatieImages(locatie, aantal, speler);

    }

    public void setEindScherm(EndOfGamePane eindScherm)
    {
        getChildren().clear();

        getChildren().add(eindScherm);
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
    }

    public void setKoopHutPane(KoopHutPane koopHutPane)
    {

        centerMainHBox.getChildren().clear();

        centerMainHBox.getChildren().add(koopHutPane);

    }

    public void setInventoryPane(InventoryPane inventoryPane)
    {
        this.inventoryPane = inventoryPane;
        centerMainHBox.getChildren().clear();
        centerMainHBox.getChildren().add(centerLeftVbox);
        centerMainHBox.getChildren().add(this.inventoryPane);
        this.inventoryPane.requestFocus();
    }

    public void setDobbelPane(DobbelPane dobbelPane)
    {
        if (firstDobbel)
            {

            if (controller.getRondeNummer() <= 3)
                {
                spelbordPane.toggleArrow();
                }

            firstDobbel = false;
            }
        centerMainHBox.getChildren().clear();
        centerMainHBox.getChildren().add(centerLeftVbox);
        centerMainHBox.getChildren().add(dobbelPane);
        dobbelPane.requestFocus();
    }

    public void setVoedselPane(VoedselPane voedselPane)
    {
        centerMainHBox.getChildren().clear();
        centerMainHBox.getChildren().add(voedselPane);
        voedselPane.requestFocus();

    }

    public void setEindeRondePane(EindeRondePane eindeRondePane)
    {
        if (shouldPlayEndOfRoundSFX)
            {
            queueSFX("gong", -1);
            }
        //System.err.println("setVoedselPanel()");
        centerMainHBox.getChildren().clear();

        centerMainHBox.getChildren().add(eindeRondePane);
        eindeRondePane.requestFocus();
    }

    public boolean shouldPlayKoopHutSFX()
    {

        return shouldPlayKoopHutSFX;
    }

    public boolean shouldPlayMenuSFX()

    {
        return shouldPlayMenuSound;
    }

    public boolean shouldPlayDobbelSFX()
    {
        return shouldPlayDobbelSFX;
    }

    public boolean shouldPlayEndOfRoundSFX()
    {
        return shouldPlayEndOfRoundSFX;
    }

    public boolean shouldPlayLocatieSFX()
    {
        return shouldPlayLocatieSFX;
    }

    public Stage getStage()
    {
        return stage;
    }

    public Pane getSpelbordPane()
    {
        return spelbordPane;
    }

    public VBox getCenterRightInventoryMainVbox()
    {
        return centerRightInventoryMainVbox;
    }

    public Pane getCenterRightInventoryMainPane()
    {
        return centerRightInventoryMainPane;
    }

    public Pane getCenterLeftVBox()
    {
        return centerLeftVbox;
    }

    public StapelsPane getStapelsPane()
    {
        return stapelsPanel;
    }

    public ButtonsPane getBottomButtonsPanel()
    {
        return bottomButtonsPanel;
    }

}
