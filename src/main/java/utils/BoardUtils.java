package utils;

import data.Board;

public class BoardUtils {


    public static void printBoardHint(Board b) {
        System.out.println("Positions on the board are as such");
        int row = 0;
        int dim = b.getDim();
        while (row < dim) {
            for (int i = 0; i < dim; i++) {
                int pos = row * dim + i;
                System.out.print(" " + pos + " |");
            }
            row++;
            System.out.println();
        }
    }

    public static void printAvailablePositions(Board b) {
        System.out.println("print all the valid positions on the board");
        int row = 0;
        int dim = b.getDim();
        while (row < dim) {
            for (int i = 0; i < dim; i++) {
                int pos = row * dim + i;
                String posVal = b.getBoard()[pos];
                if (posVal.isEmpty()) {
                    System.out.print(" [" + pos + "] |");
                } else {
                    System.out.print("  " + posVal + "  |");
                }
            }
            row++;
            System.out.println();
        }
    }
}

