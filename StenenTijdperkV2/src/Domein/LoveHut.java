package Domein;

import java.util.ArrayList;


public class LoveHut extends Locatie
{

    LoveHut()
    {
        super("LoveHut", 2, 0, -1);
    }

    public LoveHut(ArrayList<Pion> pionnen)
    {
        super("LoveHut", 2, -1, pionnen);
    }

}
