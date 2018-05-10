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
                    path = "img/Cursors/Cursor_Red_Hover2.png";
                    break;
                case 1:
                    path = "img/Cursors/Cursor_Blue_Hover.png";
                    break;
                case 2:
                    path = "img/Cursors/Cursor_Yellow_Hover.png";
                    break;
                case 3:
                    path = "img/Cursors/Cursor_Green_Hover.png";
                    break;

                }
            //  path = "img/hand.png";
            }
        else
            {
            switch (speler.index())
                {
                case 0:
                    path = "img/Cursors/Cursor_Red_Std2.png";
                    break;
                case 1:
                    path = "img/Cursors/Cursor_Blue_Std.png";
                    break;
                case 2:
                    path = "img/Cursors/Cursor_Yellow_Std.png";
                    break;
                case 3:
                    path = "img/Cursors/Cursor_Green_Std.png";
                    break;

                }
            //path = "img/hand.png";
            }

        imageView = mainScherm.getImageView(path);

    }

    public Image getImage()
    {
        return imageView.getImage();
    }

    public ImageView getImageView()
    {
        return imageView;
    }

}
