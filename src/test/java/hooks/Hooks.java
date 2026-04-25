package hooks;

import base.BaseTest;
import db.DatabaseManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks extends BaseTest {

    @Before
    public void setUpHook() {
        setUp();
        DatabaseManager.createTable();
    }

    @After
    public void tearDownHook() {
        tearDown();
    }
}