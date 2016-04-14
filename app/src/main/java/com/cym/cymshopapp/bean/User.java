package com.cym.cymshopapp.bean;

import java.io.Serializable;

/**
 * ========================
 * CYM
 */
public class User implements Serializable {
  private  long  id;
    private String modi;
    private String email;
    private String username;
    private String logo_url;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogo_url() {
        return logo_url;
    }

    public void setLogo_url(String logo_url) {
        this.logo_url = logo_url;
    }

    public String getModi() {
        return modi;
    }

    public void setModi(String modi) {
        this.modi = modi;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", id=" + id +
                ", modi='" + modi + '\'' +
                ", username='" + username + '\'' +
                ", logo_url='" + logo_url + '\'' +
                '}';
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
