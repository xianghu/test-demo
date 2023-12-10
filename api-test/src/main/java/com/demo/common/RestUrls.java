package com.demo.common;

/**
 * Class for all global constants
 */
public final class RestUrls {

    // Restful URL
    private static final String REST_ROOT = "https://gorest.co.in";
    public static final String CREATE_NEW_USER = REST_ROOT + "/public/v2/users";
    public static final String GET_USER_DETAIL = REST_ROOT + " /public/v2/users/";
    public static final String UPDATE_USER_DETAIL = REST_ROOT + "/public/v2/users/";
    public static final String DELETE_USER = REST_ROOT + "/public/v2/users/";

    private RestUrls() {
        throw new AssertionError();
    }

}