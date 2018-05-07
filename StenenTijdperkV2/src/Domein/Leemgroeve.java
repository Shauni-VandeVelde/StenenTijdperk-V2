package Domein;

import java.util.ArrayList;


public class Leemgroeve extends Locatie
{

    Leemgroeve()
    {
        super("Leemgroeve", 7, 0, 4);
    }

    public Leemgroeve(ArrayList<Pion> pionnen)
    {
        super("Leemgroeve", 7, 4, pionnen);
    }

}
