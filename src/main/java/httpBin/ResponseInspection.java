package httpBin;

import static utils.HttpBinURL.RESPONSE_HEADER_URL;

public class ResponseInspection {

    /**
     * Returns a set of response headers from the query string.
     *
     * @param text - input text
     * @return StatusCode
     */

    public int responseHeadersStatusCode(String text) {
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.postRequest(RESPONSE_HEADER_URL + text, text);
        return baseHttpRequest.getStatusCode();
    }

    /**
     * Returns a set of response headers from the query string.
     *
     * @param text - input text
     * @return JSONObject
     */

    public String responseHeadersJson(String text, String key) {
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.postRequest(RESPONSE_HEADER_URL + text, text);
        return baseHttpRequest.parseJsonObject(key);
    }
}
