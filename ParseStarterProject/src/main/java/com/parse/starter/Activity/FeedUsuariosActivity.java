package com.parse.starter.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.starter.R;
import com.parse.starter.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedUsuariosActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private ListView listView;
    private String username;
    private String objectId;
    private ArrayAdapter<ParseObject> adapter;
    private ArrayList<ParseObject> postagens;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_usuarios);

        Drawable leftArrow = getResources().getDrawable(R.drawable.ic_action_arrow_left);
        leftArrow.setColorFilter(getResources().getColor(R.color.laranja), PorterDuff.Mode.SRC_ATOP);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");
        objectId = intent.getStringExtra("objectId");
        toolbar = findViewById(R.id.feed_users_toolbar);
        toolbar.setTitle(username);
        toolbar.setTitleTextColor(getResources().getColor(R.color.laranja));
        toolbar.setNavigationIcon(leftArrow);
        setSupportActionBar(toolbar);

        postagens = new ArrayList<>();
        listView = findViewById(R.id.feed_users_list);
        adapter = new HomeAdapter( getApplicationContext(), postagens);
        listView.setAdapter(adapter);

        getPostagens();



    }

    private void getPostagens() {

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Imagem");
        parseQuery.whereEqualTo("userId", objectId )
                    .orderByDescending("createdAt");
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e == null){

                    if(objects.size() > 0){
                        postagens.clear();
                        for (ParseObject parseObject : objects){

                            postagens.add(parseObject);
                        }

                        adapter.notifyDataSetChanged();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Erro ao carregar o feed ", Toast.LENGTH_LONG).show();
                }

            }
        });

    }




}
