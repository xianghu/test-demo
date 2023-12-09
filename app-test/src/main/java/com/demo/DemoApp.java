package com.demo;

import com.demo.pages.BasePage;
import com.demo.pages.HomePage;
import com.demo.pages.LoginPage;
import com.demo.common.AppUser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FilenameFilter;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * demo app, all tests should start from here
 */
public class DemoApp {

    public static final int IMPLICIT_WAIT_TIME = 10;
    private static final long SLEEP_TIME_FOR_OPEN_AD = 5000;

    private static final String APP_DIR = "apps";
    private static final String APPIUM_SERVER_URL = "http://127.0.0.1:4723/wd/hub";

    private static AppiumDriverLocalService service;
    private static AppiumDriver<MobileElement> driver;
    private static OS executionOS = OS.ANDROID;

    public static OS getExecutionOS() {
        return executionOS;
    }

    public static void setExecutionOS(OS executionOS) {
        DemoApp.executionOS = executionOS;
    }

    private static void startAppiumService() {

        AppiumServiceBuilder builder = new AppiumServiceBuilder();
        builder.withLogFile(new File("log/appium.log"));
        service = builder.build();

        service.start();
    }

    private static void stopAppiumService() {
        if (service != null) {
            service.stop();
        }
    }

    /**
     * Start appium service
     * Create appium session
     * Install app
     * Launch app
     *
     * @throws Exception if something wrong
     */
    public static void setUpApp() throws Exception {

        startAppiumService();

        String appName = getAppFileName();
        File app = new File(APP_DIR, appName);
        DesiredCapabilities capabilities = new DesiredCapabilities();

        switch (executionOS) {
            case ANDROID:
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
                capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                capabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "");
                capabilities.setCapability(MobileCapabilityType.TAKES_SCREENSHOT, "true");

                capabilities.setCapability(AndroidMobileCapabilityType.NO_SIGN, true);
                capabilities.setCapability(AndroidMobileCapabilityType.AUTO_GRANT_PERMISSIONS, true);
                capabilities.setCapability(AndroidMobileCapabilityType.UNICODE_KEYBOARD, true);
                capabilities.setCapability(AndroidMobileCapabilityType.RESET_KEYBOARD, true);
                capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY,
                        "com.demopinche.booking.home.activity.IndexActivity");
                driver = new AndroidDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
                break;
            case IOS:
                // for simulator
                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone Simulator");
                capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
                // for real device
//                capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone");
                capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
                capabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
                capabilities.setCapability(MobileCapabilityType.NO_RESET, true);    // default false
                capabilities.setCapability(IOSMobileCapabilityType.AUTO_ACCEPT_ALERTS, true);

                driver = new IOSDriver<>(new URL(APPIUM_SERVER_URL), capabilities);
                driver.switchTo().alert().accept(); // accept push message alert since AUTO_ACCEPT_ALERTS doesn't work
                break;
            default:
                throw new Exception("executionOS must be either ANDROID or IOS");
        }
        // timeout for driver.findElementBy...
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIME, TimeUnit.SECONDS);

        Thread.sleep(SLEEP_TIME_FOR_OPEN_AD);  // sleep for open Ad
    }

    /**
     * Close app / reset
     * Stop appium session
     * Stop appium service
     *
     * @throws Exception if something wrong
     */
    public static void tearDownApp() throws Exception {
        if (driver != null) {
            // remove appium unicode input ime to reset keyboard
            if (OS.ANDROID.equals(executionOS)) {
                driver.removeApp("io.appium.android.ime");
            }
            driver.quit();
        }
        stopAppiumService();
    }

    /**
     * Launch the app which was provided in the capabilities at session creation.
     */
    public static void launchApp() throws Exception {
        driver.launchApp();
        Thread.sleep(SLEEP_TIME_FOR_OPEN_AD);
    }

    /**
     * Reset the currently running app for this session.
     */
    public static void resetApp() {
        driver.resetApp();
    }

    /**
     * Close the app which was provided in the capabilities at session creation.
     */
    public static void closeApp() {
        driver.closeApp();
    }

    public static BasePage getLoginPage() {
        return new LoginPage(driver);
    }

    /**
     * @param user
     * @return
     * @throws Exception
     */
    public static BasePage login(AppUser user) throws Exception {
        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(user.getAccountName(), user.getPassword());

        return new HomePage(driver);
    }


    /**
     * private help method to get app name.
     *
     * @return app package name
     */
    private static String getAppFileName() throws Exception {
        File folder = new File(APP_DIR);
        final String extend;

        switch (executionOS) {
            case ANDROID:
                extend = ".apk";
                break;
            case IOS:
                extend = ".zip"; // on iOS simulator
                break;
            default:
                throw new Exception("executionOS must be either ANDROID or IOS");
        }

        File[] files = folder.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(extend);
            }
        });

        String fileName;
        if (files != null && files.length == 1) {
            fileName = files[0].getName();
        } else {
            throw new Exception("There must be one and only one apk/zip/ipa file in apps folder.");
        }

        return fileName;
    }

    public enum OS {
        ANDROID,
        IOS
    }
}
