package com.cym.cymshopapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.cym.cymshopapp.bean.Tab;
import com.cym.cymshopapp.frgment.CartFragment;
import com.cym.cymshopapp.frgment.CategoryFragment;
import com.cym.cymshopapp.frgment.HomeFragment;
import com.cym.cymshopapp.frgment.HotFragment;
import com.cym.cymshopapp.frgment.MineFragment;
import com.cym.cymshopapp.widget.FragmentTabHost;

import java.util.ArrayList;
import java.util.List;

/***
 * FragmentTabHost+activity；显示整体布局；
 */
public class MainActivity extends AppCompatActivity {
    private LayoutInflater mInflater;
    private FragmentTabHost mFragmentTabHost;
    private List<Tab> tabsList = new ArrayList<>(5);
    private TabLayout mTabLayout;
    private CartFragment mCartFragment;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initTab();
        // mFragmentTabHost.addTab(tabSpec,);
        // TabLayout.Tab tab = mTabLayout.newTab();


    }

    private void initTab() {
        mInflater = LayoutInflater.from(this);
        mFragmentTabHost = (FragmentTabHost) findViewById(R.id.tabhost);
        mFragmentTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

        tabsList.add(new Tab(HomeFragment.class, "主页", R.drawable.selector_icon_home));
        tabsList.add(new Tab(HotFragment.class, "热门", R.drawable.selector_icon_hot));
       /* <string name="home">主页</string>
        <string name="hot">热卖</string>
        <string name="catagory">分类</string>
        <string name="cart">购物车</string>
        <string name="mine">我的</string>*/
        tabsList.add(new Tab(CategoryFragment.class, "分类", R.drawable.selector_icon_category));
        tabsList.add(new Tab(CartFragment.class, "购物车", R.drawable.selector_icon_cart));
        tabsList.add(new Tab(MineFragment.class, "我的", R.drawable.selector_icon_mine));
        initIndicator();
    }

    private void initIndicator() {
        for (Tab tabs : tabsList) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(tabs.getTitle());
            tabSpec.setIndicator(buildeIndicator(tabs));
            mFragmentTabHost.addTab(tabSpec, tabs.getFragment(), null);
        }
        //他的监听事件  因为是自己自定义的TaHost 所以
        mFragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                if (tabId == "购物车") {
                    cartfData();
                }
            }
        });
        //去掉分隔线  和默认选着第一个
        mFragmentTabHost.getTabWidget().setShowDividers(LinearLayoutCompat.SHOW_DIVIDER_NONE);
        mFragmentTabHost.setCurrentTab(0);
    }

    /***
     * 刷新购物车的数据
     */
    private void cartfData() {
        if (mCartFragment==null) {
            Fragment fragment = getSupportFragmentManager().findFragmentByTag("购物车");
            if (fragment!=null) {
                mCartFragment= (CartFragment) fragment;
                mCartFragment.refData();
            }

        }else {
            mCartFragment.refData();
        }
    }

    private View buildeIndicator(Tab tabs) {
        View view = mInflater.inflate(R.layout.tab_indicator, null);
        TextView tv = (TextView) view.findViewById(R.id.txt_indicator);
        ImageView iv = (ImageView) view.findViewById(R.id.icon_tab);
        //  tv.setText("主页");
        tv.setText(tabs.getTitle());
        // tv.setTextColor(r.C);
        System.out.println("title:" + tabs.getTitle());//
        //    iv.setBackgroundResource(tabs.getIcon());
        iv.setBackgroundResource(tabs.getIcon());
        System.out.println("图片：" + tabs.getIcon());
        return view;
    }


}
