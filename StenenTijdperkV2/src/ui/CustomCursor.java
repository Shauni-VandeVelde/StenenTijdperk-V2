/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;
import Domein.Speler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class CustomCursor
{
    private MainScherm mainScherm;
    private ImageView imageView;
    private Speler speler;
    private boolean standard = false;

    public CustomCursor(MainScherm mainScherm, Speler speler)
    {
        this.mainScherm = mainScherm;
        this.speler = speler;
        setImage();
    }

    public CustomCursor(MainScherm mainScherm, boolean standard)
    {
        this.mainScherm = mainScherm;
        this.standard = standard;
        this.speler = mainScherm.getController().getHuidigeSpeler();
        setImage();
    }

    public void setImage()
    {
        String path = "";
        if (!standard)
            {
            switch (speler.index())
                {
                case 0:
                    path = "img/PionRood.png";
                    break;
                case 1:
                    path = "img/PionBlauw.png";
                    break;
                case 2:
                    path = "img/PionGeel.png";
                    break;
                case 3:
                    path = "img/PionGroen.png";
                    break;

                }
            //path = "img/hand.png";
            }
        else
            {
            switch (speler.index())
                {
                case 0:
                    path = "img/cursorRood.png";
                    break;
                case 1:
                    path = "img/cursorBlauw.png";
                    break;
                case 2:
                    path = "img/cursorGeel.png";
                    break;
                case 3:
                    path = "img/cursorGroen.png";
                    break;

                }
            path = "img/hand.png";
            }

        imageView = new ImageView(this.getClass().getClassLoader().getResource(path).toExternalForm());

    }

    public Image getImage()
    {
        return imageView.getImage();
    }

}
