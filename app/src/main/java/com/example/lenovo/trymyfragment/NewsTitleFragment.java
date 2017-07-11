package com.example.lenovo.trymyfragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2017/4/27.
 */

public class NewsTitleFragment extends Fragment implements OnItemClickListener {
    private SwipeRefreshLayout swipeRefreshLayout;
    private int number = 2;
    private RecyclerView recyclerView;
    private List<News> newsList = new ArrayList<>();
    private RecyclerViewAdapter adapter;
    private boolean isTwoPane;
    private MyHandler myHandler = new MyHandler();

    private Thread thread;



    class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            //刷新listview
            //System.out.println("abcdef6" + newsList.get(0).getTitle());

            parseJSONWithJSONObject(msg.obj.toString());
            adapter.notifyDataSetChanged();
            if (swipeRefreshLayout.isRefreshing()) {
                swipeRefreshLayout.setRefreshing(false);
                Toast toast;
                toast = Toast.makeText(getActivity(), "刷新成功", Toast.LENGTH_SHORT);
                //toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
            //System.out.println("abcdef6" + newsList.get(0).getTitle());
            super.handleMessage(msg);
        }

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //System.out.println("abcdefsuper.onAttach(context);");
//        newsList = getNews();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("http://open.twtstudio.com/api/v1/news/1/page/1");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();
                    //对获取到的输入流进行读取
                    //System.out.println("abcdef2" + newsList.get(0).getTitle());
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    //System.out.println("abcdef3" + newsList.get(0).getTitle());
                    Message msg = new Message();
                    msg.obj = response.toString();
                    //parseJSONWithJSONObject(msg.obj.toString());
                    //setData(newsList);
                    myHandler.sendMessage(msg);
                    //System.out.println("abcdef4" + msg.toString());
                    //parseJSONWithJSONObject(result);
                    //System.out.println("abcdef2222" + newsList.get(2).getTitle());
                    //adapter.notifyDataSetChanged();
                    //System.out.println("abcdef3333" + newsList.get(3).getTitle());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.news_title_frag, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.news_title_recycler_view);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter = new RecyclerViewAdapter(getActivity(), newsList);
        adapter.setOnItemClickLitener(new RecyclerViewAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(getActivity(), "item click", Toast.LENGTH_SHORT).show();
                News news = newsList.get(position);
                NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getNumber());
            }

            @Override
    public void onItemLongClick(View view, int position) {

    }
});

        recyclerView.setAdapter(adapter);
        return view;
        }

    @Override
    public void onActivityCreated(Bundle savedInstaneState) {
        super.onActivityCreated(savedInstaneState);
        //if (getActivity().findViewById(R.id.news_content_layout)!=null){
        //    isTwoPane = true;
        //}else{
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorPrimary,
                R.color.colorPrimary
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection connection = null;
                        try {
                            newsList.clear();
                            URL url = new URL("http://open.twtstudio.com/api/v1/news/1/page/1");
                            connection = (HttpURLConnection) url.openConnection();
                            connection.setRequestMethod("GET");
                            connection.setConnectTimeout(8000);
                            connection.setReadTimeout(8000);
                            InputStream in = connection.getInputStream();
                            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                            StringBuilder response = new StringBuilder();
                            String line;
                            while ((line = reader.readLine()) != null) {
                                response.append(line);
                            }
                            Message msg = new Message();
                            msg.obj = response.toString();
                            myHandler.sendMessage(msg);

                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            if (connection != null) {
                                connection.disconnect();
                            }
                        }
                    }
                }).start();
                isTwoPane = false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                int lastPostion= layoutManager.findLastVisibleItemPosition();
                if (newState ==RecyclerView.SCROLL_STATE_IDLE && lastPostion+ 1 ==adapter.getItemCount()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            HttpURLConnection connection = null;
                            try {
                                URL url = new URL("http://open.twtstudio.com/api/v1/news/1/page/"+number);
                                number++;
                                connection = (HttpURLConnection) url.openConnection();
                                connection.setRequestMethod("GET");
                                connection.setConnectTimeout(8000);
                                connection.setReadTimeout(8000);
                                InputStream in = connection.getInputStream();
                                //对获取到的输入流进行读取
                                //System.out.println("abcdef2" + newsList.get(0).getTitle());
                                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                                StringBuilder response = new StringBuilder();
                                String line;
                                while ((line = reader.readLine()) != null) {
                                    response.append(line);
                                }
                                //System.out.println("abcdef3" + newsList.get(0).getTitle());
                                Message msg = new Message();
                                msg.obj = response.toString();
                                //parseJSONWithJSONObject(msg.obj.toString());
                                //setData(newsList);
                                myHandler.sendMessage(msg);
                                //System.out.println("abcdef4" + msg.toString());
                                //parseJSONWithJSONObject(result);
                                //System.out.println("abcdef2222" + newsList.get(2).getTitle());
                                //adapter.notifyDataSetChanged();
                                //System.out.println("abcdef3333" + newsList.get(3).getTitle());
                            } catch (Exception e) {
                                e.printStackTrace();
                            } finally {
                                if (connection != null) {
                                    connection.disconnect();
                                }
                            }
                        }
                    }).start();
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView,dx, dy);
                //lastVisibleItem =linearLayoutManager.findLastVisibleItemPosition();
            }
        });

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "item click", Toast.LENGTH_SHORT).show();
        News news = newsList.get(position);
        NewsContentActivity.actionStart(getActivity(), news.getTitle(), news.getNumber());
    }

    private void parseJSONWithJSONObject(String jsonData) {
        //System.out.println("abcdef6" + newsList.get(0).getTitle());
        try {
            //System.out.println("abcdef6" + newsList.get(0).getTitle());
            News news;
            //Bitmap bitmap;
            //System.out.println("abcdef6" + newsList.get(0).getTitle());
            JSONArray jsonObjs = new JSONObject(jsonData).getJSONArray("data");
            //newsList.clear();
            //System.out.println("abcdef3" + jsonObjs.length());
            for (int i = 0; i < jsonObjs.length(); i++) {
                news = new News();
                JSONObject jsonObj = jsonObjs.getJSONObject(i);
                //System.out.println("abcdefJSON" + newsList.get(0).getTitle());
                news.setTitle(jsonObj.getString("subject"));
                news.setContent(jsonObj.getString("summary"));
                news.setPic(jsonObj.getString("pic"));
                news.setNumber(jsonObj.getInt("index"));
                //bitmap = HttpURL.GetBitmap(jsonObj.getString("pic"));
                newsList.add(news);
            }
        } catch (JSONException e) {
            System.out.println("Json parse error");
            e.printStackTrace();
        }
    }

    private List<News> getNews() {
        List<News> newsList = new ArrayList<News>();
        News news1 = new News();
        news1.setTitle("Succed in College as a Learning Disabled Student");
        news1.setContent("College freshmen will soon learn to live with a roommate, adjust to a new social scene and survive less-than-stellar dining hall food. Students with learning disabilities...");
        newsList.add(news1);
        News news2 = new News();
        news2.setTitle("Succed in College as a Learning Disabled Student");
        news2.setContent("College freshmen will soon learn to live with a roommate, adjust to a new social scene and survive less-than-stellar dining hall food. Students with learning disabilities...");
        newsList.add(news2);
        return newsList;
    }

    void setData(List<News> data) {
        newsList.clear();
        newsList.addAll(data);
        System.out.println("NewsTitleFragment.setData");
        adapter.notifyDataSetChanged();
    }

}

