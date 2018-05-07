/**
 *
 * @author NotAvailable
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import Domein.DomeinController;
import Domein.Hut;
import Domein.Locatie;
import Domein.Speler;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author Shauni
 */
public class StartUI
{

    private Scanner scanner;
    private DomeinController dc = new DomeinController(false);
    private String input = "";
    private Locatie gekozenLocatie;
    private int rondeNummer = 1;
    private boolean geldigePositieNaam = false;
    private boolean pionnenGeplaatst = false;
    private boolean geldigAantalPionnen = false;
    private boolean kanPionnenPlaatsen = false;

    private StartUI()
    {
        run();
    }

    public static void main(String[] args)
    {

        StartUI startUI = new StartUI();
    }

    private void run()
    {
        scanner = new Scanner(System.in);
        verzoekAantalSpelers();
        dc.startSpel();
        //------------------------------------------------//
        // --- INITIALISEREN van de spelers, geeft de kleur terug: --- //
        for (int i = 0; i < dc.getAantalSpelers(); i++)
            {
            System.out.println("Speler: " + (i + 1) + " met kleur: " + dc.getSpelers().get(i).getKleur());
            }
        boolean firstRound = true;
        //------------------------------------------------//
        // --- Bepalen welke speler aan de beurt is --- //
        do
            {
            dc.setHuidigeSpelerIndex();
            if (!firstRound)
                {
                System.out.println(dc.endOfRound());
                }
            else
                {
                firstRound = false;
                }
            System.out.println("Start van ronde: " + rondeNummer + "\n");
            while (!dc.isEindeRonde())
                {
                System.err.println(dc.getHuidigeSpeler().getKleur() + " stapelsize = " + dc.getHuidigeSpeler().getHutten().size());
                pionnenGeplaatst = false;
                //------------------------------------------------//
                ///////////////// BEURT SPELER: /////////////////
                //
                //
                //
                if (!dc.getHuidigeSpeler().heeftAllePionnenGeplaatst())
                    {
                    System.out.println(
                            "Speler " + (dc.getHuidigeSpelerIndex() + 1) + " Heeft "
                            + "Hout: " + dc.getHuidigeSpeler().getAantalHout()
                            + " Leem: " + dc.getHuidigeSpeler().getAantalLeem()
                            + " Steen: " + dc.getHuidigeSpeler().getAantalSteen()
                            + " Goud: " + dc.getHuidigeSpeler().getAantalGoud()
                            + " Voedsel: " + dc.getHuidigeSpeler().getAantalVoedsel()
                            + " Totale waarde van gereedschap: " + dc.getHuidigeSpeler().getHoeveelheidGereedschap());
                    System.out.println("Speler " + (dc.getHuidigeSpelerIndex() + 1) + " mag nu zijn stamleden plaatsen");
                    System.out.println("Je hebt nog " + dc.getHuidigeSpeler().getAantalPionnenOver() + " pionnen over");

                    input = "";
                    gekozenLocatie = null;
                    geldigePositieNaam = false;
                    geldigAantalPionnen = false;
                    kanPionnenPlaatsen = false;

                    while (!geldigAantalPionnen || !kanPionnenPlaatsen)
                        {
                        while (!geldigePositieNaam)
                            {
                            kiesLocatieNaam();
                            }

                        if (kiesAantalPionnen())
                            {
                            continue;
                            }
                        }
                    // --- EINDE VAN DE BEURT --- //
                    if (dc.getHuidigeSpeler().heeftAllePionnenGeplaatst())
                        {
                        System.out.println("Speler " + (dc.getHuidigeSpelerIndex() + 1) + " heeft al zijn stamleden geplaatst");
                        }
                    else
                        {
                        System.out.println("Speler " + (dc.getHuidigeSpelerIndex() + 1) + "heeft nog niet al zijn pionnen geplaatst");
                        }
                    }
                //dc.setSpeler(dc.getHuidigeSpelerIndex(), dc.getHuidigeSpeler());
                dc.incrementHuidigeSpelerIndex();

                System.out.println("Pionnen ongebruikt");
                System.out.println("---");

                for (int i = 0; i < dc.getAantalSpelers(); i++)
                    {
                    System.out.println("Speler " + (i + 1) + ": " + dc.getSpelers().get(i).getAantalPionnenOver());
                    }
                }
            System.out.println("\nAlle spelers hebben hun pionnen geplaatst. \n");
            voerRollenUit();
            voedseltekortRuilen();
            dc.resetVorigeLocaties();
            rondeNummer++;
            dc.incrementStartSpelerNummer();
            System.out.println("\nEND OF ROUND " + rondeNummer + ": \n");
            }
        while (!dc.isHetSpelGedaan());
        dc.endOfGame();
    }

    private void voerRollenUit()
    {
        for (int spelerIndex = 0; spelerIndex < dc.getAantalSpelers(); spelerIndex++)
            {
            Locatie gekozenLocatie;
            int rol;
            while (dc.moetNogRollen(spelerIndex))
                {
                int totaleWaardeGereedschap = 0;
                Speler speler = dc.getSpelers().get(spelerIndex);
                gekozenLocatie = dc.getVolgendeBezetteLocatie(spelerIndex);
                rol = dc.getRol(spelerIndex, gekozenLocatie);
                System.out.println("Speler " + (spelerIndex + 1) + " heeft " + rol + " gerold op locatie " + gekozenLocatie);
                if (dc.getSpelers().get(spelerIndex).getAantalBruikbaarGereedschap() != 0)
                    {
                    boolean inOrde = false;
                    boolean firstTime = true;
                    while (!inOrde)
                        {
                        ArrayList<Integer> gereedschapIndexes = speler.getIndexesBruikbaarGereedschap();

                        boolean wilGereedschapGebruiken = wilGereedschapGebruiken(totaleWaardeGereedschap, firstTime, gereedschapIndexes.size());
                        if ((wilGereedschapGebruiken) && (gereedschapIndexes.size() != 0))
                            {
                            int index = getGereedschapsIndex(speler, gereedschapIndexes);
                            totaleWaardeGereedschap += speler.getGereedchapsFiche(index).getWaarde();
                            dc.setGereedschapGebruikt(speler, index);
                            }
                        else if ((!wilGereedschapGebruiken) || (gereedschapIndexes.size() == 0))
                            {
                            inOrde = true;
                            }
                        firstTime = false;
                        }
                    if (totaleWaardeGereedschap != 0)
                        {
                        System.out.println("je hebt " + totaleWaardeGereedschap + " gereedschap gebruikt op locatie " + gekozenLocatie + " het eindtotaal van je rollen is hierdoor " + (rol + totaleWaardeGereedschap) + " idpv " + rol);
                        }
                    }
                wilHutKopen();
                dc.geefResources(spelerIndex, rol, gekozenLocatie, totaleWaardeGereedschap);
                dc.clearPionnenVanSpeler(spelerIndex, gekozenLocatie);
                }
            }
    }

    private boolean wilGereedschapGebruiken(int totaalGereedschap, boolean firstTime, int aantalFiches)
    {
        String input = "";
        boolean eersteKeer = true;
        while (!((input.trim().equalsIgnoreCase("y")) || (input.trim().equalsIgnoreCase("n"))))
            {
            if (!eersteKeer)
                {
                System.out.println("Gelieve een correct antwoord te geven!");
                }
            if (totaalGereedschap == 0)
                {
                System.out.println("Wens je hiervoor gereedschap te gebruiken? Antwoord met y of n");
                }
            else
                {
                if (aantalFiches != 0)

                    {
                    System.out.println("Wens je nog gereedschap voor deze rol te gebruiken? Antwoord met y of n");
                    }
                else
                    {
                    System.out.println("Je hebt al je bruikbaar gereedschap gebruikt.");
                    return false;
                    }
                }
            input = scanner.nextLine();
            eersteKeer = false;
            }
        if (input.trim().equalsIgnoreCase("y"))
            {
            return true;
            }
        else
            {
            return false;
            }
    }

    private void wilHutKopen()
    {
        for (int i = 0; i < 4; i++)
            {
            if ((dc.getStapel(i) != null) && (dc.getStapel(i).getHutten().size() != 0))
                {
                if (dc.getStapel(i).getPionnenList().size() != 0)
                    {

                    Speler speler = dc.getSpelerMetKleur(dc.getStapel(i).getPion().getKleur());
                    if (dc.getStapel(i).getAantalPionnenVanSpelerMetKleur(speler.getKleur()) == 1)
                        {
                        Hut hut = dc.getStapel(i).getBovensteHut();
                        if ((hut.getHoutKost() < speler.getAantalHout()) && (hut.getLeemKost() < speler.getAantalLeem()) && (hut.getSteenKost() < speler.getAantalSteen()) && (hut.getGoudKost() < speler.getAantalGoud()))
                            {

                            String input = "";
                            System.out.println(speler.getKleur() + " heeft een pion op deze hut gezet: Hout: " + hut.getHoutKost() + " | Leem: " + hut.getLeemKost() + " | Steen:: " + hut.getSteenKost() + " | Goud: " + hut.getGoudKost());
                            System.out.println("Wens je deze te kopen?");
                            System.out.println("Je hebt momenteel " + " Hout: " + speler.getAantalHout() + " | Leem: " + speler.getAantalLeem() + " | Steen:: " + speler.getAantalSteen() + " | Goud: " + speler.getAantalGoud());

                            while (!((input.trim().equals("y")) || (input.trim().equals("n"))))
                                {
                                System.out.println("Antwoord met 'y' of 'n'.");
                                input = scanner.nextLine();
                                }

                            if (input.equalsIgnoreCase("y"))
                                {
                                dc.verrekenStapel(dc.getStapel(i), speler);
                                System.out.println("Hut gekocht door " + speler.getKleur());

                                }
                            else
                                {
                                dc.clearPionnenVanSpeler(speler, dc.getStapel(i));
                                System.out.println("Hut niet gekocht door " + speler.getKleur());
                                }
                            }
                        else
                            {
                            dc.clearPionnenVanSpeler(speler, dc.getStapel(i));
                            }
                        }
                    }
                }
            }

    }

    private int getGereedschapsIndex(Speler speler, ArrayList<Integer> gereedschapsIndexes)
    {
        boolean inOrde = false;
        String input = "";
        int gekozenIndex = -1;

        while (!inOrde)
            {

            System.out.println("Kies uit het volgende gereedschap, bevestig door de gegeven index in te voeren.");
            for (int j = 0; j < gereedschapsIndexes.size(); j++)
                {
                System.out.println("Gereedschap met index " + gereedschapsIndexes.get(j).intValue() + " en waarde " + speler.getGereedchapsFiche(gereedschapsIndexes.get(j)).getWaarde());

                }
            input = scanner.nextLine();
            try
                {
                gekozenIndex = Integer.parseInt(input);
                }
            catch (NumberFormatException e)
                {
                System.out.println("Gelieve een getal in te geven!");
                continue;
                }
            if ((gekozenIndex > getMax(gereedschapsIndexes)) || (gekozenIndex < getMin(gereedschapsIndexes)))
                {
                System.out.println("Gelieve een correcte index te geven!");
                continue;
                }
            for (int j = 0; j < gereedschapsIndexes.size(); j++)

                {
                if (gereedschapsIndexes.get(j).intValue() == gekozenIndex)
                    {
                    inOrde = true;
                    }
                }
            }
        return gekozenIndex;
    }

    private void kiesLocatieNaam()
    {

        System.out.println("Waar wil je de pionnen plaatsen?");
        System.out.println("Keuze uit : RIVIER, BOS, STEENGROEVE, LEEMGROEVE, AKKER, LOVEHUT, GEREEDSCHAPSMAKER, JACHT, STAPEL1, STAPEL2, STAPEL3, STAPEL4");
        input = scanner.nextLine();
        if (dc.isGeldigeLocatieNaam(input))
            {

            Locatie gekozenLoc = dc.getLocatie(input);
            if (!dc.isLocatieNogNietGebruikt(gekozenLoc))
                {
                System.out.println("Je hebt in een vorige beurt reeds pionnen geplaatst op deze locatie!");
                return;
                }
            if (gekozenLoc.magPionnenSelecteren())
                {
                if (gekozenLoc.heeftPlaatsGenoeg(1))
                    {
                    geldigePositieNaam = true;
                    }
                else
                    {
                    System.out.println("Er is niet genoeg plaats beschikbaar op deze locatie om nog pionnen te plaatsen!");
                    return;
                    }

                }
            else
                {
                if (gekozenLoc.kanLocatieVullen(dc.getHuidigeSpeler()))
                    {
                    if (gekozenLoc.heeftPlaatsGenoeg(gekozenLoc.getMaxPionnen()))
                        {
                        if (gekozenLoc.getNaam().toLowerCase().contains("stapel"))
                            {

                            geldigePositieNaam = true;

                            }
                        else
                            {
                            geldigePositieNaam = true;
                            }
                        }
                    else
                        {
                        System.out.println("Deze locatie is reeds bezet!");
                        }

                    }

                else
                    {
                    System.out.println("Je hebt niet genoeg pionnen om deze locatie te gebruiken!");
                    }
                }
            if (geldigePositieNaam)
                {
                gekozenLocatie = gekozenLoc;
                }
            }
        else
            {
            System.out.println("Gelieve een geldige locatienaam in te geven!");
            }

    }

    private boolean kiesAantalPionnen()
    {
        if (gekozenLocatie.magPionnenSelecteren())
            {
            // --- KEUZE AANTAL PIONNEN --- //
            int aantalPionnen = 0;
            System.out.println("Hoeveel pionnen wil je op deze locatie plaatsen? De geselecteerde locatie is:  " + input + ".  Er staan reeds " + dc.getLocatie(input).getHuidigAantalPionnen() + " pionnen op.");
            try
                {
                aantalPionnen = Integer.parseInt(scanner.nextLine());
                }
            catch (InputMismatchException e)
                {
                geldigAantalPionnen = false;
                System.out.println("Geef aub een getal in");

                return false;
                }
            catch (NumberFormatException e)
                {
                geldigAantalPionnen = false;
                System.out.println("Geef aub een getal in");

                return false;
                }
            if (aantalPionnen > dc.getHuidigeSpeler().getAantalPionnenOver() || aantalPionnen == 0)
                {
                System.out.println("Ongeldig aantal pionnen. Je moet minstens 1 pion plaatsen, en niet meer dan je aantal beschikbare pionnen");
                geldigAantalPionnen = false;
                }
            else
                {
                if (gekozenLocatie.heeftPlaatsGenoeg(aantalPionnen))
                    {
                    geldigAantalPionnen = true;
                    // --- PLAATS DE PIONNEN OP HET VELD --- //

                    kanPionnenPlaatsen = true;
                    dc.plaatsPionnenOpVeld(gekozenLocatie, aantalPionnen);

                    if (input.equalsIgnoreCase("RIVIER") || input.equalsIgnoreCase("BOS") || input.equalsIgnoreCase("STEENGROEVE") || input.equalsIgnoreCase("LEEMGROEVE") || input.equalsIgnoreCase("JACHT"))
                        {
                        pionnenGeplaatst = true;
                        }
                    if (kanPionnenPlaatsen == false)
                        {
                        System.out.println("Pionnen kunnen hier niet geplaatst worden");

                        }
                    else
                        {
                        System.out.println("Er zijn " + aantalPionnen + " stamleden geplaatst op de locatie " + gekozenLocatie.getNaam() + " door Speler met kleur: " + dc.getHuidigeSpeler().getKleur());
                        dc.addToVorigeLocatie(gekozenLocatie);
                        }

                    }
                else
                    {
                    System.out.println("Je hebt teveel pionnen geselecteerd om op deze locatie te plaatsen!");
                    System.out.println("Je kan op deze locatie nog maximaal " + gekozenLocatie.getBruikbarePionnen(dc.getHuidigeSpeler()) + " pionnen plaatsen");
                    }
                }

            }
        else
            {
            if (dc.getLocatie(input).kanLocatieVullen(dc.getHuidigeSpeler()))
                {
                geldigAantalPionnen = true;
                kanPionnenPlaatsen = true;
                dc.plaatsPionnenOpVeld(gekozenLocatie, gekozenLocatie.getMaxPionnen());
                System.out.println("Er zijn " + gekozenLocatie.getMaxPionnen() + " stamleden geplaatst op de locatie " + gekozenLocatie.getNaam() + " door Speler met kleur: " + dc.getHuidigeSpeler().getKleur());
                dc.addToVorigeLocatie(gekozenLocatie);
                return true;
                }
            else
                {
                System.out.println("Je hebt niet genoeg pionnen om deze locatie te vullen!");
                return false;
                }
            }
        return true;
    }

    private void verzoekAantalSpelers()
    {
        int aantalSpelers = 0;
        // --- Definieer het aantal spelers & controleer de waarde, als dit goed is, start het spel met het opgegeven aantal spelers: --- //
        boolean geldigAantalSpelers = false;
        while (!geldigAantalSpelers)
            {

            System.out.println("Hoeveel spelers?");

            try
                {
                aantalSpelers = Integer.parseInt(scanner.nextLine());
                }
            catch (NumberFormatException e)
                {
                geldigAantalSpelers = false;
                System.out.println("Geef aub een getal in");

                continue;
                }
            if (aantalSpelers < 2 || aantalSpelers > 4)
                {
                System.out.println("Het aantal spelers moet tussen 2 en 4 liggen");
                geldigAantalSpelers = false;
                }
            else
                {
                geldigAantalSpelers = true;
                }

            }
        dc.setAantalSpelers(aantalSpelers);

    }

    //Utility Methods:
    private boolean contains(int value, ArrayList<Integer> list)
    {
        boolean contains = false;
        for (int i = 0; i < list.size(); i++)
            {
            if (list.get(i) == value)
                {
                contains = true;
                }
            }
        return contains;
    }

    private int getMax(ArrayList<Integer> list)
    {
        int biggest = -1;
        for (int i = 0; i < list.size(); i++)
            {
            if (list.get(i) > biggest)
                {
                biggest = list.get(i);
                }
            }
        return biggest;
    }

    private int getMin(ArrayList<Integer> list)
    {
        int smallest = 5;
        for (int i = 0; i < list.size(); i++)
            {
            if (list.get(i) < smallest)
                {
                smallest = list.get(i);
                }
            }
        return smallest;
    }

    private void voedseltekortRuilen()
    {
        boolean[] accept = new boolean[dc.getAantalSpelers()];
        for (int i = 0; i < dc.getSpelers().size(); i++)
            {
            boolean first = true;
            Speler speler = dc.getSpelers().get(i);

            while (!dc.voerVoedenUit(accept))
                {

                int allResources = speler.getAantalGoud() + speler.getAantalHout() + speler.getAantalLeem() + speler.getAantalLeem();
                if (accept[i] != true)
                    {
                    if (speler.getVoedseltekort() < allResources)
                        {

                        String inruilen = "";
                        while (!((inruilen.trim().equals("y")) || (inruilen.trim().equals("n"))))
                            {
                            System.out.println("Speler " + speler.getKleur() + " Heeft " + speler.getVoedseltekort() + " voedsel te weinig om alle stamleden te voeden, wil je grondstoffen inruilen voor voedsel? Antwoord met y of n");
                            inruilen = scanner.nextLine();
                            }

                        if (inruilen.trim().toLowerCase().equals("y"))
                            {
                            while (speler.getVoedseltekort() != 0)
                                {
                                if (!first)
                                    {
                                    System.out.println("Speler " + speler.getKleur() + " Heeft " + "nog " + speler.getVoedseltekort() + " voedsel te weinig om alle stamleden te voeden, ");

                                    }
                                first = false;
                                String grondstof = "";
                                while (!((grondstof.trim().equals("goud")) || (grondstof.trim().equals("steen")) || (grondstof.trim().equals("leem")) || (grondstof.trim().equals("hout"))))
                                    {

                                    System.out.println("Speler " + speler.getKleur() + ", Welke grondstoffen wil je inruilen? \n Je hebt deze grondstoffen ter beschikking"
                                            + " Goud: " + speler.getAantalGoud()
                                            + " Steen: " + speler.getAantalSteen()
                                            + " Leem: " + speler.getAantalLeem()
                                            + " Hout: " + speler.getAantalHout());

                                    grondstof = scanner.nextLine();
                                    switch (grondstof)
                                        {
                                        case "goud":
                                            {
                                            int aantalGoudGetal = getInput("goud", speler);
                                            if ((speler.getAantalGoud() < aantalGoudGetal))
                                                {

                                                System.out.println("Speler " + speler.getKleur() + ", Je hebt niet genoeg goud");
                                                continue;
                                                }
                                            if (aantalGoudGetal > speler.getVoedseltekort())
                                                {
                                                System.out.println("Je hoeft maar " + speler.getVoedseltekort() + " goud geven! je probeerde " + aantalGoudGetal + " te ruilen.");
                                                continue;
                                                }

                                            dc.ruilGoud(aantalGoudGetal, speler);
                                            continue;

                                            }
                                        case "steen":
                                            {
                                            int aantalSteenGetal = getInput("steen", speler);
                                            if ((speler.getAantalSteen() < aantalSteenGetal))
                                                {

                                                System.out.println("Speler " + speler.getKleur() + ", Je hebt niet genoeg steen");
                                                continue;
                                                }
                                            if (aantalSteenGetal > speler.getVoedseltekort())
                                                {
                                                System.out.println("Je hoeft maar " + speler.getVoedseltekort() + " steen geven! je probeerde " + aantalSteenGetal + " te ruilen.");
                                                continue;
                                                }

                                            dc.ruilSteen(aantalSteenGetal, speler);
                                            continue;
                                            }

                                        case "leem":
                                            {
                                            int aantalLeemGetal = getInput("leem", speler);
                                            if ((speler.getAantalLeem() < aantalLeemGetal))
                                                {

                                                System.out.println("Speler " + speler.getKleur() + ", Je hebt niet genoeg leem");
                                                continue;
                                                }
                                            if (aantalLeemGetal > speler.getVoedseltekort())
                                                {
                                                System.out.println("Je hoeft maar " + speler.getVoedseltekort() + " leem geven! je probeerde " + aantalLeemGetal + " te ruilen.");
                                                continue;
                                                }

                                            dc.ruilLeem(aantalLeemGetal, speler);
                                            continue;
                                            }

                                        case "hout":
                                            {
                                            int aantalHoutGetal = getInput("hout", speler);
                                            if ((speler.getAantalHout() < aantalHoutGetal))
                                                {

                                                System.out.println("Speler " + speler.getKleur() + ", Je hebt niet genoeg hout");
                                                continue;
                                                }
                                            if (aantalHoutGetal > speler.getVoedseltekort())
                                                {
                                                System.out.println("Je hoeft maar " + speler.getVoedseltekort() + " hout geven! je probeerde " + aantalHoutGetal + " te ruilen.");
                                                continue;
                                                }

                                            dc.ruilHout(aantalHoutGetal, speler);
                                            continue;
                                            }

                                        }

                                    }
                                }

                            }
                        else
                            {
                            accept[i] = true;
                            dc.geefMinpunten(i);

                            if (!DomeinController.GUI)
                                {
                                System.out.println("Speler " + speler.getKleur() + " heeft 10 punten verloren omdat hij/zij niet genoeg voedsel heeft om zijn/haar werkers te voeden!");
                                }
                            else
                                {
                                MainScherm.console.printLine("Speler " + speler.getKleur() + " heeft 10 punten verloren omdat hij/zij niet genoeg voedsel heeft om zijn/haar werkers te voeden!");
                                }
                            continue;
                            }
                        }
                    else
                        {
                        accept[i] = true;
                        dc.geefMinpunten(i);
                        }
                    }
                else
                    {
                    break;
                    }

                }
            }
        dc.voedPionnen();

    }

    public int getInput(String type, Speler speler)
    {
        int index = 0;

        if (!DomeinController.GUI)
            {
            System.out.print("Speler " + speler.getKleur() + ", Hoeveel goud wil je inruilen? \n Je hebt:  ");
            switch (type)
                {
                case "steen":
                    System.out.print(speler.getAantalSteen() + " " + type);
                    break;
                case "hout":
                    System.out.print(speler.getAantalHout() + " " + type);
                    break;
                case "leem":
                    System.out.print(speler.getAantalLeem() + " " + type);
                    break;
                case "goud":
                    System.out.print(speler.getAantalGoud() + " " + type);
                    break;

                }
            System.out.print(" ter beschikking. ");
            }

        int aantal = 0;
        while ((aantal < 0) && aantal > speler.getVoedseltekort())
            {
            }
        String aantalGoud = scanner.nextLine();
        try
            {
            index = Integer.parseInt(aantalGoud);
            }
        catch (NumberFormatException e)
            {
            System.out.println("Gelieve een getal in te geven!");
            }

        return index;
    }

}
