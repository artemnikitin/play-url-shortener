package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.Test;
import play.libs.WS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class ShortUrlTest {

    @Test
    public void getShortUrlForUnExistedUrl(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/")
                        .setContentType("application/json")
                        .post("{\"url\":\"http://artemnikitin.com\"}").get();
                JsonNode json = response.asJson();
                assertEquals("Must return HTTP 200", 200, response.getStatus());
                assertEquals("Response body must be in JSON", "application/json", response.getHeader("Content-Type"));
                assertTrue("Error field in JSON has 0 value",
                        json.findPath("error").toString().replace("\"", "").equals("0"));
                assertTrue("Short URL must be 5 symbols at large",
                        json.findPath("url_short").toString().replace("\"", "").length() >= 5);
            }
        });
    }

    @Test
    public void getShortUrlWithWrongBody(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/")
                        .setContentType("application/json")
                        .post("{\"\":\"\"}").get();
                JsonNode json = response.asJson();
                assertEquals("Must return HTTP 400", 400, response.getStatus());
                assertEquals("Response body must be in JSON", "application/json", response.getHeader("Content-Type"));
                assertTrue("Error field in JSON has 1 value",
                        json.findPath("error").toString().replace("\"", "").equals("1"));
                assertEquals("Short URL must be empty", "",
                        json.findPath("url_short").toString().replace("\"", ""));
            }
        });
    }

    @Test
    public void getUrlForExistedUrl(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/")
                                         .setContentType("application/json")
                                         .post("{\"url\":\"http://www.linkedin.com/in/artemnikitin\"}").get();
                assertEquals("Must return HTTP 200", 200, response.getStatus());
                assertEquals("Response body must be in JSON", "application/json", response.getHeader("Content-Type"));
                assertEquals("Short URL must be exact 'abc'", "abc",
                        response.asJson().findPath("url_short").toString().replace("\"", ""));
            }
        });
    }


}
