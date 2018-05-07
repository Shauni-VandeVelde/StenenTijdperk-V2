package Domein;

import java.util.ArrayList;


public class Gereedschapsmaker extends Locatie
{

    Gereedschapsmaker()
    {
        super("Gereedschapsmaker", 1, 0, -1);
    }

    public Gereedschapsmaker(ArrayList<Pion> pionnen)
    {
        super("Gereedschapsmaker", 1, -1, pionnen);
    }

}
