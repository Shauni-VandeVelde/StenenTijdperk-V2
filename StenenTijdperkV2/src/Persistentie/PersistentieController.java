/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistentie;

import Domein.DomeinController;
import Domein.GereedschapsFiche;
import Domein.Hut;
import Domein.Kleur;
import Domein.Pion;
import Domein.Speler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import javafx.util.Pair;

/**
 *
 * @author kenzo
 */
public class PersistentieController {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/stenentijdperkdb?user=stenentijdperk&password=root&serverTimezone=UTC";

    public PersistentieController() {

    }

    public void saveNew(String naam, DomeinController dc) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection con = DriverManager.getConnection(JDBC_URL)) {

                // DC
                PreparedStatement qrySaveDC = con.prepareStatement("INSERT INTO domeincontroller(domeinControllerNaam, huidigeSpelerIndex, startSpelerIndex, rondeNummer) VALUES(?, ?, ?, ?)");
                qrySaveDC.setString(1, naam);
                qrySaveDC.setInt(2, dc.getHuidigeSpelerIndex());
                qrySaveDC.setInt(3, dc.getStartSpelerindex());
                qrySaveDC.setInt(4, dc.getRondeNummer());

                // Spelers
                PreparedStatement qrySaveSpelers[] = new PreparedStatement[dc.getSpelers().size()];

                for (int i = 0; i < dc.getSpelers().size(); ++i) {
                    qrySaveSpelers[i] = con.prepareStatement("INSERT INTO speler(spelerIndex, dcNaam, kleur, voedselPerBeurt, punten, aantalGoud, aantalHout, aantalLeem, aantalSteen, aantalVoedsel, previousHout, previousLeem, previousSteen, previousGoud, previousVoedsel, previousPunten, previousVoedselPerBeurt, previousPionnenSize, previousWaardeGereedschap, previousHuttenSize)"
                            + " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

                    qrySaveSpelers[i].setInt(1, i);
                    qrySaveSpelers[i].setString(2, naam);
                    qrySaveSpelers[i].setString(3, dc.getSpelers().get(i).getKleur().toString());
                    qrySaveSpelers[i].setInt(4, dc.getSpelers().get(i).getVoedselPerBeurt());
                    qrySaveSpelers[i].setInt(5, dc.getSpelers().get(i).getPunten());
                    qrySaveSpelers[i].setInt(6, dc.getSpelers().get(i).getAantalGoud());
                    qrySaveSpelers[i].setInt(7, dc.getSpelers().get(i).getAantalHout());
                    qrySaveSpelers[i].setInt(8, dc.getSpelers().get(i).getAantalLeem());
                    qrySaveSpelers[i].setInt(9, dc.getSpelers().get(i).getAantalSteen());
                    qrySaveSpelers[i].setInt(10, dc.getSpelers().get(i).getAantalVoedsel());
                    qrySaveSpelers[i].setInt(11, dc.getSpelers().get(i).getPreviousHout());
                    qrySaveSpelers[i].setInt(12, dc.getSpelers().get(i).getPreviousLeem());
                    qrySaveSpelers[i].setInt(13, dc.getSpelers().get(i).getPreviousSteen());
                    qrySaveSpelers[i].setInt(14, dc.getSpelers().get(i).getPreviousGoud());
                    qrySaveSpelers[i].setInt(15, dc.getSpelers().get(i).getPreviousVoedsel());
                    qrySaveSpelers[i].setInt(16, dc.getSpelers().get(i).getPreviousPunten());
                    qrySaveSpelers[i].setInt(17, dc.getSpelers().get(i).getPreviousVoedselPerBeurt());
                    qrySaveSpelers[i].setInt(18, dc.getSpelers().get(i).getPreviousPionnenSize());
                    qrySaveSpelers[i].setInt(19, dc.getSpelers().get(i).getPreviousWaardeGereedschap());
                    qrySaveSpelers[i].setInt(20, dc.getSpelers().get(i).getPreviousHuttenSize());
                }

                // Gereedschapsfiches
                ArrayList<PreparedStatement> qrySaveGereedschapsFiches = new ArrayList<PreparedStatement>();

                for (int i = 0; i < dc.getSpelers().size(); ++i) {
                    for (GereedschapsFiche gf : dc.getSpelers().get(i).getGereedschapsFiches()) {
                        PreparedStatement temp = con.prepareStatement("INSERT INTO gereedschapsfiche(spelerIndex, dcNaam, waarde, isGebruikt) VALUES(?, ?, ?, ?)");

                        temp.setInt(1, i);
                        temp.setString(2, naam);
                        temp.setInt(3, gf.getWaarde());
                        temp.setBoolean(4, gf.isGebruikt());

                        qrySaveGereedschapsFiches.add(temp);
                    }
                }

                // Pionnen
                ArrayList<PreparedStatement> qrySavePionnen = new ArrayList<PreparedStatement>();

                for (int i = 0; i < dc.getSpelers().size(); ++i) {
                    for (Pion p : dc.getSpelers().get(i).getPionnen()) {
                        PreparedStatement temp = con.prepareStatement("INSERT INTO pion(spelerID, dcNaam) VALUES(?, ?)");
                        temp.setInt(1, i);
                        temp.setString(2, naam);

                        qrySavePionnen.add(temp);
                    }
                }

                // Stapels
                PreparedStatement qrySaveStapels[] = new PreparedStatement[dc.getSpelbord().getStapels().size()];

                for (int i = 0; i < dc.getSpelbord().getStapels().size(); ++i) {
                    qrySaveStapels[i] = con.prepareStatement("INSERT INTO stapel(stapelIndex, dcNaam) VALUES(?, ?)");

                    qrySaveStapels[i].setInt(1, i);
                    qrySaveStapels[i].setString(2, naam);
                }

                // Hut
                ArrayList<PreparedStatement> qrySaveHutten = new ArrayList<PreparedStatement>();

                for (int i = 0; i < dc.getSpelers().size(); ++i) {
                    for (Hut h : dc.getSpelers().get(i).getHutten()) {
                        PreparedStatement temp = con.prepareStatement("INSERT INTO hut(spelerIndex, dcNaam, houtKost, steenKost, leemKost, goudKost) VALUES(?, ?, ?, ?, ?, ?)");

                        temp.setInt(1, i);
                        temp.setString(2, naam);
                        temp.setInt(3, h.getHoutKost());
                        temp.setInt(4, h.getSteenKost());
                        temp.setInt(5, h.getLeemKost());
                        temp.setInt(6, h.getGoudKost());
                        qrySaveHutten.add(temp);
                    }
                }
                for (int i = 0; i < dc.getSpelbord().getStapels().size(); ++i) {
                    for (Hut h : dc.getSpelbord().getStapel(i).getHutten()) {
                        PreparedStatement temp = con.prepareStatement("INSERT INTO hut(stapelIndex, dcNaam, houtKost, steenKost, leemKost, goudKost) VALUES(?, ?, ?, ?, ?, ?)");

                        temp.setInt(1, i);
                        temp.setString(2, naam);
                        temp.setInt(3, h.getHoutKost());
                        temp.setInt(4, h.getSteenKost());
                        temp.setInt(5, h.getLeemKost());
                        temp.setInt(6, h.getGoudKost());
                        qrySaveHutten.add(temp);
                    }
                }

                qrySaveDC.executeUpdate();
                for (PreparedStatement p : qrySaveSpelers) {
                    p.executeUpdate();
                }
                for (PreparedStatement p : qrySaveGereedschapsFiches) {
                    p.executeUpdate();
                }
                for (PreparedStatement p : qrySavePionnen) {
                    p.executeUpdate();
                }
                for (PreparedStatement p : qrySaveStapels) {
                    p.executeUpdate();
                }
                for (PreparedStatement p : qrySaveHutten) {
                    p.executeUpdate();
                }

                System.out.println("SAVE [" + naam + "] SUCCES!");
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Verwijdert de oude save en roept dan 'saveNew' op om de huidige op te
     * slaan onder dezelfde naam
     *
     * @param naam
     * @param dc
     */
    public void saveOverride(String naam, DomeinController dc) {
        String saveUpdateModeOff = "SET SQL_SAFE_UPDATES = 0;";

        boolean success = false;

        try (Connection con = DriverManager.getConnection(JDBC_URL + "&useSSL=false")) {
            PreparedStatement qryDeleteHutten = con.prepareStatement("DELETE FROM hut WHERE dcNaam = ?;");
            PreparedStatement qryDeletePionnen = con.prepareStatement("DELETE FROM pion WHERE dcNaam = ?");
            PreparedStatement qryDeleteGereedschapsFiches = con.prepareStatement("DELETE FROM gereedschapsfiche WHERE dcNaam = ?");
            PreparedStatement qryDeleteSpelers = con.prepareStatement("DELETE FROM speler WHERE dcNaam = ?");
            PreparedStatement qryDeleteStapels = con.prepareStatement("DELETE FROM stapel WHERE dcNaam = ?");
            PreparedStatement qryDeleteDomeinController = con.prepareStatement("DELETE FROM domeincontroller WHERE domeinControllerNaam = ?");

            qryDeleteHutten.setString(1, naam);
            qryDeletePionnen.setString(1, naam);
            qryDeleteGereedschapsFiches.setString(1, naam);
            qryDeleteSpelers.setString(1, naam);
            qryDeleteStapels.setString(1, naam);
            qryDeleteDomeinController.setString(1, naam);

            qryDeleteSpelers.executeUpdate();
            qryDeletePionnen.executeUpdate();
            qryDeleteHutten.executeUpdate();
            qryDeleteGereedschapsFiches.executeUpdate();
            qryDeleteStapels.executeUpdate();
            qryDeleteDomeinController.executeUpdate();

            success = true;
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        if (success) {
            saveNew(naam, dc);
        }
    }

    /**
     * Returnt all domeincontroller in een pair van <String, int> volgens
     * <naam, rondeNummer>
     *
     * @return
     */
    public ArrayList<Pair> getSaveNamesWithRoundNr() {
        ArrayList<Pair> saves = new ArrayList<Pair>();
        //try{
            //Class.forName("com.mysql.jdbc.Driver");
            
            try (Connection con = DriverManager.getConnection(JDBC_URL + "&useSSL=false")) {
                PreparedStatement qryGetSave = con.prepareStatement("SELECT domeinControllerNaam, rondeNummer FROM domeincontroller");
                ResultSet rs = qryGetSave.executeQuery();

                while (rs.next()) {
                    Pair temp = new Pair(rs.getString("domeinControllerNaam"), rs.getInt("rondeNummer"));
                    saves.add(temp);
                }
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        //} catch (ClassNotFoundException ex) {
        //    System.out.println(ex);
       // }

        return saves;
    }

    /**
     * Returnt een nieuwe domeincontroller met de stats van de gewenste save
     *
     * @param naam
     * @return
     */
    public DomeinController loadGame(String naam) {
        DomeinController newDC = new DomeinController(true);

        try (Connection con = DriverManager.getConnection(JDBC_URL + "&useSSL=false")) {
            PreparedStatement qryGetDomeinController = con.prepareStatement("SELECT * FROM domeincontroller WHERE domeinControllerNaam = ?");
            qryGetDomeinController.setString(1, naam);

            ResultSet rs = qryGetDomeinController.executeQuery();

            rs.next();
            // Add dc
            newDC.setStartSpelerIndex(rs.getInt("startSpelerIndex"));
            newDC.setHuidigeSpelerIndex();
            newDC.setRondeNummer(rs.getInt("rondeNummer"));

            PreparedStatement qryGetSpelers = con.prepareStatement("SELECT * FROM speler WHERE dcNaam = ?");
            qryGetSpelers.setString(1, naam);

            rs = qryGetSpelers.executeQuery();

            //Add spelers
            while (rs.next()) {
                Kleur kleur = Kleur.valueOf(rs.getString("kleur"));
                int index = rs.getInt("spelerIndex");
                Speler temp = new Speler(newDC, kleur, index);
                temp.setVoedselPerBeurt(rs.getInt("voedselPerBeurt"));
                temp.setPunten(rs.getInt("punten"));
                temp.setAantalGoud(rs.getInt("aantalGoud"));
                temp.setAantalHout(rs.getInt("aantalHout"));
                temp.setAantalLeem(rs.getInt("aantalLeem"));
                temp.setAantalSteen(rs.getInt("aantalSteen"));
                temp.setAantalVoedsel(rs.getInt("aantalvoedsel"));
                temp.setPreviousGoud(rs.getInt("previousGoud"));
                temp.setPreviousHout(rs.getInt("previousHout"));
                temp.setPreviousLeem(rs.getInt("previousLeem"));
                temp.setPreviousSteen(rs.getInt("previousSteen"));
                temp.setPreviousVoedsel(rs.getInt("previousVoedsel"));
                temp.setPreviousPunten(rs.getInt("previousPunten"));
                temp.setPreviousVoedselPerBeurt(rs.getInt("previousVoedselPerBeurt"));
                temp.setPreviousPionnenSize(rs.getInt("previousPionnenSize"));
                temp.setPreviousWaardeGereedschap(rs.getInt("previousWaardeGereedschap"));
                temp.setPreviousHuttenSize(rs.getInt("previousHuttenSize"));

                newDC.addSpeler(temp);
            }

            //Stapels clearen
            newDC.getSpelbord().clearStapelsInhoud();

            //Stapels vullen met hutten
            for (int i = 0; i < newDC.getSpelbord().getStapels().size(); ++i) {
                PreparedStatement qryGetHuttenVoorStapels = con.prepareStatement("SELECT * FROM hut WHERE dcNaam = ? AND stapelIndex = ? AND spelerIndex IS NULL");

                qryGetHuttenVoorStapels.setString(1, naam);
                qryGetHuttenVoorStapels.setInt(2, i);

                rs = qryGetHuttenVoorStapels.executeQuery();

                ArrayList<Hut> nieuweHutten = new ArrayList<Hut>();
                while (rs.next()) {
                    int houtKost, steenKost, leemKost, goudKost;
                    houtKost = rs.getInt("houtKost");
                    steenKost = rs.getInt("steenKost");
                    leemKost = rs.getInt("leemKost");
                    goudKost = rs.getInt("goudKost");

                    nieuweHutten.add(new Hut(houtKost, steenKost, goudKost, leemKost));
                }

                newDC.getSpelbord().getStapels().get(i).refill(nieuweHutten);
            }

            //Speler hutten
            for (int i = 0; i < newDC.getSpelers().size(); ++i) {
                PreparedStatement qryGetHutten = con.prepareStatement("SELECT * FROM hut WHERE dcNaam = ? AND spelerIndex = ?");

                qryGetHutten.setString(1, naam);
                qryGetHutten.setInt(2, i);

                rs = qryGetHutten.executeQuery();

                ArrayList<Hut> nieuweHutten = new ArrayList<Hut>();
                while (rs.next()) {
                    int houtKost, steenKost, leemKost, goudKost;
                    houtKost = rs.getInt("houtKost");
                    steenKost = rs.getInt("steenKost");
                    leemKost = rs.getInt("leemKost");
                    goudKost = rs.getInt("goudKost");

                    nieuweHutten.add(new Hut(houtKost, steenKost, goudKost, leemKost));
                }

                newDC.getSpelers().get(i).setHuttenRefresh(nieuweHutten);
            }

            // Pionnen
            for (int i = 0; i < newDC.getSpelers().size(); ++i) {
                PreparedStatement qryGetPionnen = con.prepareStatement("SELECT COUNT(*) AS aantalPionnen FROM pion WHERE dcNaam = ? AND spelerID = ?");

                qryGetPionnen.setString(1, naam);
                qryGetPionnen.setInt(2, i);

                rs = qryGetPionnen.executeQuery();
                rs.next();

                int aantalPionnen = rs.getInt("aantalPionnen");

                ArrayList<Pion> nieuwePionnen = new ArrayList<Pion>();
                for (int y = 0; y < aantalPionnen; ++y) {
                    nieuwePionnen.add(new Pion(newDC.getSpelers().get(i).getKleur(), false));
                }

                newDC.getSpelers().get(i).setPionnenRefresh(nieuwePionnen);
            }

            // Gereedschapsfiche
            for (int i = 0; i < newDC.getSpelers().size(); ++i) {
                PreparedStatement qryGetGereedschapsFiches = con.prepareStatement("SELECT * FROM gereedschapsfiche WHERE dcNaam = ? AND spelerIndex = ?");
                qryGetGereedschapsFiches.setString(1, naam);
                qryGetGereedschapsFiches.setInt(2, i);
                rs = qryGetGereedschapsFiches.executeQuery();

                ArrayList<GereedschapsFiche> nieuweGereedschapsFiches = new ArrayList<GereedschapsFiche>();
                while (rs.next()) {
                    int waarde = rs.getInt("waarde");

                    GereedschapsFiche temp = new GereedschapsFiche(waarde, false);

                    nieuweGereedschapsFiches.add(temp);
                }

                newDC.getSpelers().get(i).setGereedschapsFiches(nieuweGereedschapsFiches);
            }
        } catch (SQLException ex) {
            System.out.println(ex);
            return null;
        }

        return newDC;
    }

}
