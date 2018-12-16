package server.stats.server;

import com.sun.net.httpserver.HttpServer;
import server.stats.server.handlers.ChatRoomCreationHandler;
import server.stats.server.handlers.GameHistoryHandler;
import server.stats.server.handlers.GameIdCreater;

import java.io.IOException;
import java.net.InetSocketAddress;

public class GameServer {

    private static final int port = 9000;
    public static void main(String[] args) throws IOException {
        System.out.println("getting stats");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/getHistory", new GameHistoryHandler());
        server.createContext("/createChatRoomId", new ChatRoomCreationHandler());
        server.createContext("/createGameId", new GameIdCreater());

        server.start();
    }
}
