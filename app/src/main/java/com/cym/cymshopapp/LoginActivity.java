package com.cym.cymshopapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cym.cymshopapp.bean.User;
import com.cym.cymshopapp.http.OkHttpHelper;
import com.cym.cymshopapp.http.SpotsCallBack;
import com.cym.cymshopapp.msg.LogResqMsg;
import com.cym.cymshopapp.utils.DESUtils;
import com.cym.cymshopapp.utils.ToastUtils;
import com.squareup.okhttp.Response;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * ========================
 * CYM
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.et_phone)
    EditText mEt_Phone;
    @Bind(R.id.et_password)
    EditText mEt_Password;
    @Bind(R.id.btn_login_a)
    Button m_Btn_LoginA;
    @Bind(R.id.tv_regist)
    TextView mTv_Regist;
    @Bind(R.id.tv_nopassword)
    TextView mTv_Nopassword;
    private OkHttpHelper mHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mHelper = mHelper.getInstance();
    }

    @OnClick({R.id.btn_login_a, R.id.tv_regist, R.id.tv_nopassword})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login_a:
                String name = mEt_Phone.getText().toString().trim();
                String password = mEt_Password.getText().toString().trim();
                if (TextUtils.isEmpty(name) || TextUtils.isEmpty(password)) {
                    ToastUtils.show(this, "手机号或者是密码不能为空");
                    return;
                }

                requestLogin(name, password);
                break;
            case R.id.tv_regist:
                break;
            case R.id.tv_nopassword:
                break;
        }
    }

    private void requestLogin(String name, String password) {
        Map<String, String> params = new HashMap<>(2);
        params.put("phone", name);
        params.put("password", DESUtils.encrypt(password, Contant.DES_KEY));
       // mHelper.post(Contant.API.LOGIN,params,new );
        mHelper.post(Contant.API.LOGIN, params, new SpotsCallBack<LogResqMsg<User>>(this) {
            @Override
            public void onSuccess(Response response, LogResqMsg<User> userLogResqMsg) {
              //保存数据
                        String  s =userLogResqMsg.getData().toString();
                Log.i("aa",s);
                SmtApplication.getInstance().putUser(userLogResqMsg.getData(),userLogResqMsg.getToken());
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }
}
