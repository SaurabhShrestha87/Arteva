package com.arteva.user.Home.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.arteva.user.Constant;
import com.arteva.user.R;
import com.arteva.user.helper.AppController;
import com.arteva.user.model.Video;
import com.arteva.user.video.ui.VideoPlayerActivity;

import java.util.ArrayList;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.viewHolder> {

    Context context;
    ArrayList<Video> arrayList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public VideoAdapter(Context context, ArrayList<Video> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VideoAdapter.viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;
        view = LayoutInflater.from(context).inflate(R.layout.layout_video_item,  viewGroup, false);
        return new VideoAdapter.viewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoAdapter.viewHolder viewHolder, int position) {
        Video video = arrayList.get(position);
        viewHolder.iconName.setText(video.getName());
        viewHolder.noofcate.setText(context.getString(R.string.play_now));
        viewHolder.imageBG.setImageUrl(Constant.MEDIA_URL + video.getImage(), imageLoader);
        viewHolder.cardTitle.setBackgroundResource(Constant.gradientBG[position % 6]);
        viewHolder.cardTitle.setOnClickListener(v -> {
            openVideo(video);
        });
    }


    public void openVideo(Video video) {
        context.startActivity(new Intent(context, VideoPlayerActivity.class).putExtra("video", (Parcelable) video));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class viewHolder extends RecyclerView.ViewHolder {

        TextView iconName, noofcate;
        ConstraintLayout cardTitle;
        NetworkImageView imageBG;

        public viewHolder(View itemView) {
            super(itemView);

            imageBG = itemView.findViewById(R.id.imageBG);
            iconName = itemView.findViewById(R.id.item_title);
            cardTitle = itemView.findViewById(R.id.cardTitle);
            noofcate = itemView.findViewById(R.id.noofcate);
        }
    }

}
