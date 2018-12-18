package server.stats.server.data;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;

import java.util.ArrayList;
import java.util.List;

@DynamoDBTable(tableName = "GameSession")
public class GameSession {

    private String gameId;
    private boolean isGameOver;
    private String nextPlayer;
    private String winner;
    private List<String> positions = new ArrayList<>();

    public GameSession(String gameId) {
        this.gameId = gameId;
    }

    public GameSession(String gameId, boolean isGameOver, String nextPlayer, String winner, List<String> positions) {
        this.gameId = gameId;
        this.isGameOver = isGameOver;
        this.nextPlayer = nextPlayer;
        this.winner = winner;
        this.positions = positions;
    }

    @DynamoDBHashKey(attributeName = "GameId")
    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }

    @DynamoDBAttribute(attributeName = "GameOver")
    public boolean getGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    @DynamoDBAttribute(attributeName = "NextPlayer")
    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    @DynamoDBAttribute(attributeName = "Winner")
    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    @DynamoDBAttribute(attributeName = "Board")
    public List<String> getPositions() {
        return positions;
    }

    public void setPositions(List<String> positions) {
        this.positions = positions;
    }

    @Override
    public String toString() {
        return "GameSession{" +
                "gameId='" + gameId + '\'' +
                ", isGameOver=" + isGameOver +
                ", nextPlayer='" + nextPlayer + '\'' +
                ", winner='" + winner + '\'' +
                ", positions=" + positions +
                '}';
    }
}
