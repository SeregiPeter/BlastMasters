package control;

import view.ui.PlayerCustomizationPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

/**
 * Settings class for game configurations.
 * The Settings class allows for configuring various aspects of the game such as player controls, game difficulty,
 * sound settings, and more.
 */
public class Settings {

    private PlayerCustomizationPanel p1;
    private  PlayerCustomizationPanel p2;
    private final String path = "src/control/control.txt";

    public Settings() {
        this.p1= new PlayerCustomizationPanel(1,this);
        this.p2=new PlayerCustomizationPanel(2,this);
        try {
            ImageIcon bombermanIcon2 = new ImageIcon(ImageIO.read(new File("src/resources/assets/menu/bomberman1.png")));
            Image image = bombermanIcon2.getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(image);
            p2.setPlayerImage(scaledIcon);
        } catch (IOException e) {
            e.printStackTrace();
        }
        load();
    }
    public ArrayList<String> getSettings(){
        try{
            BufferedReader br = new BufferedReader(new FileReader(path));
            String[] line1=br.readLine().split(" ");
            String[] line2=br.readLine().split(" ");
            ArrayList<String> fileSettings=new ArrayList<>();
            for (int i=1;i<7;i++){
                fileSettings.add(line1[i]);
            }
            for (int i=1;i<7;i++){
                //System.out.printf(line2[i]);
                fileSettings.add(line2[i]);
            }
            return fileSettings;
        } catch (IOException e){
            throw new RuntimeException(e);
        }

    }
    private void load(){
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            String line=br.readLine();
            String[] words=line.split(" ");
            initialize(p1,words);

            line=br.readLine();
            words=line.split(" ");
            initialize(p2,words);
            br.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void initialize(PlayerCustomizationPanel p,String[] words){
        p.setPlayerName(words[0]);
        p.setControls(words);
    }
    public void write(int row,String[] settings) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line1= br.readLine();
            String line2= br.readLine();
            br.close();
            if(row==1){
                line1=settings[0]+
                        " "+
                        settings[1]+
                        " "+
                        settings[2]+
                        " "+
                        settings[3]+
                        " "+
                        settings[4]+
                        " "+
                        settings[5]+
                        " "+
                        settings[6];
            }else{
                line2=settings[0]+
                        " "+
                        settings[1]+
                        " "+
                        settings[2]+
                        " "+
                        settings[3]+
                        " "+
                        settings[4]+
                        " "+
                        settings[5]+
                        " "+
                        settings[6];
            }
            FileWriter writer=new FileWriter(path);
            writer.write(line1+'\n'+line2);
            writer.close();
        }catch (IOException e){
            throw new RuntimeException(e);
        }

    }
    public boolean compare(){
        String[] p1Settings= p1.getControls();
        String[] p2Settings= p2.getControls();

        for (String p1Word:p1Settings) {    // 90%-ra jó lesz...
            int match=0;
            for(String p2Word:p2Settings){
                if(p1Word.equalsIgnoreCase(p2Word)) return true;
            }
            for (String p1Word2:p1Settings){
                if(p1Word.equalsIgnoreCase(p1Word2)){
                    match++;
                }
            }
            if (match>1) return true;
        }
        for (String p2Word:p2Settings){
            int match=0;
            for (String p2Word2:p2Settings){
                if(p2Word.equalsIgnoreCase(p2Word2)){
                    match++;
                }
            }
            if (match>1) return true;
        }
        return false;

    }

    public PlayerCustomizationPanel getP1() {
        return p1;
    }
    public PlayerCustomizationPanel getP2(){
        return p2;
    }
}
