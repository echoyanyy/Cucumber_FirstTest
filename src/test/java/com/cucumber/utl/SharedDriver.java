package com.cucumber.utl;


import com.cucumber.config.ConfigManager;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

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
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--no-sandbox");
            options.addArguments("--start-maximized");
            options.addArguments("--disable-plugins");
            browser.setCapability(ChromeOptions.CAPABILITY, options);
        }

        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);

        browser.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        browser.setJavascriptEnabled(true);

        try {
            REAL_DRIVER = new RemoteWebDriver(new URL(config.get("selenium_server_url")), browser);
        } catch (MalformedURLException exceptions) { }
    }

    public SharedDriver () {
        super(REAL_DRIVER);
        //REAL_DRIVER.manage().window().maximize();   //会引起这个错误：cannot get automation extension
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
        System.out.println("===========开始运行某个测试用例==========");
        REAL_DRIVER.manage().deleteAllCookies();
    }

    @After
    public void embedScreenshot(Scenario scenario) {
        System.out.println("===========运行某个测试用例结束==========");
        try {

            LogEntries logEntries = REAL_DRIVER.manage().logs().get(LogType.BROWSER);
            System.out.println("=============================浏览器控制台日志================================");
            for (LogEntry entry : logEntries) {
                if (entry.getLevel().toString().contains("INFO"))
                    break;
                System.out.println(entry);
                //System.out.println( " " + entry.getLevel() + "=====" + entry.getMessage());
            }
            System.out.println("============================================================================");
            System.out.println("进行截图时页面当前的url：" + REAL_DRIVER.getCurrentUrl());

            byte[] screenshot = getScreenshotAs(OutputType.BYTES);
            scenario.embed(screenshot, "image/png");

        } catch (WebDriverException somePlatformsDontSupportScreenshots) {
            System.err.println(somePlatformsDontSupportScreenshots.getMessage());
        }
    }
}
