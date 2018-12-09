package server.stats.clients;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class ParameterStringBuilder {
    public static String getParamsString(Map<String, String> parameters) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (String k :parameters.keySet()){
            sb.append(URLEncoder.encode(k, "UTF-8"));
            sb.append("=");
            String val = (URLEncoder.encode(parameters.get(k), "UTF-8"));
            val = val.replace("+", "%20");
            sb.append(val);
            sb.append("&");
        }
        //Chop off the last "&"
        sb.delete(sb.lastIndexOf("&"), sb.length());
        return sb.toString();
    }

//    public static void main(String[] args) throws UnsupportedEncodingException {
//        Map<String, String> param = new HashMap<>();
//        param.put("a", "b");
//        String ans = getParamsString(param);
//        System.out.println(ans);
//    }
}
