package data;

public class Board {

    private static final int DEFAULT_DIM = 3;
    private final String[] board;
    private final int dim = DEFAULT_DIM;

    public Board() {
        // Init function
        int len = dim * dim;
        this.board = new String[len]; // TODO, could make this a 2 d matrix

        for (int i = 0; i < len; i++) {
            board[i] = "";
        }
    }

    public int getDim() {
        return dim;
    }

    public String[] getBoard() {
        return board;
    }

    public void setPositionValue(int position, String val) {
        if (position < 0 || position >= dim * dim) {
            System.out.printf("INVALID position, forfeit turn");
            return;
        }

        if (board[position].isEmpty() == false) {
            System.out.printf("PRE OCCUPIED position, forfeit turn");
            return;
        }

        board[position] = val;
    }

}


