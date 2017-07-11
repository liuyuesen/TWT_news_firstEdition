package com.example.lenovo.trymyfragment;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by liuyuesen on 2017/5/24.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater myLayoutInflater;
    private List<News> newsList;
    private Context myContext;
    private final int TYPE_BODY = 0;
    private final int TYPE_FOOTER = 1;
    //private String[] myTitles;


    public RecyclerViewAdapter(Context context, List<News> newsList) {
        this.newsList = newsList;
        this.myContext = context;
        myLayoutInflater = LayoutInflater.from(context);
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public int getItemViewType(int position) {
        if(position == newsList.size()){
            return TYPE_FOOTER;
        }else {
            return TYPE_BODY;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == TYPE_FOOTER) {
            FooterViewHolder footerViewHolder = new FooterViewHolder(myLayoutInflater.inflate(R.layout.footer_item,parent,false));
            return footerViewHolder;

        }
        else {
            TextViewHolder holder = new TextViewHolder(myLayoutInflater.inflate(R.layout.news_items, parent, false));
            return holder;
        }
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        int type = getItemViewType(position);
        if(type == TYPE_BODY) {
            final TextViewHolder myViewHolder = (TextViewHolder)holder;
            News news = newsList.get(position);
       // myViewHolder.pic.setImageBitmap(news.getPic());
            Glide.with(myContext)
                    .load(news.getPic())
                    .into(myViewHolder.pic);
            myViewHolder.myTextView.setText(news.getTitle());
        }
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickLitener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemClick(holder.itemView, pos);
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
                    return false;
                }
            });
        }
//        System.out.println("lys:size:  "+newsList.size());
//        holder.myTextView.setText(newsList.get(position).getTitle());
    }
    @Override
    public int getItemCount(){
        return newsList == null ? 0:newsList.size();
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder{
        public FooterViewHolder(View ItemView){
            super(ItemView);
        }
    }



public class TextViewHolder extends RecyclerView.ViewHolder {
    private ImageView pic;
    private TextView myTextView;
        public TextViewHolder(View ItemView){
            super(ItemView);
            pic = (ImageView) ItemView.findViewById(R.id.news_images);
            myTextView= (TextView) ItemView.findViewById(R.id.news_titles);
        }
    }

}


