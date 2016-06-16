package com.cucumber.utl;


import com.cucumber.config.ConfigManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class SharedDriver extends EventFiringWebDriver {
    private static WebDriver REAL_DRIVER = null;
    private static final Thread CLOSE_THREAD = new Thread() {
        @Override
        public void run() {
            REAL_DRIVER.close();
        }
    };

    static {
        Runtime.getRuntime().addShutdownHook(CLOSE_THREAD);
        ConfigManager config = new ConfigManager();

        DesiredCapabilities browser = null;

        if ("firefox".equalsIgnoreCase(config.get("browser"))) {
            browser = DesiredCapabilities.firefox();
        } else {
            browser = DesiredCapabilities.chrome();
        }
        browser.setJavascriptEnabled(true);

        try {

            REAL_DRIVER = new RemoteWebDriver(new URL(config.get("selenium_server_url")), browser);
        } catch (MalformedURLException exceptions) {

        }
    }

    public SharedDriver () {
        super(REAL_DRIVER);
        REAL_DRIVER.manage().window().maximize();
        REAL_DRIVER.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @Override
    public void close() {
        if (Thread.currentThread() != CLOSE_THREAD) {
            throw new UnsupportedOperationException("You shouldn't close this WebDriver. It's shared and will close when the JVM exits.");
        }
        super.close();
    }

    @Before
    public void deleteAllCookies() {
        REAL_DRIVER.manage().deleteAllCookies();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        try {
            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");

        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
}

