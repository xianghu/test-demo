package com.demo.pages;

import com.demo.DemoApp;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * The base page for all page objects
 */

public abstract class BasePage {

    protected final AppiumDriver<MobileElement> driver;

    public BasePage(AppiumDriver<MobileElement> driver) {
        this.driver = driver;
        // implicit wait time, may overwritten by explicit wait time below (WebDriverWait)
        PageFactory.initElements(new AppiumFieldDecorator(driver, DemoApp.IMPLICIT_WAIT_TIME, TimeUnit.SECONDS),
                this);
    }

    public WebElement findElementWithTimeout(By by, int timeOutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public boolean isElementExist(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (WebDriverException ex) {
            return false;
        }
    }
}
