/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Pion;
import javafx.animation.FadeTransition;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class PionnenLocatieDeelPane extends Pane
{
    private MainScherm mainScherm;
    private boolean hasImage = false;
    private Pion pion;

    public PionnenLocatieDeelPane(MainScherm mainScherm, Pane container, double scale, double x, double y)
    {
        this.mainScherm = mainScherm;
        prefWidthProperty().bind(container.widthProperty().divide(scale * 0.5));
        minWidthProperty().bind(container.widthProperty().multiply(scale * 0.5));
        maxWidthProperty().bind(container.widthProperty().multiply(scale * 0.5));
        prefHeightProperty().bind(container.heightProperty().multiply(scale));
        minHeightProperty().bind(container.heightProperty().multiply(scale));
        maxHeightProperty().bind(container.heightProperty().multiply(scale));
        layoutXProperty().bind(widthProperty().multiply(x));
        layoutYProperty().bind(heightProperty().multiply(y));
    }

    public void reset(boolean full)
    {
        if (full)
            {
            finishReset();
            }
        else
            {
            if (getChildren().size() == 1)
                {
                FadeTransition ft = new FadeTransition(Duration.millis(500), getChildren().get(0));
                ft.setFromValue(1.0);
                ft.setToValue(0.0);

                ft.play();
                ft.setOnFinished(e -> finishReset());

                }
            }

    }

    private void finishReset()
    {
        getChildren().clear();
        pion = null;
        hasImage = false;
    }

    public void setImage(String type, Pion pion)
    {
        reset(true);
        this.pion = pion;
        ImageView image = mainScherm.getImageView(mainScherm.getUrl(type));
        image.fitWidthProperty().bind(widthProperty());
        image.fitHeightProperty().bind(heightProperty());;
        getChildren().add(image);
        image.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(500), image);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
        hasImage = true;
    }

    public Pion getPion()
    {
        return pion;
    }

    public boolean hasImage()
    {
        return hasImage;
    }

}
