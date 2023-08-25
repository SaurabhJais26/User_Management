package com.example.usermanagement.ModelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FetchUserResponse {

    @SerializedName("users")
    List<User> userList;
    @SerializedName("status code")
    String status;

    public FetchUserResponse(List<User> userList, String status) {
        this.userList = userList;
        this.status = status;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
