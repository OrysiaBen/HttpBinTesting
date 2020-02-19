package httpBin;

import utils.HttpBinURL;

import java.util.HashMap;
import java.util.logging.*;

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


public class HttpBinClient {
    private static Logger logger = Logger.getLogger(HttpBinClient.class.getName());

    String BaseUrl;
    HashMap<String, String> BaseHeaders = new HashMap();
    Boolean IsAuthIn = false;
    String AuthUsername = "";

    public HttpBinClient(String Url) {
        this.BaseUrl = Url;
        logger.info("Initialized HttpBinClient");
    }

    public String Authorization(String name, String password) {
        logger.info("Authorization for " + name + " password: " + password);
        HashMap<String, String> AdditionalHeaders = new HashMap();
        AdditionalHeaders.putAll(BaseHeaders);
        AdditionalHeaders.put("Content-Type", "application/json;utf-8");
        BaseHttpRequest baseHttpRequest = new BaseHttpRequest();
        baseHttpRequest.setRequestHeaders(AdditionalHeaders);
        String url = HttpBinURL.SIGN_IN + "/" + name + "/" + password;
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
            System.out.println(response.getStatusLine().getStatusCode());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                result = EntityUtils.toString(entity);
                System.out.println(result);
                JSONObject json = new JSONObject(result);
                AuthUsername = json.get("user").toString();
                IsAuthIn = (Boolean) json.get("authenticated");
                logger.info("RRRR " + json.get("user").toString());
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public Boolean getAuthStatus() {
        return IsAuthIn;
    }
}
