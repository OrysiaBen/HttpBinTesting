package httpBin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class BaseHttpRequest {
    Map<String, String> requestHeaders = new HashMap();
    private CloseableHttpClient httpClient;
    private HttpRequestBase httpRequestBase;
    private String response;
    private int statusCode;

    public BaseHttpRequest() {
        httpClient = HttpClients.createDefault();
    }

    public void setRequestHeaders(Map<String, String> requestHeaders) {

        this.requestHeaders = requestHeaders;
    }

    public String getRequest(String url) {
        httpRequestBase = new HttpGet(url);
        this.requestHeaders.forEach((key, value) -> httpRequestBase.setHeader(key, value));
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase)) {
            this.response = response.toString();
            this.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                this.response = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.response;
    }

    public String postRequest(String url, String body) {
        HttpPost httpRequestBase = new HttpPost(url);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(body);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        httpRequestBase.setEntity(stringEntity);
        this.requestHeaders.forEach((key, value) -> httpRequestBase.setHeader(key, value));
        httpRequestBase.setHeader("Content-type", "application/json");
        try (CloseableHttpResponse response = httpClient.execute(httpRequestBase)) {
            this.response = response.toString();
            this.statusCode = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                this.response = EntityUtils.toString(entity);
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.response;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String parseJsonObject(String key) {
        JSONObject json = new JSONObject(response);
        return json.get(key).toString();
    }
}
