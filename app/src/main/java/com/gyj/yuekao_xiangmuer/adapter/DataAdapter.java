package com.gyj.yuekao_xiangmuer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gyj.yuekao_xiangmuer.R;
import com.gyj.yuekao_xiangmuer.bean.InfoBean;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

/**
 * data:2017/6/1
 * author:郭彦君(Administrator)
 * function:
 */
public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private LayoutInflater mLayoutInflater;
    private Context context;
    private ArrayList<InfoBean> mDataList;

    public DataAdapter(Context context, ArrayList<InfoBean> mDataList) {
        this.context = context;
        this.mDataList = mDataList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textView.setText(mDataList.get(position).getText());
        ImageLoader.getInstance().displayImage(mDataList.get(position).getImage(), holder.imageView);
    }


    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.info_text);
            imageView = (ImageView) itemView.findViewById(R.id.f1_image);

        }
    }

}
