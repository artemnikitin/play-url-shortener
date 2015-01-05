package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import support.FakeTestEnvironment;
import org.junit.Test;
import play.libs.Json;
import play.mvc.Result;
import play.test.FakeRequest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.*;

public class ShortUrlTest extends FakeTestEnvironment {

    @Test
    public void getShortUrlForUnExistedUrl() {
        Result response = callAction(
                controllers.routes.ref.ShortUrl.makeShort(),
                new FakeRequest(POST, "/")
                        .withJsonBody(Json.newObject().put("url", "http://artemnikitin.com")));
        JsonNode json = Json.parse(contentAsString(response));
        assertEquals("Must return HTTP 200", 200, status(response));
        assertEquals("Response body must be in JSON",
                "application/json", contentType(response));
        assertTrue("Error field in JSON has 0 value",
                json.findPath("error").toString().replace("\"", "").equals("0"));
        assertTrue("Short URL must be 5 symbols at large",
                json.findPath("url_short").toString().replace("\"", "").length() >= 5);

    }

    @Test
    public void getShortUrlWithWrongBody() {
        Result response = callAction(
                controllers.routes.ref.ShortUrl.makeShort(),
                new FakeRequest(POST, "/").withJsonBody(Json.newObject().put("", "")));
        JsonNode json = Json.parse(contentAsString(response));
        assertEquals("Must return HTTP 400", 400, status(response));
        assertEquals("Response body must be in JSON",
                "application/json", contentType(response));
        assertTrue("Error field in JSON has 1 value",
                json.findPath("error").toString().replace("\"", "").equals("1"));
        assertEquals("Short URL must be empty", "",
                json.findPath("url_short").toString().replace("\"", ""));
    }

    @Test
    public void getUrlForExistedUrl() {
        Result response = callAction(
                controllers.routes.ref.ShortUrl.makeShort(),
                new FakeRequest(POST, "/").withJsonBody(
                        Json.newObject().put("url", "http://www.linkedin.com/in/artemnikitin")));
        JsonNode json = Json.parse(contentAsString(response));
        assertEquals("Must return HTTP 200", 200, status(response));
        assertEquals("Response body must be in JSON",
                "application/json", contentType(response));
        assertEquals("Short URL must be exact 'abc'", "abc",
                json.findPath("url_short").toString().replace("\"", ""));
    }

}
