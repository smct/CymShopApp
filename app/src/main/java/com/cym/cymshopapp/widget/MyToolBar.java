package com.cym.cymshopapp.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.internal.widget.TintTypedArray;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TextView;

import com.cym.cymshopapp.R;
/**
 * 自定 ToolBar  还有自定的属性
 * ========================
 * CYM
 */
public class MyToolBar extends Toolbar {
     TableLayout tableLayout;
    private View mView;
    private LayoutInflater mInflater;
    private TextView mTextTitle;
    private EditText mSearchView;
    private ImageButton mRightButtom;

    public MyToolBar(Context context) {
        this(context, null);
    }
    public MyToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public MyToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        //设置控件的左边和右边的距离
        setContentInsetsRelative(20, 20);
        //自己添加属性  读取属性
        if (attrs != null) {
            final TintTypedArray a = TintTypedArray.obtainStyledAttributes(getContext(), attrs,
                    R.styleable.MyToolBar, defStyleAttr, 0);
            final Drawable rightButtonIcon = a.getDrawable(R.styleable.MyToolBar_rightButtonIcon);
            if (rightButtonIcon != null) {
                setRightButtonIcon(rightButtonIcon);
                // setNavigationIcon(navIcon);写一个自己的方法
            }
            //判断是否显示  mSearchView
            boolean isShowSearchView = a.getBoolean(R.styleable.MyToolBar_isShowSearchViw, false);
            if (isShowSearchView) {
                showSearchView();
                hideTextView();
            }
            //回收
            a.recycle();
        }
    }
    /**
     * 设置右边的图标
     */
    private void setRightButtonIcon(Drawable icon) {
        if (icon != null) {
            mRightButtom.setImageDrawable(icon);
            mRightButtom.setVisibility(VISIBLE);
        }
    }

    private void initView() {
        if (mView == null) {
            mInflater = LayoutInflater.from(getContext());
            //这些空间都是gone的属性  android:visibility="gone"
            mView = mInflater.inflate(R.layout.toolbar, null);
            mTextTitle = (TextView) mView.findViewById(R.id.toolbar_title);
            mSearchView = (EditText) mView.findViewById(R.id.toolbar_searchview);
            mRightButtom = (ImageButton) mView.findViewById(R.id.toolbar_rightButton);
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER_HORIZONTAL);
            addView(mView, lp);
        }
    }
    /***
     * Button的点击事件
     *
     * @param ls
     */
    public void setRightButtonOnClick(OnClickListener ls) {

        mRightButtom.setOnClickListener(ls);
    }

    /**
     * 重写父类的方法
     *
     * @param resId
     */
    @Override
    public void setTitle(int resId) {
        setTitle(getContext().getText(resId));
        // super.setTitle(resId);
    }

    @Override
    public void setTitle(CharSequence title) {
        //  super.setTitle(title);
        initView();
        if (mTextTitle != null) {
            mTextTitle.setText(title);
            showTextTitle();
        }
    }

    public void showSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(VISIBLE);
        }
    }

    public void hideSearchView() {
        if (mSearchView != null) {
            mSearchView.setVisibility(GONE);
        }

    }

    public void showTextTitle() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(VISIBLE);
        }
    }

    public void hideTextView() {
        if (mTextTitle != null) {
            mTextTitle.setVisibility(GONE);
        }
    }
}
