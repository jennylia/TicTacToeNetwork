package webServices.key;

import java.io.*;

public class AWSAccessKeyFactory {

    private static String DEFAULT_PATH = "/Users/jennylian/.aws/credentials";

    public static AWSAccessKey getFirstKey(){
        AWSAccessKey awsKey = null;
        try {
            awsKey = getFirstKeyWithPath(DEFAULT_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return awsKey;
    }

    public static AWSAccessKey getFirstKeyWithPath(String path) throws IOException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st = br.readLine();
        String aws_access_key_id_val = null;
        String aws_secret_access_key= null;
        while (st != null && !(aws_access_key_id_val != null && aws_secret_access_key != null)){
            System.out.println(st);
            if (st.contains("aws_access_key_id")){
                System.out.println("ah ha access key");
                aws_access_key_id_val = st.split("=")[1].trim();
            }
            if (st.contains("aws_secret_access_key")){
                System.out.println("ah ha secret key");
                aws_secret_access_key = st.split("=")[1].trim();
            }
            st = br.readLine();
        }
        return new AWSAccessKey(aws_access_key_id_val, aws_secret_access_key);
    }

}
