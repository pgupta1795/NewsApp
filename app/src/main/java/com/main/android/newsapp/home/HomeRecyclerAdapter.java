package com.main.android.newsapp.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.main.android.newsapp.R;
import com.main.android.newsapp.bottomfragment.NewsDetailActivity;
import com.main.android.newsapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Article> articles;
    private Context context;
    private RecyclerView homeRecyclerView;
    private static final String SOURCE_FORMAT = "%s • %sh";

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public HomeRecyclerAdapter(Context context, List<Article> articles, RecyclerView homeRecyclerView) {
        this.articles = articles;
        this.context = context;
        this.homeRecyclerView = homeRecyclerView;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.news_cart, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        ViewHolder viewH = ((ViewHolder)viewHolder);
        Article article = articles.get(position);
        String title = article.getTitle();
        String source = article.getSource().name;
        long diff = (new Date().getTime() - article.getPublishedAt().getTime())/(60 * 60 * 1000);
        String sourceAndTime = String.format(SOURCE_FORMAT,source,diff);

        viewH.newsTitle.setText(title);
        viewH.newsSource.setText(sourceAndTime);
        Picasso.get()
                .load(articles.get(position).getUrlToImage())
                .placeholder(R.drawable.theme_aquare)
                .resize((int) context.getResources().getDimension(R.dimen.image_WH),(int) context.getResources().getDimension(R.dimen.image_WH))
                .into(viewH.newsImage);
    }

    @Override
    public int getItemCount() {
        return articles!=null ? articles.size() : 0;
    }

    private class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView newsTitle;
        private ImageView newsImage;
        private TextView newsSource;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newsTitle = itemView.findViewById(R.id.newstitle);
            newsImage = itemView.findViewById(R.id.newsimage);
            newsSource = itemView.findViewById(R.id.newssource);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (!(v instanceof ImageView)) {
                Article articleSelected = articles.get(this.getAbsoluteAdapterPosition());
                Intent intent = new Intent(context, NewsDetailActivity.class);
                intent.putExtra(NewsDetailActivity.PARAM_ARTICLE, articleSelected);

                //Animation
                LayoutAnimationController anim = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_animation_fall_down);
                homeRecyclerView.setLayoutAnimation(anim);
                homeRecyclerView.scheduleLayoutAnimation();

                context.startActivity(intent);
                ((Activity) context).overridePendingTransition( R.anim.slide_up_animation, R.anim.fade_exit_transition );
            }
        }
    }
}
