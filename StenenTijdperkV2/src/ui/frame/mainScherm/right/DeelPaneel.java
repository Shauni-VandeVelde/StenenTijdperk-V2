/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.right;

import Domein.Kleur;
import Domein.Speler;
import javafx.animation.FadeTransition;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class DeelPaneel extends Pane
{
    private ImageView image;

    public DeelPaneel(Pane pane, double divW, double divH)
    {
        prefWidthProperty().bind(pane.widthProperty().divide(divW).multiply(divH));
        prefHeightProperty().bind(pane.heightProperty().multiply(divH));
        minWidthProperty().bind(pane.widthProperty().divide(divW).multiply(divH));
        minHeightProperty().bind(pane.heightProperty().multiply(divH));
        maxWidthProperty().bind(pane.widthProperty().divide(divW).multiply(divH));
        maxHeightProperty().bind(pane.heightProperty().multiply(divH));
    }

    public DeelPaneel(double divW, double divH, Pane pane, boolean binding)
    {
        if (binding)
            {
            prefWidthProperty().bind(pane.widthProperty().multiply(divW));
            prefHeightProperty().bind(pane.heightProperty().multiply(divH));
            minWidthProperty().bind(pane.widthProperty().multiply(divW));
            minHeightProperty().bind(pane.heightProperty().multiply(divH));
            maxWidthProperty().bind(pane.widthProperty().multiply(divW));
            maxHeightProperty().bind(pane.heightProperty().multiply(divH));
            }
    }

    public void changeBindings(Pane pane, double x, double y)
    {
        prefWidthProperty().bind(pane.widthProperty().multiply(x));
        prefHeightProperty().bind(pane.heightProperty().multiply(y));
        minWidthProperty().bind(pane.widthProperty().multiply(x));
        minHeightProperty().bind(pane.heightProperty().multiply(y));
        maxWidthProperty().bind(pane.widthProperty().multiply(x));
        maxHeightProperty().bind(pane.heightProperty().multiply(y));
    }

    public void removeContent()
    {
        getChildren().clear();
        image = null;
    }

    public void addBackgroundImage(ImageView image)
    {
        this.image = image;
        getChildren().clear();
        image.fitWidthProperty().bind(widthProperty());
        image.fitHeightProperty().bind(heightProperty());
        getChildren().add(image);
    }

    public void addButton(Button button, double x, double y)
    {
        getChildren().clear();
        if (image != null)
            {
            getChildren().add(image);
            }

        button.prefWidthProperty().bind(widthProperty().multiply(x));
        button.prefHeightProperty().bind(heightProperty().multiply(y));
        button.minWidthProperty().bind(widthProperty().multiply(x));
        button.minHeightProperty().bind(heightProperty().multiply(y));
        button.maxWidthProperty().bind(widthProperty().multiply(x));
        button.maxHeightProperty().bind(heightProperty().multiply(y));

        getChildren().add(button);
    }

    public void addControl(Control control, double x, double y)
    {
        getChildren().clear();
        if (image != null)
            {
            getChildren().add(image);
            }

        control.prefWidthProperty().bind(widthProperty().multiply(x));
        control.prefHeightProperty().bind(heightProperty().multiply(y));
        control.minWidthProperty().bind(widthProperty().multiply(x));
        control.minHeightProperty().bind(heightProperty().multiply(y));
        control.maxWidthProperty().bind(widthProperty().multiply(x));
        control.maxHeightProperty().bind(heightProperty().multiply(y));

        getChildren().add(control);
    }

    public void addCheckBox(CheckBox checkBox, double x, double y)
    {
        getChildren().clear();
        if (image != null)
            {
            getChildren().add(image);
            }

        checkBox.prefWidthProperty().bind(widthProperty().multiply(x));
        checkBox.prefHeightProperty().bind(heightProperty().multiply(y));
        checkBox.minWidthProperty().bind(widthProperty().multiply(x));
        checkBox.minHeightProperty().bind(heightProperty().multiply(y));
        checkBox.maxWidthProperty().bind(widthProperty().multiply(x));
        checkBox.maxHeightProperty().bind(heightProperty().multiply(y));

        getChildren().add(checkBox);
    }

    public void addSlider(Slider slider, double scale)
    {
        getChildren().clear();

        widthProperty().addListener(e ->
            {
            slider.setMinWidth(widthProperty().multiply(scale).doubleValue());
            slider.setMaxWidth(widthProperty().multiply(scale).doubleValue());
            slider.setPrefWidth(widthProperty().multiply(scale).doubleValue());
            });
        heightProperty().addListener(e ->
            {
            if (heightProperty().multiply(scale).doubleValue() > 75)
                {
                slider.setMinHeight(heightProperty().multiply(scale).doubleValue());
                slider.setMaxHeight(heightProperty().multiply(scale).doubleValue());
                slider.setPrefHeight(heightProperty().multiply(scale).doubleValue());
                }

            });

        getChildren().add(slider);
    }

    public void addLabel(Label label, double scale)
    {

        getChildren().clear();

        label.prefWidthProperty().bind(widthProperty().divide(scale));
        label.prefHeightProperty().bind(heightProperty().divide(scale));
        label.minWidthProperty().bind(widthProperty().divide(scale));
        label.minHeightProperty().bind(heightProperty().divide(scale));
        label.maxWidthProperty().bind(widthProperty().divide(scale));
        label.maxHeightProperty().bind(heightProperty().divide(scale));

        getChildren().add(label);
    }

    public void addLabel(Label label, double scale, double fontSize, Pos pos, String style)
    {

        getChildren().clear();

        label.prefWidthProperty().bind(widthProperty().multiply(scale));
        label.prefHeightProperty().bind(heightProperty().multiply(scale));
        label.minWidthProperty().bind(widthProperty().multiply(scale));
        label.minHeightProperty().bind(heightProperty().multiply(scale));
        label.maxWidthProperty().bind(widthProperty().multiply(scale));
        label.maxHeightProperty().bind(heightProperty().multiply(scale));

        getChildren().add(label);
        setFontTracking(label, fontSize);
        label.setAlignment(pos);
        label.setStyle(style);
    }

    public void setFontTracking(CheckBox checkBox, double fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        checkBox.fontProperty().bind(fontTracking);
        checkBox.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {
                //
                fontTracking.set(Font.font(checkBox.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));

            }

        });

    }

    public void setFontTracking(RadioButton radioButton, double fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        radioButton.fontProperty().bind(fontTracking);
        radioButton.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {

                fontTracking.set(Font.font(radioButton.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));

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

    public void setFontTracking(Label label, double fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        label.fontProperty().bind(fontTracking);
        label.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {
                //
                fontTracking.set(Font.font(label.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));

            }

        });

    }

    public void setPion(Speler speler)
    {
        if (getChildren().size() != 0)
            {
            ImageView pion = new ImageView(MainScherm.getUrl("wood"));
            if (speler.getKleur() == Kleur.ROOD)
                {
                pion = new ImageView(this.getClass().getClassLoader().getResource(MainScherm.getUrl("pionRood")).toExternalForm());
                }
            if (speler.getKleur() == Kleur.GEEL)
                {
                pion = new ImageView(this.getClass().getClassLoader().getResource(MainScherm.getUrl("pionGeel")).toExternalForm());
                }
            if (speler.getKleur() == Kleur.GROEN)
                {
                pion = new ImageView(this.getClass().getClassLoader().getResource(MainScherm.getUrl("pionGroen")).toExternalForm());
                }
            if (speler.getKleur() == Kleur.BLAUW)
                {
                pion = new ImageView(this.getClass().getClassLoader().getResource(MainScherm.getUrl("pionBlauw")).toExternalForm());
                }
            pion.fitWidthProperty().bind(widthProperty().multiply(0.25));
            pion.fitHeightProperty().bind(heightProperty().multiply(0.25));
            pion.layoutXProperty().bind(widthProperty().multiply(0.31));
            pion.layoutYProperty().bind(heightProperty().multiply(0.31));

            getChildren().add(pion);
            pion.setOpacity(0);
            FadeTransition ft = new FadeTransition(Duration.millis(600), pion);
            ft.setFromValue(0);
            ft.setToValue(1);
            ft.setCycleCount(1);
            ft.play();
            }

    }

}
