package webServices;

import org.junit.Test;
import webServices.key.AWSAccessKey;

import static org.junit.Assert.*;

public class AWSAccessKeyTest {

    private static final String key = "key";
    private static final String access = "access";

    @Test
    public void testAWSKeyGetter() {
        AWSAccessKey awsKey = new AWSAccessKey(key, access); // MyClass is tested
        assertEquals(awsKey.getAws_access_key_id(), key);
        assertEquals(awsKey.getAws_secret_access_key(), access);
    }
}