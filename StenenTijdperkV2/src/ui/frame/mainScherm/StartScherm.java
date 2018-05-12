
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.DeelPaneel;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author NotAvailable
 */
public class StartScherm extends RightPaneBlueprint
{

    private int aantalSpelers = 0;
    private BorderPane mainBorderPane;
    private ImageView backgroundImage;
    private Button nieuwSpel, laadSpel, highscores;

    public StartScherm(MainScherm mainScherm)
    {
        super(mainScherm, mainScherm, 1, 1);
        showHomeScreen();
        setActions();

    }

    private void showHomeScreen()
    {
        backgroundImage = mainScherm.getImageView(mainScherm.getUrl("start"));
        mainBorderPane = new BorderPane();

        VBox homeScreenVBox = new VBox();
        nieuwSpel = new Button("Nieuw Spel");
        laadSpel = new Button("Laad Spel");
        highscores = new Button("Highscores");
        DeelPaneel nieuwSpelDeelPaneel = new DeelPaneel(mainScherm, 1, 0.33, homeScreenVBox, true);
        DeelPaneel loadSpelDeelPaneel = new DeelPaneel(mainScherm, 1, 0.33, homeScreenVBox, true);
        DeelPaneel highscoresDeelPaneel = new DeelPaneel(mainScherm, 1, 0.33, homeScreenVBox, true);

        nieuwSpelDeelPaneel.addButton(nieuwSpel, 1, 0.8);
        loadSpelDeelPaneel.addButton(laadSpel, 1, 0.8);
        highscoresDeelPaneel.addButton(highscores, 1, 0.8);

        //
        mainBorderPane.setCenter(homeScreenVBox);
        homeScreenVBox.getChildren().add(nieuwSpelDeelPaneel);
        homeScreenVBox.getChildren().add(loadSpelDeelPaneel);
        homeScreenVBox.getChildren().add(highscoresDeelPaneel);
        getChildren().add(backgroundImage);
        getChildren().add(mainBorderPane);

        //
        prefWidthProperty().bind(mainScherm.widthProperty());
        minWidthProperty().bind(mainScherm.widthProperty());
        maxWidthProperty().bind(mainScherm.widthProperty());
        prefHeightProperty().bind(mainScherm.heightProperty());
        minHeightProperty().bind(mainScherm.heightProperty());
        maxHeightProperty().bind(mainScherm.heightProperty());
        homeScreenVBox.prefWidthProperty().bind(mainScherm.widthProperty().multiply(0.65));
        homeScreenVBox.minWidthProperty().bind(mainScherm.widthProperty().multiply(0.65));
        homeScreenVBox.maxWidthProperty().bind(mainScherm.widthProperty().multiply(0.65));
        homeScreenVBox.prefHeightProperty().bind(mainScherm.heightProperty().multiply(0.7));
        homeScreenVBox.minHeightProperty().bind(mainScherm.heightProperty().multiply(0.7));
        homeScreenVBox.maxHeightProperty().bind(mainScherm.heightProperty().multiply(0.7));
        mainBorderPane.prefWidthProperty().bind(mainScherm.getStage().widthProperty());
        mainBorderPane.prefHeightProperty().bind(mainScherm.getStage().heightProperty());
        backgroundImage.fitWidthProperty().bind(mainScherm.getStage().widthProperty());
        backgroundImage.fitHeightProperty().bind(mainScherm.getStage().heightProperty());

        //
        nieuwSpelDeelPaneel.setFontTracking(nieuwSpel, 50);
        loadSpelDeelPaneel.setFontTracking(laadSpel, 50);
        highscoresDeelPaneel.setFontTracking(highscores, 50);
        homeScreenVBox.setAlignment(Pos.CENTER);

        nieuwSpel.setStyle("-fx-background-color:transparent;-fx-text-fill:white;");
        laadSpel.setStyle("-fx-background-color:transparent;-fx-text-fill:white;");
        highscores.setStyle("-fx-background-color:transparent;-fx-text-fill:white;");

        //
        mainScherm.playMusic();

    }

    private void setActions()
    {
        nieuwSpel.setOnAction(e -> selectPlayers());
    }

    private void selectPlayers()
    {
        final ToggleGroup group = new ToggleGroup();
        final BooleanProperty firstTime = new SimpleBooleanProperty(true);
        VBox selectPlayersVBox = new VBox();
        HBox keuzePaneel = new HBox();
        Button bevestigButton = new Button("Bevestig");
        RadioButton tweeSpelersRadioButton = new RadioButton("Twee");
        RadioButton drieSpelersRadioButton = new RadioButton("Drie");
        RadioButton vierSpelersRadioButton = new RadioButton("Vier");
        Label aantalSpelersLabel = new Label("Kies het aantal spelers: ");

        DeelPaneel bevestigDeelPaneel = new DeelPaneel(mainScherm, 0.4, 0.25, selectPlayersVBox, true);

        DeelPaneel tweeSpelerDeelPaneel = new DeelPaneel(mainScherm, 0.38, 1, keuzePaneel, true);
        DeelPaneel drieSpelerDeelPaneel = new DeelPaneel(mainScherm, 0.38, 1, keuzePaneel, true);
        DeelPaneel vierSpelerDeelPaneel = new DeelPaneel(mainScherm, 0.38, 1, keuzePaneel, true);
        DeelPaneel aantalSpelersDeelPaneel = new DeelPaneel(mainScherm, 0.8, 0.25, selectPlayersVBox, true);

        //
        mainBorderPane.getChildren().clear();
        mainBorderPane.setCenter(selectPlayersVBox);

        bevestigDeelPaneel.addControl(bevestigButton, 1, 1);
        tweeSpelerDeelPaneel.addControl(tweeSpelersRadioButton, 1, 1);
        drieSpelerDeelPaneel.addControl(drieSpelersRadioButton, 1, 1);
        vierSpelerDeelPaneel.addControl(vierSpelersRadioButton, 1, 1);
        aantalSpelersDeelPaneel.addControl(aantalSpelersLabel, 1, 1);

        keuzePaneel.getChildren().add(tweeSpelerDeelPaneel);
        keuzePaneel.getChildren().add(drieSpelerDeelPaneel);
        keuzePaneel.getChildren().add(vierSpelerDeelPaneel);
        selectPlayersVBox.getChildren().add(aantalSpelersDeelPaneel);
        selectPlayersVBox.getChildren().add(keuzePaneel);
        selectPlayersVBox.getChildren().add(bevestigDeelPaneel);

        //
        selectPlayersVBox.prefWidthProperty().bind(mainScherm.widthProperty().multiply(0.8));
        selectPlayersVBox.minWidthProperty().bind(mainScherm.widthProperty().multiply(0.8));
        selectPlayersVBox.maxWidthProperty().bind(mainScherm.widthProperty().multiply(0.8));
        selectPlayersVBox.prefHeightProperty().bind(mainScherm.heightProperty().multiply(0.8));
        selectPlayersVBox.minHeightProperty().bind(mainScherm.heightProperty().multiply(0.8));
        selectPlayersVBox.maxHeightProperty().bind(mainScherm.heightProperty().multiply(0.8));

        keuzePaneel.prefWidthProperty().bind(selectPlayersVBox.widthProperty().multiply(0.8));
        keuzePaneel.minWidthProperty().bind(selectPlayersVBox.widthProperty().multiply(0.8));
        keuzePaneel.maxWidthProperty().bind(selectPlayersVBox.widthProperty().multiply(0.8));
        keuzePaneel.prefHeightProperty().bind(selectPlayersVBox.heightProperty().multiply(0.3));
        keuzePaneel.minHeightProperty().bind(selectPlayersVBox.heightProperty().multiply(0.3));
        keuzePaneel.maxHeightProperty().bind(selectPlayersVBox.heightProperty().multiply(0.3));

        //
        selectPlayersVBox.setAlignment(Pos.CENTER);
        tweeSpelerDeelPaneel.setFontTracking(tweeSpelersRadioButton, 16);
        drieSpelerDeelPaneel.setFontTracking(vierSpelersRadioButton, 16);
        vierSpelerDeelPaneel.setFontTracking(drieSpelersRadioButton, 16);
        aantalSpelersDeelPaneel.setFontTracking(aantalSpelersLabel, 32);
        bevestigDeelPaneel.setFontTracking(bevestigButton, 28);

        tweeSpelersRadioButton.setToggleGroup(group);
        drieSpelersRadioButton.setToggleGroup(group);
        vierSpelersRadioButton.setToggleGroup(group);
        tweeSpelersRadioButton.setSelected(true);

        aantalSpelersLabel.setStyle("-fx-text-fill:white;");
        tweeSpelersRadioButton.setStyle("-fx-text-fill:white;");
        drieSpelersRadioButton.setStyle("-fx-text-fill:white;");
        vierSpelersRadioButton.setStyle("-fx-text-fill:white;");
        bevestigButton.setStyle("-fx-text-fill:white;-fx-background-color:transparent;");

        bevestigButton.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                if (tweeSpelersRadioButton.isSelected())
                    {

                    fadeAndStart(2);

                    }
                if (drieSpelersRadioButton.isSelected())
                    {

                    fadeAndStart(3);
                    }
                if (vierSpelersRadioButton.isSelected())
                    {

                    fadeAndStart(4);
                    }
                bevestigButton.setDisable(true);
            }

        });
        aantalSpelersLabel.focusedProperty().addListener((observable, oldValue, newValue) ->
            {
            if (newValue && firstTime.get())
                {
                selectPlayersVBox.requestFocus();
                firstTime.setValue(false);
                }
            });

    }

    private void fadeAndStart(int aantal)
    {
        FadeTransition ft = new FadeTransition(Duration.millis(450), this);
        ft.setFromValue(1);
        ft.setToValue(0.0);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(e -> mainScherm.startSpel(aantal));

    }

}
