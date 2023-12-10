package com.demo.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

/**
 * User object
 */
public class User {

    private long id;
    private String name;
    private String email;
    private String gender;  // male, female
    private String status;  // active, inactive


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User() {
        this.name = "Test" + new Random().nextInt();
        this.email = name + "@demo.com";
        if (new Random().nextBoolean()) {
            this.gender = "male";
        } else {
            this.gender = "female";
        }
        if (new Random().nextBoolean()) {
            this.status = "active";
        } else {
            this.status = "inactive";
        }
    }

    public HashMap<String, Object> create() throws Exception {
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("name", name);
        request.put("email", email);
        request.put("gender", gender);
        request.put("status", status);

        HashMap<String, Object> response = HttpClient.call(RestUrls.CREATE_NEW_USER, "POST", request);
        if (response.get("message") == null) { // successful
            this.id = (long) response.get("id");
        }
        return response;
    }

    public HashMap<String, Object> get() throws Exception {
        Map<String, Object> request = new LinkedHashMap<>();

        return HttpClient.call(RestUrls.GET_USER_DETAIL + this.id, "GET", request);
    }

    public HashMap<String, Object> updateStatus(String newStatus) throws Exception {
        this.status = newStatus;
        Map<String, Object> request = new LinkedHashMap<>();
        request.put("status", this.status);

        return HttpClient.call(RestUrls.UPDATE_USER_DETAIL + this.id, "PUT", request);
    }

    public HashMap<String, Object> delete() throws Exception {
        Map<String, Object> request = new LinkedHashMap<>();

        return HttpClient.call(RestUrls.DELETE_USER + this.id, "DELETE", request);
    }
}
