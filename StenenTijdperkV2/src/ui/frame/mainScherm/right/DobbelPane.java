/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.right;

import Domein.GereedschapsFiche;
import Domein.Locatie;
import Domein.Speler;
import java.util.ArrayList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class DobbelPane extends RightPaneBlueprint
{
    private MainScherm mainScherm;
    private Locatie currentLocatie;
    private Label locatieLabel, rollenLabel, dobbelSteenLabel;
    private Button gereedschapsficheButton1, gereedschapsficheButton2, gereedschapsficheButton3, acceptButton;
    private boolean gereedschapsFiche1Confirm = false, gereedschapsFiche2Confirm = false, gereedschapsFiche3Confirm = false;
    private int currentRol = 0, totaalGereedschap = 0;

    public DobbelPane(MainScherm mainScherm, Speler speler, VBox vbox2, int currentRol, Locatie currentLocatie)
    {
        super(mainScherm, speler, vbox2, 0.19, 0.77);
        this.speler = speler;
        this.mainScherm = mainScherm;
        this.currentRol = currentRol;
        this.currentLocatie = currentLocatie;

        init();

        setDobbelLabel(currentRol, false);
        setLocatieLabel(currentLocatie);
        updateButtons();
    }

    private void init()
    {

        dobbelSteenLabel = new Label("0");
        locatieLabel = new Label();
        rollenLabel = new Label();
        acceptButton = new Button("Aanvaard: ");
        gereedschapsficheButton1 = new Button();
        gereedschapsficheButton2 = new Button();
        gereedschapsficheButton3 = new Button();

        ImageView consoleBackgroundImage = mainScherm.getImageView("img/TextureInventory2B.png");
        ImageView dobbelBekerImage = mainScherm.getImageView("img/DobbelSteenBeker.png");

        rollenLabel.setText("Dobbelen");
        locatieLabel.setText("Locatie: Akker");

        switch (mainScherm.getController().getSpelers().get(speler.index()).getGereedschapsFiches().size())
            {
            case 0:

                break;
            case 1:
                ImageView temp1 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde()));
                temp1.fitWidthProperty().bind(gereedschapsficheButton1.widthProperty());
                temp1.fitHeightProperty().bind(gereedschapsficheButton1.heightProperty());
                gereedschapsficheButton1.setGraphic(temp1);

                break;
            case 2:
                ImageView temp2 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde()));
                ImageView temp3 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(1).getWaarde()));
                temp2.fitWidthProperty().bind(gereedschapsficheButton1.widthProperty());
                temp2.fitHeightProperty().bind(gereedschapsficheButton1.heightProperty());
                temp3.fitWidthProperty().bind(gereedschapsficheButton2.widthProperty());
                temp3.fitHeightProperty().bind(gereedschapsficheButton2.heightProperty());
                gereedschapsficheButton1.setGraphic(temp2);
                gereedschapsficheButton2.setGraphic(temp3);
                break;
            case 3:

                ImageView temp4 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde()));
                ImageView temp5 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(1).getWaarde()));
                ImageView temp6 = mainScherm.getImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(2).getWaarde()));
                temp4.fitWidthProperty().bind(gereedschapsficheButton1.widthProperty());
                temp4.fitHeightProperty().bind(gereedschapsficheButton1.heightProperty());
                temp5.fitWidthProperty().bind(gereedschapsficheButton2.widthProperty());
                temp5.fitHeightProperty().bind(gereedschapsficheButton2.heightProperty());
                temp6.fitWidthProperty().bind(gereedschapsficheButton3.widthProperty());
                temp6.fitHeightProperty().bind(gereedschapsficheButton3.heightProperty());
                gereedschapsficheButton1.setGraphic(temp4);
                gereedschapsficheButton2.setGraphic(temp5);
                gereedschapsficheButton3.setGraphic(temp6);
                break;
            }
        HBox dobbelBox = getHBox(vbox, style, true, true, 5, 1.8, 0.75, new Node[]
            {

            }, new String[]
            {

            }, 27);
        HBox locatieBox = getHBox(vbox, style, true, true, 5, 1, 0.9, new Node[]
            {
            locatieLabel
            }, new String[]
            {
            "label"
            }, 33);
        HBox rollenBox = getHBox(vbox, style, false, true, 6, 1, 1, new Node[]
            {
            rollenLabel
            }, new String[]
            {
            "label"
            }, 26);
        HBox bruikbaarGereedschap = getHBox(vbox, style, true, true, 6.2, 1, 0.9, new Node[]
            {
            new Label("Gereedschap: ")
            }, new String[]
            {
            "label"
            }, 26);
        HBox acceptBox = getHBox(vbox, style, false, true, 9.665, 1.2, 0.9, new Node[]
            {
            acceptButton
            }, new String[]
            {
            "button"
            }, 14);
        DeelPaneel g1 = new DeelPaneel(mainScherm, 0.2437, 0.095, vbox, true);
        DeelPaneel g2 = new DeelPaneel(mainScherm, 0.2437, 0.095, vbox, true);
        DeelPaneel g3 = new DeelPaneel(mainScherm, 0.2437, 0.095, vbox, true);
        DeelPaneel dobbelImageDeelPaneel = new DeelPaneel(mainScherm, 0.42, 0.23, vbox, true);
        DeelPaneel dobbelLabelDeelPaneel = new DeelPaneel(mainScherm, 0.58, 0.23, vbox, true);
        dobbelImageDeelPaneel.addBackgroundImage(dobbelBekerImage);
        dobbelLabelDeelPaneel.addLabel(dobbelSteenLabel, 1, 18.5, Pos.CENTER, style);
        dobbelBox.getChildren().add(dobbelImageDeelPaneel);
        dobbelBox.getChildren().add(dobbelLabelDeelPaneel);
        g1.addButton(gereedschapsficheButton1, 1, 1);
        g2.addButton(gereedschapsficheButton2, 1, 1);
        g3.addButton(gereedschapsficheButton3, 1, 1);
        HBox gereedschapsBox = getHBox(vbox, new DeelPaneel[]
            {
            g1, g2, g3
            }, 9);
        setMargin(gereedschapsBox, 0.03385, 0);

        HBox fillBox = getHBox(vbox, style, false, true, 28, 4, 1, new Node[]
            {

            }, new String[]
            {

            }, 30);

        addChild(rollenBox);
        addChild(locatieBox);
        addChild(dobbelBox);
        addChild(bruikbaarGereedschap);
        addChild(acceptBox);
        addChild(fillBox);
        addChild(gereedschapsBox);
        getChildren().add(consoleBackgroundImage);
        getChildren().add(vbox);

        setMargin(acceptBox, 0.13, 0);

        consoleBackgroundImage.fitWidthProperty().bind(widthProperty());
        consoleBackgroundImage.fitHeightProperty().bind(heightProperty());

        setActions();
    }

    private void bevestigKeuze()
    {
        mainScherm.bevestigDobbel(this);
    }

    private void updateTotaalGereedschap()
    {
        totaalGereedschap = 0;
        if (gereedschapsFiche1Confirm)
            {
            totaalGereedschap += speler.getGereedchapsFiche(0).getWaarde();
            }
        if (gereedschapsFiche2Confirm)
            {
            totaalGereedschap += speler.getGereedchapsFiche(1).getWaarde();
            }
        if (gereedschapsFiche3Confirm)
            {
            totaalGereedschap += speler.getGereedchapsFiche(2).getWaarde();
            }
        setDobbelLabel(currentRol, true);

    }

    private void updateButtons()
    {
        ArrayList<GereedschapsFiche> fiches = speler.getGereedschapsFiches();
        gereedschapsficheButton1.setVisible(false);
        gereedschapsficheButton2.setVisible(false);
        gereedschapsficheButton3.setVisible(false);
        gereedschapsficheButton1.setDisable(true);
        gereedschapsficheButton2.setDisable(true);
        gereedschapsficheButton3.setDisable(true);
        for (int i = 0; i < speler.getGereedschapsFiches().size(); i++)
            {
            if (speler.getGereedchapsFiche(i) != null)
                {
                if (!speler.getGereedchapsFiche(i).isGebruikt())
                    {
                    switch (i)
                        {
                        case 0:
                            gereedschapsficheButton1.setVisible(true);
                            gereedschapsficheButton1.setDisable(false);
                            break;
                        case 1:
                            gereedschapsficheButton2.setVisible(true);
                            gereedschapsficheButton2.setDisable(false);
                            break;
                        case 2:
                            gereedschapsficheButton3.setVisible(true);
                            gereedschapsficheButton3.setDisable(false);
                            break;

                        }

                    }
                }
            }

    }

    private void kiesGereedschap(int index, boolean selected)
    {
        switch (index)
            {
            case 0:
                gereedschapsFiche1Confirm = !selected;
                break;
            case 1:
                gereedschapsFiche2Confirm = !selected;
                break;
            case 2:
                gereedschapsFiche3Confirm = !selected;
                break;
            }
        updateTotaalGereedschap();
    }

    public void setDobbelLabel(int rol, boolean gereedschap)
    {
        currentRol = rol;
        String string = rol + "";
        if (gereedschap)
            {
            string += " + " + totaalGereedschap;
            }
        dobbelSteenLabel.setText(string);
    }

    public void setLocatieLabel(Locatie locatie)
    {
        currentLocatie = locatie;
        locatieLabel.setText(locatie.getNaam());
    }

    private void setActions()
    {
        gereedschapsficheButton1.setOnAction((event) ->
            {
            kiesGereedschap(0, gereedschapsFiche1Confirm);

            });
        gereedschapsficheButton2.setOnAction((event) ->
            {
            kiesGereedschap(1, gereedschapsFiche2Confirm);
            });
        gereedschapsficheButton3.setOnAction((event) ->
            {
            kiesGereedschap(2, gereedschapsFiche3Confirm);
            });
        acceptButton.setOnAction((event) ->
            {
            acceptButton.setDisable(true);
            bevestigKeuze();
            //System.err.println("BevestigKeuze()");
            });

    }

    public int getCurrentRol()
    {
        return currentRol;
    }

    public Locatie getCurrentLocatie()
    {
        return currentLocatie;
    }

    public boolean[] getCurrentGereedschap()
    {
        return new boolean[]
            {
            gereedschapsFiche1Confirm, gereedschapsFiche2Confirm, gereedschapsFiche3Confirm
            };
    }

}
