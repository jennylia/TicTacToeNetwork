package webServices;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.model.*;
import webServices.key.AWSAccessKey;
import webServices.key.AWSAccessKeyFactory;

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
//        setupTable(GAME_HISTORY_TABLE_NAME);
        deleteTestingResources();
    }

    private static void deleteTestingResources(){
        deleteTable(EXAMPLE_TABLE_NAME);
    }

    public static void deleteTable(String table){
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
                .withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S))
                .withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
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
