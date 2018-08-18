package com.example.android.android_lib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class JokeActivity extends AppCompatActivity {

    public static String JOKE_EXTRA = "joke";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();
        if(intent.hasExtra(JOKE_EXTRA)){
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            JokeAdapter jokeAdapter = new JokeAdapter(intent.getStringExtra(JOKE_EXTRA));
            recyclerView.setAdapter(jokeAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);
        }
    }


}
