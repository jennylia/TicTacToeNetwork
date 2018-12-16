package server.stats.clients;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class StatsClient {

    public static void main(String[] args) throws IOException {

        Map<String, String> parameters = new HashMap<>();
        parameters.put("Player", "Player 1");
        String paramsString = ParameterStringBuilder.getParamsString(parameters);
        String base = "http://localhost:9000/getHistory?";
        String completeURL = base + paramsString;
        System.out.println("created: " + completeURL);
        URL url = new URL(completeURL);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setDoOutput(true);

        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);

        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder sb = new StringBuilder();
        String inputLine = reader.readLine();
        while (inputLine != null) {
            sb.append(inputLine);
            inputLine = reader.readLine();
        }
        System.out.println(sb.toString());

        String id = getGameChatroomID();
        System.out.println(id);
    }

    private static String getGameChatroomID() throws IOException {
        //Send another request
        String postBase = "http://localhost:9000/createChatRoomId";
        URL url = new URL(postBase);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");

        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes("Hello");
        wr.flush();
        wr.close();


        //wait for write
        StringBuffer response = new StringBuffer();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String input = in.readLine();
        response.append(input);
        while (input != null){
            input = in.readLine();
            response.append(input);
        }

        return response.toString();
    }
}
