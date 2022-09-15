package com.arteva.user.SignInSignUp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arteva.user.R;
import com.arteva.user.SignInSignUp.models.ClassData;
import com.arteva.user.SignInSignUp.ui.SignUpFragment;

import java.util.List;

/**
 * Created by e203 on 10/5/17.
 */

public class ClassAdapter extends RecyclerView.Adapter<ClassAdapter.InterestViewHolder> {

    private final Context mContext;
    private final SignUpFragment signUpFragment;
    private final List<ClassData> listUserData;
    private final boolean isMultiChoice;
    private int selectedPosition = -1;

    public ClassAdapter(Context context, SignUpFragment signUpFragment, List<ClassData> listGuestUserData, boolean isMultiChoice) {
        this.listUserData = listGuestUserData;
        this.mContext = context;
        this.signUpFragment = signUpFragment;
        this.isMultiChoice = isMultiChoice;
    }


    @Override
    public InterestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return new InterestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InterestViewHolder holder, final int position) {
        handleSingleChoiceSelection(holder, position, holder.tvName);
    }

    @Override
    public int getItemCount() {
        return listUserData.size();
    }

    private void handleSingleChoiceSelection(RecyclerView.ViewHolder holder, final int position, TextView textView) {
        if (selectedPosition == position) {
            textView.setBackgroundResource(R.drawable.user_data_bg_selected);
        } else {
            textView.setBackgroundResource(R.drawable.user_data_bg_unselected);
        }
        textView.setText(listUserData.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            selectedPosition = position;
            signUpFragment.setUserClass(listUserData.get(position).getId(), position);
            notifyDataSetChanged();
        });
    }

    class InterestViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;

        InterestViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
        }
    }
}
