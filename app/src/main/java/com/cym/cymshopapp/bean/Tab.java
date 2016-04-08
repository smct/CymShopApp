package com.cym.cymshopapp.bean;

/**
 * ========== ==============
 * CYM
 */
public class Tab {
    private String title;
    private int icon;
    private Class fragment;

    public Class getFragment() {
        return fragment;
    }

    public void setFragment(Class fragment) {
        this.fragment = fragment;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Tab{" +
                "fragment=" + fragment +
                ", title='" + title + '\'' +
                ", icon=" + icon +
                '}';
    }

    public Tab(Class fragment, String title, int icon) {
        this.fragment = fragment;
        this.title = title;
        this.icon = icon;
    }
}