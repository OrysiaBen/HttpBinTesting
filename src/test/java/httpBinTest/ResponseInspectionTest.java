package httpBinTest;

import httpBin.ResponseInspection;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.HttpBinConstants;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class ResponseInspectionTest {
    ResponseInspection responseInspection = new ResponseInspection();

    /**
     * <b>TC-1: Response-headers </b>
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
        Integer actual = responseInspection.responseHeadersStatusCode(text);
        assertEquals(actual, HttpBinConstants.TWO_HUNDRED);
    }

    /**
     * <b>TC-2: Response-headers </b>
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
        Integer actual = responseInspection.responseHeadersStatusCode("иии");
        assertEquals(actual, HttpBinConstants.FIVE_HUNDRED);
    }

    /**
     * <b>TC-3: Response-headers </b>
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
        String actual = responseInspection.responseHeadersJson(text, "freeform");
        assertEquals(actual, text);
    }
}
