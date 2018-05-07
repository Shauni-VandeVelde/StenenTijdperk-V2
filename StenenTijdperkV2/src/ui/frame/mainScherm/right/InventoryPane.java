/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui.frame.mainScherm.right;

import Domein.DomeinController;
import Domein.Speler;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
public class InventoryPane extends RightPaneBlueprint
{

    private MainScherm mainScherm;
    private DomeinController controller;
    private HBox voedselPane, houtPane, leemPane, steenPane, goudPane, pionnenPane, gereedschapsPane, rondePane, voedselPerBeurtPane;
    private Label steenLabel, voedselLabel, houtLabel, leemLabel, goudLabel, pionnenLabel, rondeLabel, voedselPerBeurtLabel;
    private DeelPaneel gereedschapSlot1, gereedschapSlot2, gereedschapSlot3;
    private HBox inventoryButtonsPaneel;
    private Button[] inventoryButtons;
    private String[] buttonsString;

    public InventoryPane(Speler speler, MainScherm mainScherm, VBox vbox)
    {
        super(mainScherm, speler, new VBox(), 0.19, 0.748);

        this.mainScherm = mainScherm;
        this.controller = mainScherm.getController();
        init();
        setLabels();
    }

    private void init()
    {
        setBackgroundImage("img/TextureInventory2B.png");
        initButtons();
        double columns = 10.5;
        double scale = 0.7;

        steenLabel = new Label();
        houtLabel = new Label();
        leemLabel = new Label();
        goudLabel = new Label();
        pionnenLabel = new Label();
        rondeLabel = new Label();
        voedselLabel = new Label();
        voedselPerBeurtLabel = new Label();
        rondeLabel = new Label();
        ImageView steenImage = new ImageView(this.getClass().getClassLoader().getResource("img/SteenStackS.png").toExternalForm());
        ImageView houtImage = new ImageView(this.getClass().getClassLoader().getResource("img/G_HoutS.png").toExternalForm());
        ImageView leemImage = new ImageView(this.getClass().getClassLoader().getResource("img/G_LeemS.png").toExternalForm());
        ImageView goudImage = new ImageView(this.getClass().getClassLoader().getResource("img/G_GoudS.png").toExternalForm());
        ImageView voedselImage = new ImageView(this.getClass().getClassLoader().getResource("img/Voedselfiches.png").toExternalForm());
        ImageView g1 = new ImageView(this.getClass().getClassLoader().getResource("img/GF_1.png").toExternalForm());
        ImageView g2 = new ImageView(this.getClass().getClassLoader().getResource("img/GF_2.png").toExternalForm());
        ImageView g3 = new ImageView(this.getClass().getClassLoader().getResource("img/GF_3.png").toExternalForm());

        RightPaneBlueprint.getHBox(vbox, inventoryButtonsPaneel, false, mainScherm, "", 10, controller.getSpelers().size(), 1, inventoryButtons, buttonsString, false, 18);

        voedselPerBeurtPane = getHBox(vbox, style, false, true, columns, 1, 1, new Node[]
            {
            voedselPerBeurtLabel
            }, new String[]
            {
            "label"
            }, 29);
        rondePane = getHBox(vbox, style, false, true, columns, 1, 1, new Node[]
            {
            rondeLabel
            }, new String[]
            {
            "label"
            }, 55);
        steenPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            steenImage, steenLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        houtPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            houtImage, houtLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        leemPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            leemImage, leemLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        goudPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            goudImage, goudLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        voedselPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            voedselImage, voedselLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        pionnenPane = getHBox(vbox, style, true, true, columns, 2, scale, new Node[]
            {
            pionnenImage, pionnenLabel
            }, new String[]
            {
            "image", "label"
            }, 60);
        gereedschapsPane = getHBox(vbox, style, false, true, columns, 4, 1, new Node[]
            {
            g1, g2, g3
            }, new String[]
            {
            "image", "image", "image"
            }, 60);
        DeelPaneel[] panelen = new DeelPaneel[3];
        for (int i = 0; i < 3; i++)
            {
            panelen[i] = (DeelPaneel) gereedschapsPane.getChildren().get(i);
            }

        addChild(inventoryButtonsPaneel);
        addChild(rondePane);
        addChild(pionnenPane);
        addChild(houtPane);
        addChild(leemPane);
        addChild(steenPane);
        addChild(goudPane);
        addChild(voedselPane);
        addChild(voedselPerBeurtPane);
        addChild(gereedschapsPane);

        gereedschapSlot1 = (DeelPaneel) gereedschapsPane.getChildren().get(0);
        gereedschapSlot2 = (DeelPaneel) gereedschapsPane.getChildren().get(1);
        gereedschapSlot3 = (DeelPaneel) gereedschapsPane.getChildren().get(2);

        setMargin(gereedschapsPane, 0.03, 0);
        rondeLabel.setText("Ronde: " + mainScherm.getController().getRondeNummer());

    }

    private void initButtons()
    {
        inventoryButtonsPaneel = new HBox();
        buttonsString = new String[mainScherm.getController().getSpelers().size()];
        inventoryButtons = new Button[mainScherm.getController().getSpelers().size()];

        for (int i = 0; i < mainScherm.getController().getSpelers().size(); i++)
            {
            buttonsString[i] = "button";
            inventoryButtons[i] = new Button("Speler " + (i + 1));
            getStylesheets().add(this.getClass().getClassLoader().getResource("ui/Stylesheets/Buttons.css").toExternalForm());
            }

        inventoryButtons[0].setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                mainScherm.showSpelerTab(0);

            }

        });
        inventoryButtons[1].setOnAction(new EventHandler<ActionEvent>()
        {

            @Override
            public void handle(ActionEvent event)
            {
                mainScherm.showSpelerTab(1);

            }

        });
        if (mainScherm.getController().getSpelers().size() == 3)
            {
            inventoryButtons[2].setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event)
                {
                    mainScherm.showSpelerTab(2);

                }

            });
            }
        else if (mainScherm.getController().getSpelers().size() == 4)
            {
            inventoryButtons[2].setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event)
                {
                    mainScherm.showSpelerTab(2);

                }

            });
            inventoryButtons[3].setOnAction(new EventHandler<ActionEvent>()
            {

                @Override
                public void handle(ActionEvent event)
                {
                    mainScherm.showSpelerTab(3);

                }

            });
            }

    }

    /**
     * sets inventory panel labels/images to correct players
     */
    private void setLabels()
    {
        steenLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalSteen() + "");
        houtLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalHout() + "");
        leemLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalLeem() + "");
        goudLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalGoud() + "");
        voedselLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalVoedsel() + "");
        voedselPerBeurtLabel.setText("Voedsel Per beurt: " + mainScherm.getController().getSpelers().get(speler.index()).getVoedselPerBeurt());
        pionnenLabel.setText(mainScherm.getController().getSpelers().get(speler.index()).getAantalPionnenOver() + "");

        switch (mainScherm.getController().getSpelers().get(speler.index()).getGereedschapsFiches().size())
            {
            case 0:
                gereedschapSlot1.setVisible(false);
                gereedschapSlot2.setVisible(false);
                gereedschapSlot3.setVisible(false);

                break;
            case 1:

                gereedschapSlot1.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde())));
                gereedschapSlot1.setVisible(true);
                gereedschapSlot2.setVisible(false);
                gereedschapSlot3.setVisible(false);
                break;
            case 2:

                gereedschapSlot1.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde())));
                gereedschapSlot2.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(1).getWaarde())));
                gereedschapSlot1.setVisible(true);
                gereedschapSlot2.setVisible(true);
                gereedschapSlot3.setVisible(false);
                break;
            case 3:

                gereedschapSlot1.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(0).getWaarde())));
                gereedschapSlot2.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(1).getWaarde())));
                gereedschapSlot3.addBackgroundImage(new ImageView(getUrl(mainScherm.getController().getSpelers().get(speler.index()).getGereedchapsFiche(2).getWaarde())));

                gereedschapSlot1.setVisible(true);
                gereedschapSlot2.setVisible(true);
                gereedschapSlot3.setVisible(true);
                break;
            }
    }

}
