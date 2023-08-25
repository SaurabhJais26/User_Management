package com.example.usermanagement.ModelResponse;

import com.google.gson.annotations.SerializedName;

public class DeleteAccountResponse {
    @SerializedName("status code")
    private String status;
    private String message;

    public DeleteAccountResponse(String status, String message) {
        this.status = status;
        this.message = message;
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
