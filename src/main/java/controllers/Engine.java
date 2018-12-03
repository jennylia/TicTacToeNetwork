package controllers;


import data.Board;

import java.util.Scanner;


import static utils.BoardUtils.printAvailablePositions;
import static utils.BoardUtils.printBoardHint;

public class Engine {
    Board b = new Board();
    Evaluator e = new Evaluator(b);

    public void startGame() {
        boolean player1 = true;

        boolean isOver = e.getIsGameOver();
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        printBoardHint(b);
        while (isOver == false) {
            printAvailablePositions(b);
            String currentPlayer = player1 ? "1" : "2";
            String currentSymbol = player1 ? "X" : "O";

            System.out.println("Player " + currentPlayer + ": Please make a selection");

            System.out.println("Enter a number: ");
            int n = reader.nextInt(); // Scans the next token of the input as an int.
            System.out.println("You have chosen " + n);
            e.setPositionBoard(n, currentSymbol);


            isOver = e.evaluateBoard();
            player1 = !player1;
            System.out.println("TURN OVER===========================");

        }
        reader.close();
        evaluateWinner();
    }

    private void evaluateWinner() {
        if (e.didPlayer1Win()){
            System.out.println("**********CONGRADS***********");
            System.out.println("Good Game Player 1");
            System.out.println("*****************************");

        }else if (e.didPlayer2Win()){
            System.out.println("**********CONGRADS***********");
            System.out.println("Good Game Player 2");
            System.out.println("*****************************");

        }else{
            System.out.println("Good Game TIED");
        }
    }
}
