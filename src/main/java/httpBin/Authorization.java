package httpBin;

import utils.HttpBinURL;

import java.util.HashMap;

public class Authorization {

    /**
     * Prompts the user for authorization using bearer authentication.
     *
     * @param header - input header for token
     * @param key    - input key for get the value from JSON response
     * @return JSONObject
     */

    public String getToken(String header, String key) {
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", "application/json;utf-8");
        map.put("Authorization", "Bearer " + header);
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(map);
        baseHttpRequest.getRequest(HttpBinURL.BEARER_URL);
        return baseHttpRequest.parseJsonObject(key);
    }

    /**
     * Prompts the user for authorization using bearer authentication.
     *
     * @param header - input header for token
     * @return Status Code
     */

    public Integer getTokenStatusCode(String header) {
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", "application/json;utf-8");
        map.put("Authorization", "Bearer " + header);
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(map);
        baseHttpRequest.getRequest(HttpBinURL.BEARER_URL);
        return baseHttpRequest.getStatusCode();
    }
}
