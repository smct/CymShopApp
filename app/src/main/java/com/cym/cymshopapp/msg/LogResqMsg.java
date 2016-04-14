package com.cym.cymshopapp.msg;

/**
 * ========================
 * CYM
 */
public class LogResqMsg<T> extends BaseRespMsg {
    private String token;
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LogResqMsg{" +
                "data=" + data +
                ", token='" + token + '\'' +
                '}';
    }
}
