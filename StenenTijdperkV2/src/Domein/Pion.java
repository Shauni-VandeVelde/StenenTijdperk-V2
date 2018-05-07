package Domein;


public class Pion
{

    private Kleur kleur;
    private boolean gebruikt = false;

    Pion(Kleur kleur)
    {
        this.kleur = kleur;

    }

    public Pion(Kleur kleur, boolean gebruikt)
    {
        setGebruikt(gebruikt);
        setKleur(kleur);
    }

    /**
     * For use with Gui version
     */
    void setGebruikt(boolean gebruikt)
    {
        this.gebruikt = gebruikt;
    }

    /**
     * Can be used with both versions.
     *
     * @param kleur
     */
    private void setKleur(Kleur kleur)
    {
        this.kleur = kleur;
    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    public Kleur getKleur()
    {
        return kleur;
    }

    /**
     * For use with Gui version
     */
    public boolean isGebruikt()
    {
        return gebruikt;
    }

}
