package com.cym.cymshopapp.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * ========================
 * CYM   改成单例模式
 */
public class OkHttpHelper {
    private static OkHttpHelper mInstance;
    private Handler mHandler;

    static {
        mInstance = new OkHttpHelper();
    }

    private OkHttpHelper() {

        mson = new Gson();

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);
        mHandler = new android.os.Handler(Looper.getMainLooper());
    }

    private Gson mson;
    private static OkHttpClient mHttpClient;


    public static OkHttpHelper getInstance() {

        return mInstance;
    }

    /**
     * @param url
     */
    public void get(String url, BaseCallBack baseCallBack) {
        Request request = builderRequest(url, null, HttpMethodType.GET);
        doRequest(request, baseCallBack);
    }

    /**
     * @param url
     */
    public void post(String url, Map<String, String> params, BaseCallBack baseCallBack) {
        Request request = builderRequest(url, params, HttpMethodType.POST);
        doRequest(request, baseCallBack);
    }

    /***
     * 构建request对象
     * 更具  是post 还是get的方式
     *
     * @param url
     * @param params
     * @param methodType
     * @return
     */
    private Request builderRequest(String url, Map<String, String> params, HttpMethodType methodType) {

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        if (methodType == HttpMethodType.GET) {
            builder.get();
        } else if (methodType == HttpMethodType.POST) {
            RequestBody body = builderBody(params);
            builder.post(body);
        }
        return builder.build();
    }

    /**
     * 创建post 所需要的参数
     *
     * @param params
     * @return
     */
    public RequestBody builderBody(Map<String, String> params) {
        FormEncodingBuilder formEncodingBuilder = new FormEncodingBuilder();
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                formEncodingBuilder.add(entry.getKey(), entry.getValue());
            }
        }
        return formEncodingBuilder.build();
    }

    public void doRequest(Request request, final BaseCallBack baseCallBack) {
        baseCallBack.onBeforeRequest(request);
        mHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                // baseCallBack.onFailure(request, e);
                failureCallBase(baseCallBack, request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                responseCallBase(baseCallBack, response);
                if (response.isSuccessful()) {
                    String gson = response.body().string();
                    Log.d("ok封装", gson);
                    if (baseCallBack.mType == String.class) {
                        //   baseCallBack.onSuccess(response, null);
                        scuessCallBase(baseCallBack, response, gson);
                    } else {
                        //如何解析错误 try   再调用
                        try {
                          /*  Type type = new TypeToken<List<Banner>>() { }.getType();
                            mBannerList = mGson.fromJson(s, type);*/
                            Object obj =   mson.fromJson(gson,baseCallBack.mType);
                            Log.d("ok封装1", obj.toString());
                            // baseCallBack.onSuccess(response, object);
                            scuessCallBase(baseCallBack, response, obj);
                        } catch (JsonParseException e) {
                            //   baseCallBack.onError(response, response.code(), e);
                            errorCallBase(baseCallBack, response, e);
                        }
                    }
                } else {
                    //  baseCallBack.onError(response, response.code(), null);
                    errorCallBase(baseCallBack,response, null);
                }
            }
        });
    }

    public void scuessCallBase(final BaseCallBack callBack, final Response response, final Object obj) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response,obj);
            }
        });
    }

    public void responseCallBase(final BaseCallBack callBack, final Response response) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onResponse(response);
            }
        });
    }

    public void failureCallBase(final BaseCallBack callBack, final Request req, final IOException e) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onFailure(req, e);
            }
        });
    }

    public void errorCallBase(final BaseCallBack baseCallBack, final Response resp, final JsonParseException ee) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onError(resp,resp.code(),ee);
            }
        });
    }

    enum HttpMethodType {
        GET, POST,
    }
}
