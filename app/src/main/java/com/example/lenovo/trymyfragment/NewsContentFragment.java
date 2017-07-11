package com.example.lenovo.trymyfragment;

import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by lenovo on 2017/4/27.
 */

public class NewsContentFragment extends Fragment{
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        view = inflater.inflate(R.layout.news_content_frag,container,false);
        return view;
    }

    public void refresh (String newsTitle,String newsContent) {
        View visiblitylayout = view.findViewById (R.id.visibility_layout);
        visiblitylayout.setVisibility(View.VISIBLE);
        TextView newsTitleText = (TextView) view.findViewById(R.id.news_title);
        //TextView newsContentText = (TextView) view.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);//刷新新闻的标题
        //newsContentText.setText(newsContent);//刷新新闻的内容
        //System.out.println("Lys" + newsContentText.getText().toString());
    }
}
