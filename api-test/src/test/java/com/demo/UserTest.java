package com.demo;

import com.demo.common.User;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.*;

/**
 * test user
 */
public class UserTest {
    private User user;

    @BeforeClass
    public static void oneTimeSetUp() {
    }

    @Before
    public void setUp() {
        user = new User();
    }

    @Test
    public void testUserCreate() throws Exception {
        HashMap<String, Object> resultMap = user.create();

        Assert.assertEquals("name wrong", user.getName(), resultMap.get("name"));
        Assert.assertEquals("email wrong", user.getEmail(), resultMap.get("email"));
        Assert.assertEquals("gender wrong", user.getGender(), resultMap.get("gender"));
        Assert.assertEquals("status wrong", user.getStatus(), resultMap.get("status"));

        // create again
        resultMap = user.create();
        Assert.assertEquals("should failed with code 422", 422, resultMap.get("code"));
    }

    @Test
    public void testUserUpdateStatus() throws Exception {
        user.create();

        String newStatus;
        if ("active".equals(user.getStatus())) {
            newStatus = "inactive";
        } else {
            newStatus = "active";
        }

        HashMap<String, Object> resultMap = user.updateStatus(newStatus);
        Assert.assertEquals("status wrong", user.getStatus(), resultMap.get("status"));
     }

    @After
    public void tearDown() throws Exception {
        user.delete();
    }
}
