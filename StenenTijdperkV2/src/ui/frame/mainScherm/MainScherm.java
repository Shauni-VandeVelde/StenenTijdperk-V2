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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.ImageCursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.stage.Stage;
import javafx.util.Duration;
import ui.CustomCursor;
import ui.frame.StartGui;
import ui.frame.StartScherm;
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
            "start", "arrow", "voedsel", "wood", "console", "dobbelbeker", "gereedschapfiche1", "gereedschapfiche2", "gereedschapfiche3", "gereedschapFiche4", "goud", "hout", "leem", "steen", "pionRood",
            "pionBlauw", "pionGeel", "pionGroen", "StapelKaarten", "stapelscherm", "inventory"
            },
            {
            "img/StartScherm.jpg", "img/YellowArrow.png", "img/Voedselfiches.png", "img/TextureWoodResized.png", "img/ConsoleScherm.png", "img/DobbelSteenBeker.png", "img/GF_1.png", "img/GF_2.png", "img/GF_3.png", "img/GF_4.png", "img/Goud.png", "img/Hout.png", "img/Leem.png", "img/Steen.png", "img/PionRood2S.png", "img/PionBlauw2S.png", "img/PionGeel2S.png", "img/PionGroen2S.png", "img/StapelKaarten.png", "img/StapelPaneSchermB.png", "img/TextureInventory2.png"
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

    private boolean firstDobbel = true;
    private boolean finishRoundFirst = true;
    private boolean isPionnenPlaatsenFase = true;
    private boolean isSpelGestart = false;
    private boolean isSpelGedaan = false;
    private boolean isPlayingMusic = true;
    private boolean eersteDobbelVanRonde = true;
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

    public MainScherm(Stage stage)
    {
        this.stage = stage;
        getChildren().clear();
        StartScherm startScherm = new StartScherm(this);

        getChildren().add(startScherm);

        startScherm.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(2800), startScherm);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }

    public void startSpel(int aantal)
    {
        fadeMusic();
        isSpelGestart = true;
        playMusic();
        initPanels();
        controller = new DomeinController(true);
        controller.setAantalSpelers(aantal);
        controller.startSpel();
        stapelsPanel.init();
        bottomButtonsPanel.initAfter();
        setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(1200), this);
        ft.setFromValue(0);
        ft.setToValue(1.1);
        ft.setCycleCount(1);
        ft.play();
        init();
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
        console.printLine(locatiePane.getCurrentlySelectedNumber() + " pionnen geplaatst op locatie: " + locatiePane.getLocatie().getNaam());
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

        if (firstDobbel)
            {
            if (shouldPlayDobbelSFX)
                {
                queueSFX("dice1", -1);
                }

            isPionnenPlaatsenFase = false;
            controller.setHuidigeSpelerIndex();
            firstDobbel = false;
            }
        CustomCursor tempCursor = new CustomCursor(this, false);
        getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
        int spelerIndex = getVolgendeSpeler();
        if (spelerIndex != -1)
            {
            console.printLine("Speler " + controller.getSpelers().get(spelerIndex).getKleur() + " is aan beurt.");
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

                controller.geefResources(dobbel.getSpeler().index(), dobbel.getCurrentRol(), dobbel.getCurrentLocatie(), 0);
                console.printLine("Speler " + dobbel.getSpeler().getKleur() + " heeft " + dobbel.getCurrentRol() + " gerold op locatie: " + dobbel.getCurrentLocatie().getNaam());
                console.printLine("Hij kreeg hiervoor " + (dobbel.getCurrentRol() / dobbel.getCurrentLocatie().getWaarde()) + " " + controller.getNaamVanResource(dobbel.getCurrentLocatie()) + ".");
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

                console.printLine("Speler " + dobbel.getSpeler().getKleur() + " heeft " + dobbel.getCurrentRol() + " gerold op locatie: " + dobbel.getCurrentLocatie().getNaam() + " en gebruikte voor deze locatie " + totaalGereedschap + " gereedschap.");

                console.printLine("En kreeg hiervoor " + (dobbel.getCurrentRol() / dobbel.getCurrentLocatie().getWaarde()) + " " + controller.getNaamVanResource(dobbel.getCurrentLocatie()) + ".");
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

                firstDobbel = true;
                }
            }
        else
            {
            System.err.println("dobbel null");
            controller.setHuidigeSpelerIndex();
            resetAccept();
            startVoeden();
            firstDobbel = false;
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
            console.printLine("Hut gekocht door speler " + koopHutPane.getSpeler().getKleur());
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
            if (!accept[controller.getHuidigeSpelerIndex()])
                {
                if (speler.getVoedseltekort() > 0)
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
                        console.printLine("Speler " + speler.getKleur() + " kon de upkeep van pionnen niet betalen en kreeg 10 minpunten.");
                        startVoeden();
                        }
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
            console.printLine("Speler " + v.getSpeler().getKleur() + " wou de upkeep van pionnen niet betalen en kreeg 10 minpunten.");
            }
        controller.incrementHuidigeSpelerIndex();
        startVoeden();
    }

    private void endGame()
    {
        setEindScherm(new EndOfGamePane(this, centerRightInventoryMainPane));
        FadeTransition ft = new FadeTransition(Duration.millis(550), this);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }

    public void finishRound()
    {

        if (finishRoundFirst)
            {
            controller.voedPionnen();
            controller.verrekenAkker();
            controller.verrekenGereedschapsmaker();
            controller.verrekenLoveHut();
            finishRoundFirst = false;
            eersteDobbelVanRonde = true;
            if (!controller.isHetSpelGedaan())
                {
                setEindeRondePane(new EindeRondePane(this, centerMainHBox));
                }
            }
        else
            {
            finishRoundFirst = true;
            firstDobbel = true;
            System.err.println("Round " + controller.getRondeNummer() + ": " + " Finished");
            console.printLine("Round " + controller.getRondeNummer() + ": " + " Finished");
            resetAccept();
            controller.resetVorigeLocaties();
            controller.resetLocaties();
            controller.resetPionnen();
            controller.resetGebruiktGereedschap();
            controller.incrementRondeNummer();
            controller.incrementStartSpelerNummer();
            controller.setHuidigeSpelerIndex();
            spelbordPane.resetAkker();
            spelbordPane.resetLovehut();
            spelbordPane.resetGereedschapsMaker();
            stapelsPanel.clearPionnen();

            spelbordPane.turnOffArrow();
            stapelsPanel.updateStapels();
            bottomButtonsPanel.update();
            isPionnenPlaatsenFase = true;
            tabNaarHuidigeSpeler();

            }

        if (controller.isHetSpelGedaan())
            {
            FadeTransition ft = new FadeTransition(Duration.millis(550), this);
            ft.setFromValue(1);
            ft.setToValue(0);
            ft.setCycleCount(1);
            ft.play();
            ft.setOnFinished(e -> endGame());
            stopMusic();

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
        return isPionnenPlaatsenFase;
    }

    public boolean isIsSpelGestart()
    {
        return isSpelGestart;
    }

    public boolean isIsSpelGedaan()
    {
        return isSpelGedaan;
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

        SFXPlayers.remove(player);
    }

    public void playMusic()
    {
        if (isSpelGestart)
            {
            if (!isSpelGedaan)
                {
                fadeMusic();
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
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(50),
                            new KeyValue(SFXPlayers.get(i).getPlayer().volumeProperty(), 0)));
            timeline.play();
            int j = i;
            timeline.setOnFinished(e -> remove(j));
            }
    }

    private void remove(int j)
    {
        if (j < SFXPlayers.size())
            {
            SFXPlayers.remove(SFXPlayers.get(j));
            }
    }

    public void fadeMusic()
    {
        for (int i = 0; i < SFXPlayers.size(); i++)
            {

            if (SFXPlayers.get(i).isMusic())
                {
                Timeline timeline = new Timeline(
                        new KeyFrame(Duration.millis(1200),
                                new KeyValue(SFXPlayers.get(i).getPlayer().volumeProperty(), 0)));
                timeline.play();
                int index = i;
                if (i < SFXPlayers.size())
                    {
                    try
                        {
                        timeline.setOnFinished(e -> SFXPlayers.get(index).getPlayer().stop());

                        }
                    catch (IndexOutOfBoundsException e)
                        {
                        //Indien een van de SFX zich ondertussen heeft verwijdert uit de lijst.
                        continue;
                        }
                    }

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
            if (isPlayingMusic)
                {
                if (SFXPlayers.get(i).isMusic())
                    {
                    SFXPlayers.get(i).getPlayer().pause();
                    }
                isPlayingMusic = false;
                }
            else
                {
                if (SFXPlayers.get(i).isMusic())
                    {
                    SFXPlayers.get(i).getPlayer().play();
                    }
                isPlayingMusic = true;
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
    public void init()
    {
        accept = new boolean[controller.getAantalSpelers()];
        setCenter(centerMainHBox);
        setBottom(bottomVBox);
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

        bottomVBox = new VBox();
        centerLeftVbox = new VBox();
        centerRightInventoryMainVbox = new VBox();
        centerMainHBox = new HBox();
        spelbordPane = new CenterPane(this);
        consolePane = new ConsolePane(this, console, stage);
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
        PauzePane p = new PauzePane(this.stage, this, controller);

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
        queueSFX("win", 80);
        getChildren().clear();

        getChildren().add(eindScherm);
        /// controller.setHuidigeSpelerIndex();
        // CustomCursor tempCursor = new CustomCursor(this, false);
        //getStage().getScene().setCursor(new ImageCursor(tempCursor.getImage()));
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
        if (eersteDobbelVanRonde)
            {

            if (controller.getRondeNummer() <= 3)
                {
                spelbordPane.toggleArrow();
                }

            eersteDobbelVanRonde = false;
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

    public ImageView getImageView(String path)
    {
        ImageView imageView;
        try
            {
            imageView = new ImageView(this.getClass().getClassLoader().getResource(path).toExternalForm());
            return imageView;
            }
        catch (Exception e)
            {
            System.err.println("File Not Found: " + path);
            }
        return null;

    }

}
