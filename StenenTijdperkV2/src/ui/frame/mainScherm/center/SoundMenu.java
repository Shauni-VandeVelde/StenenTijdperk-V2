/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.DeelPaneel;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author NotAvailable
 */
public class SoundMenu extends RightPaneBlueprint
{
    private Slider masterVolumeSlider, musicVolumeSlider, SFXVolumeSlider;
    private Button acceptButton;
    private CheckBox menuSounds, locationSounds, endOfRoundSounds, koopHutSounds, dobbelSounds;
    private Label masterLabel, musicLabel, SFXLabel;
    private PauzePane pauzePane;
    private double masterVolume, musicVolume, SFXVolume;

    public SoundMenu(MainScherm mainScherm, PauzePane pauzePane)
    {
        super(mainScherm, mainScherm.getController().getSpelers().get(0), pauzePane, 1, 0.77);
        this.pauzePane = pauzePane;
        init();
        setActions();
    }

    public void init()
    {
        HBox options = new HBox();
        menuSounds = new CheckBox("Menu SFX");
        locationSounds = new CheckBox("Locaties SFX");
        endOfRoundSounds = new CheckBox("Einde Ronde SFX");
        koopHutSounds = new CheckBox("Koop Hut SFX");
        dobbelSounds = new CheckBox("Dobbel SFX");
        menuSounds.setWrapText(true);
        locationSounds.setWrapText(true);
        endOfRoundSounds.setWrapText(true);
        koopHutSounds.setWrapText(true);
        dobbelSounds.setWrapText(true);

        menuSounds.setStyle("-fx-text-fill:white;");
        locationSounds.setStyle("-fx-text-fill:white;");
        endOfRoundSounds.setStyle("-fx-text-fill:white;");
        koopHutSounds.setStyle("-fx-text-fill:white;");
        dobbelSounds.setStyle("-fx-text-fill:white;");

        ImageView background = mainScherm.getImageView("img/PauzeMenuBg.png");
        background.fitWidthProperty().bind(widthProperty());
        background.fitHeightProperty().bind(heightProperty());

        getChildren().add(background);
        DeelPaneel masterLabelPaneel = new DeelPaneel(mainScherm,0.4, 0.24, this, true);
        DeelPaneel musicLabelPaneel = new DeelPaneel(mainScherm,0.4, 0.24, this, true);
        DeelPaneel SFXLabelPaneel = new DeelPaneel(mainScherm,0.4, 0.24, this, true);

        DeelPaneel menuSoundsDeelPaneel = new DeelPaneel(mainScherm,0.2, 0.15, this, true);
        DeelPaneel locationSoundsDeelPaneel = new DeelPaneel(mainScherm,0.2, 0.15, this, true);
        DeelPaneel endOfRoundSoundsDeelPaneel = new DeelPaneel(mainScherm,0.2, 0.15, this, true);
        DeelPaneel koopHutSoundsDeelPaneel = new DeelPaneel(mainScherm,0.2, 0.15, this, true);
        DeelPaneel dobbelSoundsDeelPaneel = new DeelPaneel(mainScherm,0.2, 0.15, this, true);

        VBox labels = new VBox();
        VBox sliders = new VBox();
        VBox masterSliderPaneel = new VBox();
        VBox musicSliderPaneel = new VBox();
        VBox effectsSliderPaneel = new VBox();
        masterVolumeSlider = new Slider();
        musicVolumeSlider = new Slider();
        SFXVolumeSlider = new Slider();
        acceptButton = new Button("Bevestig");
        masterLabel = new Label("Master Volume: " + mainScherm.getMasterVolume());
        musicLabel = new Label("Music Volume: " + mainScherm.getMusicVolume());
        SFXLabel = new Label("Effects Volume: " + mainScherm.getSFXVolume());

        menuSoundsDeelPaneel.addCheckBox(menuSounds, 1, 1);
        locationSoundsDeelPaneel.addCheckBox(locationSounds, 1, 1);
        endOfRoundSoundsDeelPaneel.addCheckBox(endOfRoundSounds, 1, 1);
        koopHutSoundsDeelPaneel.addCheckBox(koopHutSounds, 1, 1);
        dobbelSoundsDeelPaneel.addCheckBox(dobbelSounds, 1, 1);

        options.getChildren().add(menuSoundsDeelPaneel);
        options.getChildren().add(locationSoundsDeelPaneel);
        options.getChildren().add(endOfRoundSoundsDeelPaneel);
        options.getChildren().add(koopHutSoundsDeelPaneel);
        options.getChildren().add(dobbelSoundsDeelPaneel);

        labels.getChildren().add(masterLabelPaneel);
        labels.getChildren().add(musicLabelPaneel);
        labels.getChildren().add(SFXLabelPaneel);
        sliders.getChildren().add(masterSliderPaneel);
        sliders.getChildren().add(musicSliderPaneel);
        sliders.getChildren().add(effectsSliderPaneel);
        masterLabelPaneel.addLabel(masterLabel, 1);
        musicLabelPaneel.addLabel(musicLabel, 1);
        SFXLabelPaneel.addLabel(SFXLabel, 1);
        masterSliderPaneel.getChildren().add(masterVolumeSlider);
        musicSliderPaneel.getChildren().add(musicVolumeSlider);
        effectsSliderPaneel.getChildren().add(SFXVolumeSlider);

        getChildren().add(labels);
        getChildren().add(sliders);
        getChildren().add(acceptButton);
        getChildren().add(options);
        options.layoutXProperty().bind(widthProperty().multiply(0.01));
        options.layoutYProperty().bind(heightProperty().multiply(0.725));

        labels.layoutXProperty().bind(widthProperty().multiply(0.1));
        labels.layoutYProperty().bind(heightProperty().multiply(0.07));
        sliders.layoutXProperty().bind(widthProperty().multiply(0.48));
        sliders.layoutYProperty().bind(heightProperty().multiply(0.07));
        acceptButton.layoutXProperty().bind(widthProperty().multiply(0.35));
        acceptButton.layoutYProperty().bind(heightProperty().multiply(0.875));
        labels.prefWidthProperty().bind(masterLabelPaneel.widthProperty());
        labels.prefHeightProperty().bind(masterLabelPaneel.heightProperty());
        labels.minWidthProperty().bind(masterLabelPaneel.widthProperty());
        labels.maxHeightProperty().bind(masterLabelPaneel.heightProperty());
        labels.maxWidthProperty().bind(masterLabelPaneel.widthProperty());
        labels.minHeightProperty().bind(masterLabelPaneel.heightProperty());

        acceptButton.prefWidthProperty().bind(widthProperty().multiply(0.3));
        acceptButton.prefHeightProperty().bind(heightProperty().multiply(0.13));
        acceptButton.minWidthProperty().bind(widthProperty().multiply(0.3));
        acceptButton.maxHeightProperty().bind(heightProperty().multiply(0.13));
        acceptButton.maxWidthProperty().bind(widthProperty().multiply(0.3));
        acceptButton.minHeightProperty().bind(heightProperty().multiply(0.13));

        masterSliderPaneel.prefWidthProperty().bind(widthProperty().multiply(0.4));
        masterSliderPaneel.minWidthProperty().bind(widthProperty().multiply(0.4));
        masterSliderPaneel.maxWidthProperty().bind(widthProperty().multiply(0.4));
        masterSliderPaneel.prefHeightProperty().bind(heightProperty().multiply(0.24));
        masterSliderPaneel.minHeightProperty().bind(heightProperty().multiply(0.24));
        masterSliderPaneel.maxHeightProperty().bind(heightProperty().multiply(0.24));

        musicSliderPaneel.prefWidthProperty().bind(widthProperty().multiply(0.4));
        musicSliderPaneel.minWidthProperty().bind(widthProperty().multiply(0.4));
        musicSliderPaneel.maxWidthProperty().bind(widthProperty().multiply(0.4));
        musicSliderPaneel.prefHeightProperty().bind(heightProperty().multiply(0.24));
        musicSliderPaneel.minHeightProperty().bind(heightProperty().multiply(0.24));
        musicSliderPaneel.maxHeightProperty().bind(heightProperty().multiply(0.24));

        effectsSliderPaneel.prefWidthProperty().bind(widthProperty().multiply(0.4));
        effectsSliderPaneel.minWidthProperty().bind(widthProperty().multiply(0.4));
        effectsSliderPaneel.maxWidthProperty().bind(widthProperty().multiply(0.4));
        effectsSliderPaneel.prefHeightProperty().bind(heightProperty().multiply(0.24));
        effectsSliderPaneel.minHeightProperty().bind(heightProperty().multiply(0.24));
        effectsSliderPaneel.maxHeightProperty().bind(heightProperty().multiply(0.24));

        masterLabelPaneel.setFontTracking(masterLabel, 27);
        musicLabelPaneel.setFontTracking(musicLabel, 27);
        SFXLabelPaneel.setFontTracking(SFXLabel, 27);

        menuSoundsDeelPaneel.setFontTracking(menuSounds, 24);
        locationSoundsDeelPaneel.setFontTracking(locationSounds, 24);
        endOfRoundSoundsDeelPaneel.setFontTracking(endOfRoundSounds, 24);
        koopHutSoundsDeelPaneel.setFontTracking(koopHutSounds, 24);
        dobbelSoundsDeelPaneel.setFontTracking(dobbelSounds, 24);

        masterVolumeSlider.setValue(mainScherm.getMasterVolume());
        musicVolumeSlider.setValue(mainScherm.getMusicVolume());
        SFXVolumeSlider.setValue(mainScherm.getSFXVolume());

        masterVolumeSlider.setMax(1);
        masterVolumeSlider.setMin(0);
        musicVolumeSlider.setMax(1);
        musicVolumeSlider.setMin(0);
        SFXVolumeSlider.setMax(1);
        SFXVolumeSlider.setMin(0);

        masterVolumeSlider.setShowTickMarks(true);
        masterVolumeSlider.setShowTickLabels(false);
        musicVolumeSlider.setShowTickMarks(false);
        musicVolumeSlider.setShowTickLabels(false);
        SFXVolumeSlider.setShowTickMarks(false);
        SFXVolumeSlider.setShowTickLabels(false);

        if (mainScherm.shouldPlayMenuSFX())
            {
            menuSounds.setSelected(true);
            }
        else
            {
            menuSounds.setSelected(false);
            }
        if (mainScherm.shouldPlayDobbelSFX())
            {
            dobbelSounds.setSelected(true);
            }
        else
            {
            dobbelSounds.setSelected(false);
            }
        if (mainScherm.shouldPlayEndOfRoundSFX())
            {
            endOfRoundSounds.setSelected(true);
            }
        else
            {
            endOfRoundSounds.setSelected(false);
            }
        if (mainScherm.shouldPlayKoopHutSFX())
            {
            koopHutSounds.setSelected(true);
            }
        else
            {
            koopHutSounds.setSelected(false);
            }
        if (mainScherm.shouldPlayLocatieSFX())
            {
            locationSounds.setSelected(true);
            }
        else
            {
            locationSounds.setSelected(false);
            }

        masterSliderPaneel.setAlignment(Pos.CENTER);
        musicSliderPaneel.setAlignment(Pos.CENTER);
        effectsSliderPaneel.setAlignment(Pos.CENTER);
        masterLabel.setAlignment(Pos.CENTER);
        musicLabel.setAlignment(Pos.CENTER);
        SFXLabel.setAlignment(Pos.CENTER);

        acceptButton.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());

    }

    public void setActions()
    {

        masterVolumeSlider.valueProperty().addListener(e ->
            {
            mainScherm.setMasterVolume(masterVolumeSlider.getValue());
            masterLabel.setText("Master Volume: " + masterVolumeSlider.getValue());
            });
        musicVolumeSlider.valueProperty().addListener(e ->
            {
            mainScherm.setMusicVolume(musicVolumeSlider.getValue());
            musicLabel.setText("Master Volume: " + musicVolumeSlider.getValue());
            });
        SFXVolumeSlider.valueProperty().addListener(e ->
            {
            mainScherm.setSFXVolume(SFXVolumeSlider.getValue());
            SFXLabel.setText("Master Volume: " + SFXVolumeSlider.getValue());
            });
        acceptButton.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            masterVolume = masterVolumeSlider.getValue();
            musicVolume = musicVolumeSlider.getValue();
            SFXVolume = SFXVolumeSlider.getValue();

            pauzePane.confirmSFX(this);

            });
        menuSounds.selectedProperty().addListener(e ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.toggleMenuSFX();
            });
        dobbelSounds.selectedProperty().addListener(e ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.toggleDobbelSFX();
            });
        koopHutSounds.selectedProperty().addListener(e ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.toggleKoopHutSFX();
            });
        endOfRoundSounds.selectedProperty().addListener(e ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.toggleEndOfRoundSFX();
            });
        locationSounds.selectedProperty().addListener(e ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            mainScherm.toggleLocatieSFX();
            });
    }

    public double getSFXVolume()
    {
        return SFXVolume;
    }

    public double getMusicVolume()
    {
        return musicVolume;
    }

    public double getMasterVolume()
    {
        return masterVolume;
    }

}
