package controllers;


import controllers.history.HistoryKeeper;
import controllers.history.HistoryKeeperDynamoDBImp;
import data.Board;

import java.util.Scanner;


import static utils.BoardUtils.printAvailablePositions;
import static utils.BoardUtils.printBoardHint;

public class Engine {
    Board b = new Board();
    Evaluator e = new Evaluator(b);
    HistoryKeeper historyKeeper = new HistoryKeeperDynamoDBImp();

    public void startGame() {
        int turn = 0;
        boolean player1 = true;

        boolean isOver = e.getIsGameOver();
        Scanner reader = new Scanner(System.in);  // Reading from System.in

        String currentPlayer = "";
        String currentSymbol = "";

        printBoardHint(b);
        while (isOver == false) {
            printAvailablePositions(b);
            currentPlayer = player1 ? "1" : "2";
            currentSymbol = player1 ? "X" : "O";

            System.out.println("Player " + currentPlayer + ": Please make a selection");

            System.out.println("Enter a number: ");
            int n = reader.nextInt(); // Scans the next token of the input as an int.
            System.out.println("You have chosen " + n);
            e.setPositionBoard(n, currentSymbol);


            isOver = e.evaluateBoard();
            player1 = !player1;
            turn++;
            historyKeeper.recordGameHistory("Player " + currentPlayer, turn, n, null, false);

            System.out.println("TURN OVER===========================");

        }
        reader.close();
        evaluateWinner();
    }

    private void evaluateWinner() {
        if (e.didPlayer1Win()) {
            System.out.println("**********CONGRADS***********");
            System.out.println("Good Game Player 1");
            System.out.println("*****************************");
            historyKeeper.recordGameHistory("Player 1", -1, -1, null, true);

        } else if (e.didPlayer2Win()) {
            System.out.println("**********CONGRADS***********");
            System.out.println("Good Game Player 2");
            System.out.println("*****************************");
            historyKeeper.recordGameHistory("Player 2", -1, -1, null, true);

        } else {
            System.out.println("Good Game TIED");
            historyKeeper.recordGameHistory("TIED", -1, -1, null, true);
        }

    }
}
