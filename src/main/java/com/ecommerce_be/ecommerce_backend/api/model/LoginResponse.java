package com.ecommerce_be.ecommerce_backend.api.model;

import com.ecommerce_be.ecommerce_backend.model.LocalUser;

public class LoginResponse {

    private String jwt;
    private boolean success;

    private LocalUser user;

    public LocalUser getUser() {
        return user;
    }

    public void setUser(LocalUser user) {
        this.user = user;
    }




    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setFailureReason(String failureReason) {
        this.failureReason = failureReason;
    }

    private String failureReason;

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
