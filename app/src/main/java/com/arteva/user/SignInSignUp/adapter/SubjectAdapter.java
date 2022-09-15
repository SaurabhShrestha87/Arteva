package com.arteva.user.SignInSignUp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.arteva.user.R;
import com.arteva.user.SignInSignUp.models.SubjectData;
import com.arteva.user.SignInSignUp.ui.SignUpFragment;

import java.util.List;

/**
 * Created by e203 on 10/5/17.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectAdapter.InterestViewHolder> {

    private final SignUpFragment signUpFragment;
    private final List<SubjectData> listUserData;

    public SubjectAdapter(SignUpFragment signUpFragment, List<SubjectData> listGuestUserData) {
        this.listUserData = listGuestUserData;
        this.signUpFragment = signUpFragment;
    }

    @Override
    public InterestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_data, parent, false);
        return new InterestViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final InterestViewHolder holder, final int position) {
        handleMultiChoiceSelection(holder, position, holder.tvName);
    }

    @Override
    public int getItemCount() {
        return listUserData.size();
    }

    private void handleMultiChoiceSelection(RecyclerView.ViewHolder holder, final int position, TextView textView) {
        if (listUserData.get(position).isSelected()) {
            textView.setBackgroundResource(R.drawable.user_data_bg_selected);
        } else {
            textView.setBackgroundResource(R.drawable.user_data_bg_unselected);
        }
        textView.setText(listUserData.get(position).getName());
        holder.itemView.setOnClickListener(view -> {
            listUserData.get(position).setSelected(!listUserData.get(position).isSelected());
            signUpFragment.setUserSubjectListData(listUserData);
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
