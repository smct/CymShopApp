package com.cym.cymshopapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.widget.TextView;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class WaresAdapter extends SimpleAdapter<Wares> {

    public WaresAdapter(List<Wares> mDates, Context ct) {
        super(mDates, ct, R.layout.template_grid_wares);
    }

    @Override
    public void bindDates(BaseViewHolder holder, Wares wares) {
        TextView text_title = holder.getTextView(R.id.text_title);
        text_title.setText(wares.getName());
        holder.getTextView(R.id.text_price).setText("￥" + wares.getPrice());
        SimpleDraweeView  simpleDraweeView = (SimpleDraweeView) holder.getView(R.id.drawee_view);
        simpleDraweeView.setImageURI(Uri.parse(wares.getImgUrl()));
    }
}
