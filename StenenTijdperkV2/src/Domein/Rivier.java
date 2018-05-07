package Domein;

import java.util.ArrayList;


public class Rivier extends Locatie
{

    Rivier()
    {
        super("Rivier", 7, 0, 6);

    }

    public Rivier(ArrayList<Pion> pionnen)
    {
        super("Rivier", 7, 6, pionnen);
    }

}
