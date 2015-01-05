package support;

import org.junit.After;
import org.junit.Before;
import play.test.FakeApplication;
import play.test.Helpers;

public class FakeTestEnvironment {

    private FakeApplication app;

    @Before
    public void setUp() {
        if (app == null) {
            app = Helpers.fakeApplication(Helpers.inMemoryDatabase());
            Helpers.start(app);
        }
    }

    @After
    public void stop() {
        if (app != null) {
            Helpers.stop(app);
            app = null;
        }
    }

}
