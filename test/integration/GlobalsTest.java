package integration;

import org.junit.Test;
import play.mvc.Result;
import support.Server;

import static org.junit.Assert.assertEquals;
import static play.test.Helpers.callAction;
import static play.test.Helpers.contentAsString;

public class GlobalsTest extends Server {

    @Test
    public void getIndexPage(){
        Result result = callAction(controllers.routes.ref.Application.index());
        assertEquals("Wrong text in index() controller",
                contentAsString(result), "This is sample URL shortener application");
    }

}
