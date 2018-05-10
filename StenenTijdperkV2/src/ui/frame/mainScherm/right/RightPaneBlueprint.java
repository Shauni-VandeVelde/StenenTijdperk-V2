/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.right;

import Domein.Speler;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public abstract class RightPaneBlueprint extends Pane
{

    public MainScherm mainScherm;
    protected VBox vbox;
    protected ImageView pionnenImage;
    protected String style;
    protected Speler speler;
    protected double x = 0.19;
    protected double y = 0.69;

    /**
     * Get a HBox with deelPanelen
     *
     * @param vbox
     * @param columns hoogte van HBox T.O.V parent
     * @param aantal gedeelte van width van hbox dat per deelpaneel toegekend
     * wordt
     * @param scale scale content zoals images, labels, buttons met een waarde
     * van 0-1, best 0.6-0.85 om plaats voor margin over te laten
     * @param nodes lijst van alle elementen zoals button, image, label die je
     * in de HBox wilt,
     * @param types elk element in de nodes lijst moet een corresponderende
     * entry hebben in deze lijst, met het type in lowercase bv: "button"
     * @return
     */
    public static HBox getHBox(Pane vbox, HBox hbox, boolean margin, MainScherm mainScherm, String style, double columns, double aantal, double scale, Node[] nodes, String[] types, boolean multiply, int fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());

        if (multiply)
            {
            hbox.prefHeightProperty().bind(vbox.heightProperty().multiply(columns));
            hbox.minHeightProperty().bind(vbox.heightProperty().multiply(columns));
            hbox.maxHeightProperty().bind(vbox.heightProperty().multiply(columns));
            }
        else
            {
            hbox.prefHeightProperty().bind(vbox.heightProperty().divide(columns));
            hbox.minHeightProperty().bind(vbox.heightProperty().divide(columns));
            hbox.maxHeightProperty().bind(vbox.heightProperty().divide(columns));
            }

        hbox.prefWidthProperty().bind(vbox.widthProperty());
        hbox.minWidthProperty().bind(vbox.widthProperty());
        hbox.maxWidthProperty().bind(vbox.widthProperty());

        for (int i = 0; i < nodes.length; i++)
            {

            DeelPaneel labelPane = new DeelPaneel(mainScherm, vbox, aantal, scale);
            if (types[i].equals("button"))
                {
                style = "";

                Button button = (Button) nodes[i];

                labelPane.addButton(button, scale, scale);
                button.prefHeightProperty().bind(hbox.heightProperty().multiply(scale));
                button.maxHeightProperty().bind(hbox.heightProperty().multiply(scale));
                button.minHeightProperty().bind(hbox.heightProperty().multiply(scale));
                button.prefWidthProperty().bind(hbox.widthProperty().divide(aantal));
                button.minWidthProperty().bind(hbox.widthProperty().divide(aantal));
                button.maxWidthProperty().bind(hbox.widthProperty().divide(aantal));
                // button.getStylesheets().add(this.getClass().getClassLoader().getResource( "ui/Stylesheets/Buttons.css").toExternalForm());
                button.fontProperty().bind(fontTracking);
                if (i == 0)
                    {
                    style = "-fx-text-fill:red; ";
                    }
                if (i == 1)
                    {
                    style = "-fx-text-fill:blue;";
                    }
                if (i == 2)
                    {
                    style = "-fx-text-fill:yellow;";
                    }
                if (i == 3)
                    {
                    style = "-fx-text-fill:green;";
                    }

                final int d = nodes.length;
                button.setStyle(style);

                button.widthProperty().addListener(new ChangeListener<Number>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
                    {
                        if (d > 2)
                            {
                            fontTracking.set(Font.font(button.getFont().getFamily(), FontWeight.EXTRA_BOLD, newWidth.doubleValue() / 100 * fontSize));
                            }
                        else
                            {
                            fontTracking.set(Font.font(button.getFont().getFamily(), FontWeight.EXTRA_BOLD, newWidth.doubleValue() / 100 * fontSize));
                            }

                    }

                });
                }
            if (types[i].equals("label"))
                {
                Label label = (Label) nodes[i];
                label.setAlignment(Pos.CENTER);
                labelPane.addLabel(label, scale);
                label.prefHeightProperty().bind(hbox.heightProperty().multiply(scale));
                label.prefWidthProperty().bind(hbox.widthProperty().divide(aantal).multiply(scale));
                label.maxHeightProperty().bind(hbox.heightProperty().multiply(scale));
                label.maxWidthProperty().bind(hbox.widthProperty().divide(aantal).multiply(scale));
                label.minHeightProperty().bind(hbox.heightProperty().multiply(scale));
                label.minWidthProperty().bind(hbox.widthProperty().divide(aantal).multiply(scale));
                label.setStyle(style);
                label.fontProperty().bind(fontTracking);
                }
            if (types[i].equals("image"))
                {
                labelPane.addBackgroundImage((ImageView) nodes[i]);
                }
            hbox.getChildren().add(labelPane);
            if (margin)
                {

                hbox.heightProperty().addListener(e ->
                    {
                    HBox.setMargin(labelPane, new Insets(hbox.heightProperty().divide(columns + 2).doubleValue(), hbox.widthProperty().divide(aantal * 12).doubleValue(), hbox.heightProperty().divide(columns + 2).doubleValue(), hbox.widthProperty().divide(aantal * 12).doubleValue()));
                    });
                }
            }

        return hbox;
    }

    protected RightPaneBlueprint(MainScherm mainScherm, Speler speler, Pane container, double x, double y)
    {
        this.x = x;
        this.y = y;
        this.mainScherm = mainScherm;
        this.speler = speler;
        vbox = new VBox();
        Stage s = mainScherm.getStage();
        vbox.prefHeightProperty().bind(s.heightProperty().multiply(y));
        vbox.prefWidthProperty().bind(s.widthProperty().multiply(x));
        vbox.minHeightProperty().bind(s.heightProperty().multiply(y));
        vbox.minWidthProperty().bind(s.widthProperty().multiply(x));
        vbox.maxHeightProperty().bind(s.heightProperty().multiply(y));
        vbox.maxWidthProperty().bind(s.widthProperty().multiply(x));
        prefHeightProperty().bind(s.heightProperty().multiply(y));
        prefWidthProperty().bind(s.widthProperty().multiply(x));
        minHeightProperty().bind(s.heightProperty().multiply(y));
        minWidthProperty().bind(s.widthProperty().multiply(x));
        maxHeightProperty().bind(s.heightProperty().multiply(y));
        maxWidthProperty().bind(s.widthProperty().multiply(x));
        setStyle(("-fx-background-color:grey;"));

        if (speler.getKleur().toString().equals("ROOD"))
            {
            style = "-fx-text-fill: red;";;
            pionnenImage = mainScherm.getImageView(MainScherm.getUrl("pionRood"));

            }
        else if (speler.getKleur().toString().equals("BLAUW"))
            {
            style = "-fx-text-fill: blue;";;
            pionnenImage = mainScherm.getImageView(MainScherm.getUrl("pionBlauw"));

            }
        else if (speler.getKleur().toString().equals("GEEL"))
            {
            style = "-fx-text-fill: #8e800c;";
            pionnenImage = mainScherm.getImageView(MainScherm.getUrl("pionGeel"));

            }
        else if (speler.getKleur().toString().equals("GROEN"))
            {
            style = "-fx-text-fill: green;";;
            pionnenImage = mainScherm.getImageView(MainScherm.getUrl("pionGroen"));

            }

        style += "-fx-font-size:1.4em; -fx-font-weight:900; -fx-stroke: black; -fx-stroke-width: 2;";
        // getChildren().add(vbox);

    }

    protected RightPaneBlueprint(MainScherm mainScherm, Pane container, Speler speler)
    {
        this.mainScherm = mainScherm;
        this.speler = speler;
        vbox = new VBox();
        Stage s = mainScherm.getStage();
        vbox.prefHeightProperty().bind(s.heightProperty().multiply(0.45));
        vbox.prefWidthProperty().bind(s.widthProperty().multiply(0.2));
        vbox.minHeightProperty().bind(s.heightProperty().multiply(0.45));
        vbox.minWidthProperty().bind(s.widthProperty().multiply(0.2));
        vbox.maxHeightProperty().bind(s.heightProperty().multiply(0.45));
        vbox.maxWidthProperty().bind(s.widthProperty().multiply(0.2));
        prefWidthProperty().bind(container.prefWidthProperty());
        minWidthProperty().bind(container.prefWidthProperty());
        maxWidthProperty().bind(container.prefWidthProperty());
        prefHeightProperty().bind(container.prefHeightProperty());
        minHeightProperty().bind(container.prefHeightProperty());
        maxHeightProperty().bind(container.prefHeightProperty());
        if (speler.getKleur().toString().equals("ROOD"))
            {
            style = "-fx-text-fill: red;";;
            pionnenImage = mainScherm.getImageView(mainScherm.getUrl("pionRood"));
            }
        else if (speler.getKleur().toString().equals("BLAUW"))
            {
            style = "-fx-text-fill: blue;";;
            pionnenImage = mainScherm.getImageView(mainScherm.getUrl("pionBlauw"));
            }
        else if (speler.getKleur().toString().equals("GEEL"))
            {
            style = "-fx-text-fill: #fff21a;";
            pionnenImage = mainScherm.getImageView(mainScherm.getUrl("pionGeel"));
            }
        else if (speler.getKleur().toString().equals("GROEN"))
            {
            style = "-fx-text-fill: green;";;
            pionnenImage = mainScherm.getImageView(mainScherm.getUrl("pionGroen"));
            }

        style += "-fx-font-size:1.4em; -fx-font-weight:900; -fx-stroke: black -fx-stroke-width: 2px";

    }

    protected RightPaneBlueprint(MainScherm mainScherm, Pane container, double x, double y)
    {
        this.mainScherm = mainScherm;
        this.x = x;
        this.y = y;

        Stage s = mainScherm.getStage();

        prefWidthProperty().bind(container.prefWidthProperty().multiply(x));
        minWidthProperty().bind(container.prefWidthProperty().multiply(x));
        maxWidthProperty().bind(container.prefWidthProperty().multiply(x));
        prefHeightProperty().bind(container.prefHeightProperty().multiply(y));
        minHeightProperty().bind(container.prefHeightProperty().multiply(y));
        maxHeightProperty().bind(container.prefHeightProperty().multiply(y));
    }

    protected void addChild(Node node)
    {
        vbox.getChildren().add(node);

    }

    protected void setMargin(Pane pane, double x, double y)
    {
        DeelPaneel[] panelen = new DeelPaneel[pane.getChildren().size()];
        for (int i = 0; i < pane.getChildren().size(); i++)
            {
            panelen[i] = (DeelPaneel) pane.getChildren().get(i);
            }

        for (int i = 0; i < pane.getChildren().size(); i++)
            {
            final int temp = i;
            pane.widthProperty().addListener(e ->
                {
                HBox.setMargin(panelen[temp], new Insets(heightProperty().multiply(y).doubleValue(), widthProperty().multiply(x).doubleValue(), heightProperty().multiply(y * 4).doubleValue(), widthProperty().multiply(x).doubleValue()));
                });
            }

    }

    protected void setMargin(double x, double y, DeelPaneel pane, HBox hbox)
    {

        widthProperty().addListener(e ->
            {
            HBox.setMargin(pane, new Insets(hbox.heightProperty().multiply(y).doubleValue(), hbox.widthProperty().multiply(x).doubleValue(), hbox.heightProperty().multiply(y * 4).doubleValue(), hbox.widthProperty().multiply(x).doubleValue()));
            });

    }

    /**
     * Sets an image to any button you put as the parameter
     *
     * @param button
     * @param path
     */
    protected void setImageView(Button button, String path)
    {
        ImageView image = mainScherm.getImageView(path);
        image.fitWidthProperty().bind(button.prefWidthProperty());
        image.fitHeightProperty().bind(button.prefHeightProperty());
        button.setGraphic(image);
    }

    /**
     * sets the background for the whole panel & scales according to the panel's
     * size
     *
     * @param path
     */
    protected void setBackgroundImage(String path)
    {
        getChildren().clear();
        ImageView consoleBackgroundImage = mainScherm.getImageView(path);

        consoleBackgroundImage.fitWidthProperty().bind(mainScherm.getStage().widthProperty().multiply(x));
        consoleBackgroundImage.fitHeightProperty().bind(mainScherm.getStage().heightProperty().multiply(y));
        getChildren().add(consoleBackgroundImage);
        getChildren().add(vbox);

    }

    public static HBox getHBox(Pane vbox, DeelPaneel[] deelpanelen, double columns)
    {
        HBox hbox = new HBox();
        hbox.prefHeightProperty().bind(vbox.heightProperty().divide(columns));
        hbox.minHeightProperty().bind(vbox.heightProperty().divide(columns));
        hbox.maxHeightProperty().bind(vbox.heightProperty().divide(columns));
        hbox.prefWidthProperty().bind(vbox.widthProperty());
        hbox.minWidthProperty().bind(vbox.widthProperty());
        hbox.maxWidthProperty().bind(vbox.widthProperty());
        for (int i = 0; i < deelpanelen.length; i++)
            {
            hbox.getChildren().add(deelpanelen[i]);
            }
        return hbox;

    }

    protected VBox getVBox(DeelPaneel[] deelpanelen, double x, double y)
    {

        VBox hbox = new VBox();
        hbox.prefHeightProperty().bind(vbox.heightProperty().multiply(y));
        hbox.minHeightProperty().bind(vbox.heightProperty().multiply(y));
        hbox.maxHeightProperty().bind(vbox.heightProperty().multiply(y));
        hbox.prefWidthProperty().bind(vbox.widthProperty().multiply(x));
        hbox.minWidthProperty().bind(vbox.widthProperty().multiply(x));
        hbox.maxWidthProperty().bind(vbox.widthProperty().multiply(x));
        for (int i = 0; i < deelpanelen.length; i++)
            {
            hbox.getChildren().add(deelpanelen[i]);
            }

        return hbox;

    }

    /**
     * Get a HBox with deelPanelen
     *
     * @param vbox
     * @param columns hoogte van HBox T.O.V parent
     * @param aantal gedeelte van width van hbox dat per deelpaneel toegekend
     * wordt
     * @param scale scale content zoals images, labels, buttons met een waarde
     * van 0-1, best 0.6-0.85 om plaats voor margin over te laten
     * @param nodes lijst van alle elementen zoals button, image, label die je
     * in de HBox wilt,
     * @param types elk element in de nodes lijst moet een corresponderende
     * entry hebben in deze lijst, met het type in lowercase bv: "button"
     * @return
     */
    protected HBox getHBox(VBox vbox, String style, boolean margin, boolean standardSheet, double columns, double aantal, double scale, Node[] nodes, String[] types, int fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
        HBox tempPane = new HBox();
        tempPane.prefHeightProperty().bind(vbox.heightProperty().divide(columns));
        tempPane.minHeightProperty().bind(vbox.heightProperty().divide(columns));
        tempPane.maxHeightProperty().bind(vbox.heightProperty().divide(columns));
        tempPane.prefWidthProperty().bind(vbox.widthProperty());
        tempPane.minWidthProperty().bind(vbox.widthProperty());
        tempPane.maxWidthProperty().bind(vbox.widthProperty());

        for (int i = 0; i < nodes.length; i++)
            {

            DeelPaneel labelPane = new DeelPaneel(mainScherm, tempPane, aantal, scale);
            if (types[i].equals("slider"))
                {
                Slider slider = (Slider) nodes[i];
                labelPane.addSlider(slider, scale);
                slider.setShowTickMarks(true);
                slider.setShowTickLabels(true);
                slider.setMajorTickUnit(0.25f);
                slider.setBlockIncrement(0.1f);
                }
            if (types[i].equals("button"))
                {

                Button button = (Button) nodes[i];
                if (standardSheet)
                    {
                    button.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
                    }

                labelPane.addButton(button, scale, scale);

                button.prefHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                button.prefWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));
                button.maxHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                button.maxWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));
                button.minHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                button.minWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));
                button.fontProperty().bind(fontTracking);

                button.widthProperty().addListener(new ChangeListener<Number>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
                    {
                        fontTracking.set(Font.font(button.getFont().getFamily(), FontWeight.EXTRA_BOLD, newWidth.doubleValue() / 100 * fontSize));
                    }

                });

                }
            if (types[i].equals("label"))
                {
                //  style += "-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );";
                Label label = (Label) nodes[i];

                label.setAlignment(Pos.CENTER);
                labelPane.addLabel(label, scale);
                label.prefHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                label.prefWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));
                label.maxHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                label.maxWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));
                label.minHeightProperty().bind(tempPane.heightProperty().multiply(scale));
                label.minWidthProperty().bind(tempPane.widthProperty().divide(aantal).multiply(scale));

                label.setStyle(style);

                label.fontProperty().bind(fontTracking);
                label.heightProperty().addListener(new ChangeListener<Number>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
                    {
                        //
                        fontTracking.set(Font.font(label.getFont().getFamily(), FontWeight.EXTRA_BOLD, newWidth.doubleValue() / 100 * fontSize));

                    }

                });

                }
            if (types[i].equals("image"))
                {
                labelPane.addBackgroundImage((ImageView) nodes[i]);
                }
            tempPane.getChildren().add(labelPane);
            if (margin)
                {
                tempPane.heightProperty().addListener(e ->
                    {
                    HBox.setMargin(labelPane, new Insets(tempPane.heightProperty().divide(100).multiply(8).doubleValue(), tempPane.widthProperty().divide(100).multiply(columns).doubleValue(), tempPane.heightProperty().divide(100).multiply(8).doubleValue(), tempPane.widthProperty().divide(100).multiply(columns).doubleValue()));
                    });
                }

            }

        return tempPane;
    }

    /**
     * get url voor gereedschapsfiches op waarde
     *
     * @param waarde
     * @return
     */
    protected String getUrl(int waarde)
    {
        if (waarde == 1)
            {
            return "img/GF_1.png";
            }
        if (waarde == 2)
            {
            return "img/GF_2.png";
            }
        if (waarde == 3)
            {
            return "img/GF_3.png";
            }
        if (waarde == 4)
            {
            return "img/GF_4.png";
            }
        else
            {
            return "img/pionblauwe.png";
            }
    }

    protected Label getLabel(Label label, String stijl, double vScale, double hScale, double fontSize)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
        label.setAlignment(Pos.CENTER);
        label.setWrapText(true);
        label.prefHeightProperty().bind(vbox.heightProperty().multiply(vScale));
        label.prefWidthProperty().bind(vbox.widthProperty().multiply(hScale));
        label.maxHeightProperty().bind(vbox.heightProperty().multiply(vScale));
        label.maxWidthProperty().bind(vbox.widthProperty().multiply(hScale));
        label.minHeightProperty().bind(vbox.heightProperty().multiply(vScale));
        label.minWidthProperty().bind(vbox.widthProperty().multiply(hScale));
        label.setStyle(stijl);
        label.fontProperty().bind(fontTracking);
        label.widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldWidth, Number newWidth)
            {
                fontTracking.set(Font.font(label.getFont().getFamily(), FontWeight.EXTRA_BOLD, newWidth.doubleValue() / 100 * fontSize));
            }

        });
        return label;
    }

    public Speler getSpeler()
    {
        return speler;
    }

}
