package support;

import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;
import play.test.Helpers;
import play.test.TestServer;

public class Server {

    protected FakeApplication app;
    protected int port = 3333;
    protected TestServer testServer;

    @Before
    public void setUp() {
        if (testServer == null) {
            app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
            testServer = Helpers.testServer(port, app);
            testServer.start();
        }
    }

    @After
    public void stop() {
        if (testServer != null) {
            testServer.stop();
            testServer = null;
            app = null;
        }
    }

}
