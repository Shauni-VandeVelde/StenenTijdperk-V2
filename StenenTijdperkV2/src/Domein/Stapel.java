package Domein;

import java.util.ArrayList;


public class Stapel extends Locatie
{

    private ArrayList<Hut> hutten;
    private boolean heeftPion;
    private int currentTopIndex = 6;
    private DomeinController controller;

    Stapel(DomeinController controller, int index)
    {

        super("Stapel" + index, 1, 0, -1);
        this.controller = controller;
        maakHutten();
    }

    public Stapel(ArrayList<Pion> pionnen, ArrayList<Hut> hutten, int index)
    {
        super("Stapel" + index, 1, -1, pionnen);
        this.hutten = hutten;

    }

    public ArrayList<Hut> getHutten()
    {
        return hutten;
    }

    /**
     * Can be used with both versions. Verwijdert een kaart van de stapel (dus
     * de bovenste)
     */
    void verwijder()
    {
        hutten.remove(hutten.size() - 1);
        --currentTopIndex;
    }

    /**
     * Can be used with both versions.
     */
    void maakHutten()
    {
        hutten = new ArrayList<>();
        for (int i = 0; i < 7; i++)
            {
            hutten.add(new Hut());
            }

    }

    public String magPlaatsenOpStapel(Speler speler)
    {
        Hut hut = getBovensteHut();
        String errorMessage = "";
        if (speler.getAantalGoud() < hut.getGoudKost())
            {
            if (!errorMessage.equals(""))
                {
                errorMessage += "\n";
                }
            errorMessage += (hut.getGoudKost() - speler.getAantalGoud()) + " Goud Te Weinig";

            }

        if (speler.getAantalHout() < hut.getHoutKost())
            {
            if (!errorMessage.equals(""))
                {
                errorMessage += "\n";
                }
            errorMessage += (hut.getHoutKost() - speler.getAantalHout()) + " Hout Te Weinig";
            }

        if (speler.getAantalLeem() < hut.getLeemKost())
            {
            if (!errorMessage.equals(""))
                {
                errorMessage += "\n";
                }
            errorMessage += (hut.getLeemKost() - speler.getAantalLeem()) + " Leem Te Weinig";
            }

        if (speler.getAantalSteen() < hut.getSteenKost())
            {
            if (!errorMessage.equals(""))
                {
                errorMessage += "\n";
                }
            errorMessage += (hut.getSteenKost() - speler.getAantalSteen()) + " Steen Te Weinig";
            }

        return errorMessage;
    }

    public void verwijderBovensteHut()
    {
        hutten.remove(currentTopIndex);
        --currentTopIndex;

        verwijderPion();
    }

    public Pion getPion()
    {
        return this.getPionnenList().get(0);
    }

    public int getSpelerIndex()
    {
        int index = -1;
        for (int i = 0; i < controller.getAantalSpelers(); i++)
            {
            if (getPion().getKleur() == controller.getSpelers().get(i).getKleur())
                {
                index = i;
                }
            }
        return index;
    }

    public void verwijderPion()
    {
        this.heeftPion = false;
        this.getPionnenList().clear();
    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    public Hut getBovensteHut()
    {
        Hut hut = new Hut();
        if (currentTopIndex >= 0)
            {
            return hutten.get(currentTopIndex);
            }
        else
            {
            return null;
            }
    }

    /**
     * For use with Both versions
     */
    boolean heeftPion()
    {
        boolean pion = false;
        if (!DomeinController.GUI)
            {
            return heeftPion;
            }
        else
            {
            if (getHuidigAantalPionnen() == 1)
                {
                return true;
                }
            else
                {
                return false;
                }
            }

    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    public int getCurrentTopIndex()
    {
        return currentTopIndex;
    }

}
