package main;

import view.ui.MainMenu;

import javax.swing.*;
import java.io.IOException;

public class BlastMastersMain {
    public static void main(String[] args) throws IOException {
        MainMenu mainMenu = new MainMenu();
        mainMenu.setContentPane(mainMenu.panelMain);
        mainMenu.setTitle("Blast Masters");
        mainMenu.setSize(1520, 747);
        mainMenu.setVisible(true);
        mainMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}