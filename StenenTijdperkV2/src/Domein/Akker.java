package Domein;

import java.util.ArrayList;


public class Akker extends Locatie
{

    Akker()
    {
        super("Akker", 1, 0, -1);

    }

    public Akker(ArrayList<Pion> pionnen)
    {
        super("Akker", 1, -1, pionnen);
    }

}
