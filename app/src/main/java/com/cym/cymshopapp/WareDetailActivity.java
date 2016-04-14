package com.cym.cymshopapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;

import com.cym.cymshopapp.bean.Wares;
import com.cym.cymshopapp.utils.CartProvider;

import java.io.Serializable;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import dmax.dialog.SpotsDialog;

/**
 * ========================
 * CYM
 */
public class WareDetailActivity extends AppCompatActivity {


    @Bind(R.id.webView)
    WebView mWebView;
    @Bind(R.id.share)
    Button btn_share;

    private Wares mWare;

    private WebAppInterface mAppInterfce;

    private CartProvider cartProvider;

    private SpotsDialog mDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ware_detail);
        ButterKnife.bind(this);
        Serializable serializable = getIntent().getSerializableExtra(Contant.WARE);
        if(serializable ==null)
            this.finish();

        mDialog = new SpotsDialog(this,"loading....");
        mDialog.show();


        mWare = (Wares) serializable;
        cartProvider = new CartProvider(this);

        initWebView();
    }

    private void initWebView() {
        WebSettings settings = mWebView.getSettings();

        settings.setJavaScriptEnabled(true);
        //默认是 true   是阻塞的  图片显示不出来的
        settings.setBlockNetworkImage(false);
        settings.setAppCacheEnabled(true);


        mWebView.loadUrl(Contant.API.WARES_DETAIL);
        Log.i("weburl", Contant.API.WARES_DETAIL);
        mAppInterfce = new WebAppInterface(this);
        mWebView.addJavascriptInterface(mAppInterfce, "appInterface");
        mWebView.setWebViewClient(new WC());

    }


    @OnClick(R.id.share)
    public void onClick() {
        showShare();
    }
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

// 分享时Notification的图标和文字  2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        // text是分享文本，所有平台都需要这个字段
        oks.setText(mWare.getName());

        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        oks.setImageUrl(mWare.getImgUrl());
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");

// 启动分享GUI
        oks.show(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);
    }

    private class WebAppInterface {
        private Context mContext;
        public WebAppInterface(Context context){
            mContext = context;
        }
        @JavascriptInterface
        public  void showDetail(){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.i("qqq",mWare.getId()+"");
                    mWebView.loadUrl("javascript:showDetail("+mWare.getId()+")");
            //        mWebView.loadUrl("javascript:showDetail("+mWare.getId()+")");

                }
            });
        }
        @JavascriptInterface
        public void buy(long id){
            cartProvider.put(mWare);
           // ToastUtils.show(mContext,"已添加到购物车");
            Toast.makeText(mContext,"已添加到购物车",0).show();
        }
        @JavascriptInterface
        public void addFavorites(long id){

        }
    }

    private class WC extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);


            if(mDialog !=null && mDialog.isShowing())
                mDialog.dismiss();

            mAppInterfce.showDetail();


        }
    }
}

