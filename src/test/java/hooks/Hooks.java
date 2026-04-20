package hooks;

import base.BaseTest;
import io.cucumber.java.Before;
import io.cucumber.java.After;

public class Hooks {

    @Before
    public void setUp() {
        BaseTest.initDriver();
    }

    @After
    public void tearDown() {
        BaseTest.quitDriver();
    }
}