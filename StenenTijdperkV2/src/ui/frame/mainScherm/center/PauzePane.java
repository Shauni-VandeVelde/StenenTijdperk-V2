/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author kenzo
 */
public class PauzePane extends Pane
{

    private Stage stage;
    private MainScherm mainScherm;
    private VBox menuOptionsVBox, soundMenuVBox;
    private HBox mainHBox;
    private Label lblPauzeMenu;
    private Button btnTerugNaarSpel;
    private Button btnSave;
    private Button btnLoad;
    private Button btnTerugNaarMenu;
    private Button btnSluiten;
    private Button btnSoundOptions;
    private boolean soundMenuActive = false;
    private boolean savePaneActive = false;
    private boolean loadPaneActive = false;
    private ImageView background;

    public PauzePane(Stage stage, MainScherm mainScherm)
    {
        super();

        menuOptionsVBox = new VBox();
        soundMenuVBox = new VBox();
        mainHBox = new HBox();
        lblPauzeMenu = new Label("Pauze Menu");
        btnTerugNaarSpel = new Button("Terug naar spel");
        btnSave = new Button("Save");
        btnLoad = new Button("Load");
        btnSoundOptions = new Button("Sound");
        btnTerugNaarMenu = new Button("Terug naar menu");
        btnSluiten = new Button("Sluit spel");
        background = new ImageView(this.getClass().getClassLoader().getResource("img/PauzeMenuBg.png").toExternalForm());

        setLayout();
        getChildren().add(background);
        menuOptionsVBox.getChildren().add(lblPauzeMenu);
        menuOptionsVBox.getChildren().add(btnTerugNaarSpel);
        menuOptionsVBox.getChildren().add(btnSoundOptions);
        menuOptionsVBox.getChildren().add(btnSave);
        menuOptionsVBox.getChildren().add(btnLoad);
        menuOptionsVBox.getChildren().add(btnTerugNaarMenu);
        menuOptionsVBox.getChildren().add(btnSluiten);

        getChildren().add(menuOptionsVBox);

        btnTerugNaarSpel.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        btnSoundOptions.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        btnSave.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        btnLoad.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        btnTerugNaarMenu.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        btnSluiten.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());

        setActions();

        this.stage = stage;
        this.mainScherm = mainScherm;
    }

    private void setLayout()
    {
        mainHBox.setAlignment(Pos.CENTER);
        mainHBox.prefWidthProperty().bind(widthProperty());

        menuOptionsVBox.prefWidthProperty().bind(widthProperty());
        menuOptionsVBox.prefHeightProperty().bind(heightProperty());
        soundMenuVBox.prefWidthProperty().bind(widthProperty());
        soundMenuVBox.prefHeightProperty().bind(heightProperty());

        menuOptionsVBox.setAlignment(Pos.CENTER);

        lblPauzeMenu.setFont(new Font("Arial", 30));

        btnTerugNaarSpel.prefWidthProperty().bind(widthProperty().multiply(0.65));

        //btnTerugNaarSpel.prefHeightProperty().bind(heightProperty());
        //Alle buttonwidths en heights worden gelijk gezet aan die van btnTerugNaarSpel
        btnSoundOptions.prefWidthProperty().bind(btnTerugNaarSpel.widthProperty());
        btnSave.prefWidthProperty().bind(btnTerugNaarSpel.widthProperty());
        //btnSave.prefHeightProperty().bind(btnTerugNaarSpel.widthProperty());
        btnLoad.prefWidthProperty().bind(btnTerugNaarSpel.widthProperty());
        //btnLoad.prefHeightProperty().bind(btnTerugNaarSpel.widthProperty());
        btnTerugNaarMenu.prefWidthProperty().bind(btnTerugNaarSpel.widthProperty());
        //btnTerugNaarMenu.prefHeightProperty().bind(btnTerugNaarSpel.widthProperty());
        btnSluiten.prefWidthProperty().bind(btnTerugNaarSpel.widthProperty());
        //btnSluiten.prefHeightProperty().bind(btnTerugNaarSpel.widthProperty());

        //v.setStyle("-fx-background-color: blue");
        background.fitWidthProperty().bind(widthProperty());
        background.fitHeightProperty().bind(heightProperty());

        this.widthProperty().addListener(e ->
            {
            double x, y;
            x = 100.0;
            y = 10.0;
            menuOptionsVBox.setMargin(menuOptionsVBox, new Insets(heightProperty().multiply(y).doubleValue(), widthProperty().multiply(x).doubleValue(), heightProperty().multiply(y * 4).doubleValue(), widthProperty().multiply(x).doubleValue()));
            });
    }

    public void confirmSFX(SoundMenu menu)
    {
        mainScherm.setMasterVolume(menu.getMasterVolume());
        mainScherm.setMusicVolume(menu.getMusicVolume());
        mainScherm.setSFXVolume(menu.getSFXVolume());
        toggleSoundMenu();

    }

    public void toggleSoundMenu()
    {
        if (soundMenuActive)
            {
            getChildren().clear();
            getChildren().add(background);
            getChildren().add(menuOptionsVBox);
            soundMenuActive = false;
            }
        else
            {
            getChildren().clear();
            getChildren().add(background);
            getChildren().add(new SoundMenu(mainScherm, this));
            soundMenuActive = true;
            }
    }
    
        public void toggleSavePane(){
        if(savePaneActive)
        {
            getChildren().clear();
            getChildren().add(background);
            getChildren().add(menuOptionsVBox);
        }
        else
        {
            getChildren().clear();
            getChildren().add(new SavePane());
        }
        
        savePaneActive = !savePaneActive;
    }
    
    public void toggleLoadPane(){
        if(loadPaneActive)
        {
            getChildren().clear();
            getChildren().add(background);
            getChildren().add(menuOptionsVBox);
        }
        else
        {
            getChildren().clear();
            getChildren().add(new LoadPane());
        }
        
        loadPaneActive = !loadPaneActive;
    }

    private void setActions()
    {
        btnTerugNaarSpel.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                mainScherm.closePauzeMenu();
            }

        });
        btnSoundOptions.setOnAction((event) -> toggleSoundMenu());

        btnSave.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                toggleSavePane();
            }

        });

        btnLoad.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                toggleLoadPane();
            }

        });

        btnTerugNaarMenu.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
            }

        });

        btnSluiten.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                stage.close();
            }

        });
    }

}
