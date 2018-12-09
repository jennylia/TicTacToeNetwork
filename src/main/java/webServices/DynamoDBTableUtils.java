package webServices;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import webServices.key.AWSAccessKey;
import webServices.key.AWSAccessKeyFactory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class DynamoDBTableUtils {

    static AWSAccessKey awsKey = AWSAccessKeyFactory.getFirstKey();
    static String accessKey = awsKey.getAws_access_key_id();
    static String secretKey = awsKey.getAws_secret_access_key();

    static BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);
    static AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();
    static DynamoDB dynamoDB = new DynamoDB(client);


    //Resources
    private static final String EXAMPLE_TABLE_NAME = "ExampleTable";
    private static final String GAME_HISTORY_TABLE_NAME = "GameHistory";


    public static void main(String[] args) {
//        recordGameHistory();
        testRecordGameHistory();
    }

    private static void testRecordGameHistory(){
        setupTable(GAME_HISTORY_TABLE_NAME);

        Table table = dynamoDB.getTable(GAME_HISTORY_TABLE_NAME);
        try{
            Item item = new Item().withPrimaryKey("Id", "123")
                    .withString("Player", "Player1")
                    .withNumber("Turn", 1)
                    .withNumber("Position", 1)
                    .withStringSet("Friends", new HashSet<String>(Arrays.asList("Jenny", "mike")))
                    .withBoolean("Won", false);

            table.putItem(item);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    public static void recordGameHistory() {
        setupTable(GAME_HISTORY_TABLE_NAME);
        // Start to persist


        Map<String, AttributeValue> item_values =
                new HashMap<String, AttributeValue>();

        String name = "player 1";
        item_values.put("Name", new AttributeValue(name));
        try {
            client.putItem(GAME_HISTORY_TABLE_NAME, item_values);
        } catch (ResourceNotFoundException e) {
            System.err.format("Error: The table \"%s\" can't be found.\n", GAME_HISTORY_TABLE_NAME);
            System.err.println("Be sure that it exists and that you've typed its name correctly!");
            System.exit(1);
        } catch (AmazonServiceException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    private static void deleteTestingResources() {
        deleteTable(EXAMPLE_TABLE_NAME);
    }

    public static void deleteTable(String table) {
        try {
            client.deleteTable(table);
        } catch (AmazonServiceException e) {
            System.err.println(e.getErrorMessage());
            System.exit(1);
        }
    }

    private static void setupTable(String tableName) {
        try {
            Table table = dynamoDB.getTable(tableName);
            String description = table.describe().toString();
            System.out.println(description);
        } catch (ResourceNotFoundException e) {
            System.out.println("Do not have this table yet so let's go create one");
            createExampleTable(tableName);
        }
    }


    // Note: DDB wrapper has better ways to create table
    private static void createExampleTable(String tableName) {
        CreateTableRequest request = new CreateTableRequest()
                .withAttributeDefinitions(new AttributeDefinition("Id", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("Id", KeyType.HASH))
                .withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
                .withTableName(tableName);

        try {
            CreateTableResult result = client.createTable(request);
            System.out.println(result.getTableDescription().getTableName());
        } catch (AmazonServiceException e) {
            e.printStackTrace();
        }
    }
}
