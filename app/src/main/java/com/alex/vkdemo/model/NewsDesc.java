package com.alex.vkdemo.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by marat on 01.03.15.
 */
public class NewsDesc {


    private final int postDate;
    private final String title;
    private  final int likeCount;
    private final String photoUrl;

public NewsDesc(String title,int likeCount,String url,int postDate){
    this.title=title;
    this.likeCount=likeCount;
    this.photoUrl=url;
    this.postDate=postDate;
}

    public int getLikeCount() {
        return likeCount;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPostDate() {
        Date date=new Date(postDate);
        DateFormat df = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss");
        return df.format(date);
    }

    public int getDate() {
        return postDate;
    }
}
