/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.bottom;

import javafx.beans.binding.Bindings;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class ConsolePane extends Pane
{
    MainScherm mainScherm;
    private Console console;
    private Stage stage;

    public ConsolePane(MainScherm mainScherm, Console console, Stage stage)
    {
        this.mainScherm = mainScherm;
        this.stage = stage;
        this.console = console;
        init();
    }

    private void init()
    {
        ImageView consoleBackgroundImage = mainScherm.getImageView(mainScherm.getUrl("console"));

        consoleBackgroundImage.fitWidthProperty().bind(widthProperty());
        consoleBackgroundImage.fitHeightProperty().bind(heightProperty());
        prefHeightProperty().bind(Bindings.divide(stage.heightProperty(), 5));
        prefWidthProperty().bind(stage.widthProperty().subtract(15));
        console.prefHeightProperty().bind(heightProperty());
        console.prefWidthProperty().bind(widthProperty());
        getChildren().add(consoleBackgroundImage);
        getChildren().add(console);
        getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/TransparentTextArea.css").toExternalForm());
    }

}
