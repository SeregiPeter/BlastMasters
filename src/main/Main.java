package main;

import model.board.Board;
import model.board.Direction;
import model.board.Image;

import java.io.IOException;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) throws IOException {
        // Provide the path to the input file
        String filePath = "src/resources/maps/map1.txt";

        // Create a new Board object
        Board board = new Board(15, filePath);

        // Print the toString representation of the board
        System.out.println(board.toString());

    }
}