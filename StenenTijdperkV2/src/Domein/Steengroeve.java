package Domein;

import java.util.ArrayList;


public class Steengroeve extends Locatie
{

    Steengroeve()
    {
        super("Steengroeve", 7, 0, 5);

    }

    public Steengroeve(ArrayList<Pion> pionnen)
    {
        super("Steengroeve", 7, 5, pionnen);
    }

}
