/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame;

import Domein.DomeinController;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import testing.Cmd;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class StartGui extends Application
{
    private final boolean showSelectScreen = true;
    public static boolean isPaused = false;
    private Stage stage;
    private Cmd cmd;
    private MainScherm main;
    private Pane pane;

    @Override
    public void start(Stage stage)
    {
        this.stage = stage;
        main = new MainScherm(this, stage);

        main.prefWidthProperty().bind(stage.widthProperty());
        main.minWidthProperty().bind(stage.widthProperty());
        main.maxWidthProperty().bind(stage.widthProperty());
        main.prefHeightProperty().bind(stage.heightProperty());
        main.minHeightProperty().bind(stage.heightProperty());
        main.maxHeightProperty().bind(stage.heightProperty());

        // cmd = new Cmd(main, mainScherm.getController());
        pane = new Pane();
        pane.getChildren().add(main);
        pane.setStyle("-fx-background-color:black;");
        Scene scene = new Scene(pane, 1024, 768);
        stage.setFullScreen(true);
        stage.setScene(scene);
        stage.setTitle("Stenen Tijdperk");
        stage.setMinWidth(1030);
        stage.setMinHeight(650);
        double op = 0;
        Button b = new Button();
        stage.setOpacity(op);

        FadeTransition ft = new FadeTransition(Duration.millis(400), b);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
        ft.setOnFinished(e -> stage.setOpacity(1));

        stage.show();
        //

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
            {
            if (key.getCode() == KeyCode.TAB)
                {

                //  cmd.finishRound();
                }
            });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
            {
            if (key.getCode() == KeyCode.ENTER)
                {

//                cmd.plaatsMaxPionnenBeschikbaar();
                }
            });

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
            {
            if (key.getCode() == KeyCode.P)
                {
                if (isPaused)

                    {
                    main.closePauzeMenu();
                    }
                else
                    {
                    main.openPauzeMenu();
                    }

                }
            });

        main.requestFocus();
        main.requestFocus();
        // main.initCursor();

    }

    public static void togglePause()
    {
        isPaused = !isPaused;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void loadGame(DomeinController newDC)
    {
        newDC.setAantalSpelers(newDC.getSpelers().size());
        String[] temp = new String[newDC.getAantalSpelers()];
        newDC.setVorigeLocaties(temp);

        main = new MainScherm(this, stage, newDC);
        main.startSpel();
        main.prefWidthProperty().bind(stage.widthProperty());
        main.minWidthProperty().bind(stage.widthProperty());
        main.maxWidthProperty().bind(stage.widthProperty());
        main.prefHeightProperty().bind(stage.heightProperty());
        main.minHeightProperty().bind(stage.heightProperty());
        main.maxHeightProperty().bind(stage.heightProperty());
        pane.getChildren().clear();
        pane.getChildren().add(main);

        pane.setStyle("-fx-background-color:black;");
        Scene scene = new Scene(pane, 1024, 768);

        stage.setScene(scene);
        stage.show();
        main.requestFocus();
        main.requestFocus();
    }

}
