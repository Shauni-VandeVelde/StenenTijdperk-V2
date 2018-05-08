/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Speler;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import ui.frame.mainScherm.MainScherm;
import ui.frame.mainScherm.right.DeelPaneel;
import ui.frame.mainScherm.right.RightPaneBlueprint;


/**
 *
 * @author NotAvailable
 */
public class VoedselPane extends RightPaneBlueprint
{

    private Label mededelingLabel, vraagLabel, voedselLabel;
    private Button yes, no;
    private Slider houtSlider, leemSlider, steenSlider, goudSlider;
    private Label houtLabel, leemLabel, steenLabel, goudLabel;
    private boolean inOrde;
    private Speler speler;
    private int index;

    public VoedselPane(MainScherm mainScherm, Pane container, Speler speler)
    {
        super(mainScherm, speler, container, 1, 0.75);
        this.speler = speler;
        index = speler.index();
        init();

    }

    private void init()
    {
        inOrde = false;
        setBackgroundImage("img/ConsoleScherm.png");
        mededelingLabel = new Label("Je hebt niet genoeg voedsel om je pionnen te voeden!\n");
        vraagLabel = new Label("Wens je andere grondstoffen in te ruilen om je pionnen te voeden?");
        voedselLabel = new Label("12 voedsel te weinig");
        houtLabel = new Label("0");
        leemLabel = new Label("0");
        steenLabel = new Label("0");
        goudLabel = new Label("0");

        getLabel(mededelingLabel, style, 0.15, 1, 2.8);
        getLabel(vraagLabel, style, 0.15, 1, 2.6);
        getLabel(voedselLabel, style, 0.15, 0.2, 2.6);

        yes = new Button("Bevestig");
        no = new Button("Nee");

        ImageView houtImage = new ImageView(this.getClass().getClassLoader().getResource("img/HoutStack.png").toExternalForm());
        ImageView leemImage = new ImageView(this.getClass().getClassLoader().getResource("img/LeemStack.png").toExternalForm());
        ImageView steenImage = new ImageView(this.getClass().getClassLoader().getResource("img/SteenStack.png").toExternalForm());
        ImageView goudImage = new ImageView(this.getClass().getClassLoader().getResource("img/GoudStack.png").toExternalForm());

        goudSlider = getSlider();
        leemSlider = getSlider();
        steenSlider = getSlider();
        houtSlider = getSlider();

        houtSlider.setMax(speler.getAantalHout());
        leemSlider.setMax(speler.getAantalHout());
        steenSlider.setMax(speler.getAantalHout());
        goudSlider.setMax(speler.getAantalHout());

        DeelPaneel houtImageDeel = new DeelPaneel(0.120, 0.135, vbox, false);
        DeelPaneel houtSliderDeel = new DeelPaneel(0.65, 0.08, vbox, true);
        DeelPaneel leemImageDeel = new DeelPaneel(0.120, 0.135, vbox, false);
        DeelPaneel leemSliderDeel = new DeelPaneel(0.65, 0.08, vbox, true);
        DeelPaneel steenImageDeel = new DeelPaneel(0.120, 0.135, vbox, false);
        DeelPaneel steenSliderDeel = new DeelPaneel(0.65, 0.08, vbox, true);
        DeelPaneel goudImageDeel = new DeelPaneel(0.120, 0.135, vbox, false);
        DeelPaneel goudSliderDeel = new DeelPaneel(0.65, 0.08, vbox, true);
        DeelPaneel houtLabelDeel = new DeelPaneel(0.8, 0.07, vbox, false);
        DeelPaneel leemLabelDeel = new DeelPaneel(0.8, 0.07, vbox, false);
        DeelPaneel steenLabelDeel = new DeelPaneel(0.8, 0.07, vbox, false);
        DeelPaneel goudLabelDeel = new DeelPaneel(0.8, 0.07, vbox, false);

        HBox houtBox = new HBox();
        HBox leemBox = new HBox();
        HBox steenBox = new HBox();
        HBox goudBox = new HBox();
        HBox voedselBox = new HBox();

        voedselBox = getHBox(vbox, style, false, false, 12, 3, 1, new Node[]
            {
            voedselLabel
            }, new String[]
            {
            "label"
            }, 48);
        HBox buttonBox = getHBox(vbox, style, false, false, 8, 2, 0.7, new Node[]
            {
            yes, no
            }, new String[]
            {
            "button", "button"
            }, 7);

        houtBox = getHBox(vbox, new DeelPaneel[]
            {
            houtImageDeel, houtSliderDeel, houtLabelDeel
            }, 10);
        leemBox = getHBox(vbox, new DeelPaneel[]
            {
            leemImageDeel, leemSliderDeel, leemLabelDeel
            }, 10);
        steenBox = getHBox(vbox, new DeelPaneel[]
            {
            steenImageDeel, steenSliderDeel, steenLabelDeel
            }, 10);
        goudBox = getHBox(vbox, new DeelPaneel[]
            {
            goudImageDeel, goudSliderDeel, goudLabelDeel
            }, 10);

        if (speler.getAantalHout() == 0)
            {
            houtSliderDeel.setVisible(false);
            houtImage.setVisible(false);
            houtBox.setVisible(false);
            }
        if (speler.getAantalLeem() == 0)
            {
            leemSliderDeel.setVisible(false);
            leemImage.setVisible(false);
            leemBox.setVisible(false);
            }
        if (speler.getAantalSteen() == 0)
            {
            steenSliderDeel.setVisible(false);
            steenImage.setVisible(false);
            steenBox.setVisible(false);
            }
        if (speler.getAantalGoud() == 0)
            {
            goudSliderDeel.setVisible(false);
            goudImage.setVisible(false);
            goudBox.setVisible(false);
            }

        houtImageDeel.addBackgroundImage(houtImage);
        leemImageDeel.addBackgroundImage(leemImage);
        steenImageDeel.addBackgroundImage(steenImage);
        goudImageDeel.addBackgroundImage(goudImage);
        DeelPaneel ja = (DeelPaneel) buttonBox.getChildren().get(0);
        DeelPaneel nee = (DeelPaneel) buttonBox.getChildren().get(1);

        houtLabelDeel.addLabel(houtLabel, 0.9);
        leemLabelDeel.addLabel(leemLabel, 0.9);
        steenLabelDeel.addLabel(steenLabel, 0.9);
        goudLabelDeel.addLabel(goudLabel, 0.9);

        houtSliderDeel.addSlider(houtSlider, 1);
        leemSliderDeel.addSlider(leemSlider, 1);
        steenSliderDeel.addSlider(steenSlider, 1);
        goudSliderDeel.addSlider(goudSlider, 1);

        addChild(mededelingLabel);
        addChild(vraagLabel);
        addChild(buttonBox);
        addChild(voedselBox);
        addChild(houtBox);
        addChild(leemBox);
        addChild(steenBox);
        addChild(goudBox);

        houtLabelDeel.changeBindings(houtBox, 0.1, 0.8);
        leemLabelDeel.changeBindings(houtBox, 0.1, 0.8);
        steenLabelDeel.changeBindings(houtBox, 0.1, 0.8);
        goudLabelDeel.changeBindings(houtBox, 0.1, 0.8);

        houtImageDeel.changeBindings(houtBox, 0.095, 0.7);
        leemImageDeel.changeBindings(houtBox, 0.095, 0.7);
        steenImageDeel.changeBindings(houtBox, 0.095, 0.7);
        goudImageDeel.changeBindings(houtBox, 0.095, 0.7);

        setMargin(houtBox, 0.03, 0);
        setMargin(leemBox, 0.03, 0);
        setMargin(steenBox, 0.03, 0);
        setMargin(goudBox, 0.03, 0);
        setMargin(voedselBox, 0.330, 0);
        setMargin(buttonBox, 0.07, 0);

        setMargin(0.03, 0.0, houtImageDeel, houtBox);
        setMargin(0.03, 0.0, leemImageDeel, leemBox);
        setMargin(0.03, 0.0, steenImageDeel, steenBox);
        setMargin(0.03, 0.0, goudImageDeel, goudBox);

        setMargin(0, 0.0, houtLabelDeel, houtBox);
        setMargin(0, 0.0, leemLabelDeel, leemBox);
        setMargin(0, 0.0, steenLabelDeel, steenBox);
        setMargin(0, 0.0, goudLabelDeel, goudBox);

        ja.setFontTracking(yes, 43);
        nee.setFontTracking(no, 43);

        houtLabel.setAlignment(Pos.CENTER);
        leemLabel.setAlignment(Pos.CENTER);
        steenLabel.setAlignment(Pos.CENTER);
        goudLabel.setAlignment(Pos.CENTER);

        houtSlider.setMax(speler.getAantalHout());
        leemSlider.setMax(speler.getAantalLeem());
        steenSlider.setMax(speler.getAantalSteen());
        goudSlider.setMax(speler.getAantalGoud());

        updateVoedselLabel();
        setActions();

        houtLabel.setStyle(style);
        leemLabel.setStyle(style);
        steenLabel.setStyle(style);
        goudLabel.setStyle(style);
        yes.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        no.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
        yes.setOpacity(0.5);
    }

    private void setActions()
    {
        houtSlider.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2)
            {
                houtLabel.setText((int) houtSlider.getValue() + "");
                updateVoedselLabel();
            }

        });
        leemSlider.valueProperty().addListener(new ChangeListener()
        {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2)
            {
                leemLabel.setText((int) leemSlider.getValue() + "");
                updateVoedselLabel();
            }

        });
        steenSlider.valueProperty().addListener(new ChangeListener()
        {
            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2)
            {
                steenLabel.setText((int) steenSlider.getValue() + "");
                updateVoedselLabel();
            }

        });

        goudSlider.valueProperty().addListener(new ChangeListener()
        {

            @Override
            public void changed(ObservableValue arg0, Object arg1, Object arg2)
            {
                goudLabel.setText((int) goudSlider.getValue() + "");
                updateVoedselLabel();
            }

        });
        yes.setOnAction((event) ->
            {

            int temp = (int) houtSlider.getValue();
            temp += (int) leemSlider.getValue();
            temp += (int) steenSlider.getValue();
            temp += (int) goudSlider.getValue();
            if (temp == speler.getVoedseltekort())
                {
                if (mainScherm.shouldPlayMenuSFX())
                    {
                    mainScherm.queueSFX("menu", 80);
                    }
                inOrde = true;
                mainScherm.beeindigVoeden(this);
                }
            });
        no.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", 80);
                }
            inOrde = false;
            mainScherm.beeindigVoeden(this);
            });
    }

    private void updateVoedselLabel()
    {
        int temp = (int) houtSlider.getValue();
        temp += (int) leemSlider.getValue();
        temp += (int) steenSlider.getValue();
        temp += (int) goudSlider.getValue();
        int result = speler.getVoedseltekort() - temp;
        if (result > 0)
            {
            voedselLabel.setText("Nog " + result + " voedsel te weinig!");
            yes.setOpacity(0.5);
            }
        if (result == 0)
            {
            voedselLabel.setText("Druk op bevestig.");
            yes.setOpacity(1);

            }
        if (result < 0)
            {
            voedselLabel.setText("" + result + " grondstoffen te veel!");
            yes.setOpacity(0.5);
            }

    }

    public boolean isInOrde()
    {
        return inOrde;
    }

    public Slider getSlider()
    {
        Slider slider = new Slider();
        slider.setMinorTickCount(1);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setValue(0);
        slider.setShowTickMarks(true);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(false);

        slider.valueProperty().addListener(e ->
            {
            slider.setValue(Math.round(slider.valueProperty().doubleValue()));
            });

        widthProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {
                if (widthProperty().multiply(0.024).intValue() < 32)
                    {
                    String tempStyle = "-fx-font-size:";
                    tempStyle += widthProperty().multiply(0.024).intValue();
                    slider.setStyle(tempStyle);
                    }
                else
                    {
                    String tempStyle = "-fx-font-size:";
                    tempStyle += 32;
                    slider.setStyle(tempStyle);
                    }
            }

        });

        if (index == 0)
            {
            slider.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/SliderRed.css").toExternalForm());
            }
        if (index == 1)
            {
            slider.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/SliderBlue.css").toExternalForm());
            }
        if (index == 2)
            {
            slider.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/SliderYellow.css").toExternalForm());
            }
        if (index == 3)
            {
            slider.getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/SliderGreen.css").toExternalForm());
            }
        return slider;
    }

    public int getAantalHout()
    {
        return (int) houtSlider.getValue();
    }

    public int getAantalLeem()
    {
        return (int) leemSlider.getValue();
    }

    public int getAantalSteen()
    {
        return (int) steenSlider.getValue();
    }

    public int getAantalGoud()
    {
        return (int) goudSlider.getValue();
    }

}
