package com.cym.cymshopapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.ShoppingCart;
import com.cym.cymshopapp.utils.CartProvider;
import com.cym.cymshopapp.widget.NumberAddSubView;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class CarAdapter extends SimpleAdapter<ShoppingCart> implements BaseAdapter.OnitemsClickListner {
    CheckBox mCheckBox;
    private TextView tv_total;
    private CartProvider mCartProvider;

    public CarAdapter(List<ShoppingCart> mDates, Context ct, int layoutResID, CheckBox mCheckBox, TextView tv_tital) {
        super(mDates, ct, layoutResID);
        mCartProvider = new CartProvider(ct);
        this.mCheckBox = mCheckBox;
        this.tv_total = tv_tital;
        showTotalPrice();
        setOnitemsClickListner(this);
        setChecked();

    }

    private void setChecked() {
        mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                checkAll(mCheckBox.isChecked());
            }
        });
    }

    private void checkAll(boolean checked) {
        int i = 0;
        for (ShoppingCart cart : mDates) {
            cart.setIsChecked(checked);
            notifyItemChanged(i);
            i++;
        }
        showTotalPrice();
    }



    @Override
    public void bindDates(BaseViewHolder holder, final ShoppingCart shoppingCart) {
        TextView text_price = holder.getTextView(R.id.text_price);
        TextView text_title = holder.getTextView(R.id.text_title);
        NumberAddSubView numberAddSubView = (NumberAddSubView) holder.getView(R.id.num_control);
        SimpleDraweeView simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);

        CheckBox checBox = (CheckBox) holder.getView(R.id.checkbox);
        text_price.setText(shoppingCart.getPrice() + "");
        text_title.setText(shoppingCart.getName());
        simpleDraweeView.setImageURI(Uri.parse(shoppingCart.getImgUrl()));
        checBox.setChecked(shoppingCart.isChecked());
        numberAddSubView.setValue(shoppingCart.getCount());
        numberAddSubView.setmOnBntClickListner(new NumberAddSubView.OnBntClickListner() {
            @Override
            public void addBtn(View view, int values) {
                shoppingCart.setCount(values);
                mCartProvider.update(shoppingCart);
                showTotalPrice();
            }

            @Override
            public void subBtn(View view, int values) {
                shoppingCart.setCount(values);
                mCartProvider.update(shoppingCart);
                showTotalPrice();
            }
        });
    }

    private void showTotalPrice() {
        getTotalPrice();
    }

    /**
     *  整个回调点击事件
     *
     * @param v
     * @param positon
     */
    @Override
    public void OnClick(View v, int positon) {
        ShoppingCart item = getItem(positon);
        item.setIsChecked(!item.isChecked());
        notifyItemChanged(positon);
        //  改变下面的CHeckBox的状态
        checkListen();
        showTotalPrice();
    }

    /**
     * 改变下面的CHeckBox的状态
     */
    private void checkListen() {
        if (mDates.size()>0) {
            int numBer=mDates.size();
            int  checkNumber=0;
            for (ShoppingCart  cart:mDates){
                if (cart.isChecked()){
                    checkNumber++;
                }
            }
            if (numBer==checkNumber){
                mCheckBox.setChecked(true);
                showTotalPrice();
            }else{
                mCheckBox.setChecked(false);
                showTotalPrice();
            }
        }
    }

    public void getTotalPrice() {
        float  sum=0;
        for (ShoppingCart cart : mDates) {
            if (cart.isChecked()) {
               sum+=cart.getPrice()*cart.getCount();
            }
        }
        tv_total.setText("$"+sum+"");
        tv_total.setTextColor(Color.RED);
    }
}
