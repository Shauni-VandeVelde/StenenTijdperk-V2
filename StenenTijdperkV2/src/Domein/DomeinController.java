package Domein;

import java.util.ArrayList;


public class DomeinController
{
    public static boolean GUI;

    private Spelbord spelbord;
    private ArrayList<Speler> spelers = new ArrayList<>();
    private String[] vorigeLocaties;
    private final int goudWaarde = 6;
    private final int leemWaarde = 4;
    private final int steenWaarde = 5;
    private final int houtWaarde = 3;

    private int huidigeSpelerIndex = 0;
    private int startSpelerindex = 0;
    private int aantalSpelers = 0;

    private int rondeNummer = 1;

    public DomeinController(boolean GUI)
    {
        this.GUI = GUI;
        this.spelbord = new Spelbord(this);
    }

    public Stapel getBezetteStapel(Speler speler)
    {
        for (int i = 0; i < 4; i++)
            {
            if (spelbord.getStapel(i).getPionnenList().size() != 0)
                {
                if (spelbord.getStapel(i).getPionnenList().get(0).getKleur() == speler.getKleur())
                    {
                    return spelbord.getStapel(i);
                    }
                }

            }
        // System.err.println("Returned null");
        return null;
    }

    public void incrementRondeNummer()
    {
        rondeNummer++;
    }

    public void incrementHuidigeSpelerIndex()
    {
        huidigeSpelerIndex++;
        if (huidigeSpelerIndex == spelers.size())
            {
            huidigeSpelerIndex = 0;
            }
    }

    public void voedselPenalty(Speler speler)
    {
        speler.setAantalVoedsel(0);
        geefMinpunten(speler.index());
    }

    /**
     * Can be used with both versions.
     */
    public void incrementStartSpelerNummer()
    {
        startSpelerindex++;
        if (startSpelerindex == aantalSpelers)
            {
            startSpelerindex = 0;
            }
    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    public void addToVorigeLocatie(Locatie nieuweLocatie)
    {
        vorigeLocaties[getHuidigeSpelerIndex()] = vorigeLocaties[getHuidigeSpelerIndex()] + nieuweLocatie.getNaam();
    }

    public boolean verrekenStapel(Stapel stapel, Speler speler)
    {
        Hut hut = stapel.getBovensteHut();
        if ((hut.getGoudKost() < speler.getAantalGoud()) && (hut.getHoutKost() < speler.getAantalHout()) && (hut.getLeemKost() < speler.getAantalLeem()) && (hut.getSteenKost() < speler.getAantalSteen()))
            {
            speler.setAantalHout(speler.getAantalGoud() - hut.getHoutKost());
            speler.setAantalLeem(speler.getAantalGoud() - hut.getLeemKost());
            speler.setAantalSteen(speler.getAantalGoud() - hut.getSteenKost());
            speler.setAantalGoud(speler.getAantalGoud() - hut.getGoudKost());
            speler.voegHutToe(hut);
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

    public void verrekenAkker()
    {
        if (getLocatie("akker").getHuidigAantalPionnen() == 1)
            {
            Speler speler = getSpelerMetKleur(getLocatie("akker").getPionnenList().get(0).getKleur());
            speler.incrementVoedselPerBeurt();
            getLocatie("akker").clearPionnen();
            }
    }

    public void verrekenGereedschapsmaker()
    {
        if (getLocatie("gereedschapsmaker").getHuidigAantalPionnen() == 1)
            {
            Speler speler = getSpelerMetKleur(getLocatie("gereedschapsmaker").getPionnenList().get(0).getKleur());
            speler.geefNieuwGereedschap();
            getLocatie("gereedschapsmaker").clearPionnen();
            }
    }

    public void verrekenLoveHut()
    {
        if (getLocatie("lovehut").getHuidigAantalPionnen() == 2)
            {
            Speler speler = getSpelerMetKleur(getLocatie("lovehut").getPionnenList().get(0).getKleur());
            speler.incrementPionnen();
            getLocatie("lovehut").clearPionnen();
            }
    }

    public void resetLocaties()
    {
        for (int i = 0; i < getAllLocaties().size(); i++)
            {
            getAllLocaties().get(i).reset();
            }
    }

    public void resetGebruiktGereedschap()
    {
        for (int i = 0; i < aantalSpelers; i++)
            {
            spelers.get(i).clearGebruiktGereedschap();
            }
    }

    public void resetVorigeLocaties()
    {
        for (int i = 0; i < vorigeLocaties.length; i++)
            {
            vorigeLocaties[i] = "";
            }
    }

    public void resetPionnen()
    {
        for (int i = 0; i < aantalSpelers; i++)
            {
            spelers.get(i).reset();
            }
    }

    public void startSpel()
    {

        for (int i = 0; i < getAantalSpelers(); i++)
            {
            spelers.add(i, new Speler(Kleur.values()[i], i));
            }

        vorigeLocaties = new String[getAantalSpelers()];
        resetVorigeLocaties();
        setHuidigeSpelerIndex();

    }

    public void endOfGame()
    {

        System.out.println("Punten");
        System.out.println("----------");
        for (Speler speler : spelers)
            {
            System.out.println("Speler " + speler.getKleur() + ": " + speler.getPunten());
            }
        System.out.println();

        for (Speler speler : spelers)
            {
            speler.berekenPuntenVanHutten(houtWaarde, leemWaarde, steenWaarde, goudWaarde);
            speler.converteerGrondstoffenNaarPunten();
            }

        Speler winnaar = getSpelerMetHoogstePunten();

        if (!isGelijkspel(winnaar.getPunten()))
            {
            System.out.println("----------");
            System.out.println("Speler " + winnaar.getKleur() + " heeft gewonnen!");
            System.out.println("----------");
            }
        else
            {
            ArrayList<Speler> winnaars = getWinnaars(winnaar.getPunten());

            System.out.println("Het is een gelijkspel!");
            System.out.println("De volgende spelers hebben een gelijkspel behaald:");
            for (Speler speler : winnaars)
                {
                System.out.println("Speler: " + speler.getKleur());
                }
            System.out.println("\n Einde spel.");
            }
    }

    public void geefResources(int spelerIndex, int rol, Locatie locatie, int aantalGereedschap)
    {

        Speler speler = getSpelers().get(spelerIndex);
        int hoeveelheid = 0;

        hoeveelheid = (rol + aantalGereedschap) / locatie.getWaarde();
        if (locatie.getNaam().equalsIgnoreCase("rivier"))
            {
            speler.setAantalGoud(speler.getAantalGoud() + hoeveelheid);
            }
        if (locatie.getNaam().equalsIgnoreCase("bos"))
            {
            speler.setAantalHout(speler.getAantalHout() + hoeveelheid);
            }
        if (locatie.getNaam().equalsIgnoreCase("leemgroeve"))
            {
            speler.setAantalLeem(speler.getAantalLeem() + hoeveelheid);
            }
        if (locatie.getNaam().equalsIgnoreCase("steengroeve"))
            {
            speler.setAantalSteen(speler.getAantalSteen() + hoeveelheid);
            }
        if (locatie.getNaam().equalsIgnoreCase("jacht"))
            {
            speler.krijgNieuwVoedsel(hoeveelheid);
            }

    }

    public void plaatsPionnenOpVeld(Locatie gekozenLocatie, int aantalPionnen)
    {

        getHuidigeSpeler().plaatsPionnen(gekozenLocatie, aantalPionnen);
        vorigeLocaties[getHuidigeSpelerIndex()] += gekozenLocatie.getNaam();
    }

    public boolean voerVoedenUit(boolean[] accept)
    {
        boolean allAccept = true;
        for (int i = 0; i < spelers.size(); i++)

            {

            if (spelers.get(i).getVoedseltekort() <= 0)
                {
                accept[i] = true;
                }

            }
        for (int i = 0; i < accept.length; i++)
            {

            if (accept[i] == false)
                {
                allAccept = false;
                }
            }
        return allAccept;

    }

    public void voedPionnen()
    {
        for (int i = 0; i < spelers.size(); i++)
            {
            Speler speler = getSpelers().get(i);

            speler.voedPionnen();

            }
    }

    public void ruilGoud(int aantalGoud, Speler speler)
    {
        speler.ruilResource("goud", aantalGoud);

    }

    public void ruilSteen(int aantalSteen, Speler speler)
    {

        speler.ruilResource("steen", aantalSteen);
    }

    public void ruilLeem(int aantalLeem, Speler speler)
    {
        speler.ruilResource("leem", aantalLeem);
    }

    public void ruilHout(int aantalHout, Speler speler)
    {
        speler.ruilResource("hout", aantalHout);
    }

    public void geefMinpunten(int spelerIndex)
    {
        Speler speler = getSpelers().get(spelerIndex);
        speler.setPunten(speler.getPunten() - 10);
        speler.clearVoedsel();
    }

    public void clearPionnenVanSpeler(int spelerIndex, Locatie locatie)
    {

        locatie.verwijderPionnenVanSpelerMetKleur(spelers.get(spelerIndex).getKleur());
    }

    public void clearPionnenVanSpeler(Speler speler, Locatie locatie)
    {

        locatie.verwijderPionnenVanSpelerMetKleur(speler.getKleur());
    }

    public boolean isLocatieNogNietGebruikt(Locatie gekozenLocatie)
    {
        if (getVorigeLocatie(getHuidigeSpelerIndex()).toLowerCase().contains(gekozenLocatie.getNaam().toLowerCase()))
            {
            return false;
            }
        else
            {
            return true;
            }
    }

    public boolean isGeldigeLocatieNaam(String naam)
    {
        if (getLocatie(naam) != null)
            {
            return true;
            }
        else
            {
            return false;
            }
    }

    //tbla
    public void setGereedschapGebruikt(Speler speler, int index)
    {
        speler.getGereedchapsFiche(index).toggleGebruikt();
    }

    public void setHuidigeSpelerIndex()
    {
        this.huidigeSpelerIndex = startSpelerindex;
    }

    public void setAantalSpelers(int aantalSpelers)
    {
        this.aantalSpelers = aantalSpelers;
    }

    public boolean isHetSpelGedaan()
    {
        // Check of het spel voorbij is
        boolean isEenStapelLeeg = false;

        for (int i = 0; i < 4; i++)
            {
            if (spelbord.getStapel(i).getHutten().size() == 0)
                {
                isEenStapelLeeg = true;
                }

            }
        return isEenStapelLeeg;
    }

    public boolean isGelijkspel(int hoogstePunten)
    {
        int hoeveelHeidSpelersMetHoogstePunten = 0;
        for (Speler speler : spelers)
            {
            if (speler.getPunten() == hoogstePunten)
                {
                ++hoeveelHeidSpelersMetHoogstePunten;
                }

            if (hoeveelHeidSpelersMetHoogstePunten > 1)
                {
                return true;
                }
            }

        return false;
    }

    public boolean isEindeRonde()
    {
        boolean nogPionnen = false;
        for (int i = 0; i < getAantalSpelers(); i++)
            {
            if (!spelers.get(i).heeftAllePionnenGeplaatst())
                {
                nogPionnen = true;
                }
            }
        return !nogPionnen;
    }

    public boolean moetNogRollen(int spelerIndex)
    {
        if (getVolgendeBezetteLocatie(spelerIndex) == null)
            {
            return false;
            }
        else
            {
            return true;
            }
    }

    public boolean alleSpelersHebbenHunPionnenGeplaatst()
    {
        for (Speler speler : spelers)
            {
            if (!speler.heeftAllePionnenGeplaatst())
                {
                return false;
                }
            }

        return true;
    }

    public String getNaamVanResource(Locatie locatie)
    {
        switch (locatie.getNaam().trim().toLowerCase())
            {
            case "bos":
                return "hout";
            case "leemgroeve":
                return "leem";
            case "steengroeve":
                return "steen";
            case "rivier":
                return "goud";
            case "jacht":
                return "voedsel";
            }
        return "";
    }

    public int getRondeNummer()
    {
        return rondeNummer;
    }

    public int getHuidigeSpelerIndex()
    {
        return huidigeSpelerIndex;

    }

    public int getStartSpelerindex()
    {
        return startSpelerindex;
    }

    public int getAantalSpelers()
    {
        return aantalSpelers;
    }

    public int getGereedschapsWaarde(int spelerIndex, int index)
    {
        return spelers.get(spelerIndex).getGereedchapsFiche(index).getWaarde();
    }

    public int getRol(int spelerIndex, Locatie locatie)
    {

        Kleur kleur = getSpelers().get(spelerIndex).getKleur();
        int totaalOgen = 0;
        for (int i = 0; i < locatie.getAantalPionnenVanSpelerMetKleur(kleur); ++i)
            {
            totaalOgen += (int) ((6 * Math.random()) + 1);
            }

        if (locatie.getWaarde() != -1)
            {

            return totaalOgen;
            }

        return -1;
    }

    public ArrayList<Integer> getBruikbaarGereedschapIndexesSpelerMetIndex(int spelerIndex)
    {
        return spelers.get(spelerIndex).getIndexesBruikbaarGereedschap();
    }

    public Locatie getVolgendeBezetteLocatie(int spelerIndex)
    {
        Kleur kleur = getSpelers().get(spelerIndex).getKleur();
        ArrayList<Locatie> locList = getAllLocaties();
        Locatie locatie = null;
        for (int i = 0; i < locList.size(); i++)
            {
            if (locList.get(i).getAantalPionnenVanSpelerMetKleur(kleur) != 0)
                {
                if (locList.get(i).getWaarde() != -1)
                    {
                    locatie = locList.get(i);
                    }
                }
            }

        return locatie;
    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    public String getVorigeLocatie(int index)
    {
        return vorigeLocaties[index];
    }

    public Speler getHuidigeSpeler()
    {
        return spelers.get(huidigeSpelerIndex);
    }

    private Speler getSpelerMetHoogstePunten()
    {

        Speler highest = spelers.get(0);

        for (Speler speler : spelers)
            {
            if (speler.getPunten() > highest.getPunten())
                {
                highest = speler;
                }
            }

        return highest;
    }

    public ArrayList<Speler> getWinnaars(int hoogsteAantalPunten)
    {
        ArrayList<Speler> winnaars = new ArrayList<Speler>();

        for (Speler speler : spelers)
            {
            if (speler.getPunten() == hoogsteAantalPunten)
                {
                winnaars.add(speler);
                }
            }

        return winnaars;
    }

    public ArrayList<Speler> getOrderedWinnaars()
    {

        ArrayList<Speler> temp = spelers;

        ArrayList<Speler> lijst = new ArrayList<>();

        while (temp.size() != 0)
            {
            Speler speler = temp.get(0);
            for (int j = 0; j < temp.size(); j++)
                {
                int a = speler.getPunten() + speler.getWaardeVanResources() + speler.getHutten().size() * 3;
                int b = temp.get(j).getPunten() + temp.get(j).getWaardeVanResources() + temp.get(j).getHutten().size() * 3;
                if (a < b)
                    {
                    speler = temp.get(j);
                    System.out.println(a + " > " + b);

                    }
                }

            lijst.add(speler);
            temp.remove(speler);
            }
        return lijst;
    }

    public ArrayList<Speler> getSpelers()
    {
        return spelers;
    }

    public ArrayList<Locatie> getAllLocaties()
    {
        return spelbord.getAllLocaties();
    }

    public Locatie getLocatie(String naam)
    {

        ArrayList<Locatie> all = getAllLocaties();
        for (int i = 0; i < all.size(); i++)
            {
            if (naam.equalsIgnoreCase(all.get(i).getNaam()))
                {
                return all.get(i);
                }
            }
        return null;
    }

    public String endOfRound()
    {
        String message = "\n";
        for (Speler speler : spelers)
            {

            // Check elke pion
            int pionIndex = 0;

            speler.clearGebruiktGereedschap();

            if (getLocatie("akker").getAantalPionnenVanSpelerMetKleur(speler.getKleur()) == 1)
                {
                speler.setVoedselPerBeurt(speler.getVoedselPerBeurt() + 1);
                }
            if (getLocatie("lovehut").getAantalPionnenVanSpelerMetKleur(speler.getKleur()) == 2)
                {
                if (!speler.incrementPionnen())
                    {
                    message += "Speler " + speler.getKleur() + " heeft al het maximum aan pionnen bereikt.";
                    }
                }
            if (getLocatie("gereedschapsmaker").getAantalPionnenVanSpelerMetKleur(speler.getKleur()) == 1)
                {
                speler.geefNieuwGereedschap();
                }

            speler.reset();

            message += "\nSpeler " + speler.getKleur() + " zijn voedsel per beurt is nu " + speler.getVoedselPerBeurt() + ".";
            message += "\n_____________________________________";
            }
        spelbord.resetLocaties();

        return message;
    }

    public Speler getSpelerMetKleur(Kleur kleur)
    {
        for (Speler speler : spelers)
            {
            if (speler.getKleur() == kleur)
                {
                return speler;
                }
            }

        // Verplicht. Zal deze lijn nooit mogen bereiken
        return spelers.get(0);
    }

    public Stapel getStapel(int index)
    {
        return spelbord.getStapel(index);
    }

    public Spelbord getSpelbord()
    {
        return spelbord;
    }

}
