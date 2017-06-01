package com.gyj.yuekao_xiangmuer.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cundong.recyclerview.HeaderAndFooterRecyclerViewAdapter;
import com.cundong.recyclerview.HeaderSpanSizeLookup;
import com.cundong.recyclerview.RecyclerViewUtils;
import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.activity.SampleHeader;
import com.gyj.yuekao_xiangmuer.adapter.DataAdapter;
import com.gyj.yuekao_xiangmuer.bean.InfoBean;
import com.gyj.yuekao_xiangmuer.bean.ZuiXinBean;
import com.gyj.yuekao_xiangmuer.bean.ZuiXinMoreBean;
import com.gyj.yuekao_xiangmuer.util.GsonUtils;
import com.gyj.yuekao_xiangmuer.util.HttpUtils;
import com.gyj.yuekao_xiangmuer.util.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * data:2017/6/1
 * author:郭彦君(Administrator)
 * function:
 */
public class Fragment1 extends Fragment {
    private SwipeRefreshLayout swipe_refresh_layout;
    private View view;
    private RecyclerView mRecyclerView = null;
    private DataAdapter mDataAdapter = null;
    private ArrayList<ZuiXinBean.StoriesBean> stories;
    private HeaderAndFooterRecyclerViewAdapter mHeaderAndFooterRecyclerViewAdapter = null;
    boolean isLoading;
    Handler mHandler = new Handler();
    private GridLayoutManager manager;
    private List<InfoBean> mList = new ArrayList<InfoBean>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment1_layout, null);
        swipe_refresh_layout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.f1_rv);
        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListener();
        setData();
    }

    private void setData() {
        HttpUtils.getEnqueue(Url.ZUI_XIN, null, new HttpUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                ZuiXinBean zuiXinBean = GsonUtils.gsonToBean(result, ZuiXinBean.class);
                Log.d("ssssssssss", result);
                List<ZuiXinBean.TopStoriesBean> top_stories = zuiXinBean.getTop_stories();
                stories = (ArrayList<ZuiXinBean.StoriesBean>) zuiXinBean.getStories();
                Log.d("zzzzzzzzz", stories.size() + "");
                for (int i = 0; i < stories.size(); i++) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setImage(stories.get(i).getImages().get(0));
                    infoBean.setText(stories.get(i).getTitle());
                    mList.add(infoBean);
                }

                manager = new GridLayoutManager(getActivity(), 1);
                mRecyclerView.setLayoutManager(manager);
                mDataAdapter = new DataAdapter(getActivity(), (ArrayList<InfoBean>) mList);
                mRecyclerView.setAdapter(mDataAdapter);
                mHeaderAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mDataAdapter);
                mRecyclerView.setAdapter(mHeaderAndFooterRecyclerViewAdapter);
                manager.setSpanSizeLookup(new HeaderSpanSizeLookup((HeaderAndFooterRecyclerViewAdapter) mRecyclerView.getAdapter(), manager.getSpanCount()));
                SampleHeader sampleHeader = new SampleHeader(getActivity());
                sampleHeader.initBanner(top_stories);
                RecyclerViewUtils.setHeaderView(mRecyclerView, sampleHeader);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });

    }

    private void setListener() {
        swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipe_refresh_layout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.clear();
                        setData();
                        swipe_refresh_layout.setRefreshing(false);
                    }
                }, 2000);
            }
        });
        //
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.d("test", "StateChanged = " + newState);


            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = manager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition +1 ==  mDataAdapter.getItemCount()) {
                    Toast.makeText(getActivity(), "加载更多", Toast.LENGTH_SHORT).show();
                    if (!isLoading) {
                        isLoading = true;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //加载
                                getServerData2();
                                isLoading = false;
                            }
                        }, 1000);
                    }
                }
            }
        });
    }


    private void getServerData2() {
        HttpUtils.getEnqueue(Url.ZUI_XIN_MORE, null, new HttpUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                ZuiXinMoreBean zuiXinMoreBean = GsonUtils.gsonToBean(result, ZuiXinMoreBean.class);
                List<ZuiXinMoreBean.StoriesBean> stories = zuiXinMoreBean.getStories();
                for (int i = 0; i < stories.size(); i++) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setImage(stories.get(i).getImages().get(0));
                    infoBean.setText(stories.get(i).getTitle());
                    mList.add(infoBean);
                }
                mDataAdapter.notifyDataSetChanged();
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }
}
