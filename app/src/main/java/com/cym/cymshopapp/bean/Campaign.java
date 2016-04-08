package com.cym.cymshopapp.bean;

import java.io.Serializable;

/**
 * ========================
 * CYM
 */
public class Campaign  implements Serializable{
    private  long id;
    private  String  title;
    private String imgUrl;

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
