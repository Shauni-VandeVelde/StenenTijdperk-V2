package Domein;

import java.util.ArrayList;


public class Jacht extends Locatie
{

    Jacht()
    {
        super("Jacht", 40, 0, 2);
    }

    public Jacht(ArrayList<Pion> pionnen)
    {
        super("Jacht", 40, 2, pionnen);
    }

}
