package com.cym.cymshopapp.adapter;

import android.content.Context;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.Category;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class CategoryAdapter extends SimpleAdapter<Category> {
    public CategoryAdapter(List<Category> mDates, Context ct, int layoutResID) {
        super(mDates, ct, layoutResID);
    }

    @Override
    public void bindDates(BaseViewHolder holder, Category category) {
                 holder.getTextView(R.id.textView).setText(category.getName());
    }
}
