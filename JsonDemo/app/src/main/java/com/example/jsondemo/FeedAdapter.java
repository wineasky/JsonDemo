package com.example.jsondemo;

import android.content.Context;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by w on 2017/7/19.
 */
public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.MyViewHolder>  {
    private ArrayList<Feed> mFeed = new ArrayList<>();
    String[] mType;
    Context context;

    public  FeedAdapter(){}
    public void addData(List<Feed> list){
        mFeed.clear();
        mFeed.addAll(list);
        notifyDataSetChanged();

    }

    @Override
    public FeedAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.feed_view, parent, false);
        FeedAdapter.MyViewHolder viewHolder = new FeedAdapter.MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FeedAdapter.MyViewHolder holder, int i) {
        Feed feed = mFeed.get(i);


        holder.title.setText(feed.title);
        holder.img.setImageBitmap(feed.ico);
        holder.preview.setText(feed.summary);
        holder.look.setText(feed.count_browse+"人看过");
        holder.comment.setText(feed.count_review+"评论");
        holder.type.setText(feed.type);
     //   mType = context.getResources().getStringArray(R.array.category_name);
      /*  if(feed.category==0){
            holder.type.setText("["+mType[0]+"]");
        }else if(feed.category == 3){
            holder.type.setText("["+mType[1]+"]");
        }else if(feed.category == 6) {
            holder.type.setText("["+mType[2]+"]");
        }else if(feed.category == 5){
                holder.type.setText("["+mType[3]+"]");
        }else if(feed.category == 10) {
                holder.type.setText("["+mType[4]+"]");
        }else if(feed.category == 7) {
                holder.type.setText("["+mType[5]+"]");
        }else if(feed.category == 2) {
                holder.type.setText("["+mType[6]+"]");
        }else if(feed.category == 9) {
                holder.type.setText("["+mType[7]+"]");
        }else if(feed.category == 8) {
                holder.type.setText("["+mType[8]+"]");
        }else if(feed.category == 4) {
                holder.type.setText("["+mType[9]+"]");
        }else  {
            holder.type.setText("[其他]");
        }*/


 /*   switch (feed.category){
            case 0:
                holder.type.setText(mType[0]);
                break;
            case 3:
                holder.type.setText(mType[1]);
                break;
            case 6:
                holder.type.setText(mType[2]);
                break;
            case 5:
                holder.type.setText(mType[3]);
                break;
            case 10:
                holder.type.setText(mType[4]);
                break;
            case 7:
                holder.type.setText(mType[5]);
                break;
            case 2:
                holder.type.setText(mType[6]);
                break;
            case 9:
                holder.type.setText(mType[7]);
                break;
            case 8:
                holder.type.setText(mType[8]);
                break;
            case 4:
                holder.type.setText(mType[9]);
                break;
            default:
                holder.type.setText("其他");
                break;
        }*/
    }


    @Override
    public int getItemCount() {
        return mFeed.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

       public TextView title;
       public ImageView img;
       public TextView preview;
       public TextView look;
       public TextView comment;
       public TextView type;


        public MyViewHolder(View v) {
            super(v);
            title = (TextView) v.findViewById(R.id.title);
            img = (ImageView)v.findViewById(R.id.img);
            preview = (TextView)v.findViewById(R.id.preview);
            comment = (TextView)v.findViewById(R.id.comment);
            look = (TextView)v.findViewById(R.id.look);
            type =(TextView)v.findViewById(R.id.type);
        }
    }
}
