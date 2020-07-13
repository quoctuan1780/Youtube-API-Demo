package com.example.youtube_api_demo.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.youtube_api_demo.R;
import com.example.youtube_api_demo.models.VideoYT;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import static android.content.ContentValues.TAG;

public class AdapterHome extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<VideoYT> videoList;

    public AdapterHome() {
    }

    public AdapterHome(Context context, List<VideoYT> videoList) {
        this.context = context;
        this.videoList = videoList;
    }

    class YoutubeHolder extends RecyclerView.ViewHolder{

        ImageView thumbnail;
        TextView judul, tanggal;

        public YoutubeHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail=itemView.findViewById(R.id.imageView);
            judul=itemView.findViewById(R.id.tv_judul);
            tanggal=itemView.findViewById(R.id.tv_tgUpdate);

        }

        public void setData(VideoYT data) {
            String getJudul=data.getSnippet().getTitle();
            String getTgl=data.getSnippet().getPublishedAt();
            String getThumb=data.getSnippet().getThumbnails().getMediumThumb().getUrl();
            judul.setText(getJudul);
            tanggal.setText(getTgl);
            Picasso.get().load(getThumb).placeholder(R.mipmap.ic_launcher).fit().centerCrop().into(thumbnail, new Callback() {
                @Override
                public void onSuccess() {
                    Log.d(TAG, "onSuccess: call setdata in adapterHome");
                }

                @Override
                public void onError(Exception e) {
                    Log.d(TAG, "onFail: call setdata in adapterHome" + e);
                }
            });
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view=inflater.inflate(R.layout.row_item,parent,false);
        return new YoutubeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        VideoYT videoYT=videoList.get(position);
        YoutubeHolder yth= (YoutubeHolder) holder;
        yth.setData(videoYT);
    }

    @Override
    public int getItemCount() {
        return videoList.size();
    }
}
