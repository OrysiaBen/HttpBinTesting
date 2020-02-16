package httpBinTest;

import httpBin.Authorization;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.HttpBinConstants;

import java.lang.reflect.Method;

import static org.testng.Assert.assertEquals;

public class AuthorizationTest {
    Authorization authorization = new Authorization();

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
        String actual = authorization.getToken(text, "token");
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
        Integer actual = authorization.getTokenStatusCode("AbCdEf123456");
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
        Integer actual = authorization.getTokenStatusCode("");
        assertEquals(actual,HttpBinConstants.FOUR_HUNDRED_ONE );
    }

}
