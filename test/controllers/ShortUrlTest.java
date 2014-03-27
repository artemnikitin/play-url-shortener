package controllers;

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
                assertEquals("Must return HTTP 200", 200, response.getStatus());
                assertEquals("Response body must be in JSON", "application/json", response.getHeader("Content-Type"));
                assertTrue("Short URL must be 5 symbols at large",
                        response.asJson().findPath("url_short").toString().replace("\"", "").length() >= 5);
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
