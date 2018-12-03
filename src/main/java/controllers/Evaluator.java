package controllers;


import data.Board;

public class Evaluator {
    Board boardModel;

    private boolean isGameOver;
    private boolean player1Win; // Could be tied
    private boolean player2Win;

    public Evaluator(Board boardModel) {
        this.boardModel = boardModel;
    }



    public boolean evaluateBoard() {
        if (isGameOver) {
            return isGameOver;
        }

        String[] board = boardModel.getBoard();
        boolean boardFull = isFull(board);
        if (boardFull) {
            isGameOver = true;
            return isGameOver;
        }

        boolean currentPlayer1Status = didPlayerWin("X");
        boolean currentPlayer2Status = didPlayerWin("O");

        if (currentPlayer1Status) {
            player1Win = true;
            isGameOver = true;
            return isGameOver;
        } else if (currentPlayer2Status) {
            player2Win = true;
            isGameOver = true;
            return isGameOver;
        }

        return isGameOver;

    }

    private boolean didPlayerWin(String x) {
        // scan row.
        String[] board = boardModel.getBoard();
        int dim = boardModel.getDim();
        for (int i = 0; i < dim; i++) {
            if (board[i].equals(x) && board[i + 1].equals(x) && board[i + 2].equals(x)) {
                return true;
            }
        }
        // scan col.
        for (int j = 0; j < dim; j++) {
            if (board[j].equals(x) && board[j + dim * 1].equals(x) && board[j + dim * 2].equals(x)) {
                return true;
            }
        }
        // scan a left diagonal (generalize, 0 + (dim + 1))
        if (board[0].equals(x) && board[4].equals(x) && board[8].equals(x)) {
            return true;
        }
        // scan a left diagonal (generalize, corner + (dim -  1))

        if (board[2].equals(x) && board[4].equals(x) && board[6].equals(x)) {
            return true;
        }
        return false;
    }

    private boolean isFull(String[] board) {
        for (String s : board) {
            if (s.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public void setPositionBoard(int position, String val) {
        boardModel.setPositionValue(position, val);
    }

    public boolean getIsGameOver() {
        return isGameOver;
    }

    public boolean didPlayer1Win() {
        return player1Win;
    }

    public boolean didPlayer2Win() {
        return player2Win;
    }
}
