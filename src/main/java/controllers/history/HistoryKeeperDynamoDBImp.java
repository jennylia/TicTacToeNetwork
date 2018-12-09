package controllers.history;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import webServices.key.AWSAccessKey;
import webServices.key.AWSAccessKeyFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HistoryKeeperDynamoDBImp implements HistoryKeeper{

    private static final AWSAccessKey awsKey = AWSAccessKeyFactory.getFirstKey();
    private static final BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsKey.getAws_access_key_id(), awsKey.getAws_secret_access_key());
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    private static final DynamoDB dynamoDB = new DynamoDB(client);

    private static final String GAME_HISTORY_TABLE_NAME = "GameHistory";

    private String generateHistoryID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public void recordGameHistory(String playerName, int turn, int position, String[] friendsArg, boolean won) {
        String[] defaultFriends = {"Mike", "Jenny"};
        String[] friendsRecorded = (friendsArg == null || friendsArg.length == 0) ? defaultFriends : friendsArg;

        Set<String> friends = new HashSet<String>(Arrays.asList(friendsRecorded));

        Table table = dynamoDB.getTable(GAME_HISTORY_TABLE_NAME);
        try {
            Item item = new Item().withPrimaryKey("Id", generateHistoryID())
                    .withString("Player", playerName)
                    .withNumber("Turn", turn)
                    .withNumber("Position", position)
                    .withStringSet("Friends", friends)
                    .withBoolean("Won", won);

            table.putItem(item);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
