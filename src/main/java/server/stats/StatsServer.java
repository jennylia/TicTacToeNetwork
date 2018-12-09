package server.stats;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class StatsServer {

    private static final int port = 9000;
    public static void main(String[] args) throws IOException {
        System.out.println("getting stats");

        HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/getHistory", new GameHistoryHandler());
        server.start();
    }
}
