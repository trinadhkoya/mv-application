package com.mindvalley.evaluation;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.android.valley.MindValleyHTTP;
import com.android.valley.listener.HttpListener;
import com.android.valley.utils.CacheManager;

import com.mindvalley.evaluation.api.PIEndPoints;
import com.mindvalley.evaluation.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Handler;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity {

    public ArrayList<User> userData = new ArrayList<>();

    RecyclerView userListRecyclerView;
    RecyclerView.LayoutManager usersLayoutManager;
    RecyclerView.Adapter userAdapter;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    public boolean LOADING_PAGE = true;
    public int visibleItemsCount, pastVisibleItems, totalItemsCount;

    Handler handler = new Handler();


    ViewGroup rootView;

    CacheManager<JSONArray> jsonArrayCacheManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        //10 MB
        jsonArrayCacheManager = new CacheManager<>(10 * 1024 * 1024);

        userListRecyclerView = (RecyclerView) findViewById(R.id.listUserRecycleView);
        usersLayoutManager = new LinearLayoutManager(getApplicationContext());
        userListRecyclerView.setLayoutManager(usersLayoutManager);
        userAdapter = new UserAdapter(userData, UserListActivity.this);
        userListRecyclerView.setAdapter(userAdapter);


        userListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemsCount = usersLayoutManager.getChildCount();
                totalItemsCount = usersLayoutManager.getItemCount();
                pastVisibleItems = ((LinearLayoutManager) userListRecyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                if (LOADING_PAGE) {
                    if ((visibleItemsCount + pastVisibleItems) == totalItemsCount) {

                        populateUserInfo();
                        LOADING_PAGE = false;
                    }
                }

            }
        });

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                userData.clear();
                userAdapter.notifyDataSetChanged();
                populateUserInfo();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        populateUserInfo();

    }

    private void populateUserInfo() {
        MindValleyHTTP.from(this)
                .serve(MindValleyHTTP.Method.GET, PIEndPoints.USERS_PROFILE_URL)
                .asJSONArray().setCacheManager(jsonArrayCacheManager).setCallback(new HttpListener<JSONArray>() {
            @Override
            public void onRequest() {
                startSync();
            }

            @Override
            public void onResponse(JSONArray data) {
                if (data != null) {
                    for (int i = 0; i < data.length(); i++) {
                        try {
                            JSONObject jsonObject = data.getJSONObject(i);
                            String userName = jsonObject.getJSONObject("user").optString("username");
                            String name = jsonObject.getJSONObject("user").optString("name");
                            String profileImgUrl = jsonObject.getJSONObject("user").getJSONObject("profile_image").optString("small").toString();

                            User user = new User();
                            user.name = name;
                            user.userName = userName;
                            user.profileImage = profileImgUrl;

                            userData.add(user);
                            userAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            LOADING_PAGE = false;
                        }
                    }
                }

            }

            @Override
            public void onError() {
                LOADING_PAGE = false;
            }

            @Override
            public void onCancel() {
                LOADING_PAGE = false;
            }
        });

    }

    private void startSync() {
        LOADING_PAGE = false;
        if (mSwipeRefreshLayout.isRefreshing()) {
            mSwipeRefreshLayout.setRefreshing(true);
        }

    }
}
