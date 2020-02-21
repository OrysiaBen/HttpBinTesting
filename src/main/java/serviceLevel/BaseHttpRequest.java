package serviceLevel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import httpBinClient.HttpBinClient;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.apache.log4j.*;

public class BaseHttpRequest {
    private static org.apache.log4j.Logger logger = Logger.getLogger(BaseHttpRequest.class);

    Map<String, String> requestHeaders = new HashMap();
    private CloseableHttpClient httpClient;
    private HttpRequestBase httpRequestBase;
    private String response;
    private int statusCode;

    public BaseHttpRequest() {
        logger.setLevel(Level.ALL);
        logger.debug("Initialized BaseHttpRequest");
        httpClient = HttpClients.createDefault();
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {
        logger.debug("Set Headers to: "+ requestHeaders);
        this.requestHeaders = requestHeaders;
    }

    public void HandleResponse() {
        logger.info("Handle response for " + httpRequestBase);
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase)) {
            this.response = response.toString();
            this.statusCode = response.getStatusLine().getStatusCode();
            logger.info("Response code: " + this.statusCode);
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                this.response = EntityUtils.toString(entity);
                logger.info("Response: " + this.response);
            }
        } catch (ClientProtocolException e) {
            logger.error("When handle response ClientProtocolException error occured " + e);
            e.printStackTrace();
        } catch (IOException e) {
            logger.error("When handle response IOException error occured " + e);
            e.printStackTrace();
        }
    }

    public String getRequest(String url) {
        logger.info("Process get request for " + url);
        httpRequestBase = new HttpGet(url);
        this.requestHeaders.forEach((key, value) -> httpRequestBase.setHeader(key, value));
        HandleResponse();
        return this.response;
    }

    public String postRequest(String url, String body) {
        logger.info("Process post request for " + url);
        logger.info("Body: "+ body);
        HttpPost httpRequestBasePost = new HttpPost(url);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(body);
            logger.debug("stringEntity " + stringEntity);
        } catch (UnsupportedEncodingException e) {
            logger.error("UnsupportedEncodingException " + e);
            e.printStackTrace();
        }
        httpRequestBasePost.setEntity(stringEntity);
        this.requestHeaders.forEach((key, value) -> httpRequestBasePost.setHeader(key, value));
        httpRequestBasePost.setHeader("Content-type", "application/json");
        httpRequestBase=httpRequestBasePost;
        HandleResponse();
        return this.response;
    }

    public int getStatusCode() {
        logger.info("status code: " + this.statusCode);
        return this.statusCode;
    }

    public String parseJsonObject(String key) {
        logger.debug("Getting key: " + key + " from " + response);
        JSONObject json = new JSONObject(response);
        return json.get(key).toString();
    }

    public String getResponse() {
        logger.info("Response: " + response);
        return this.response;
    }
}
