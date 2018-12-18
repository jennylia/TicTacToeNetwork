package server.stats.server.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import webServices.key.AWSAccessKey;
import webServices.key.AWSAccessKeyFactory;

public class DynamoDBClient {

    private static final AWSAccessKey awsKey = AWSAccessKeyFactory.getFirstKey();
    private static final BasicAWSCredentials awsCreds = new BasicAWSCredentials(awsKey.getAws_access_key_id(), awsKey.getAws_secret_access_key());
    private static final AmazonDynamoDB client = AmazonDynamoDBClientBuilder.standard()
            .withCredentials(new AWSStaticCredentialsProvider(awsCreds))
            .build();

    static DynamoDB dynamoDB = new DynamoDB(client);
    static DynamoDBMapper dynamoDBMapper = new DynamoDBMapper(client);

    public static DynamoDB getDynamoDB() {
        return dynamoDB;
    }

    public static AmazonDynamoDB getClient() {
        return client;
    }

    public static DynamoDBMapper getDynamoDBMapper() {
        return dynamoDBMapper;
    }
}
