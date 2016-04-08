package com.cym.cymshopapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.ShoppingCart;
import com.cym.cymshopapp.bean.Wares;
import com.cym.cymshopapp.utils.CartProvider;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class HWAdapter extends SimpleAdapter<Wares> {
    //private ShoppingCart mShoppingCart;
    private CartProvider mCartProvider;

    public HWAdapter(List<Wares> mDates, Context ct) {
        super(mDates, ct, R.layout.template_hot_wares);
    //    mShoppingCart = new ShoppingCart();
        mCartProvider = new CartProvider(ct);

    }

    @Override
    public void bindDates(BaseViewHolder holder, final Wares wares) {
        TextView tv_price = (TextView) holder.getTextView(R.id.text_price);
        TextView tv_title = (TextView) holder.getTextView(R.id.text_title);
        Button bnt = (Button) holder.getView(R.id.btn_add);
        SimpleDraweeView mDrawwView = (SimpleDraweeView) holder.getView(R.id.drawee_view);

        tv_price.setText("$" + wares.getPrice() + "");
        tv_title.setText(wares.getName());
        mDrawwView.setImageURI(Uri.parse(wares.getImgUrl()));
        bnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    ShoppingCart  mShoppingCart= (ShoppingCart) wares;
            //    convertData(wares);
                mCartProvider.put(convertData(wares));
                Toast.makeText(mContext,"已经添加到购物车中了",0).show();
            }
        });
    }


    private ShoppingCart convertData(Wares wares) {
        ShoppingCart cart = new ShoppingCart();
        cart.setId(wares.getId());
        cart.setDescription(wares.getDescription());
        cart.setImgUrl(wares.getImgUrl());
        cart.setPrice(wares.getPrice());
        cart.setName(wares.getName());
        return cart;
    }
}
