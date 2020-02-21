package httpBinTest;

import httpBinClient.HttpBinClient;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.HttpBinConstants;
import utils.HttpBinURL;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class HttpBinClientTest {
    HttpBinClient httpBinClient = new HttpBinClient(HttpBinURL.BASE_URL);

    /**
     * <b>TC-1: Authorization(token) positive test </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some data to get return a set of response headers from the query string.
     * </ul>
     * <p>
     * Expected Result: date which we input
     */


    @DataProvider(name = "tokenText")
    public Object[][] createDataForTokenText(Method m) {
        return new Object[][]{
                new Object[]{"AbCdEf123456"}
                , new Object[]{"-5"}
        };
    }

    @Test(dataProvider = "tokenText")
    public void getTokenPositiveTest(String text) {
        String actual = httpBinClient.getToken(text, "token");
        assertEquals(actual, text);
    }

    /**
     * <b>TC-2: Authorization(token) positive test </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some data to get token.
     * </ul>
     * <p>
     * Expected Result: 200
     */

    @Test()
    public void getStatusCodeTokenPositiveTest() {
        Integer actual = httpBinClient.getTokenStatusCode("AbCdEf123456");
        assertEquals(actual,HttpBinConstants.TWO_HUNDRED );
    }

    /**
     * <b>TC-3: Authorization(token) negative test </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some not correct data to get token.
     * </ul>
     * <p>
     * Expected Result: 401
     */

    @Test()
    public void getStatusCodeTokenNegativeTest() {
        Integer actual = httpBinClient.getTokenStatusCode("");
        assertEquals(actual,HttpBinConstants.FOUR_HUNDRED_ONE );
    }


    /**
     * <b>TC-4: Response-headers </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some data to get return a set of response headers from the query string.
     * </ul>
     * <p>
     * Expected Result: 200
     */

    @DataProvider(name = "responseHeadersStatusCode")
    public Object[][] createDataForResponseHeaders(Method m) {
        return new Object[][]{
                new Object[]{""}
                , new Object[]{"Hello"}
                , new Object[]{"+-*"}
                , new Object[]{"-5"}
        };
    }

    @Test(dataProvider = "responseHeadersStatusCode")
    public void responseHeadersStatusCodePositiveTest(String text) {
        Integer actual = httpBinClient.responseHeadersStatusCode(text);
        assertEquals(actual, HttpBinConstants.TWO_HUNDRED);
    }

    /**
     * <b>TC-5: Response-headers </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some not correct data to get return a set of response headers from the query string.
     * </ul>
     * <p>
     * Expected Result: 500
     */

    @Test()
    public void responseHeadersStatusCodeNegativeTest() {
        Integer actual = httpBinClient.responseHeadersStatusCode("иии");
        assertEquals(actual, HttpBinConstants.FIVE_HUNDRED);
    }

    /**
     * <b>TC-6: Response-headers </b>
     * <p>
     * Scenario:
     * <ul>
     * <li>1. Send request with some data to get return a set of response headers from the query string.
     * </ul>
     * <p>
     * Expected Result: date which we input
     */

    @DataProvider(name = "responseHeadersText")
    public Object[][] createDataForResponseHeadersText(Method m) {
        return new Object[][]{
                new Object[]{""}
                , new Object[]{"Hello"}
                , new Object[]{"-*rhdtxnznfxfnzf"}
                , new Object[]{"-5"}
        };
    }

    @Test(dataProvider = "responseHeadersText")
    public void responseGetJsonValuePositiveTest(String text) {
        String actual = httpBinClient.responseHeadersJson(text, "freeform");
        assertEquals(actual, text);
    }


}
