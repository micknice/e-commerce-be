package com.ecommerce_be.ecommerce_backend.api.model;

public class DataChange<T> {

    private T data;
    private ChangeType changeType;

    public DataChange() {

    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public DataChange(ChangeType changeType, T data) {
        this.changeType = changeType;
        this.data = data;
    }

    public enum ChangeType {
        INSERT,
        UPDATE,
        DELETE
    }
}
