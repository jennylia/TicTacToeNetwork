package webServices.key;


public class AWSAccessKey {

    private String aws_access_key_id;
    private String aws_secret_access_key;

    public AWSAccessKey(String aws_access_key_id, String aws_secret_access_key) {
        this.aws_access_key_id = aws_access_key_id;
        this.aws_secret_access_key = aws_secret_access_key;
    }

    public String getAws_access_key_id() {
        return aws_access_key_id;
    }

    public String getAws_secret_access_key() {
        return aws_secret_access_key;
    }


//    public static void main(String[] args) {
//        try {
//            AWSKeyReaderFactory(null);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        System.out.println("read keys");
//    }

}
