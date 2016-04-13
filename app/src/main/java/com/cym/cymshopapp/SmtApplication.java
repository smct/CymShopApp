package com.cym.cymshopapp;

import android.app.Application;

import com.cym.cymshopapp.bean.User;
import com.cym.cymshopapp.utils.UserLocalData;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * ========================
 * CYM
 */
public class SmtApplication extends Application {
    private User mUser;
    private  static SmtApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mInstance=this;
        initUser();
        //初始化user 数据；
    }
    public static SmtApplication getInstance(){
        return   mInstance;
    }
    private void initUser(){
        this.mUser= UserLocalData.getUser(this);

    }
    public  User  getUser(){
        return  null;
    }
    public void  putUser(User user,String token){
        this.mUser=user;
        UserLocalData.putUser(user,this);
        UserLocalData.putToken(token,this);
    }
    public  void  clearAll(){
        this.mUser=null;
        UserLocalData.clearToken(this);
        UserLocalData.clearToken(this);
    }
}
