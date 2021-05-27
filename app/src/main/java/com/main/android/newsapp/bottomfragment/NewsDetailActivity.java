package com.main.android.newsapp.bottomfragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.provider.Browser;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.main.android.newsapp.R;
import com.main.android.newsapp.models.Article;
import com.squareup.picasso.Picasso;

import java.util.Date;

public class NewsDetailActivity extends AppCompatActivity {

    public static final String PARAM_ARTICLE = "ARTICLE";
    private Article article;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news_detail);
        getArticleParam();
        updateArticleViews();
        setupToolbar();
        addButtonListeners();
    }

    private void addButtonListeners() {
        ImageButton share = findViewById(R.id.iv_share);
        Button readFull = findViewById(R.id.btn_read_full);

        share.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            String shareText = article.getTitle() + "\n" + article.getUrl();
            intent.putExtra(Intent.EXTRA_TEXT, shareText);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.setType("text/plain");
            startActivity(intent);
        });

        readFull.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getUrl()));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra(Browser.EXTRA_APPLICATION_ID, getPackageName());
            startActivity(intent);
        });
    }

    private void setupToolbar() {
        Toolbar closeBtn = findViewById(R.id.toolbar);
        setSupportActionBar(closeBtn);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    private void getArticleParam() {
        Intent intent = getIntent();
        if (intent != null ) {
            article = (Article) intent.getSerializableExtra(PARAM_ARTICLE);
        }
    }

    private void updateArticleViews() {
        String title = article.getTitle();
        String source = article.getSource().name;
        Date publishedAt = article.getPublishedAt();
        long diff = (new Date().getTime() - publishedAt.getTime())/(60 * 60 * 1000);
        String time = ""+diff +"h";
        String description = article.getDescription();
        String content = article.getContent();
        String author = article.getAuthor();

        ((TextView)findViewById(R.id.tv_news_title)).setText(title);
        ((TextView)findViewById(R.id.tv_news_source)).setText(source);
        Picasso.get()
                .load(article.getUrlToImage())
                .placeholder(R.drawable.theme_aquare)
                .resize((int) getResources().getDimension(R.dimen.image_WH),(int) getResources().getDimension(R.dimen.image_WH))
                .into(((ImageView)findViewById(R.id.iv_news_image)));
        ((TextView)findViewById(R.id.tv_news_content)).setText(content);
        ((TextView)findViewById(R.id.tv_news_desc)).setText(description);
        ((TextView)findViewById(R.id.tv_time)).setText(time);
        assert author != null;
        ((TextView)findViewById(R.id.tv_news_author)).setText(author);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.fade_enter_transition, R.anim.slide_down_animation);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}