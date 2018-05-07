/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Domein;


/**
 *
 * @author NotAvailable
 */
public class GereedschapsFiche
{
    private int waarde;
    private boolean isGebruikt;

    GereedschapsFiche()
    {
        waarde = 1;
    }

    public GereedschapsFiche(int waarde, boolean isGebruikt)
    {
        setWaarde(waarde);
        isGebruikt = false;
    }

    /**
     * Can be used with both versions.
     *
     * @return
     */
    void increment()
    {
        if (waarde < 4)
            {
            waarde++;
            }
    }

    /**
     * Can be used with both versions
     */
    void reset()
    {
        isGebruikt = false;
    }

    /**
     * Can be used with both versions
     */
    public void toggleGebruikt()
    {
        isGebruikt = !isGebruikt;
    }

    public boolean isGebruikt()
    {
        return isGebruikt;
    }

    private void setWaarde(int waarde)
    {
        this.waarde = waarde;
    }

    public int getWaarde()
    {
        return waarde;
    }

}
