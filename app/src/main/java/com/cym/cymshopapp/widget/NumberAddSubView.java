package com.cym.cymshopapp.widget;


import android.content.Context;
import android.support.v7.internal.widget.TintTypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cym.cymshopapp.R;

/**
 * ========================
 * CYM
 * 自定义数字加减控件
 * 1输入框输入的只能是数字而且不能通过键盘输入
 * 2控制数字的加减
 * 3监听加减的监听
 * 4有一个最大最小值
 * 5 自定义属性v
 */
public class NumberAddSubView extends LinearLayout implements OnClickListener {
    private LayoutInflater mInflater;
    private Button mBtn_add;
    private Button mBtn_sub;
    private TextView mTvSum;
    private int value;
    private int maxValue;
    private int minValue;

    public NumberAddSubView(Context context) {
       this(context, null);

    }

    public NumberAddSubView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
     ;
    }

    public NumberAddSubView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mInflater = LayoutInflater.from(context);
        initView();
        //读取自定义的属性
        if (attrs != null) {
            TintTypedArray a = TintTypedArray.obtainStyledAttributes(
                    context, attrs,
                    R.styleable.NumberAddSubView, defStyleAttr, 0);
            int val = a.getInt(R.styleable.NumberAddSubView_values, 0);
            setValue(val);

            int valMax = a.getInt(R.styleable.NumberAddSubView_maxValue, 0);
            setMaxValue(valMax);

            int valMin = a.getInt(R.styleable.NumberAddSubView_minValue, 0);
            setMinValue(valMin);
            a.recycle();
        }
    }

    public void initView() {
        View view = mInflater.inflate(R.layout.widet_num_add_sub, this, true);
        mTvSum = (TextView) view.findViewById(R.id.etxt_num);
        mBtn_add = (Button) view.findViewById(R.id.btn_add);
        mBtn_sub = (Button) view.findViewById(R.id.btn_sub);
        mBtn_add.setOnClickListener(this);
        mBtn_sub.setOnClickListener(this);

    }


    public int getValue() {
        String values = mTvSum.getText().toString();
        if (values != null) {
            this.value = Integer.parseInt(values);
        }
        return value;
    }

    public void setValue(int value) {
        mTvSum.setText(value+"");
        this.value = value;
    }

    public int getMinValue() {
        return minValue;
    }

    public void setMinValue(int minValue) {
        this.minValue = minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
    }

    public interface OnBntClickListner {
        public void addBtn(View view, int values);

        public void subBtn(View view, int values);
    }

    public OnBntClickListner mOnBntClickListner;

    public void setmOnBntClickListner(OnBntClickListner mOnBntClickListner) {
        this.mOnBntClickListner = mOnBntClickListner;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                add();
                if (mOnBntClickListner != null) {
                    mOnBntClickListner.addBtn(v, value);
                }
                break;
            case R.id.btn_sub:
                sub();
                if (mOnBntClickListner != null) {
                    mOnBntClickListner.subBtn(v, value);
                }
                break;
        }
    }

    public void add() {
        this.value=getValue();
        if (value < maxValue) {
            value = value + 1;
            mTvSum.setText(value + "");
        }
    }

    public void sub() {
        this.value=getValue();
        if (value > minValue) {
            value = value - 1;
            mTvSum.setText(value + "");
        }
    }

    ;
}
