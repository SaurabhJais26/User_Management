package com.example.usermanagement.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    User user;
    @SerializedName("status code")
    String status,message;

    public LoginResponse(User user, String status, String message) {
        this.user = user;
        this.status = status;
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
