package com.cym.cymshopapp.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cym.cymshopapp.Contant;
import com.cym.cymshopapp.LoginActivity;
import com.cym.cymshopapp.R;
import com.cym.cymshopapp.SmtApplication;
import com.cym.cymshopapp.bean.User;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * ========================
 * CYM
 */
public class MineFragment extends Fragment {
    @Bind(R.id.circleImageView)
    CircleImageView mCircle_ImageView;
    @Bind(R.id.tv_login)
    TextView mTv_Login;
    @Bind(R.id.tv_address)
    TextView mTv_Address;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        showUser();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_login, R.id.tv_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_login:
                Intent  intent=new Intent(getActivity(),LoginActivity.class);
                startActivityForResult(intent, Contant.REQUEST_CODE);
                break;
            case R.id.tv_address:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
       // super.onActivityResult(requestCode, resultCode, data);
        showUser();
    }

    private void showUser() {
        User user = SmtApplication.getInstance().getUser();
        if (user != null) {
            mTv_Login.setText(user.getUsername());
            Picasso.with(getContext()).load(user.getLogo_url()).into(mCircle_ImageView);
        } else {
            mTv_Login.setText("请先登录");
        }
    }
}
