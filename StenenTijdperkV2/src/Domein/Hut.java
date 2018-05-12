package Domein;

import java.util.Random;


public class Hut
{

    private int houtKost;
    private int steenKost;
    private int goudKost;
    private int leemKost;

    Hut()
    {
        randomizeCost();
    }

    public Hut(int houtKost, int steenKost, int goudKost, int leemKost)
    {
        setHoutKost(houtKost);
        setSteenKost(steenKost);
        setGoudKost(goudKost);
        setLeemKost(leemKost);
    }

    public boolean kanHutKopen(Speler speler)
    {
        boolean kanKopen = true;
        if (speler.getAantalHout() < houtKost)
            {
            kanKopen = false;
            }
        if (speler.getAantalLeem() < leemKost)
            {
            kanKopen = false;
            }
        if (speler.getAantalSteen() < steenKost)
            {
            kanKopen = false;
            }
        if (speler.getAantalGoud() < goudKost)
            {
            kanKopen = false;
            }
        return kanKopen;
    }

    public String getPrijsMessage()
    {
        String message = "";
        if (getHoutKost() != 0)
            {
            message += "Hout: " + getHoutKost();
            }
        if (getLeemKost() != 0)
            {
            if (!message.equals(""))
                {
                message += " | ";
                }
            message += "Leem: " + getLeemKost();
            }
        if (getSteenKost() != 0)
            {
            if (!message.equals(""))
                {
                message += " | ";
                }
            message += "Steen: " + getSteenKost();
            }
        if (getGoudKost() != 0)
            {
            if (!message.equals(""))
                {
                message += " | ";
                }
            message += "Goud: " + getGoudKost();
            }
        return message;

    }

    private void randomizeCost()
    {
        int count = 0;
        Random rand = new Random();
        while (count < 3)
            {
            switch (rand.nextInt(4))
                {
                case 0:
                    houtKost++;
                    count++;
                    break;

                case 1:
                    steenKost++;
                    count++;
                    break;
                case 2:
                    goudKost++;
                    count++;
                    break;
                case 3:
                    leemKost++;
                    count++;
                    break;
                }

            }
    }

    private void setLeemKost(int Leemkost)
    {
        this.leemKost = Leemkost;
    }

    private void setGoudKost(int Goudkost)
    {
        this.goudKost = Goudkost;
    }

    private void setHoutKost(int Houtkost)
    {
        this.houtKost = Houtkost;
    }

    private void setSteenKost(int Steenkost)
    {
        this.steenKost = Steenkost;
    }

    int berekenPunten()
    {
        return (houtKost * 3) + (leemKost * 4) + (steenKost * 5) + (goudKost * 6);
    }

    public int getSteenKost()
    {
        return steenKost;
    }

    public int getGoudKost()
    {
        return goudKost;
    }

    public int getLeemKost()
    {
        return leemKost;
    }

    public int getHoutKost()
    {
        return houtKost;
    }

    void print()
    {
        System.out.println("houtKost: " + houtKost);
        System.out.println("steenKost: " + steenKost);
        System.out.println("goudKost: " + goudKost);
        System.out.println("leemKost: " + leemKost);
    }

}
