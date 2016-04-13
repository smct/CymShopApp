package com.cym.cymshopapp.frgment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
import com.cym.cymshopapp.WareDetailActivity;
import com.cym.cymshopapp.adapter.BaseAdapter;
import com.cym.cymshopapp.adapter.CategoryAdapter;
import com.cym.cymshopapp.adapter.WaresAdapter;
import com.cym.cymshopapp.adapter.decortion.DividerGridItemDecoration;
import com.cym.cymshopapp.adapter.decortion.DividerItemDecortion;
import com.cym.cymshopapp.bean.Banner;
import com.cym.cymshopapp.bean.Category;
import com.cym.cymshopapp.bean.Page;
import com.cym.cymshopapp.bean.Wares;
import com.cym.cymshopapp.http.BaseCallBack;
import com.cym.cymshopapp.http.OkHttpHelper;
import com.cym.cymshopapp.http.SpotsCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class CategoryFragment extends Fragment {
    @ViewInject(R.id.recyclerview_category)
    private RecyclerView mRecyclerView;

    @ViewInject(R.id.recyclerview_wares)
    private RecyclerView mRecyclerView_wares;
    private OkHttpHelper mOkHttpHelper = OkHttpHelper.getInstance();
    private List<Category> mCategories;
    @ViewInject(R.id.recyclerview_category)
    private CategoryAdapter mCategoryAdapter;
    @ViewInject(R.id.slider)
    private SliderLayout mSliderLayout;
    public static final int STATE_NORMAL = 0;
    public static final int STATE_REGRESH = 1;
    public static final int STATE_MORE = 2;
    public int state = STATE_NORMAL;
    private int curpage = 1;
    private int pageSize = 20;
    private int totalPage;
    @ViewInject(R.id.refresh_layout)
    private MaterialRefreshLayout materialRefreshLayout;
    private List<Wares> mWares;
    private WaresAdapter mWaresAdapter;
    private long categoryId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ViewUtils.inject(this, view);
        requestCategoryData();
        requestBannerData();
        initRereshLayout();
        return view;
    }

    public void requestCategoryData() {
        ;
        mOkHttpHelper.get(Contant.API.CATEGORY_LIST, new SpotsCallBack<List<Category>>(getContext()) {


            @Override
            public void onSuccess(Response response, List<Category> categories) {
                if (categories.size() >= 0) {
                    mCategories = categories;
                    showCategoryData(mCategories);
                }
                categoryId = categories.get(0).getId();
                requestWares(categoryId);
            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

    }

    public void showCategoryData(List<Category> mCategories) {
        mCategoryAdapter = new CategoryAdapter(mCategories, getContext(), R.layout.template_single_text);
        mRecyclerView.addItemDecoration(new DividerItemDecortion(getContext(), 1));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnitemsClickListner(new BaseAdapter.OnitemsClickListner() {
            @Override
            public void OnClick(View v, int positon) {
                Category category = mCategoryAdapter.getItem(positon);
                categoryId=category.getId();
                curpage=1;
                state=STATE_MORE;
                requestWares(categoryId);
                Log.i("categoryId被點擊了", categoryId + "");

            }
        });
    }

    private void requestBannerData() {

        mOkHttpHelper.get(Contant.API.BANNER + "?type=1", new SpotsCallBack<List<Banner>>(getContext()) {
            @Override
            public void onSuccess(Response response, List<Banner> banners) {

                Log.i("CallBack返回的数据", banners.get(0).getImgUrl());
                showSliderView(banners);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(getActivity(), "net exception", 0).show();
            }

        });
    }

    private void showSliderView(List<Banner> banners) {
        if (banners != null) {
            for (Banner banner : banners) {
                DefaultSliderView sliderView = new DefaultSliderView(this.getActivity());
                sliderView.image(banner.getImgUrl());
                sliderView.description(banner.getName());
                sliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(sliderView);


            }
            //设置圆点
            mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            mSliderLayout.setCustomAnimation(new DescriptionAnimation());
            //专场额效果 类似的动画；
            mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateDown);
            mSliderLayout.setDuration(3000);

        }
    }

    public void requestWares(long categoryId) {
        String url = Contant.API.WARES_LIST + "?categoryId=" + categoryId + "&curPage=" + curpage + "&pageSize=" + pageSize;
        Log.i("url+hot", url);
        mOkHttpHelper.get(url, new BaseCallBack<Page<Wares>>() {
            @Override
            //http://112.124.22.238:8081/course_api/wares/list?categoryId=1&curPage=1&pageSize=20
            public void onBeforeRequest(Request request) {

            }

            @Override
            public void onFailure(Request request, Exception e) {

            }

            @Override
            public void onResponse(Response response) {

            }

            @Override
            public void onSuccess(Response response, Page<Wares> waresPage) {
                curpage = waresPage.getCurrentPage();
                totalPage = waresPage.getTotalPage();
                showWaresDate(waresPage.getList());
            }


            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });
    }

    private void showWaresDate(final List<Wares> mWares) {
        //判断状态
        switch (state) {
            case STATE_NORMAL:

                if (mWaresAdapter==null) {
                    mRecyclerView_wares.addItemDecoration(new DividerGridItemDecoration(getContext()));
                    mWaresAdapter = new WaresAdapter(mWares, getContext());


                    mWaresAdapter.setOnitemsClickListner(new BaseAdapter.OnitemsClickListner() {
                        @Override
                        public void OnClick(View v, int positon) {
                            Toast.makeText(getContext(), "我被点击了" + positon, 0).show();
                            Wares wares = mWaresAdapter.getItem(positon);
                            Intent intent=new Intent(getActivity(), WareDetailActivity.class);
                            intent.putExtra(Contant.WARE,wares);
                            startActivity(intent);

                        }
                    });
                    mRecyclerView_wares.setAdapter(mWaresAdapter);
                    mRecyclerView_wares.setLayoutManager(new GridLayoutManager(getContext(), 2));

                }else {
                    mWaresAdapter.clearData();
                    mWaresAdapter.addData(mWares);
                }

                break;

            case STATE_REGRESH:
                mWaresAdapter.clearData();
                mWaresAdapter.addData(mWares);
                mRecyclerView_wares.scrollToPosition(0);
                materialRefreshLayout.finishRefresh();
                break;

            case STATE_MORE:
                mWaresAdapter.addData(mWaresAdapter.getDatas().size(), mWares);
                mRecyclerView_wares.scrollToPosition(mWaresAdapter.getDatas().size());
                materialRefreshLayout.finishRefreshLoadMore();
                break;
      //   mRefreshLayout.
        }

    }
    public void initRereshLayout() {
       materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                reFreshDate();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                lodaMore();
                if (curpage <= totalPage) {
                    lodaMore();
                    ;

                } else {
                    Toast.makeText(getContext(), "没有更多的数据了", 0).show();
                    materialRefreshLayout.finishRefreshLoadMore();
                }
            }
        });

    }

    private void lodaMore() {
        state = STATE_MORE;
        curpage = ++curpage;
        requestWares(categoryId);
    }

    private void reFreshDate() {
        curpage = 1;
        state = STATE_REGRESH;
        requestWares(categoryId);
    }
}