package com.gyj.yuekao_xiangmuer.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.activity.ZhuTiActivity;
import com.gyj.yuekao_xiangmuer.adapter.RvZtAdapter;
import com.gyj.yuekao_xiangmuer.bean.ZhuTiBean;
import com.gyj.yuekao_xiangmuer.util.GsonUtils;
import com.gyj.yuekao_xiangmuer.util.HttpUtils;
import com.gyj.yuekao_xiangmuer.util.Url;

import java.util.List;

/**
 * data:2017/6/1
 * author:郭彦君(Administrator)
 * function:
 */
public class Fragment4 extends Fragment {

    private RecyclerView recycler_zhuti;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment4_layout, null);
        mContext = getActivity();
        initView(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getServerData();

    }

    private void getServerData() {
        HttpUtils.getEnqueue(Url.ZHU_TI, null, new HttpUtils.MyCallback() {
            @Override
            public void onSuccess(String result) {
                ZhuTiBean zhuTiBean = GsonUtils.gsonToBean(result, ZhuTiBean.class);
                List<ZhuTiBean.OthersBean> others = zhuTiBean.getOthers();
                initRv(others);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    private void initRv(final List<ZhuTiBean.OthersBean> others) {
        GridLayoutManager layoutManager = new GridLayoutManager(mContext, 2);
        recycler_zhuti.setLayoutManager(layoutManager);
        RvZtAdapter adapter = new RvZtAdapter(mContext, others);
        recycler_zhuti.setAdapter(adapter);
        adapter.setmOnItemClickListener(new RvZtAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(mContext, ZhuTiActivity.class);
                intent.putExtra("id", others.get(position).getId());
                startActivity(intent);
            }
        });
    }

    private void initView(View view) {
        recycler_zhuti = (RecyclerView) view.findViewById(R.id.recycler_zhuti);
    }

}
