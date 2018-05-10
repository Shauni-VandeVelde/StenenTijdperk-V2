/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sound;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Random;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import ui.frame.mainScherm.MainScherm;


/**
 *
 * @author NotAvailable
 */
public class Player
{
    private MediaPlayer mediaPlayer;
    private double volume = 0.6;
    private String path;
    private MainScherm mainScherm;
    private boolean music = false;
    private boolean soundEffect = false;
    private boolean isMusicLoop = false;

    public Player(MainScherm mainscherm, String type, double volume)
    {
        this.mainScherm = mainscherm;

        setPath(type);
        System.out.println(path);
        if (!path.equals(""))
            {
            try
                {
                Media media = new Media(getFile(path).toURI().toString());
                // System.out.println("Media " + media.getSource());
                mediaPlayer = new MediaPlayer(media);

                //this.volume = volume / 100;
                if (volume != -1)
                    {
                    this.volume = volume;
                    }
                setVolume();

                mediaPlayer.play();

                mediaPlayer.setOnEndOfMedia(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        finish();
                    }

                });

                }
            catch (Exception e)
                {
                e.printStackTrace();
                }
            }
        else
            {
            System.err.println("Player -> Invalid type/path.");
            }

    }

    private void finish()
    {

        mainScherm.removeSFX(this);

    }

    private void setPath(String type)
    {
        Random random = new Random();
        if (type.toLowerCase().trim().contains("stapel"))
            {
            type = "stapel";
            System.out.println("Stapel");
            }
        switch (type.toLowerCase().trim())
            {

            ////// Music files:
            case "musicloop":
                music = true;
                isMusicLoop = true;
                path = getRandomSong();
                break;
            case "win":
                music = true;
                path = "/Music/win.mp3";
                break;
            case "egypt1":
                music = true;
                path = "/Music/Egypt1.mp3";
                break;
            case "egypt2":
                music = true;
                path = "/Music/Egypt2.mp3";
                break;
            case "egypt3":
                music = true;
                path = "/Music/Egypt3.mp3";
                break;
            case "egypt4":
                music = true;
                path = "/Music/Egypt4.mp3";
                break;
            case "egypt5":
                music = true;
                path = "/Music/Egypt5.mp3";
                break;
            case "egypt6":
                music = true;
                path = "/Music/Egypt6.mp3";
                break;
            case "egypt7":
                music = true;
                path = "/Music/Egypt7.mp3";
                break;
            case "egypt8":
                music = true;
                path = "/Music/Egypt8.mp3";
                break;
            case "egypt9":
                music = true;
                path = "/Music/Egypt9.mp3";
                break;
            case "mainmenu":
                music = true;
                path = "/Music/MainMenu.mp3";
                break;

            ////// SFX files:
            case "dice1":
                soundEffect = true;
                path = "/SFX/Dice1.mp3";
                volume = 0.5;
                break;
            case "dice2":
                soundEffect = true;
                path = "/SFX/Dice2.mp3";
                volume = 0.5;
                break;

            case "bos":
                soundEffect = true;
                path = "/SFX/bos2.mp3";
                volume = 0.45;
                break;
            case "leemgroeve":
                soundEffect = true;
                path = "/SFX/Leemgroeve1.mp3";
                volume = 1;
                break;
            case "steengroeve":
                soundEffect = true;
                path = "/SFX/Steengroeve1.mp3";
                volume = 0.6;
                break;
            case "rivier":
                soundEffect = true;
                volume = 0.5;
                if (random.nextBoolean())
                    {
                    path = "/SFX/Rivier1.mp3";

                    }
                else
                    {
                    path = "/SFX/Rivier2.mp3";
                    }

                break;
            case "jacht":
                soundEffect = true;
                volume = 0.55;
                if (random.nextBoolean())
                    {
                    path = "/SFX/Jacht1.mp3";
                    }
                else
                    {
                    path = "/SFX/Jacht2.mp3";
                    }
                break;
            case "akker":
                soundEffect = true;
                volume = 0.45;
                path = "/SFX/Akker1.mp3";
                break;
            case "gereedschapsmaker":
                soundEffect = true;
                volume = 0.5;
                path = "/SFX/Gereedschapsmaker1.mp3";
                break;
            case "lovehut":
                soundEffect = true;
                volume = 0.9;
                path = "/SFX/LoveHut.mp3";
                break;
            case "stapel":
                soundEffect = true;
                volume = 0.35;
                path = "/SFX/Stapel.mp3";
                break;
            case "negative":
                soundEffect = true;
                volume = 0.85;
                path = "/SFX/MagNiet.mp3";
                break;
            case "gong":
                soundEffect = true;
                volume = 0.85;
                path = "/SFX/gong.mp3";
                break;
            case "menu":
                soundEffect = true;

                path = "/SFX/Menu.mp3";

                break;
            }

    }

    public void setVolume()
    {

        double j = 0;
        if (music)
            {
            j = mainScherm.getMasterVolume() * (mainScherm.getMusicVolume() * volume);
            }
        if (soundEffect)
            {
            j = mainScherm.getMasterVolume() * (mainScherm.getSFXVolume() * volume);
            }

        mediaPlayer.setVolume(j);

    }

    private String getRandomSong()
    {

        Random random = new Random();

        int j = random.nextInt(8) + 1;
        String type = "";
        switch (j)
            {

            case 1:
                setPath("egypt1");
                break;
            case 2:
                setPath("egypt2");
                break;
            case 3:
                setPath("egypt3");
                break;
            case 4:
                setPath("egypt4");
                break;
            case 5:
                setPath("egypt5");
                break;
            case 6:
                setPath("egypt6");
                break;
            case 7:
                setPath("egypt7");
                break;
            case 8:
                setPath("egypt8");
                break;
            case 0:
                setPath("egypt9");
                break;
            }
        if (!mainScherm.isSpelGestart())
            {
            setPath("mainMenu");
            }
        return path;
    }

    public boolean isMusic()
    {
        return music;
    }

    public boolean isSoundEffect()
    {
        return soundEffect;
    }

    public boolean isMusicLoop()
    {
        return isMusicLoop;
    }

    public boolean pathContains(String string)
    {
        string = string.toLowerCase().trim();

        if (path.toLowerCase().contains(string))
            {
            return true;
            }
        return false;

    }

    public MediaPlayer getPlayer()

    {
        return mediaPlayer;
    }

    File getFile(String path)
    {
        File file = null;
        String resource = path;
        URL res = getClass().getResource(resource);
        if (res.toString().startsWith("jar:"))
            {
            try
                {
                InputStream input = getClass().getResourceAsStream(resource);
                file = File.createTempFile("tempfile", ".tmp");
                OutputStream out = new FileOutputStream(file);
                int read;
                byte[] bytes = new byte[1024];

                while ((read = input.read(bytes)) != -1)
                    {
                    out.write(bytes, 0, read);
                    }
                file.deleteOnExit();
                }
            catch (IOException ex)
                {
                ex.printStackTrace();
                }
            }
        else
            {
            file = new File(res.getFile());
            }

        if (file != null && !file.exists())
            {
            throw new RuntimeException("Error: File " + file + " not found!");
            }
        return file;
    }

}
