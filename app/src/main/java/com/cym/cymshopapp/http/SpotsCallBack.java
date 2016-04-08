package com.cym.cymshopapp.http;

import android.content.Context;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import dmax.dialog.SpotsDialog;

/**
 * ========================
 * CYM
 */
public abstract class SpotsCallBack <T>extends BaseCallBack<T> {
    private SpotsDialog mDialog;
    private Context mContent;
    public  SpotsCallBack(Context ct){
        this.mContent=ct;
        mDialog=new SpotsDialog(mContent,"  玩命加载中");

    }
    public void ShoweDialog(){
        if (mDialog!=null) {
            mDialog.show();
        }
    }
    public void dismissDailog(){
        if (mDialog!=null) {
            mDialog.dismiss();
        }
    }
    @Override
    public void onBeforeRequest(Request request) {
                   ShoweDialog();
    }

    @Override
    public void onFailure(Request request, Exception e) {
      //  dismissDailog();

    }

    /***
     * 这个方法是 成功的时候   但是不是数据不一定过有的
     * @param response
     */
    @Override
    public void onResponse(Response response) {
        dismissDailog();
    }


    //这两个方法由其子类去实现

  /*  @Override
    public void onSuccess(Response response, Object o) {

    }

    @Override
    public void onError(Response response, int code, Exception e) {

    }*/
}
