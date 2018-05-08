package Domein;

import java.util.ArrayList;
import java.util.Random;
import ui.frame.mainScherm.MainScherm;


public class Speler
{

    private int index;
    private int voedselPerBeurt;
    private int punten;
    private Kleur kleur;
    private int aantalGoud;
    private int aantalHout;
    private int aantalLeem;
    private int aantalVoedsel;
    private int aantalSteen;

    private int previousHout;
    private int previousLeem;
    private int previousSteen;
    private int previousGoud;
    private int previousVoedsel;
    private int voedselEindeVorigeBeurt = 12;
    private int previousPunten;
    private int previousVoedselPerBeurt;
    private int previousPionnenSize;
    private int previousWaardeGereedschap;
    private int addedVoedsel;
    private int upkeep;

    private int previousHuttenSize;

    private ArrayList<GereedschapsFiche> gereedschapsFiches = new ArrayList<>();
    private ArrayList<Hut> hutten = new ArrayList<>();
    private ArrayList<Pion> pionnen = new ArrayList<>();

    Speler(Kleur kleur, int index)
    {
        this.index = index;
        this.kleur = kleur;
        Random rand = new Random();

        setVoedselPerBeurt(8);
        setPunten(0);
        setAantalHout(0);
        setAantalLeem(0);
        setAantalSteen(0);
        setAantalGoud(0);
        setAantalVoedsel(12);
        addedVoedsel = 0;
        voedselEindeVorigeBeurt = aantalVoedsel;
        previousPionnenSize = hoeveelheidPionnenStart;
        previousHuttenSize = hutten.size();
        int hoeveelheidPionnenStart = 5;
        for (int i = 0; i < hoeveelheidPionnenStart; i++)
            {
            pionnen.add(i, new Pion(kleur));

            }/*
        for (int i = 0; i < 12; i++)
            {
            geefNieuwGereedschap();
            }*/


    }

    public Speler(int index, int aantalGoud, int aantalHout, int aantalLeem, int aantalSteen, int aantalVoedsel, int aantalVoedselPerBeurt, Kleur kleur, ArrayList<Pion> pionnen, ArrayList<Hut> hutten, ArrayList<GereedschapsFiche> gereedschapsfiches)
    {
        setIndex(index);
        setAantalGoud(aantalGoud);
        setAantalHout(aantalHout);
        setAantalLeem(aantalLeem);
        setAantalSteen(aantalSteen);
        setAantalVoedsel(aantalVoedsel);
        setVoedselPerBeurt(aantalVoedselPerBeurt);
        setKleur(kleur);
        setPunten(punten);
        setHutten(hutten);
        setPionnen(pionnen);
        setGereedschapsFiches(gereedschapsfiches);
    }

    public void ruilResource(String type, int aantal)
    {
        type = type.trim().toLowerCase();
        switch (type)
            {
            case "hout":
                aantalHout -= aantal;
                aantalVoedsel += aantal;
                break;
            case "leem":
                aantalLeem -= aantal;
                aantalVoedsel += aantal;
                break;
            case "steen":
                aantalSteen -= aantal;
                aantalVoedsel += aantal;
                break;
            case "goud":
                aantalGoud -= aantal;
                aantalVoedsel += aantal;
                break;
            }
    }

    void geefNieuwGereedschap()
    {
        previousWaardeGereedschap = getHoeveelheidGereedschap();
        if (gereedschapsFiches.size() == 0)
            {
            gereedschapsFiches.add(new GereedschapsFiche());
            }
        else
            {
            upgrade();
            }
    }

    private ArrayList<GereedschapsFiche> upgrade()
    {
        if (gereedschapsFiches.size() == 1)
            {
            gereedschapsFiches.add(new GereedschapsFiche());
            }
        else if (gereedschapsFiches.size() == 2)
            {
            gereedschapsFiches.add(new GereedschapsFiche());
            }
        else if (gereedschapsFiches.size() >= 3)
            {
            if ((gereedschapsFiches.get(0).getWaarde() == 1) && (gereedschapsFiches.get(1).getWaarde() == 1) && (gereedschapsFiches.get(2).getWaarde() == 1))
                {
                gereedschapsFiches.get(0).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 2) && (gereedschapsFiches.get(1).getWaarde() == 1) && (gereedschapsFiches.get(2).getWaarde() == 1))
                {
                gereedschapsFiches.get(1).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 2) && (gereedschapsFiches.get(1).getWaarde() == 2) && (gereedschapsFiches.get(2).getWaarde() == 1))
                {
                gereedschapsFiches.get(2).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 2) && (gereedschapsFiches.get(1).getWaarde() == 2) && (gereedschapsFiches.get(2).getWaarde() == 2))
                {
                gereedschapsFiches.get(0).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 3) && (gereedschapsFiches.get(1).getWaarde() == 2) && (gereedschapsFiches.get(2).getWaarde() == 2))
                {
                gereedschapsFiches.get(1).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 3) && (gereedschapsFiches.get(1).getWaarde() == 3) && (gereedschapsFiches.get(2).getWaarde() == 2))
                {
                gereedschapsFiches.get(2).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 3) && (gereedschapsFiches.get(1).getWaarde() == 3) && (gereedschapsFiches.get(2).getWaarde() == 3))
                {
                gereedschapsFiches.get(0).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 4) && (gereedschapsFiches.get(1).getWaarde() == 3) && (gereedschapsFiches.get(2).getWaarde() == 3))
                {
                gereedschapsFiches.get(1).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 4) && (gereedschapsFiches.get(1).getWaarde() == 4) && (gereedschapsFiches.get(2).getWaarde() == 3))
                {
                gereedschapsFiches.get(2).increment();
                }
            else if ((gereedschapsFiches.get(0).getWaarde() == 4) && (gereedschapsFiches.get(1).getWaarde() == 4) && (gereedschapsFiches.get(2).getWaarde() == 4))
                {
                if (!DomeinController.GUI)
                    {
                    System.out.println("Maximum gereedschap reeds bereikt.");
                    }
                else
                    {
                    MainScherm.console.printLine("Maximum gereedschap reeds bereikt!");
                    }
                }
            }
        return gereedschapsFiches;
    }

    void reset()
    {
        voedselEindeVorigeBeurt = aantalVoedsel;
        previousVoedselPerBeurt = voedselPerBeurt;
        // previousVoedsel = aantalVoedsel;
        previousVoedsel = aantalVoedsel;
        previousHout = aantalHout;
        previousSteen = aantalSteen;
        previousLeem = aantalLeem;
        previousGoud = aantalGoud;
        addedVoedsel = 0;
        previousPionnenSize = this.pionnen.size();
        for (int i = 0; i < pionnen.size(); i++)
            {
            pionnen.get(i).setGebruikt(false);
            }
    }

    public void resetFood()
    {
        aantalVoedsel = 0;
    }

    void geefVoedselPerBeurt()
    {

        this.aantalVoedsel += this.voedselPerBeurt;
    }

    void clearGebruiktGereedschap()
    {
        for (int i = 0; i < gereedschapsFiches.size(); i++)
            {
            gereedschapsFiches.get(i).reset();

            }
    }

    void voegHutToe(Hut hut)
    {
        hutten.add(hut);
    }

    void berekenPuntenVanHutten(int houtWaarde, int leemWaarde, int steenWaarde, int goudWaarde)
    {
        int punten = 0;

        for (Hut hut : hutten)
            {
            punten += hut.berekenPunten(houtWaarde, leemWaarde, steenWaarde, goudWaarde);
            }

        this.punten = punten;
    }

    void converteerGrondstoffenNaarPunten()
    {
        punten += aantalGoud;
        punten += aantalHout;
        punten += aantalLeem;
        punten += aantalSteen;

        aantalGoud = 0;
        aantalHout = 0;
        aantalLeem = 0;
        aantalSteen = 0;
    }

    public int getWaardeVanResources()
    {
        int resources = 0;
        resources += aantalHout;
        resources += aantalLeem;
        resources += aantalSteen;
        resources += aantalGoud;
        resources += aantalVoedsel;
        return resources;

    }

    void plaatsPionnen(Locatie loc, int aantalPionnen)
    {
        for (int i = 0; i < aantalPionnen; ++i)
            {

            if (getVrijePion() != 99)
                {

                loc.plaatsPion(pionnen.get(getVrijePion()));

                }

            }

    }

    void krijgNieuwVoedsel(int hoeveelheid)
    {
        addedVoedsel = hoeveelheid;
        aantalVoedsel += hoeveelheid;
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    private void setKleur(Kleur kleur)
    {
        this.kleur = kleur;
    }

    void setVoedselPerBeurt(int voedselPerBeurt)
    {
        previousVoedselPerBeurt = this.voedselPerBeurt;
        this.voedselPerBeurt = voedselPerBeurt;
    }

    void setPunten(int punten)
    {
        previousPunten = this.punten;

        this.punten = punten;
    }

    void setAantalGoud(int aantalGoud)
    {
        previousGoud = this.aantalGoud;
        this.aantalGoud = aantalGoud;
    }

    void setAantalHout(int aantalHout)
    {
        previousHout = this.aantalHout;
        this.aantalHout = aantalHout;
    }

    void setAantalLeem(int aantalLeem)
    {
        previousLeem = this.aantalLeem;
        this.aantalLeem = aantalLeem;
    }

    void setAantalSteen(int aantalSteen)
    {
        previousSteen = this.aantalSteen;
        this.aantalSteen = aantalSteen;
    }

    void setAantalVoedsel(int aantalVoedsel)
    {

        this.aantalVoedsel = aantalVoedsel;
    }

    public int getVoedselEindeVorigeBeurt()
    {
        return voedselEindeVorigeBeurt;
    }

    public void clearVoedsel()
    {
        aantalVoedsel = 0;
    }

    private void setHutten(ArrayList<Hut> hutten)
    {
        previousHuttenSize = this.hutten.size();
        this.hutten = hutten;
    }

    private void setPionnen(ArrayList<Pion> pionnen)
    {
        previousPionnenSize = this.pionnen.size();
        this.pionnen = pionnen;
    }

    private void setGereedschapsFiches(ArrayList<GereedschapsFiche> gereedschapsFiches)
    {
        this.gereedschapsFiches = gereedschapsFiches;
    }

    public GereedschapsFiche getGereedchapsFiche(int index)
    {
        return gereedschapsFiches.get(index);
    }

    public Kleur getKleur()
    {
        return kleur;
    }

    public boolean heeftAllePionnenGeplaatst()
    {
        for (Pion pion : pionnen)
            {
            if (!pion.isGebruikt())
                {
                return false;
                }
            }
        return true;
    }

    void voedPionnen()
    {

        geefVoedselPerBeurt();
        upkeep = pionnen.size();
        aantalVoedsel = aantalVoedsel - upkeep;

        if (aantalVoedsel < 0)
            {
            setPunten(getPunten() - 10);
            resetFood();
            }
    }

    boolean incrementPionnen()
    {
        previousPionnenSize = this.pionnen.size();
        int maxHoeveelheidPionnen = 10;

        if (pionnen.size() >= maxHoeveelheidPionnen)
            {
            return false;
            }

        pionnen.add(new Pion(this.kleur));
        return true;
    }

    boolean incrementVoedselPerBeurt()
    {
        int maxVoedselPerBeurt = 10;
        previousVoedselPerBeurt = this.voedselPerBeurt;
        if (voedselPerBeurt >= maxVoedselPerBeurt)
            {
            if (!DomeinController.GUI)
                {
                System.out.println("Voedsel per beurt kan niet meer hoger!");
                }
            else
                {
                MainScherm.console.printLine("Voedsel per beurt kan niet meer hoger!");
                }
            return false;
            }

        voedselPerBeurt++;

        return true;
    }

    public ArrayList<Integer> getIndexesBruikbaarGereedschap()
    {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = 0; i < gereedschapsFiches.size(); i++)
            {
            if (!gereedschapsFiches.get(i).isGebruikt())
                {
                temp.add(Integer.valueOf(i));
                }
            }
        return temp;
    }

    /**
     * Both versions.
     */
    public ArrayList<Pion> getPionnen()
    {
        return pionnen;
    }

    /**
     * Both versions.
     */
    public ArrayList<Hut> getHutten()
    {
        return hutten;
    }

    public int getUpkeep()
    {
        return upkeep;
    }

    public int getVrijePion()
    {

        for (int i = 0; i < pionnen.size(); i++)
            {
            if (!pionnen.get(i).isGebruikt())
                {
                return i;
                }
            }
        return 99;
    }

    public int getAantalPionnenOver()
    {
        int aantal = 0;
        for (Pion pion : pionnen)
            {
            if (!pion.isGebruikt())
                {
                ++aantal;
                }
            }

        return aantal;
    }

    public int getGereedschapsFicheSize()
    {
        return gereedschapsFiches.size();
    }

    public int getPreviousPionnenSize()
    {
        return previousPionnenSize;
    }

    public ArrayList<GereedschapsFiche> getGereedschapsFiches()
    {
        return gereedschapsFiches;
    }

    public int getAantalBruikbaarGereedschap()
    {
        int aantal = 0;
        for (int i = 0; i < gereedschapsFiches.size(); i++)
            {
            if (!gereedschapsFiches.get(i).isGebruikt())
                {
                aantal++;
                }
            }
        return aantal;
    }

    public int getPreviousWaardeGereedschap()
    {
        return previousWaardeGereedschap;
    }

    public int getPreviousHuttenSize()
    {
        return previousHuttenSize;
    }

    public int getHoeveelheidGereedschap()
    {
        int temp = 0;

        for (int i = 0; i < gereedschapsFiches.size(); i++)
            {
            temp += gereedschapsFiches.get(i).getWaarde();
            }
        return temp;
    }

    public int index()
    {
        return index;
    }

    public int getAantalSteen()
    {
        return aantalSteen;
    }

    public int getVoedselPerBeurt()
    {
        return voedselPerBeurt;
    }

    public int getPunten()
    {
        return punten;
    }

    public int getAantalLeem()
    {
        return aantalLeem;
    }

    public int getAantalVoedsel()
    {
        return aantalVoedsel;
    }

    public int getAantalHout()
    {
        return aantalHout;
    }

    public int getAantalGoud()
    {
        return aantalGoud;
    }

    public int getVoedseltekort()
    {
        return pionnen.size() - aantalVoedsel;
    }

    public int getPreviousHout()
    {
        return previousHout;
    }

    public int getPreviousLeem()
    {
        return previousLeem;
    }

    public int getPreviousSteen()
    {
        return previousSteen;
    }

    public int getPreviousGoud()
    {
        return previousGoud;
    }

    public int getPreviousVoedsel()
    {
        return previousVoedsel;
    }

    public int getPreviousPunten()
    {
        return previousPunten;
    }

    public int getPreviousVoedselPerBeurt()
    {
        return previousVoedselPerBeurt;
    }

    public int getAddedVoedsel()
    {
        return addedVoedsel;
    }

    public boolean kanVoedselTeKortBetalen()
    {
        int voedseltekort = getVoedseltekort();
        int somResources = aantalGoud + aantalHout + aantalLeem + aantalSteen;

        if (voedseltekort <= 0)
            {
            return true;
            }

        if (somResources >= voedseltekort)
            {
            return true;
            }

        return false;
    }

    public void printInfo()
    {
        System.out.println("Kleur: " + kleur);
        //System.out.println("aantalGereedschap: " + aantalGereedschap);
        // System.out.println("gereedschapsPlaatsVolgendeRoll: " + this.gereedschapsPlaatsVolgendeRoll);
        //System.out.println("aantalGereedschapVolgendeRoll: " + this.aantalGereedschapVolgendeRoll);
        System.out.println("aantalGoud: " + aantalGoud);
        System.out.println("aantalHout: " + aantalHout);
        System.out.println("aantalSteen: " + aantalSteen);
        System.out.println("aantalLeem: " + aantalLeem);
        System.out.println("aantalVoedsel: " + aantalVoedsel);
        System.out.println("voedselPerBeurt: " + voedselPerBeurt);
        System.out.println("Punten: " + punten);
        System.out.println("aantalHutten: " + hutten.size());
        System.out.println("aantalPionnen: " + pionnen.size());
    }

}
