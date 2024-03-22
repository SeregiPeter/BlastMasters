package main;

import model.board.Board;
import view.GameWindow;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Provide the path to the input file
        String filePath = "src/resources/maps/map1.txt";

        // Create a new Board object
        Board board = new Board(15, filePath);


        //test base game
        JFrame frame;
        frame = new JFrame("BlastMasters");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        GameWindow gameArea = new GameWindow(board);
        frame.getContentPane().add(gameArea);
        frame.setPreferredSize(new Dimension(1520, 747));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);



    }
}