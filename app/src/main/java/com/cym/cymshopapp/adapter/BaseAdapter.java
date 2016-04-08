package com.cym.cymshopapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * adapter 的封装
 * 1 数据使用泛型
 * 2绑定数据使用抽闲方法来实现
 * 3ViewHolder中的view成员变量通过view的数组来实现；
 * 4 基类中提供常用的方法
 * ========================
 * CYM
 */
public abstract class BaseAdapter<T, H extends BaseViewHolder> extends RecyclerView.Adapter<BaseViewHolder> {
    protected List<T> mDates;
    protected LayoutInflater mInflater;

    public void setOnitemsClickListner(OnitemsClickListner onitemsClickListner) {
        this.onitemsClickListner = onitemsClickListner;
    }

    protected Context mContext;
    protected int mLayoutResId;
    public OnitemsClickListner onitemsClickListner;

    public BaseAdapter(List<T> mDates, Context ct, int layoutResID) {
        this.mDates = mDates;
        this.mContext = ct;
        this.mInflater = LayoutInflater.from(ct);
        this.mLayoutResId = layoutResID;

    }

    public interface OnitemsClickListner {
        public void OnClick(View v, int positon);
    }

    public void clearData() {
        mDates.clear();
        notifyItemRangeChanged(0, mDates.size());
    }

    public void addData(List<T> wares) {
        mDates.addAll(wares);
        notifyItemRangeChanged(0, wares.size());
    }

    public void addData(int position, List<T> wares) {
        mDates.addAll(wares);
        notifyItemRangeChanged(position, mDates.size());
    }

    public List<T> getDatas() {
        return mDates;
    }


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //items布局
        View view = mInflater.inflate(mLayoutResId, null, false);
        return new BaseViewHolder(view, onitemsClickListner);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        T t = getItem(position);
        bindDates(holder, t);
    }

    public T getItem(int position) {
        return mDates.get(position);
    }

    /**
     * 抽象方法子类实现
     *
     * @param holder
     * @param t
     */
    public abstract void bindDates(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return mDates.size();
    }


}
