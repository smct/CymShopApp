package com.cym.cymshopapp.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ========================
 * CYM
 */
public class BaseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    //SparseArray 类似Java中的map集合
    private SparseArray<View> views;
    BaseAdapter.OnitemsClickListner onitemsClickListner;
    public BaseViewHolder(View itemView, BaseAdapter.OnitemsClickListner onitemsClickListner) {
        super(itemView);
        this.onitemsClickListner=onitemsClickListner;
        views = new SparseArray<>();

        itemView.setOnClickListener(this);
    }

    public View getView(int id) {
        return findView(id);
    }

    public TextView getTextView(int id) {
        return findView(id);

    }

    public ImageView getImageView(int id) {
        return findView(id);
    }
    public Button getButton(int id ){
        return  findView(id);
    }



    protected <T extends View> T findView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }
    @Override
    public void onClick(View v) {
        if (onitemsClickListner!=null) {
            onitemsClickListner.OnClick(v,getLayoutPosition());
        }
    }
}
