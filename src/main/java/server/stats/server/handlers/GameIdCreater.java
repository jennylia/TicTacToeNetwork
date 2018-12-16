package server.stats.server.handlers;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.PutItemRequest;
import com.amazonaws.services.dynamodbv2.model.PutItemResult;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.stats.server.aws.DynamoDBClient;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.UUID;

public class GameIdCreater implements HttpHandler {
    private static final AmazonDynamoDB client = DynamoDBClient.getClient();
    static DynamoDB dynamoDB = DynamoDBClient.getDynamoDB();

    private static final String GAME_ROOM_TABLE_NAME = "GameRoom";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        UUID uuid = UUID.randomUUID();
        String gameId = uuid.toString();

        JsonObject responseObj = new JsonObject();
        responseObj.addProperty("gameId", gameId);
        String responseString = responseObj.toString();
        System.out.println(responseString);

        // Write this ID to database
        boolean persisted = persistGameId(gameId);
        OutputStream os = httpExchange.getResponseBody();

        if (persisted) {
            httpExchange.sendResponseHeaders(200, responseString.length());
            os.write(responseString.toString().getBytes());
        } else {
            String errorMessage = "something is wrong... try again";
            httpExchange.sendResponseHeaders(500, errorMessage.length());
            os.write(errorMessage.getBytes());
        }
        os.close();
    }

    private boolean persistGameId(String gameId) {
        Table table = dynamoDB.getTable(GAME_ROOM_TABLE_NAME);
        try {
            Item item = new Item().withPrimaryKey("GameId", gameId)
                    .withBoolean("Terminated", false)
                    .withNumber("CurrentPlayer", 1);
            table.putItem(item);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

}
