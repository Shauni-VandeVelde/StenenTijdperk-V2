/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.bottom;

import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
        double fontSize = 18;
        console.setStyle("-fx-text-fill: Black;");
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        console.fontProperty().bind(fontTracking);
        console.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {
                fontTracking.set(Font.font(console.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));
            }

        });

    }

    public void setFontTracking(Button button, double fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        button.fontProperty().bind(fontTracking);
        button.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {

                fontTracking.set(Font.font(button.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));

            }

        });

    }
    
    public void clear(){
        this.console.clearText();
    }

}
