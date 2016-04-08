package com.cym.cymshopapp.frgment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.cym.cymshopapp.Contant;
import com.cym.cymshopapp.R;
import com.cym.cymshopapp.adapter.BaseAdapter;
import com.cym.cymshopapp.adapter.BaseViewHolder;
import com.cym.cymshopapp.adapter.HWAdapter;
import com.cym.cymshopapp.adapter.HotFragmentAdapter;
import com.cym.cymshopapp.adapter.decortion.DividerItemDecortion;
import com.cym.cymshopapp.bean.Page;
import com.cym.cymshopapp.bean.Wares;
import com.cym.cymshopapp.http.OkHttpHelper;
import com.cym.cymshopapp.http.SpotsCallBack;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class HotFragment extends Fragment {
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();
    private int curpage = 1;
    private int pageSize = 20;
    private List<Wares> mPageWares;
    @ViewInject(R.id.recyclerview)
    private RecyclerView mRecyclerView;
    @ViewInject(R.id.refresh_view)
    private MaterialRefreshLayout mRefreshLayout;
    private HWAdapter hotFragmentAdapter;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_REGRESH = 1;
    public static final int STATE_MORE = 2;
    public int state = STATE_NORMAL;
    private int totalCount;
    // private

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerview);
        mRefreshLayout = (MaterialRefreshLayout) view.findViewById(R.id.refresh_view);
        //     com.lidroid.xutils.ViewUtils.inject(view);

        getDate();
        initRereshLayout();
        return view;
    }

    public void initRereshLayout() {
        mRefreshLayout.setLoadMore(true);
        mRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                reFreshDate();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                lodaMore();
                if (curpage <= totalCount) {
                    lodaMore();
                    ;

                } else {
                    Toast.makeText(getContext(), "没有更多的数据了", 0).show();
                    mRefreshLayout.finishRefreshLoadMore();
                }
            }
        });

    }

    private void lodaMore() {
        state = STATE_MORE;
        curpage = ++curpage;
        getDate();
    }

    private void reFreshDate() {
        curpage = 1;
        state = STATE_REGRESH;
        getDate();

    }

    private void getDate() {
        // http://112.124.22.238:8081/course_api/wares/hot?curpage=1&pageSize=10;
        //  http://112.124.22.238:8081/course_api/wares/hot?curpage=1&pageSize=10
        String url = Contant.API.WARES_HOT + "?curPage=" + curpage + "&pageSize=" + pageSize;
        Log.i("HOTUrl:", url);
        mOkHttpHelper.get(url, new SpotsCallBack<Page<Wares>>(getActivity()) {


            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                mPageWares = waresPage.getList();
                totalCount = waresPage.getTotalCount();
                curpage = waresPage.getCurrentPage();
               showDate();
            }

            @Override
            public void onError(Response response, int code, Exception e) {

                Log.i("HOT:", "ONError");
            }
        });
    }

    private void showDate() {
        //判断状态
        switch (state) {
            case STATE_NORMAL:
              /*  mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), 1));
                hotFragmentAdapter = new HotFragmentAdapter(getContext(), mPageWares);
                mRecyclerView.setAdapter(hotFragmentAdapter);*/
             mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(), 1));
                hotFragmentAdapter = new HWAdapter(mPageWares,getContext());
                mRecyclerView.setAdapter(hotFragmentAdapter);
                hotFragmentAdapter.setOnitemsClickListner(new BaseAdapter.OnitemsClickListner() {
                    @Override
                    public void OnClick(View v, int positon) {
                        Toast.makeText(getContext(),"我被点击了"+positon,0).show();;
                    }
                });
                break;

            case STATE_REGRESH:
                hotFragmentAdapter.clearData();
                hotFragmentAdapter.addData(mPageWares);
                mRecyclerView.scrollToPosition(0);
                mRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                hotFragmentAdapter.addData(hotFragmentAdapter.getDatas().size(), mPageWares);
                mRecyclerView.scrollToPosition(hotFragmentAdapter.getDatas().size());
                mRefreshLayout.finishRefreshLoadMore();
                break;


            //   mRefreshLayout.
        }

    }
}