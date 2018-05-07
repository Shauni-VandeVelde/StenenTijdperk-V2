package Domein;

import java.util.ArrayList;


public class Bos extends Locatie
{

    Bos()
    {
        super("Bos", 7, 0, 3);

    }

    public Bos(ArrayList<Pion> pionnen)
    {
        super("Bos", 7, 3, pionnen);
    }

}
