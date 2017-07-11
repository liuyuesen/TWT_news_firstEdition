package com.example.lenovo.trymyfragment;

/**
 * Created by lenovo on 2017/4/26.
 */

public class News {
    private String title;
    private String content;
    private String pic;
    private int number;
    public String getPic(){return pic;}
    public void setPic(String pic){this.pic = pic;}
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public String getContent(){
        return content;
    }
    public void setContent(String content){
        this.content = content;
    }
    public int getNumber() {return number;}
    public void setNumber(int number){this.number = number;}
}
