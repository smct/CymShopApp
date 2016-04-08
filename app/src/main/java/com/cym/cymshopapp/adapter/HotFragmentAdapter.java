package com.cym.cymshopapp.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.Wares;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class HotFragmentAdapter extends RecyclerView.Adapter<HotFragmentAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    private List<Wares> mDates;


    public HotFragmentAdapter(Context mContext, List<Wares> mPageWares) {
        this.mContext = mContext;
        this.mDates = mPageWares;
        mInflater = LayoutInflater.from(mContext);
    }

    public void clearData() {
        mDates.clear();
        notifyItemRangeChanged(0, mDates.size());
    }

    public void addData(List<Wares> wares) {
        mDates.addAll(wares);
        notifyItemRangeChanged(0, wares.size());
    }

    public void addData(int position, List<Wares> wares) {
        mDates.addAll(wares);
        notifyItemRangeChanged(position, mDates.size());
    }

    public List<Wares> getDatas() {
        return mDates;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(mInflater.inflate(R.layout.template_hot_wares, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Wares wares = mDates.get(position);
        holder.tv_price.setText("$" + wares.getPrice() + "");
        holder.tv_wareTitle.setText(wares.getName());
        holder.mDrawwView.setImageURI(Uri.parse(wares.getImgUrl()));

    }


    @Override
    public int getItemCount() {
        if (mDates.size() > 0) {
            return mDates.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        SimpleDraweeView mDrawwView;
        TextView tv_wareTitle;
        TextView tv_price;
        Button bnt_add;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_price = (TextView) itemView.findViewById(R.id.text_price);
            tv_wareTitle = (TextView) itemView.findViewById(R.id.text_title);
            bnt_add = (Button) itemView.findViewById(R.id.btn_add);
            mDrawwView = (SimpleDraweeView) itemView.findViewById(R.id.drawee_view);
            bnt_add.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {

        }
    }

}
