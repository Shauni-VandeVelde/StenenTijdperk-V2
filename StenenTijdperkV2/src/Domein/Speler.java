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
    private int hoeveelheidPionnenStart = 5;
    private int previousHuttenSize;
    private int totaalPenalty = 0;
    private DomeinController dc;
    private ArrayList<GereedschapsFiche> gereedschapsFiches = new ArrayList<>();
    private ArrayList<Hut> hutten = new ArrayList<>();
    private ArrayList<Pion> pionnen = new ArrayList<>();

    public Speler(DomeinController dc, Kleur kleur, int index)
    {
        this.dc = dc;
        this.index = index;
        this.kleur = kleur;
        Random rand = new Random();

        setVoedselPerBeurt(0);
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
        previousWaardeGereedschap = getHoeveelheidGereedschap();

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

    public void reset()
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
            previousWaardeGereedschap = getHoeveelheidGereedschap();
            }
    }

    public void clearVoedsel()
    {
        aantalVoedsel = 0;
    }

    public void voegHutToe(Hut hut)
    {
        hutten.add(hut);
        System.out.println("Hut Added");

    }

    public int berekenPuntenVanHutten()
    {
        int totaal = 0;

        for (Hut hut : hutten)
            {
            totaal += hut.berekenPunten();

            }

        return totaal;
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

    void voedPionnen()
    {

        geefVoedselPerBeurt();
        upkeep = pionnen.size();
        aantalVoedsel = aantalVoedsel - upkeep;

        if (aantalVoedsel < 0)
            {
            setPunten(getPunten() - 10);
            totaalPenalty -= 10;

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

    boolean verrekenStapel(Stapel stapel)
    {
        Hut hut = stapel.getBovensteHut();
        if ((hut.getGoudKost() < aantalGoud) && (hut.getHoutKost() < aantalHout) && (hut.getLeemKost() < aantalLeem) && (hut.getSteenKost() < aantalSteen))
            {
            aantalHout = aantalHout - hut.getHoutKost();
            aantalLeem = aantalLeem - hut.getLeemKost();
            aantalSteen = aantalSteen - hut.getSteenKost();
            aantalGoud = aantalGoud - hut.getGoudKost();
            System.out.println("Hut Kost " + hut.getHoutKost() + hut.getLeemKost() + hut.getSteenKost() + hut.getGoudKost());
            punten += hut.berekenPunten();
            System.err.println("Punten: " + punten);
            voegHutToe(hut);
            stapel.verwijderBovensteHut();
            stapel.clearPionnen();
            return true;
            }
        else
            {
            stapel.clearPionnen();
            return false;
            }
    }

    private void setIndex(int index)
    {
        this.index = index;
    }

    private void setKleur(Kleur kleur)
    {
        this.kleur = kleur;
    }

    public void setVoedselPerBeurt(int voedselPerBeurt)
    {
        previousVoedselPerBeurt = this.voedselPerBeurt;
        this.voedselPerBeurt = voedselPerBeurt;
    }

    public void setPunten(int punten)
    {
        previousPunten = this.punten;

        this.punten = punten;
    }

    public void setAantalGoud(int aantalGoud)
    {
        previousGoud = this.aantalGoud;
        this.aantalGoud = aantalGoud;
    }

    public void setAantalHout(int aantalHout)
    {
        previousHout = this.aantalHout;
        this.aantalHout = aantalHout;
    }

    public void setAantalLeem(int aantalLeem)
    {
        previousLeem = this.aantalLeem;
        this.aantalLeem = aantalLeem;
    }

    public void setAantalSteen(int aantalSteen)
    {
        previousSteen = this.aantalSteen;
        this.aantalSteen = aantalSteen;
    }

    public void setAantalVoedsel(int aantalVoedsel)
    {

        this.aantalVoedsel = aantalVoedsel;
    }

    public void setHutten(ArrayList<Hut> hutten)
    {
        previousHuttenSize = this.hutten.size();
        this.hutten = hutten;
    }

    public void setHuttenRefresh(ArrayList<Hut> hutten)
    {
        this.hutten = hutten;
        previousHuttenSize = hutten.size() - 1;
    }

    private void setPionnen(ArrayList<Pion> pionnen)
    {
        previousPionnenSize = this.pionnen.size();
        this.pionnen = pionnen;
    }

    public void setPionnenRefresh(ArrayList<Pion> pionnen)
    {
        this.pionnen = pionnen;
        previousPionnenSize = pionnen.size() - 1;
    }

    public void setGereedschapsFiches(ArrayList<GereedschapsFiche> gereedschapsFiches)
    {
        this.gereedschapsFiches = gereedschapsFiches;
    }

    public void setPreviousHout(int previousHout)
    {
        this.previousHout = previousHout;
    }

    public void setPreviousLeem(int previousLeem)
    {
        this.previousLeem = previousLeem;
    }

    public void setPreviousSteen(int previousSteen)
    {
        this.previousSteen = previousSteen;
    }

    public void setPreviousGoud(int previousGoud)
    {
        this.previousGoud = previousGoud;
    }

    public void setPreviousVoedsel(int previousVoedsel)
    {
        this.previousVoedsel = previousVoedsel;
    }

    public void setVoedselEindeVorigeBeurt(int voedselEindeVorigeBeurt)
    {
        this.voedselEindeVorigeBeurt = voedselEindeVorigeBeurt;
    }

    public void setPreviousPunten(int previousPunten)
    {
        this.previousPunten = previousPunten;
    }

    public void setPreviousVoedselPerBeurt(int previousVoedselPerBeurt)
    {
        this.previousVoedselPerBeurt = previousVoedselPerBeurt;
    }

    public void setPreviousPionnenSize(int previousPionnenSize)
    {
        this.previousPionnenSize = previousPionnenSize;
    }

    public void setPreviousWaardeGereedschap(int previousWaardeGereedschap)
    {
        this.previousWaardeGereedschap = previousWaardeGereedschap;
    }

    public void setAddedVoedsel(int addedVoedsel)
    {
        this.addedVoedsel = addedVoedsel;
    }

    public void setUpkeep(int upkeep)
    {
        this.upkeep = upkeep;
    }

    public void setHoeveelheidPionnenStart(int hoeveelheidPionnenStart)
    {
        this.hoeveelheidPionnenStart = hoeveelheidPionnenStart;
    }

    public void setPreviousHuttenSize(int previousHuttenSize)
    {
        this.previousHuttenSize = previousHuttenSize;
    }

    public void setTotaalPenalty(int totaalPenalty)
    {
        this.totaalPenalty = totaalPenalty;
    }

    public void setDc(DomeinController dc)
    {
        this.dc = dc;
    }

    public GereedschapsFiche getGereedchapsFiche(int index)
    {
        return gereedschapsFiches.get(index);
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

    public int getVoedselEindeVorigeBeurt()
    {
        return voedselEindeVorigeBeurt;
    }

    public ArrayList<Pion> getPionnen()
    {
        return pionnen;
    }

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

    public int getIndex()
    {
        return index;
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

    public int getAantalSteen()
    {
        return aantalSteen;
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

    public int getTotaalPenalty()
    {
        return totaalPenalty;
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
