package com.alex.vkdemo.adapter;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

import com.alex.vkdemo.R;
import com.alex.vkdemo.dummy.VkNewsItem;

import java.util.List;

/**
 * Created by marat on 28.02.15.
 */
public class VkNewsAdapter extends ArrayAdapter<VkNewsItem> {
    int layout;
    LayoutInflater layoutInflater;

    public VkNewsAdapter(Context context,  List<VkNewsItem> items) {
        super(context,R.layout.news_item,android.R.id.text1,items);
            layoutInflater=LayoutInflater.from(context);
            layout=R.layout.news_item;

    }

    @Override
    public int getCount() {

        return super.getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

       View view= layoutInflater.inflate(layout,null);

        return view;
    }
}
