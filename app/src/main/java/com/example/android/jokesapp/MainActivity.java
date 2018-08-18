package com.example.android.jokesapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.android_lib.JokeActivity;
import com.example.android.backend.myApi.MyApi;
import com.example.android.lib.JokeClass;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements JokeCategoryAdapter.ListItemClickListener{
    public static String DOG_JOKES = "DOG";
    public static String CAT_JOKES = "CAT";
    public static String MED_JOKES = "MED";
    public static String JOKE_EXTRA = "joke";

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdView mAdView = findViewById(R.id.adView);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        progressBar = findViewById(R.id.progress_bar);

        JokeCategoryAdapter jokeCategoryAdapter = new JokeCategoryAdapter(this, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(jokeCategoryAdapter);
        recyclerView.setHasFixedSize(true);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        switch (clickedItemIndex){
            case 0:
                new EndpointsAsyncTask().execute(new Pair<Context, String>(this, DOG_JOKES));
                break;
            case 1:
                new EndpointsAsyncTask().execute(new Pair<Context, String>(this, CAT_JOKES));
                break;
            case 2:
                new EndpointsAsyncTask().execute(new Pair<Context, String>(this, MED_JOKES));
                break;
            default:
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context,String>,Void,String>{

        private MyApi myApiService = null;
        private Context context;
        @Override
        protected String doInBackground(Pair<Context,String>... param) {
            if(myApiService == null){
                MyApi.Builder builder = new MyApi.Builder(AndroidHttp.newCompatibleTransport(),
                        new AndroidJsonFactory(), null)
                        .setRootUrl("http://10.0.2.2:8080/_ah/api/")
                        .setGoogleClientRequestInitializer(new GoogleClientRequestInitializer() {
                            @Override
                            public void initialize(AbstractGoogleClientRequest<?> request) throws IOException {
                                request.setDisableGZipContent(true);
                            }
                        });
                myApiService = builder.build();
            }

            context = param[0].first;

            try {
                String string = myApiService.sayHi(param[0].second).execute().getData();
                return myApiService.sayHi(param[0].second).execute().getData();
            } catch (IOException e) {
                return e.getMessage();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(String s) {
            progressBar.setVisibility(View.INVISIBLE);
            Intent jokeIntent = new Intent(context, JokeActivity.class);
            jokeIntent.putExtra(JOKE_EXTRA, s);
            startActivity(jokeIntent);
        }
    }
}
