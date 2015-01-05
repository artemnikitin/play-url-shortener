package integration;

import org.junit.Test;
import play.libs.WS;
import support.Server;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GlobalsTest extends Server {

    @Test
    public void getUnExistedUrl() {
        WS.Response response = WS.url("http://localhost:3333/asd/2?result=1")
                .setContentType("application/json")
                .put("{\"url\":\"https://twitter.com/artemnikitin\"}").get();
        assertEquals("For nonexistent URL must be returned 404", 404, response.getStatus());
        assertEquals("Response body must be exact the same",
                "Requested URL not found!", response.getBody());
    }

    @Test
    public void malformedRequest() {
        WS.Response response = WS.url("http://localhost:3333/")
                .setContentType("application/json")
                .post("abchdgcgdcgdv").get();
        assertEquals("For malformed body must be returned 400", 400, response.getStatus());
        assertTrue("Response body must be exact the same",
                response.getBody().contains("We have a problem with you request"));
    }

}
