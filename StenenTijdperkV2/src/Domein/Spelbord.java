package Domein;

import java.util.ArrayList;


public class Spelbord
{
    private DomeinController controller;
    private Rivier rivier;
    private Bos bos;
    private Steengroeve steengroeve;
    private Leemgroeve leemgroeve;
    private Akker akker;
    private LoveHut loveHut;
    private Gereedschapsmaker gereedschapsmaker;
    private Jacht jacht;

    private ArrayList<Stapel> stapels = new ArrayList<Stapel>();

    public Spelbord(DomeinController controller)
    {
        this.controller = controller;
        this.rivier = new Rivier();
        this.bos = new Bos();
        this.steengroeve = new Steengroeve();
        this.leemgroeve = new Leemgroeve();
        this.akker = new Akker();
        this.loveHut = new LoveHut();
        this.gereedschapsmaker = new Gereedschapsmaker();
        this.jacht = new Jacht();

        for (int i = 0; i < 4; i++)
            {
            stapels.add(i, new Stapel(controller, i + 1));
            }
    }

    public Spelbord(Akker akker, Bos bos, Leemgroeve leemgroeve, Steengroeve steengroeve, Rivier rivier, LoveHut loveHut, Gereedschapsmaker gereedschapsmaker, Jacht jacht, ArrayList<Stapel> stapels)
    {
        this.akker = akker;
        this.bos = bos;
        this.leemgroeve = leemgroeve;
        this.steengroeve = steengroeve;
        this.rivier = rivier;
        this.loveHut = loveHut;
        this.gereedschapsmaker = gereedschapsmaker;
        this.jacht = jacht;
        this.stapels = stapels;

    }

    public void resetLocaties()
    {
        rivier.reset();
        bos.reset();
        steengroeve.reset();
        leemgroeve.reset();
        akker.reset();
        loveHut.reset();
        gereedschapsmaker.reset();
        jacht.reset();
    }

    public ArrayList<Locatie> getAllLocaties()
    {
        ArrayList<Locatie> all = new ArrayList<>();
        all.add(rivier);
        all.add(bos);
        all.add(steengroeve);
        all.add(leemgroeve);
        all.add(akker);
        all.add(loveHut);
        all.add(gereedschapsmaker);
        all.add(jacht);
        all.add(stapels.get(0));
        all.add(stapels.get(1));
        all.add(stapels.get(2));
        all.add(stapels.get(3));

        return all;
    }

    public Stapel getStapel(int index)
    {
        return stapels.get(index);
    }

    public ArrayList<Stapel> getStapels()
    {
        return this.stapels;
    }

    public void clearStapelsInhoud(){
        for(Stapel st : stapels){
            st.clear();
        }
    }
}
