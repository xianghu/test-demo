package com.demo;

import com.demo.DemoApp;
import com.demo.common.AppUser;
import com.demo.pages.LoginPage;
import com.demo.pages.HomePage;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * test login page
 */
public class LoginTest {

    private static AppUser user;
    private static LoginPage loginPage;
    private static HomePage homePage;

    @BeforeClass
    public static void beforeAll() throws Exception {
        DemoApp.setUpApp();

        user = new AppUser();

        loginPage = (LoginPage) DemoApp.getLoginPage();
    }

    @AfterClass
    public static void afterAll() throws Exception {
        DemoApp.tearDownApp();
    }

    @Test
    public void testLogin() throws Exception {
        // before login
        Assert.assertNotNull("Login page is not loaded.", loginPage);

        // after login
        HomePage homePage = (HomePage) DemoApp.login(user);
        Assert.assertNotNull("Home page is not loaded.", homePage);
    }
}
