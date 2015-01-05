package controllers;

import support.FakeTestEnvironment;
import org.junit.Test;
import play.mvc.Result;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class FullUrlTest extends FakeTestEnvironment {

    @Test
    public void getUrlForUnexistedCode() {
        Result response = callAction(controllers.routes.ref.FullUrl.redirectTo("2312313"));
        assertEquals("On unexisted code must return HTTP 400", 400, status(response));
        assertTrue("Text not equal to text in controller",
            contentAsString(response).contains("URL not find! Please check that your submit."));
    }

    @Test
    public void getUrlForExistedCode() {
        Result response = callAction(controllers.routes.ref.FullUrl.redirectTo("abc"));
        assertEquals("On existed code must return HTTP 301", 301, status(response));
        assertEquals("On existed code header Location must exist",
                "http://www.linkedin.com/in/artemnikitin", redirectLocation(response));
    }


}
