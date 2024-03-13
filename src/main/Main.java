package main;

import model.board.Direction;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        for(int i = 0; i < 20; i++) {
            System.out.println(Direction.getDirectionExcept(Direction.UP));
        }

    }
}