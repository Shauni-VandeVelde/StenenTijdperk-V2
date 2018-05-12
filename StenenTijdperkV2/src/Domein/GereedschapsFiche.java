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

    void increment()
    {
        if (waarde < 4)
            {
            waarde++;
            }
    }

    void reset()
    {
        isGebruikt = false;
    }

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
