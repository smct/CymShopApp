package com.cym.cymshopapp.adapter;

import android.content.Context;

import java.util.List;

/**
 * ========================
 * CYM
 */
public abstract class SimpleAdapter<T> extends BaseAdapter<T,BaseViewHolder> {

    public SimpleAdapter(List<T> mDates, Context ct, int layoutResID) {
        super(mDates, ct, layoutResID);
    }


}
