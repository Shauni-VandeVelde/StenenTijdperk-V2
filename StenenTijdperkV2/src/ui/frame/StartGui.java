/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame;

import Domein.DomeinController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import testing.Cmd;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class StartGui extends Application
{

    private final boolean showSelectScreen = true;
    private static boolean isPaused = false;
    private DomeinController controller;
    private Cmd cmd;

    @Override
    public void start(Stage stage)
    {
        MainScherm main;
        if (showSelectScreen)
            {
            controller = new DomeinController(true);
            main = new MainScherm(controller, stage);
            if (main.openStartScherm())
                {
                controller.startSpel();
//            main.opStartSpel();
                }
            else
                {
                Platform.exit();
                System.exit(0);
                }
            }
        else
            {
            controller = new DomeinController(true);
            main = new MainScherm(controller, stage);
            controller.setAantalSpelers(2);
            controller.startSpel();
            }
        main.init();
        main.prefWidthProperty().bind(stage.widthProperty());
        main.minWidthProperty().bind(stage.widthProperty());
        main.maxWidthProperty().bind(stage.widthProperty());
        main.prefHeightProperty().bind(stage.heightProperty());
        main.minHeightProperty().bind(stage.heightProperty());
        main.maxHeightProperty().bind(stage.heightProperty());

        cmd = new Cmd(main, controller);
        Scene scene = new Scene(main, 1024, 768);
        stage.setScene(scene);
        stage.setTitle("Stenen Tijdperk");
        stage.setMinWidth(1030);
        stage.setMinHeight(650);
        stage.show();

        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
            {
            if (key.getCode() == KeyCode.TAB)
                {

                cmd.finishRound();
                }
            });
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
            {
            if (key.getCode() == KeyCode.ENTER)
                {

                cmd.plaatsMaxPionnenBeschikbaar();
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

    }

    public static void togglePause()
    {
        isPaused = !isPaused;
    }

    public static void main(String[] args)
    {
        launch(args);
    }

}
