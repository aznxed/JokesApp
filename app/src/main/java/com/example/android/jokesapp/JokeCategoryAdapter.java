package com.example.android.jokesapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class JokeCategoryAdapter extends RecyclerView.Adapter<JokeCategoryAdapter.RecyclerViewHolders>{

    private String[] categories = {"Dog Jokes", "Cat Jokes", "Medicine Jokes"};
    private Context context;
    private String[] links = {"https://cdn6.littlethings.com/app/uploads/2017/05/cute-dog-names-1200.jpg",
            "https://i1.wp.com/chartcons.com/wp-content/uploads/Funny-Cat-Jokes2.jpg?resize=1021%2C576&ssl=1",
            "http://www.best-country.org//img/article/735/735/3587medicine_orig.jpg"};

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }
    final private ListItemClickListener clickListener;

    public JokeCategoryAdapter(Context context, ListItemClickListener clickListener){
        this.context = context;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public RecyclerViewHolders onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View layoutView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.joke_category, null);
        return new RecyclerViewHolders(layoutView);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolders recyclerViewHolders, int position) {
        recyclerViewHolders.jokeCategory.setText(categories[position]);
        Picasso.with(context).load(links[position]).error(R.drawable.ic_broken_image_black_24dp).centerCrop().fit().into(recyclerViewHolders.jokeImage);
    }

    @Override
    public int getItemCount() {
        return categories.length;
    }

    class RecyclerViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener{
        private ImageView jokeImage;
        private TextView jokeCategory;


        public RecyclerViewHolders(@NonNull View itemView) {
            super(itemView);
            jokeImage = itemView.findViewById(R.id.category_image);
            jokeCategory = itemView.findViewById(R.id.category_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            clickListener.onListItemClick(clickedPosition);
        }
    }
}
