package controllers;

import org.junit.Test;
import play.libs.WS;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class FullUrlTest {

    @Test
    public void getUrlForUnexistedCode(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/2312313").get().get();
                assertEquals("On unexisted code must return HTTP 400", 400, response.getStatus());
                assertEquals("On unexisted code must return Bad Request", "Bad Request", response.getStatusText());
                assertEquals("Text not equal to text in controller", "URL not find! Please check that your submit.", response.getBody());
            }
        });
    }

    @Test
    public void getUrlForExistedCode(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/abc").setFollowRedirects(false).get().get();
                assertEquals("On existed code must return HTTP 301", 301, response.getStatus());
                assertEquals("On existed code must return Moved Permanently", "Moved Permanently", response.getStatusText());
                assertEquals("On existed code header Location must exist", "http://www.linkedin.com/in/artemnikitin", response.getHeader("Location"));
            }
        });
    }


}
