package com.example.lenovo.trymyfragment;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2017/4/27.
 */

public class NewsAdapter extends ArrayAdapter <News> {
    private int resourceId;

    public NewsAdapter(Context context, int textViewResurceId, List<News> objects){
        super(context,textViewResurceId,objects);
        resourceId = textViewResurceId;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        News news = getItem(position);
        View view;
        if (convertView ==null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,null);
        }else{
            view = convertView;
        }
        TextView newsTiTleText = (TextView) view.findViewById(R.id.news_title);
        newsTiTleText.setText((news.getTitle()));
        return view;
    }
}
