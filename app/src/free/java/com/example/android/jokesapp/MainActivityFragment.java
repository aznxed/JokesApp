package com.example.android.jokesapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.android.android_lib.JokeActivity;
import com.example.android.backend.myApi.MyApi;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;

import java.io.IOException;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements JokeCategoryAdapter.ListItemClickListener{

    public static String DOG_JOKES = "DOG";
    public static String CAT_JOKES = "CAT";
    public static String MED_JOKES = "MED";
    public static String JOKE_EXTRA = "joke";

    private ProgressBar progressBar;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        AdView mAdView = view.findViewById(R.id.adView);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        progressBar = view.findViewById(R.id.progress_bar);

        JokeCategoryAdapter jokeCategoryAdapter = new JokeCategoryAdapter(getContext(), this);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 1));
        recyclerView.setAdapter(jokeCategoryAdapter);
        recyclerView.setHasFixedSize(true);

        // Create an ad request. Check logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use AdRequest.Builder.addTestDevice("ABCDEF012345") to get test ads on this device."
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();
        mAdView.loadAd(adRequest);
        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {
        switch (clickedItemIndex){
            case 0:
                new MainActivityFragment.EndpointsAsyncTask().execute(new Pair<>(getContext(), DOG_JOKES));
                break;
            case 1:
                new MainActivityFragment.EndpointsAsyncTask().execute(new Pair<>(getContext(), CAT_JOKES));
                break;
            case 2:
                new MainActivityFragment.EndpointsAsyncTask().execute(new Pair<>(getContext(), MED_JOKES));
                break;
            default:
        }
    }

    class EndpointsAsyncTask extends AsyncTask<Pair<Context,String>,Void,String> {

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
                return myApiService.sayHi(param[0].second).execute().getData();
            } catch(IOException e) {
                return "";
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