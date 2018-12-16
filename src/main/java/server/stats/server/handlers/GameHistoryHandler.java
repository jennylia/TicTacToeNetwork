package server.stats.server.handlers;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.*;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import server.stats.server.aws.DynamoDBClient;
import webServices.key.AWSAccessKey;
import webServices.key.AWSAccessKeyFactory;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class GameHistoryHandler implements HttpHandler {

    private static final AmazonDynamoDB client = DynamoDBClient.getClient();
    private static final String PLAYER_PARAM = "Player";
    private static final String[] VALID_PARAM_VALUES = new String[]{PLAYER_PARAM};
    private static final Set<String> VALID_PARAMS = new HashSet<>(Arrays.asList(VALID_PARAM_VALUES));
    private static final String GAME_HISTORY_TABLE_NAME = "GameHistory";

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String reqParams = httpExchange.getRequestURI().getRawQuery();

        String response = "Game Data Retrieved: \n ";
        System.out.println("request param: " + reqParams);

        if (reqParams != null) {
            String decoded = java.net.URLDecoder.decode(reqParams, "UTF-8");
            System.out.printf("decoded URL: " + decoded);

            Map<String, String> requestParams = parseParams(decoded);
            String player = requestParams.get(PLAYER_PARAM);

            String gameHistory = retrieveGameHistory(player);
            response += gameHistory;
        }

        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private Map<String, String> parseParams(String reqParams) throws UnsupportedEncodingException {
        if (reqParams == null || reqParams.isEmpty()) {
            return null;
        }
        String[] tokens = reqParams.split("&");
        Map<String, String> params = new HashMap<String, String>();
        for (String t : tokens) {
            String[] pairVal = t.split("=");

            if (VALID_PARAMS.contains(pairVal[0])) {
                params.put(pairVal[0], pairVal[1]);
            }
        }
        return params;
    }

    private static String retrieveGameHistory(String player) {
        Map<String, Condition> scanFilter = new HashMap<>();
        Condition condition = new Condition()
                .withComparisonOperator(ComparisonOperator.EQ.toString())
                .withAttributeValueList(new AttributeValue().withS(player));
        scanFilter.put("Player", condition);
        ScanRequest scanRequest = new ScanRequest(GAME_HISTORY_TABLE_NAME).withScanFilter(scanFilter);

        ScanResult scanResult = client.scan(scanRequest);

        List<Map<String, AttributeValue>> items = scanResult.getItems();
        for (int i = 0; i < items.size(); i++) {
            Set<String> keys = items.get(i).keySet();
            System.out.println("keySet: " + keys);
            for (String s : keys) {
                System.out.println("Val: " + items.get(i).get(s));
            }
        }

        String resultRaw = scanResult.toString();
        System.out.println("getting Result: \n" + resultRaw);
        Gson gson = new Gson();
        String json = gson.toJson(scanResult);
        return json;
    }

}
