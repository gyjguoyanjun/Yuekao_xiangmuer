package com.gyj.yuekao_xiangmuer.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * data:2017/6/1
 * author:郭彦君(Administrator)
 * function:
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private ArrayList<Fragment> fList;
    private ArrayList<String> sList;

    public MyPagerAdapter(FragmentManager fm, Context context, ArrayList<Fragment> fList, ArrayList<String> sList) {
        super(fm);
        this.context = context;
        this.fList = fList;
        this.sList = sList;
    }

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fList.get(position);
    }

    @Override
    public int getCount() {
        return fList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return sList.get(position);//页卡标题
    }
}
