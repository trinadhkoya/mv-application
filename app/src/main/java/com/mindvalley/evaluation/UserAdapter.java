package com.mindvalley.evaluation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.utils.CacheManager;
import com.mindvalley.evaluation.model.User;

import java.util.ArrayList;

/**
 * Created by trinadhkoya on 27/01/17.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    ArrayList<User> users;
    CacheManager<Bitmap> bitmapCacheManager;

    public UserAdapter(ArrayList<User> userData, UserListActivity userListActivity) {
        this.context = userListActivity;
        this.users = userData;
        this.bitmapCacheManager = new CacheManager<>(10 * 1024 * 1024);

    }


    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserAdapter.ViewHolder holder, int position) {
        MindValleyHTTP.from(context).serve(MindValleyHTTP.Method.GET, users.get(position).getProfileImage()).asBitMap().setCacheManager(bitmapCacheManager).setCallback(new HttpListener<Bitmap>() {
            @Override
            public void onRequest() {

            }

            @Override
            public void onResponse(Bitmap data) {
                holder.userProfieImageView.setImageBitmap(data);
            }

            @Override
            public void onError() {

            }

            @Override
            public void onCancel() {

            }
        });

        holder.userNameTextView.setText(users.get(position).getName());
        holder.userProfileNameTextView.setText(users.get(position).getUserName());


    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    @Override
    public void onClick(View v) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView userProfieImageView;
        TextView userNameTextView, userProfileNameTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            userProfieImageView = (ImageView) itemView.findViewById(R.id.user_profile_pic);
            userNameTextView = (TextView) itemView.findViewById(R.id.user_name);
            userProfileNameTextView = (TextView) itemView.findViewById(R.id.profile_name);
        }

    }

    public void wipe() {
        this.users.clear();
        notifyDataSetChanged();
    }

}
