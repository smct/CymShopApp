package com.cym.cymshopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cym.cymshopapp.adapter.BaseAdapter;
import com.cym.cymshopapp.adapter.HWAdapter;
import com.cym.cymshopapp.adapter.decortion.DividerItemDecoration;
import com.cym.cymshopapp.bean.Page;
import com.cym.cymshopapp.bean.Wares;
import com.cym.cymshopapp.utils.Pager;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class WareListActivity extends AppCompatActivity  implements Pager.OnPageListener<Wares>,TabLayout.OnTabSelectedListener,View.OnClickListener {
    @ViewInject(R.id.tab_layout)
    private TabLayout mTablayout;

    @ViewInject(R.id.recycler_view)
    private RecyclerView mRecyclerview_wares;

    @ViewInject(R.id.txt_summary)
    private TextView mTxtSummary;
    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout mRefreshLayout;

    public static final int TAG_DEFAULT = 0;
    public static final int TAG_SALE = 1;
    public static final int TAG_PRICE = 2;
    private int orderBy = 0;
    private long campaignId = 0;
    private HWAdapter mWaresAdapter;
    private Pager pager;
    ;
    public static final int ACTION_LIST=1;
    public static final int ACTION_GIRD=2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warelist);
        ViewUtils.inject(this);
        campaignId = getIntent().getLongExtra(Contant.COMPAINGAIN_ID, 0);
        initTab();
        getDate();
    }

    private void getDate() {
        pager= Pager.newBuilder().setUrl(Contant.API.WARES_CAMPAIN_LIST)
                .putParam("campaignId",campaignId)
                .putParam("orderBy",orderBy)
                .setRefreshLayout(mRefreshLayout)
                .setLoadMore(true)
                .setOnPageListener(this)
                .build(this,new TypeToken<Page<Wares>>(){}.getType());

        pager.request();
    }

    private void initTab() {
        TabLayout.Tab tab = mTablayout.newTab();
        tab.setText("默认");
        tab.setTag(TAG_DEFAULT);

        mTablayout.addTab(tab);


        tab = mTablayout.newTab();
        tab.setText("价格");
        tab.setTag(TAG_PRICE);

        mTablayout.addTab(tab);

        tab = mTablayout.newTab();
        tab.setText("销量");
        tab.setTag(TAG_SALE);

        mTablayout.addTab(tab);


      //  mTablayout.setOnTabSelectedListener(this);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void load(List<Wares> datas, int totalPage, int totalCount) {
        mTxtSummary.setText("共有"+totalCount+"件商品");
        if (mWaresAdapter == null) {
            mWaresAdapter = new HWAdapter(datas,this);
            mWaresAdapter.setOnitemsClickListner(new BaseAdapter.OnitemsClickListner() {
                @Override
                public void OnClick(View v, int positon) {
                            Wares wares = mWaresAdapter.getItem(positon);
                            Intent intent = new Intent(WareListActivity.this, WareDetailActivity.class);
                            intent.putExtra(Contant.WARE, wares);
                            startActivity(intent);
                        }
                    });
            mRecyclerview_wares.setAdapter(mWaresAdapter);
            mRecyclerview_wares.setLayoutManager(new LinearLayoutManager(this));
            mRecyclerview_wares.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
            mRecyclerview_wares.setItemAnimator(new DefaultItemAnimator());
        } else {
            //mWaresAdapter.refreshData(datas);
        }

    }

    @Override
    public void refresh(List<Wares> datas, int totalPage, int totalCount) {
      /*  mWaresAdapter.refreshData(datas);
        mRecyclerview_wares.scrollToPosition(0);*/
    }

    @Override
    public void loadMore(List<Wares> datas, int totalPage, int totalCount) {
            //  mWaresAdapter.load
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
