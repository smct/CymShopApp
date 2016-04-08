package com.cym.cymshopapp.frgment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cym.cymshopapp.Contant;
import com.cym.cymshopapp.R;
import com.cym.cymshopapp.adapter.HomeFragmentAdapter;
import com.cym.cymshopapp.adapter.decortion.DividerItemDecortion;
import com.cym.cymshopapp.bean.Banner;
import com.cym.cymshopapp.bean.Campaign;
import com.cym.cymshopapp.bean.HomeCampaign;
import com.cym.cymshopapp.http.BaseCallBack;
import com.cym.cymshopapp.http.OkHttpHelper;
import com.cym.cymshopapp.http.SpotsCallBack;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.util.ArrayList;
import java.util.List;


/**
 * ========================
 * CYM
 */
public class HomeFragment extends Fragment {
    private SliderLayout mSliderLayout;
    private RecyclerView mRecyclerView;
    private Gson mGson = new Gson();
   private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                mSliderLayout.addSlider(textSliderView);
                mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                mSliderLayout.setCustomAnimation(new DescriptionAnimation());

                mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
                mSliderLayout.setDuration(3000);
            }
        }
    };
    private List<Banner> mBannerList = new ArrayList<>();
    private OkHttpHelper okHttpHelper = OkHttpHelper.getInstance();
    private TextSliderView textSliderView;
    private HomeFragmentAdapter mHomeFragmentAdapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        //  RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.w);
        mSliderLayout = (SliderLayout) view.findViewById(R.id.slider);
   //   requestImage();
        // initSlier();
     //  initRecyclerView(view);
        return view;
    }

    private void initRecyclerView(View view) {
      mRecyclerView = (RecyclerView) view.findViewById(R.id.home_recyclerView);

        okHttpHelper.get(Contant.API.CAMPAIGN_HOME, new BaseCallBack<List<HomeCampaign>>() {
            @Override
            public void onBeforeRequest(Request request) {
            }

            @Override
            public void onFailure(Request request, Exception e) {
            }

            @Override
            public void onResponse(Response response) {
            }

            @Override
            public void onSuccess(Response response, List<HomeCampaign> homeCampaigns) {

                initRecyclerDate(homeCampaigns);
            }

            @Override
            public void onError(Response response, int code, Exception e) {
            }
        });


    }

    private void initRecyclerDate(List<HomeCampaign> homeCampaigns) {
        mHomeFragmentAdapter = new HomeFragmentAdapter(homeCampaigns,getActivity());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //这是风格县
        mRecyclerView.addItemDecoration(new DividerItemDecortion(getActivity(),1));
        mRecyclerView.setAdapter(mHomeFragmentAdapter);
        //  点击事假
        mHomeFragmentAdapter.setOnCampaigItemClick(new HomeFragmentAdapter.OnCampaigItemClick() {
            @Override
            public void onItenClick(View view, Campaign campaign) {
                Toast.makeText(getActivity(),campaign.getTitle(),0).show();
            }
        });
    }

    private void initSlier() {
        if (mBannerList != null) {
            for (Banner banner : mBannerList) {
                textSliderView = new TextSliderView(this.getActivity());
                textSliderView.image(banner.getImgUrl());
                textSliderView.description(banner.getName());
                textSliderView.setScaleType(BaseSliderView.ScaleType.Fit);
                mSliderLayout.addSlider(textSliderView);

             handler.sendEmptyMessage(0);

               /* textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                    @Override
                    public void onSliderClick(BaseSliderView slider) {
                        //图片的点击事件
                    }
                });*/
            }

        }

        //设置圆点
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        //专场额效果 类似的动画；
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.RotateUp);
        mSliderLayout.setDuration(3000);
        //设置点击事件
      /*  mSliderLayout.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });*/
    }


    private void requestImage() {
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
        okHttpHelper.get(url, new SpotsCallBack<List<Banner>>(getContext()){
            @Override
            public void onSuccess(Response response, List<Banner> banners) {

                    Log.i("CallBack返回的数据",banners.get(0).getImgUrl());

                 initSlier();
            }
            @Override
            public void onError(Response response, int code, Exception e) {
                Toast.makeText(getActivity(), "net exception", 0).show();
            }

        });
        /*OkHttpClient client = new OkHttpClient();
        String url = "http://112.124.22.238:8081/course_api/banner/query?type=1";
        RequestBody body = new FormEncodingBuilder()
                .add("type", "1")
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)//post//方法请求数据
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
               // Toast.makeText(getActivity(), "net exception", 0).show();
            }

            @Override
            public void onResponse(Response response) throws IOException {
                if (response.isSuccessful()) {
                    String s = response.body().string();
                    Log.i("网络请求的数据", s);
                  //  Log.i("请求数据的封装", mBannerList.get(0).getImgUrl());
                    Type type = new TypeToken<List<Banner>>() {
                    }.getType();
                    mBannerList = mGson.fromJson(s, type);
                    initSlier();
                }
            }
        });*/

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSliderLayout.stopAutoCycle();
    }
}
