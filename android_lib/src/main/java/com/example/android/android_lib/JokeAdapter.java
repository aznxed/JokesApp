package com.example.android.android_lib;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.JokeHolder>{

    private String[] jokes;

    public JokeAdapter(String jokes){
        this.jokes = jokes.split(",");
    }

    @NonNull
    @Override
    public JokeAdapter.JokeHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.joke_view, null);
        return new JokeHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull JokeAdapter.JokeHolder jokeHolder, int i) {
        String[] jokeandanswer = jokes[i].split("-");
        Log.d("Test", jokes[i]);
        jokeHolder.joke.setText(jokeandanswer[0]);
        jokeHolder.answer.setText(jokeandanswer[1]);
    }

    @Override
    public int getItemCount() {
        if(jokes == null){
            return 0;
        }
        return jokes.length;
    }

    class JokeHolder extends RecyclerView.ViewHolder{

        private TextView joke;
        private TextView answer;

        private JokeHolder(View view){
            super(view);
            joke = view.findViewById(R.id.joke);
            answer = view.findViewById(R.id.answer);
        }
    }

}
