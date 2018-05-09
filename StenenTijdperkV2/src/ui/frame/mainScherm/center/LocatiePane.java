/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.center;

import Domein.Locatie;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class LocatiePane extends VBox
{

    private MainScherm mainScherm;
    private Locatie locatie;
    private Pane centerPane;
    private HBox topHBox;
    private String baseStyle = "";
    private Button minus = new Button("-"), plus = new Button("+"), bevestig = new Button("Bevestig");
    private Label currentlySelectedNumberLabel, locatieLabel, plaatsvoorXPionnenLabel;
    private int currentlySelectedNumber = 1;

    public LocatiePane(MainScherm mainScherm, Pane centerPane, Locatie locatie)
    {
        this.mainScherm = mainScherm;
        this.centerPane = centerPane;
        this.locatie = locatie;
        baseStyle = "-fx-text-fill:   #e1e2ea    ;";
        double x = 0;
        double y = 0;
        switch (locatie.getNaam().trim().toLowerCase())
            {
            case "bos":
                x = 0.35;
                y = 0.05;
                break;
            case "leemgroeve":
                x = 0.63;
                y = 0.05;
                break;
            case "steengroeve":
                x = 0.815;
                y = 0.26;
                break;
            case "rivier":
                x = 0.73;
                y = 0.65;
                break;
            case "jacht":
                x = 0.07;
                y = 0.07;
                break;
            case "akker":
                x = 0.3;
                y = 0.54;
                break;
            case "gereedschapsmaker":
                x = 0.03;
                y = 0.55;
                break;
            case "lovehut":
                x = 0.19;
                y = 0.77;
                break;

            }

        layoutXProperty().bind(centerPane.widthProperty().multiply(x));
        layoutYProperty().bind(centerPane.heightProperty().multiply(y));
        init();
        setBindings();
        setActions();
        Platform.runLater(new Runnable()
        {
            @Override
            public void run()
            {   //Zorgt ervoor dat het scherm niet direct sluit omdat 1 van de onderdelen de focus heeft bij creatie
                requestFocus();
            }

        });

    }

    private void init()
    {

        getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/LocatieBackground.css").toExternalForm());
        setId("pane");

        topHBox = new HBox();
        locatieLabel = new Label("" + locatie.getNaam());
        plaatsvoorXPionnenLabel = new Label("Plaats voor:  " + locatie.getAantalPlaatsenVrij());
        minus = new Button("-");
        plus = new Button("+");
        bevestig = new Button("Bevestig");
        currentlySelectedNumber = locatie.getBruikbarePionnen(mainScherm.getController().getHuidigeSpeler());
        currentlySelectedNumberLabel = new Label(currentlySelectedNumber + "");

        setFontTracking(60, locatieLabel);
        setFontTracking(62, plaatsvoorXPionnenLabel);
        setFontTracking(40, currentlySelectedNumberLabel);
        setFontTracking(60, minus);
        setFontTracking(52, plus);
        setFontTracking(42, bevestig);

        locatieLabel.setAlignment(Pos.CENTER);
        plaatsvoorXPionnenLabel.setAlignment(Pos.BOTTOM_CENTER);
        currentlySelectedNumberLabel.setAlignment(Pos.CENTER);

        locatieLabel.setStyle(baseStyle + "-fx-background-color:transparent;");
        plaatsvoorXPionnenLabel.setStyle(baseStyle + "-fx-background-color:transparent;");
        currentlySelectedNumberLabel.setStyle(baseStyle + "-fx-background-color:transparent;");
        bevestig.setStyle(baseStyle + "-fx-background-color:transparent;");
        minus.setStyle(baseStyle + "-fx-background-color:transparent;");
        plus.setStyle(baseStyle + "-fx-background-color:transparent;");

        topHBox.getChildren().addAll(minus, currentlySelectedNumberLabel, plus);
        getChildren().add(locatieLabel);
        getChildren().add(plaatsvoorXPionnenLabel);
        getChildren().add(topHBox);
        getChildren().add(bevestig);

    }

    private void setBindings()
    {
        prefHeightProperty().bind(centerPane.heightProperty().multiply(0.23));
        minHeightProperty().bind(centerPane.heightProperty().multiply(0.23));
        maxHeightProperty().bind(centerPane.heightProperty().multiply(0.23));
        prefWidthProperty().bind(centerPane.widthProperty().multiply(0.165));
        minWidthProperty().bind(centerPane.widthProperty().multiply(0.165));
        maxWidthProperty().bind(centerPane.widthProperty().multiply(0.165));

        topHBox.prefHeightProperty().bind(heightProperty().multiply(0.3));
        topHBox.minHeightProperty().bind(heightProperty().multiply(0.3));
        topHBox.maxHeightProperty().bind(heightProperty().multiply(0.3));
        topHBox.prefWidthProperty().bind(widthProperty());
        topHBox.minWidthProperty().bind(widthProperty());
        topHBox.maxWidthProperty().bind(widthProperty());

        locatieLabel.prefHeightProperty().bind(heightProperty().multiply(0.22));
        locatieLabel.minHeightProperty().bind(heightProperty().multiply(0.22));
        locatieLabel.maxHeightProperty().bind(heightProperty().multiply(0.22));
        locatieLabel.prefWidthProperty().bind(widthProperty());
        locatieLabel.minWidthProperty().bind(widthProperty());
        locatieLabel.maxWidthProperty().bind(widthProperty());

        plaatsvoorXPionnenLabel.prefHeightProperty().bind(heightProperty().multiply(0.18));
        plaatsvoorXPionnenLabel.minHeightProperty().bind(heightProperty().multiply(0.18));
        plaatsvoorXPionnenLabel.maxHeightProperty().bind(heightProperty().multiply(0.18));
        plaatsvoorXPionnenLabel.prefWidthProperty().bind(widthProperty());
        plaatsvoorXPionnenLabel.minWidthProperty().bind(widthProperty());
        plaatsvoorXPionnenLabel.maxWidthProperty().bind(widthProperty());

        bevestig.prefHeightProperty().bind(heightProperty().multiply(0.3));
        bevestig.minHeightProperty().bind(heightProperty().multiply(0.3));
        bevestig.maxHeightProperty().bind(heightProperty().multiply(0.3));
        bevestig.prefWidthProperty().bind(widthProperty());
        bevestig.minWidthProperty().bind(widthProperty());
        bevestig.maxWidthProperty().bind(widthProperty());

        minus.prefHeightProperty().bind(topHBox.heightProperty());
        minus.minHeightProperty().bind(topHBox.heightProperty());
        minus.maxHeightProperty().bind(topHBox.heightProperty());
        minus.prefWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        minus.minWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        minus.maxWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));

        plus.prefHeightProperty().bind(topHBox.heightProperty());
        plus.minHeightProperty().bind(topHBox.heightProperty());
        plus.maxHeightProperty().bind(topHBox.heightProperty());
        plus.prefWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        plus.minWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        plus.maxWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));

        currentlySelectedNumberLabel.prefHeightProperty().bind(topHBox.heightProperty());
        currentlySelectedNumberLabel.minHeightProperty().bind(topHBox.heightProperty());
        currentlySelectedNumberLabel.maxHeightProperty().bind(topHBox.heightProperty());
        currentlySelectedNumberLabel.prefWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        currentlySelectedNumberLabel.minWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
        currentlySelectedNumberLabel.maxWidthProperty().bind(topHBox.widthProperty().multiply(0.3333333333333333));
    }

    private void setActions()
    {
        plus.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            if (currentlySelectedNumber + 1 <= locatie.getBruikbarePionnen(mainScherm.getController().getHuidigeSpeler()))
                {
                currentlySelectedNumber++;
                currentlySelectedNumberLabel.setText(currentlySelectedNumber + "");
                }
            });
        minus.setOnAction((event) ->
            {
            if (mainScherm.shouldPlayMenuSFX())
                {
                mainScherm.queueSFX("menu", -1);
                }
            if (!getLocatie().getNaam().toLowerCase().equals("lovehut"))
                {
                if (currentlySelectedNumber - 1 > 0)
                    {
                    currentlySelectedNumber--;
                    currentlySelectedNumberLabel.setText(currentlySelectedNumber + "");
                    }
                }

            });
        bevestig.setOnAction((event) ->
            {

            if (currentlySelectedNumber != 0)
                {
                if (locatie.getNaam().toLowerCase().equals("lovehut"))
                    {
                    if (currentlySelectedNumber == 2)
                        {
                        mainScherm.bevestigPlaatsen(this);
                        minus.requestFocus();
                        bevestig.setDisable(true);
                        minus.requestFocus();
                        }
                    }
                else
                    {
                    mainScherm.bevestigPlaatsen(this);
                    minus.requestFocus();
                    bevestig.setDisable(true);
                    minus.requestFocus();
                    }

                }
            });
        // Controleert of 1 van de onderdelen focus heeft, indien geen enkel onderdeel focus heeft sluit de Pane
        focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!locatieLabel.isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!plus.isFocused())
                        & (!minus.isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        plaatsvoorXPionnenLabel.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!isFocused())
                        & (!locatieLabel.isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!plus.isFocused())
                        & (!minus.isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        locatieLabel.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!plus.isFocused())
                        & (!minus.isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        currentlySelectedNumberLabel.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!locatieLabel.isFocused())
                        & (!isFocused())
                        & (!plus.isFocused())
                        & (!minus.isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        plus.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!locatieLabel.isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!isFocused())
                        & (!minus.isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        minus.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!locatieLabel.isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!plus.isFocused())
                        & (!isFocused())
                        & (!bevestig.isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        bevestig.focusedProperty().addListener((obs, wasFocused, isNowFocused) ->
            {
            if (!isNowFocused)
                {
                if ((!plaatsvoorXPionnenLabel.isFocused())
                        & (!locatieLabel.isFocused())
                        & (!currentlySelectedNumberLabel.isFocused())
                        & (!plus.isFocused())
                        & (!minus.isFocused())
                        & (!isFocused()))
                    {
                    centerPane.getChildren().remove(this);
                    }
                }
            });
        mainScherm.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent e)
            {
                mainScherm.getBottomButtonsPanel().requestFocus();
            }

        });
    }

    private void setFontTracking(double fontSize, Button button)
    {
        ObjectProperty<Font> fontTracking = new SimpleObjectProperty<Font>(Font.getDefault());
        button.fontProperty().bind(fontTracking);
        button.heightProperty().addListener(new ChangeListener<Number>()
        {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldHeight, Number newHeight)
            {
                //
                fontTracking.set(Font.font(button.getFont().getFamily(), FontWeight.EXTRA_BOLD, newHeight.doubleValue() / 100 * fontSize));

            }

        });
    }

    private void setFontTracking(double fontSize, Label label)
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

    public Locatie getLocatie()
    {
        return locatie;
    }

    public int getCurrentlySelectedNumber()
    {
        return currentlySelectedNumber;
    }

}
