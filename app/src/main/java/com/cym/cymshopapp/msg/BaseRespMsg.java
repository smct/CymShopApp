package com.cym.cymshopapp.msg;

import java.io.Serializable;

/**
 * ========================
 * CYM
 */
public class BaseRespMsg implements Serializable {
    private static final int STATE_SUCCESS = 1;
    private static final int STATE_ERROR = 0;
    private static final String MSG_SUCCESS = "sucess";
    protected int state=STATE_SUCCESS;
    protected String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "BaseRespMsg{" +
                "message='" + message + '\'' +
                ", state=" + state +
                '}';
    }
}
