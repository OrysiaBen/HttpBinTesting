package httpBinClient;

import serviceLevel.BaseHttpRequest;
import utils.HttpBinURL;

import java.util.HashMap;
//import java.util.logging.*;
import org.apache.log4j.*;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static utils.HttpBinURL.RESPONSE_HEADER_URL;


public class HttpBinClient {
//    private static Logger logger = Logger.getLogger(HttpBinClient.class.getName());
    private static org.apache.log4j.Logger logger = Logger.getLogger(HttpBinClient.class);

    String BaseUrl;
    HashMap<String, String> BaseHeaders = new HashMap();
    Boolean IsAuthIn = false;
    String AuthUsername = "";

    public HttpBinClient(String Url) {
        logger.setLevel(Level.ALL);
        this.BaseUrl = Url;
        logger.info("Initialized HttpBinClient for " + this.BaseUrl);
    }

    public String Authorization(String name, String password) {
        logger.info("Authorization for " + name + " password: " + password);
        HashMap<String, String> AdditionalHeaders = new HashMap();
        AdditionalHeaders.putAll(BaseHeaders);
        AdditionalHeaders.put("Content-Type", "application/json;utf-8");
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(AdditionalHeaders);
        logger.debug("Headers: "+ AdditionalHeaders);
        String url = HttpBinURL.SIGN_IN + "/" + name + "/" + password;
        logger.debug("get method to: " + url);
        HttpGet request = new HttpGet(url);
        CredentialsProvider provider = new BasicCredentialsProvider();
        provider.setCredentials(
                AuthScope.ANY,
                new UsernamePasswordCredentials(name, password)
        );
        String result = "";
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(provider)
                .build();
             CloseableHttpResponse response = httpClient.execute(request)) {
            logger.info("basic auth response status code: " + response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                logger.info("basic auth respone: " + result);
                JSONObject json = new JSONObject(result);
                AuthUsername = json.get("user").toString();
                IsAuthIn = (Boolean) json.get("authenticated");
            }
        } catch (ClientProtocolException e) {
            logger.error("ClientProtocolException when auth " + e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("IOException when auth " + e);
            e.printStackTrace();
        }
        return result;
    }

    public Boolean getAuthStatus() {
        logger.debug("get auth status");
        return IsAuthIn;
    }

    /**
     * Returns a set of response headers from the query string.
     *
     * @param text - input text
     * @return StatusCode
     */

    public int responseHeadersStatusCode(String text) {
        logger.info("In responseHeadersStatusCode");
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        logger.info("Post method to " + RESPONSE_HEADER_URL + " with: " + text);
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
        logger.debug("running httpBinClient method responseHeadersJson with " + text + "; " + key);
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        logger.info("post method to " + RESPONSE_HEADER_URL + text + " with: " + key);
        baseHttpRequest.postRequest(RESPONSE_HEADER_URL + text, text);
        return baseHttpRequest.parseJsonObject(key);
    }

    /**
     * Prompts the user for authorization using bearer authentication.
     *
     * @param header - input header for token
     * @param key    - input key for get the value from JSON response
     * @return JSONObject
     */

    public String getToken(String header, String key) {
        logger.info("GET method for HttBinClient");
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", "application/json;utf-8");
        map.put("Authorization", "Bearer " + header);
        logger.debug("Headers: " + map);
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(map);
        logger.info("send get to " + HttpBinURL.BEARER_URL);
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
        logger.info("Check token status code for " + header);
        HashMap<String, String> map = new HashMap();
        map.put("Content-Type", "application/json;utf-8");
        map.put("Authorization", "Bearer " + header);
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(map);
        logger.debug("Headers: " + map);
        logger.info("send get method to " + HttpBinURL.BEARER_URL);
        baseHttpRequest.getRequest(HttpBinURL.BEARER_URL);
        return baseHttpRequest.getStatusCode();
    }

}
