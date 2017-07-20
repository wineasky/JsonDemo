package com.example.jsondemo;

import android.graphics.Bitmap;

/**
 * Created by w on 2017/7/18.
 "count_browse":6651,
 "count_review":1,
 "create_time":"1498030369",
 "update_time":"1498030369",
 "start_time
 */
public class Feed{
    public String id;
    public String uid;
    public int category;
    public String title;
    public String summary;
    public String tids;
    public String tags;
    public String pic_min;
    public String pic_mid;
    public int is_index;
    public int is_hot;
    public int is_top;
    public int star;
    public int count_browse;
    public int count_review;
    public long create_time;
    public long update_time;
    public UserInfo usr;
    public Bitmap ico;
    public String type;

    public static class UserInfo {
        public String nickname;
        public String alias;
        public String icon;
    }
}
