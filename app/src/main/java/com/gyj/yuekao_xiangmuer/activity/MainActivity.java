package com.gyj.yuekao_xiangmuer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.adapter.MyPagerAdapter;
import com.gyj.yuekao_xiangmuer.fragment.Fragment1;
import com.gyj.yuekao_xiangmuer.fragment.Fragment2;
import com.gyj.yuekao_xiangmuer.fragment.Fragment3;
import com.gyj.yuekao_xiangmuer.fragment.Fragment4;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    private void initData() {
        ArrayList<Fragment> fList = new ArrayList<>();
        ArrayList<String> sList = new ArrayList<>();

        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();
        Fragment4 fragment4 = new Fragment4();
        fList.add(fragment1);
        fList.add(fragment2);
        fList.add(fragment3);
        fList.add(fragment4);

        sList.add("最新日报");
        sList.add("专栏");
        sList.add("热门");
        sList.add("主题日报");


        MyPagerAdapter mAdapter = new MyPagerAdapter(getSupportFragmentManager(), MainActivity.this, fList, sList);
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        mTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器


    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("首页");
    }
}
