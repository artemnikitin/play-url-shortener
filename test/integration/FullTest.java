package integration;

import org.junit.Test;
import play.libs.WS;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static play.test.Helpers.running;
import static play.test.Helpers.testServer;

public class FullTest {

    @Test
    public void fullCheck(){
        running(testServer(3333), new Runnable() {
            public void run() {
                WS.Response response = WS.url("http://localhost:3333/")
                        .setContentType("application/json")
                        .post("{\"url\":\"https://twitter.com/artemnikitin\"}").get();
                String code = response.asJson().findPath("url_short").toString().replace("\"", "");
                assertEquals("Must return HTTP 200", 200, response.getStatus());
                assertEquals("Response body must be in JSON", "application/json", response.getHeader("Content-Type"));
                assertTrue("Short URL must be 5 symbols or longer", code.length() >= 5);

                response = WS.url("http://localhost:3333/" + code)
                             .setFollowRedirects(false)
                             .get().get();
                assertEquals("Must return HTTP 301", 301, response.getStatus());
                assertEquals("Must return header Moved Permanently", "Moved Permanently", response.getStatusText());
                assertEquals("Header Location must exist and contain right URL", "https://twitter.com/artemnikitin", response.getHeader("Location"));
            }
        });
    }

}
