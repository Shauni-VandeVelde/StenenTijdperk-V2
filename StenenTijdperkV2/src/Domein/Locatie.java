package Domein;

import java.util.ArrayList;


public abstract class Locatie
{

    private String naam;
    private int maxPionnen;
    private int huidigAantalPionnen;
    private int waarde = -1;
    private ArrayList<Pion> pionnen = new ArrayList<>();

    protected Locatie(String naam, int maxPionnen, int huidigAantalPionnen, int waarde)
    {
        setNaam(naam);
        setMaxPionnen(maxPionnen);
        setHuidigAantalPionnen(huidigAantalPionnen);
        setWaarde(waarde);

    }

    protected Locatie(String naam, int maxPionnen, int waarde, ArrayList<Pion> pionnen)
    {
        setNaam(naam);
        setMaxPionnen(maxPionnen);
        setHuidigAantalPionnen(pionnen.size());
        setWaarde(waarde);
        setPionnen(pionnen);
    }

    public boolean heeftPionnen()
    {
        if (getPionnenList().size() > 0)
            {
            return true;
            }
        return false;
    }

    protected void plaatsPion(Pion pion)
    {
        pionnen.add(pion);
        pion.setGebruikt(true);
        huidigAantalPionnen = pionnen.size();
    }

    public boolean heeftPlaatsGenoeg(int aantal)
    {
        if (aantal <= getAantalPlaatsenVrij())
            {
            return true;
            }
        else
            {
            return false;
            }
    }

    public int getBruikbarePionnen(Speler speler)
    {

        int j = getMaxPionnen() - getHuidigAantalPionnen();
        if (j < speler.getAantalPionnenOver())
            {
            return j;
            }
        else
            {
            return speler.getAantalPionnenOver();
            }

    }

    public boolean kanLocatieVullen(Speler speler)
    {
        if (speler.getAantalPionnenOver() >= getMaxPionnen())
            {
            return true;
            }
        else
            {
            return false;
            }
    }

    public boolean magPionnenSelecteren()
    {
        if (getWaarde() == -1)
            {
            return false;
            }
        else
            {
            return true;
            }
    }

    public int getAantalPlaatsenVrij()
    {
        return getMaxPionnen() - getHuidigAantalPionnen();
    }

    protected void clearPionnen()
    {
        pionnen.clear();

    }

    protected void verwijderPionnenVanSpelerMetKleur(Kleur kleur)
    {
        ArrayList<Pion> toRemove = new ArrayList<>();
        for (int i = 0; i < pionnen.size(); i++)
            {

            if ((pionnen.get(i).getKleur()) == kleur)
                {
                toRemove.add(pionnen.get(i));
                }
            }
        for (int i = 0; i < toRemove.size(); i++)
            {
            pionnen.remove(toRemove.get(i));
            }
    }

    protected void reset()
    {
        setHuidigAantalPionnen(0);
        clearPionnen();
    }

    private void setNaam(String naam)
    {
        this.naam = naam;
    }

    private void setWaarde(int waarde)
    {
        this.waarde = waarde;
    }

    private void setMaxPionnen(int maxPionnen)
    {
        this.maxPionnen = maxPionnen;
    }

    protected void setHuidigAantalPionnen(int huidigAantalPionnen)
    {
        this.huidigAantalPionnen = huidigAantalPionnen;
    }

    private void setPionnen(ArrayList<Pion> pionnen)
    {
        this.pionnen = pionnen;
    }

    public String getNaam()
    {
        return naam;
    }

    public int getWaarde()
    {
        return waarde;
    }

    public int getMaxPionnen()
    {
        return maxPionnen;
    }

    public int getHuidigAantalPionnen()
    {
        return huidigAantalPionnen;
    }

    public int getAantalPionnenVanSpelerMetKleur(Kleur kleur)
    {
        int aantal = 0;
        for (int i = 0; i < pionnen.size(); i++)
            {
            if ((pionnen.get(i).getKleur()) == kleur)
                {
                aantal++;
                }
            }
        return aantal;
    }

    public ArrayList<Pion> getPionnenList()
    {
        return pionnen;
    }

    @Override
    public String toString()
    {
        return naam;
    }

}
