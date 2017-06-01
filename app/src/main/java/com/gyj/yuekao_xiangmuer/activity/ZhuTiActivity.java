package com.gyj.yuekao_xiangmuer.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.adapter.RvItemAdapter;
import com.gyj.yuekao_xiangmuer.bean.InfoBean;
import com.gyj.yuekao_xiangmuer.bean.ZhuTiJuTiBean;
import com.gyj.yuekao_xiangmuer.util.GsonUtils;
import com.gyj.yuekao_xiangmuer.util.HttpUtils;
import com.gyj.yuekao_xiangmuer.util.Url;

import java.util.ArrayList;
import java.util.List;

/**
 * 类的用途：
 * Created by jinhu
 * 2017/5/30  19:27
 */

public class ZhuTiActivity extends Activity {
    private RecyclerView recycler_zhuti;
    private int mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_zhuti);
        initView();
        initData();
        getServerData();
    }

    private void initData() {
        Intent intent = getIntent();
        mId = intent.getIntExtra("id", 0);
        Toast.makeText(this, mId + "", Toast.LENGTH_SHORT).show();
    }

    private void getServerData() {
        HttpUtils.getEnqueue(Url.ZHU_TI_JT + "/" + mId, null, new HttpUtils.MyCallback() {

            @Override
            public void onSuccess(String result) {

                ZhuTiJuTiBean zhuTiJuTiBean = GsonUtils.gsonToBean(result, ZhuTiJuTiBean.class);
                List<ZhuTiJuTiBean.StoriesBean> stories = zhuTiJuTiBean.getStories();
                List<InfoBean> list = new ArrayList<InfoBean>();
                for (int i = 0; i < stories.size(); i++) {
                    InfoBean infoBean = new InfoBean();
                    infoBean.setText(stories.get(i).getTitle());
                    if (null != stories.get(i).getImages()) {
                        infoBean.setImage(stories.get(i).getImages().get(0));
                    }
                    list.add(infoBean);
                }
                initRv(list);
            }

            @Override
            public void onError(String errorMsg) {

            }
        });
    }

    private void initRv(List<InfoBean> list) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recycler_zhuti.setLayoutManager(layoutManager);
        RvItemAdapter adapter = new RvItemAdapter(this, list);
        recycler_zhuti.setAdapter(adapter);
    }

    private void initView() {
        recycler_zhuti = (RecyclerView) findViewById(R.id.recycler_zhuti);
    }
}
