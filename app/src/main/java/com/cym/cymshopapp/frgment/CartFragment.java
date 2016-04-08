package com.cym.cymshopapp.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.adapter.CarAdapter;
import com.cym.cymshopapp.adapter.decortion.DividerGridItemDecoration;
import com.cym.cymshopapp.bean.ShoppingCart;
import com.cym.cymshopapp.utils.CartProvider;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class CartFragment extends Fragment {
    @ViewInject(R.id.btn_del)
    private Button btn_select;
    @ViewInject(R.id.btn_order)
    private Button btn_order;
    @ViewInject(R.id.txt_total)
    private TextView tv_tital;
    @ViewInject(R.id.checkbox_all)
    private CheckBox mCheckBox;
    @ViewInject(R.id.recycler_vieww)
    private RecyclerView mRecyclerView;


    private CartProvider mCartProvider;
    private CarAdapter mCarAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ViewUtils.inject(this,view);
        mCartProvider = new CartProvider(getContext());
         showData();
        return view;
    }

    public void showData() {
        List<ShoppingCart> carts = mCartProvider.getAll();
        mCarAdapter = new CarAdapter(carts,getContext(), R.layout.template_cart,mCheckBox,tv_tital);
        mRecyclerView.setAdapter(mCarAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(getContext()),0);
    }
   public void   refData(){
        mCarAdapter.clearData();
       List<ShoppingCart> carts = mCartProvider.getAll();

       mCarAdapter.addData(carts);


   }
}
