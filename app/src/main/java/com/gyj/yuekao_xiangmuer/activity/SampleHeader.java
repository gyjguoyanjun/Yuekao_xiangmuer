package com.gyj.yuekao_xiangmuer.activity;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.bean.ZuiXinBean;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cundong on 2015/10/9.
 * <p/>
 * RecyclerView的HeaderView，简单的展示一个TextView
 */
public class SampleHeader extends RelativeLayout {

    private List<ZuiXinBean.TopStoriesBean> imaList;
    private Banner banner;

    public SampleHeader(Context context) {
        super(context);
        init(context);
    }

    public SampleHeader(Context context, ArrayList<ZuiXinBean.TopStoriesBean> imaList) {
        super(context);
        this.imaList = imaList;
        init(context);
    }

    public SampleHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SampleHeader(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context) {
        View view = inflate(context, R.layout.sample_header, this);
         banner = (Banner) view.findViewById(R.id.banner);
    }

    public void initBanner(List<ZuiXinBean.TopStoriesBean> top_stories) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (int i = 0; i < top_stories.size(); i++) {
            images.add(top_stories.get(i).getImage());
            titles.add(top_stories.get(i).getTitle());
        }
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }


}