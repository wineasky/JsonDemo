package com.example.jsondemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.RelativeDateTimeFormatter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

public class MainActivity extends AppCompatActivity {
    private Bitmap ico;
    private final static String REQUEST_URL = "http://api.u148.net/json/0/1";
    private final static int MSG_REQUEST_OK = 233;
    private String mJson;
    private RecyclerView recyclerView_feed;
    private ArrayList<Feed> feedls;
    private FeedAdapter mAdapter;


    private final Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (MSG_REQUEST_OK != msg.what) return;
              mAdapter.addData(feedls);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView_feed = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView_feed.setLayoutManager(new LinearLayoutManager(this));
        recyclerView_feed.setItemAnimator(new DefaultItemAnimator());
        recyclerView_feed.setHasFixedSize(true);

        mAdapter = new FeedAdapter();
        recyclerView_feed.setAdapter(mAdapter);


        new Thread() {
            @Override
            public void run() {
            feedls = parse(requestJson());
            mHandler.sendEmptyMessage(MSG_REQUEST_OK);
            }
        }.start();


    }

    public String getJsonTxt(String filename){
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader bf = new BufferedReader
                            (new InputStreamReader(getAssets().open(filename)));
            String line;
            while((line = bf.readLine()) != null){
                sb.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }

    public ArrayList<Feed>  parse(String json) {
         Log.i("parse里的第一句话",json);
         ArrayList<Feed> feedList  = new ArrayList();
         try {
            JSONObject jObj = new JSONObject(json);
            JSONObject dataObj = jObj.getJSONObject("data");
            JSONArray feed = dataObj.getJSONArray("data");
             for (int i = 0; i <feed.length(); i++) {
                 JSONObject feedData = feed.getJSONObject(i);
                 setAndLog(feedList,feedData);
             }
         } catch (Exception e) {
            e.printStackTrace();
            Log.i("parse里的erro","erro:"+e);
        }
        return feedList;
    }

    private void setAndLog(List<Feed> feedList, JSONObject feedData) throws JSONException, IOException {
        JSONObject userInfo = feedData.optJSONObject("usr");
        Feed feed = new Feed();
        feed.id =feedData.getString("id");
        feed.uid =feedData.getString("uid");
        feed.category = feedData.getInt("category");
        feed.title = feedData.getString("title");
        feed.summary = feedData.getString("summary");
        feed.tids = feedData.getString("tids");
        feed.tags = feedData.getString("tags");
        feed.pic_min = feedData.getString("pic_min");
        feed.pic_mid = feedData.getString("pic_mid");
        feed.is_index = feedData.getInt("is_index");
        feed.is_hot = feedData.getInt("is_hot");
        feed.is_top = feedData.getInt("is_top");
        feed.star = feedData.getInt("star");
        feed.count_browse =feedData.getInt("count_browse");
        feed.count_review = feedData.getInt("count_review");
        feed.create_time = feedData.getInt("create_time");
        feed.update_time = feedData.getInt("update_time");
        Feed.UserInfo usr = new Feed.UserInfo();
        usr.nickname = userInfo.getString("nickname");
        usr.alias = userInfo.getString("alias");
        usr.icon = userInfo.getString("icon");
        feed.usr = usr;
        feed.ico = getBitmap(feed.pic_min);
        feed.type = getType(feed);
        feedList.add(feed);
        Log.i("feed","id#"+feed.id);
        Log.i("feed","uid#"+feed.uid);
        Log.i("feed","category#"+feed.category);
        Log.i("feed","title#"+feed.title);
        Log.i("feed","summary#"+feed.summary);
        Log.i("feed","tids#"+feed.tids);
        Log.i("feed","tags#"+feed.tags);
        Log.i("feed","pic_min#"+feed.pic_min);
        Log.i("feed","pic_mid#"+feed.create_time);
        Log.i("feed","update_time#"+feed.update_time);
        Log.i("feed","usr#"+feed.usr);

     }



     private String getType(Feed feed){
        String[] mType =getResources().getStringArray(R.array.category_name);
        if(feed.category==0){
           feed.type ="["+mType[0]+"]";
        }else if(feed.category == 3){
            feed.type ="["+mType[1]+"]";
        }else if(feed.category == 6) {
            feed.type ="["+mType[2]+"]";
        }else if(feed.category == 5){
            feed.type ="["+mType[3]+"]";
        }else if(feed.category == 10) {
            feed.type ="["+mType[4]+"]";
        }else if(feed.category == 7) {
            feed.type ="["+mType[5]+"]";
        }else if(feed.category == 2) {
            feed.type ="["+mType[6]+"]";
        }else if(feed.category == 9) {
            feed.type ="["+mType[7]+"]";
        }else if(feed.category == 8) {
            feed.type ="["+mType[8]+"]";
        }else if(feed.category == 4) {
            feed.type ="["+mType[9]+"]";
        }else  {
            feed.type ="[其他]";
        }
        return feed.type;

    }

    private String requestJson() {
        HttpURLConnection conn = null;
        try {
            URL url = new URL(REQUEST_URL);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);
            conn.setRequestMethod("GET");
            conn.connect();

            int statusCode = conn.getResponseCode();
            if (statusCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));

                StringBuilder sb = new StringBuilder();
                String line;
                while (null != (line = reader.readLine())) {
                    sb.append(line);
                }
                reader.close();
                is.close();
                mJson = sb.toString();
            } else {
                //error happens!
            }
        } catch (MalformedURLException e) {
            //todo error handle
        } catch (IOException e) {
            //todo error handle
        } finally {
            if (null != conn) conn.disconnect();
        }
        return mJson;
    }

    private String getText(){
       String json =  requestJson();
       List<Feed> feedList= parse(json);
       StringBuffer tempText= new StringBuffer();
       for (int i = 0;i<feedList.size();i++){
           tempText.append(feedList.get(i).title+"\r\n");
       }
       json = ""+tempText;
        return json;
    }



    public Bitmap getBitmap(String u) throws IOException {
        return BitmapFactory.decodeStream(getImageViewInputStream(u));
    }

    public static InputStream getImageViewInputStream(String u) throws IOException {
        InputStream inputStream = null;
        URL url = new URL(u);                    //服务器地址
        if (url != null) {
            //打开连接
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setConnectTimeout(3000);//设置网络连接超时的时间为3秒
            httpURLConnection.setRequestMethod("GET");        //设置请求方法为GET
            httpURLConnection.setDoInput(true);                //打开输入流
            int responseCode = httpURLConnection.getResponseCode();    // 获取服务器响应值
            if (responseCode == HttpURLConnection.HTTP_OK) {        //正常连接
                inputStream = httpURLConnection.getInputStream();        //获取输入流
            }
        }
        return inputStream;
    }



}
