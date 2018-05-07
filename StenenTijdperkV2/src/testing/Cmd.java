/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testing;

import Domein.DomeinController;
import Domein.Locatie;
import Domein.Speler;
import java.util.ArrayList;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class Cmd
{

    private MainScherm mainScherm;
    private DomeinController dc;

    public Cmd(MainScherm mainScherm, DomeinController dc)
    {
        this.mainScherm = mainScherm;
        this.dc = dc;

    }

    public void finishRound()
    {

        ArrayList<Speler> spelers = dc.getSpelers();
        ArrayList<Locatie> locaties = dc.getAllLocaties();

        for (int i = 0; i < spelers.size(); i++)
            {
            while (spelers.get(i).getAantalPionnenOver() != 0)
                {
                if (!spelers.get(i).heeftAllePionnenGeplaatst())
                    {
                    for (int j = 0; j < locaties.size(); j++)
                        {
                        if (spelers.get(i).getAantalPionnenOver() > 0)
                            {
                            if (locaties.get(j).heeftPlaatsGenoeg(1))
                                {

                                dc.plaatsPionnenOpVeld(locaties.get(j), i);
                                mainScherm.volgendeSpeler();
                                }
                            }
                        }
                    }
                }

            }

    }

    public void plaatsMaxPionnenBeschikbaar()
    {
        Speler speler = dc.getHuidigeSpeler();
        Locatie locatie = mainScherm.getBottomButtonsPanel().getGekozenLocatie();
        if (!dc.alleSpelersHebbenHunPionnenGeplaatst())
            {
            mainScherm.getBottomButtonsPanel().setSliderMax();
            mainScherm.getBottomButtonsPanel().bevestigPlaatsPionnen();
            }

        else
            {
            mainScherm.volgendeSpeler();
            }
    }

    public void goTo(String name)
    {

    }

}
