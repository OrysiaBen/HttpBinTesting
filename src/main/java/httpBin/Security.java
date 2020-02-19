package httpBin;
import utils.HttpBinURL;

import java.util.HashMap;
public class Security {
    public String signIn(String name, String password) {
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", "application/json;utf-8");
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(map);
        baseHttpRequest.postRequest(HttpBinURL.SIGN_IN, "{" +
                "  \"email\": \"" + name + "\"," +
                "  \"password\": \"" + password + "\"" +
                "}");
        return baseHttpRequest.getResponse();
    }
}