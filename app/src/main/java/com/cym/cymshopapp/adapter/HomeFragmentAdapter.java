package com.cym.cymshopapp.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cym.cymshopapp.R;
import com.cym.cymshopapp.bean.Campaign;
import com.cym.cymshopapp.bean.HomeCampaign;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * ========================
 * CYM
 */
public class HomeFragmentAdapter extends RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder> {
    private List<HomeCampaign> mList;
    private LayoutInflater mInflater;
    private Context mContext;
    public static final int VIEW_TYPE_R = 0;
    public static final int VIEW_TYOE_L = 1;
    OnCampaigItemClick  onCampaigItemClick;

    public void setOnCampaigItemClick(OnCampaigItemClick onCampaigItemClick) {
        this.onCampaigItemClick = onCampaigItemClick;
    }

    public HomeFragmentAdapter(List<HomeCampaign> mList, Context context) {
        this.mList = mList;
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mInflater = LayoutInflater.from(parent.getContext());
        if (viewType == VIEW_TYPE_R) {
            return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview2, parent, false));
        }
        return new ViewHolder(mInflater.inflate(R.layout.template_home_cardview, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HomeCampaign homeCampaign = mList.get(position);
        holder.tv_title.setText(homeCampaign.getTitle());
        //   加载网络的图片holder.iv_Big.setImageDrawable();
        Picasso.with(mContext).load(homeCampaign.getCpOne().getImgUrl()).into(holder.iv_Big);
        Picasso.with(mContext).load(homeCampaign.getCpTwo().getImgUrl()).into(holder.iv_top);
        Picasso.with(mContext).load(homeCampaign.getCpThre().getImgUrl()).into(holder.iv_button);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (position % 2 == 0) {
            return VIEW_TYPE_R;
        } else {
            return VIEW_TYOE_L;
        }
      /*  return super.getItemViewType(position);*/
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_title;
        ImageView iv_top;
        ImageView iv_button;
        ImageView iv_Big;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.text_title);
            iv_Big = (ImageView) itemView.findViewById(R.id.imgview_big);
            iv_button = (ImageView) itemView.findViewById(R.id.imgview_small_bottom);
            ;
            iv_top = (ImageView) itemView.findViewById(R.id.imgview_small_top);
            iv_Big.setOnClickListener(this);
            iv_top.setOnClickListener(this);
            iv_button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
          /*  HomeCampaign  homeCampaign=mList.get(getLayoutPosition());
            switch (v.getId()) {
                case R.id.imgview_big:
                    if (onCampaigItemClick!=null) {
                        onCampaigItemClick.onItenClick(v,homeCampaign.getCpOne());
                    }
                    break;
                case R.id.imgview_small_top:
                    if (onCampaigItemClick!=null) {
                        onCampaigItemClick.onItenClick(v,homeCampaign.getCpTwo());
                    }
                    break;
                case  R.id.imgview_small_bottom:
                    if (onCampaigItemClick!=null) {
                        onCampaigItemClick.onItenClick(v,homeCampaign.getCpThre());
                    }
                    break;
            }
        }*/
            if (onCampaigItemClick!=null) {
                anim(v);
                //执行一个翻转的动画
            }

    }
    private  void anim(final View v){

        ObjectAnimator animator =  ObjectAnimator.ofFloat(v, "rotationX", 0.0F, 360.0F)
                .setDuration(200);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {

                HomeCampaign campaign = mList.get(getLayoutPosition());

                switch (v.getId()){

                    case  R.id.imgview_big:
                        onCampaigItemClick.onItenClick(v, campaign.getCpOne());
                        break;

                    case  R.id.imgview_small_top:
                        onCampaigItemClick.onItenClick(v, campaign.getCpTwo());
                        break;

                    case R.id.imgview_small_bottom:
                        onCampaigItemClick.onItenClick(v, campaign.getCpThre());
                        break;

                }

            }
        });
        animator.start();
    }
}

    public interface OnCampaigItemClick {
        public void onItenClick(View view, Campaign campaign);
    }
}
