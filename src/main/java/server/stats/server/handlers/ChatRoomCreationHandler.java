package server.stats.server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.UUID;

public class ChatRoomCreationHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        UUID uuid = UUID.randomUUID();
        String chatID = uuid.toString();

        InputStreamReader isr = new InputStreamReader(httpExchange.getRequestBody(), "utf-8");

        BufferedReader br = new BufferedReader(isr);
        String query = br.readLine();
        StringBuffer sb = new StringBuffer();
        while (query != null){
            sb.append(query);
            System.out.println(query);
            query = br.readLine();
        }

        String responseBody = sb.toString();
        System.out.println(responseBody);

        // send out the response
        String response = "echo back: " + chatID + responseBody;
        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }
}
