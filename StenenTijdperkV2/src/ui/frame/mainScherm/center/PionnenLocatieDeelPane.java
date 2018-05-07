/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Pion;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class PionnenLocatieDeelPane extends Pane
{

    private boolean hasImage = false;
    private Pion pion;

    public PionnenLocatieDeelPane(MainScherm mainScherm, Pane container, double scale, double x, double y)
    {
        prefWidthProperty().bind(container.widthProperty().divide(scale * 0.5));
        minWidthProperty().bind(container.widthProperty().multiply(scale * 0.5));
        maxWidthProperty().bind(container.widthProperty().multiply(scale * 0.5));
        prefHeightProperty().bind(container.heightProperty().multiply(scale));
        minHeightProperty().bind(container.heightProperty().multiply(scale));
        maxHeightProperty().bind(container.heightProperty().multiply(scale));
        layoutXProperty().bind(widthProperty().multiply(x));
        layoutYProperty().bind(heightProperty().multiply(y));
    }

    public void reset()
    {

        getChildren().clear();
        pion = null;
        hasImage = false;
    }

    public void setImage(String type, Pion pion)
    {
        reset();
        this.pion = pion;
        ImageView image = new ImageView(this.getClass().getClassLoader().getResource(MainScherm.getUrl(type)).toExternalForm());
        image.fitWidthProperty().bind(widthProperty());
        image.fitHeightProperty().bind(heightProperty());;
        getChildren().add(image);
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
