package com.demo.common;

/**
 * app user
 */
public class AppUser {

    private String accountName;
    private String password;
    private String name;

    public AppUser() {
        this.accountName = "TestUser_1";
        this.password = "Passw0de_1";
    }
  
    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
