package com.arteva.user.Home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.arteva.user.Constant;
import com.arteva.user.R;
import com.arteva.user.activity.LevelActivity;
import com.arteva.user.activity.SubcategoryActivity;
import com.arteva.user.helper.AppController;
import com.arteva.user.model.Category_;

import java.util.ArrayList;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ItemRowHolder> {

    private final ArrayList<Category_> dataList;
    public Context mContext;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();


    public CategoryAdapter(Context context, ArrayList<Category_> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_maincat, parent, false);
        return new ItemRowHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ItemRowHolder holder, final int position) {

        final Category_ category = dataList.get(position);

        holder.text.setText(category.getName());
        holder.image.setDefaultImageResId(R.mipmap.ic_launcher);
        holder.image.setImageUrl(category.getImage(), imageLoader);

        holder.tvNoOfQue.setText(mContext.getString(R.string.que) + category.getTtlQues());
        holder.noofcat.setText(mContext.getString(R.string.category) + category.getNoOfCate());
        holder.relativeLayout
                .setOnClickListener(v -> {
                    Constant.CATE_ID = category.getId();
                    Constant.cate_name = category.getName();
                    if (!category.getTtlQues().equals("0")) {
                        if (!category.getNoOfCate().equals("0")) {
                            Intent intent = new Intent(mContext, SubcategoryActivity.class);
                            mContext.startActivity(intent);
                        } else {
                            if (category.getMaxLevel() == null) {
                                Constant.TotalLevel = 0;
                            } else if (category.getMaxLevel().equals("null")) {
                                Constant.TotalLevel = 0;
                            } else {
                                Constant.TotalLevel = Integer.parseInt(category.getMaxLevel());
                            }
                            Intent intent = new Intent(mContext, LevelActivity.class);
                            intent.putExtra("fromQue", "cate");
                            mContext.startActivity(intent);
                        }
                    } else {
                        Toast.makeText(mContext, mContext.getString(R.string.question_not_available), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {
        public NetworkImageView image;
        public TextView text, tvNoOfQue, noofcat;
        RelativeLayout relativeLayout, cardTitle;

        public ItemRowHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.cateImg);
            text = itemView.findViewById(R.id.item_title);
            relativeLayout = itemView.findViewById(R.id.cat_layout);
            tvNoOfQue = itemView.findViewById(R.id.noofque);
            noofcat = itemView.findViewById(R.id.noofcate);
            cardTitle = itemView.findViewById(R.id.cardTitle);
        }
    }
}

