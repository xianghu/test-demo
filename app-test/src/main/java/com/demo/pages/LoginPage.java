package com.demo.pages;

import com.demo.common.AppUser;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.iOSXCUITFindBy;

/**
 * login page
 */
public class LoginPage extends BasePage {

    @AndroidFindBy(id = "com.demo:id/account")
    private MobileElement accountNameField;

    @AndroidFindBy(id = "com.demo:id/password")
    @iOSXCUITFindBy(xpath = "//*[1]//*[1]//*[1]//*[1]//*[2]//*[1]//*[1]//*[2]//*[1]")
    private MobileElement passwordField;

    @AndroidFindBy(id = "com.demo:id/submit")
    @iOSXCUITFindBy(accessibility = "submit")
    private MobileElement loginButton;

    public LoginPage(AppiumDriver<MobileElement> driver) {
        super(driver);
    }

    public BasePage login(AppUser user) {
        return login(user.getAccountName(), user.getPassword());
    }

    public BasePage login(String account, String password) {

        accountNameField.sendKeys(account);
        passwordField.sendKeys(password);

        loginButton.click();

        return new HomePage(driver);
    }
}
