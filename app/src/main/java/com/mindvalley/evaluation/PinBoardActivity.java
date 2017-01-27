package com.mindvalley.evaluation;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Interpolator;

public class PinBoardActivity extends AppCompatActivity {

    FloatingActionButton listUsersButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_board);
        bindPinBoardActivity();



    }

    private void bindPinBoardActivity() {
        listUsersButton= (FloatingActionButton) findViewById(R.id.list_users_button);
        listUsersButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userListIntent=new Intent(PinBoardActivity.this,UserListActivity.class);
                startActivity(userListIntent);
            }
        });
    }
}
