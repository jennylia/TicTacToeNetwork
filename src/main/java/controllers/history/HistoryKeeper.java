package controllers.history;

public interface HistoryKeeper {
    void recordGameHistory(String playerName, int turn, int position, String[] friendsArg, boolean won);
}
