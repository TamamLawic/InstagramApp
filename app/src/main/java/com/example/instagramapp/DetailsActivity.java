package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import org.parceler.Parcels;

import java.util.Date;

public class DetailsActivity extends AppCompatActivity {
    Post post;

    TextView tvDescriptionDetails;
    TextView tvTimeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimeDetails = findViewById(R.id.tvTimeDetails);

        //unwrap post's data from the pass
        post = (Post) Parcels
                .unwrap(getIntent()
                        .getParcelableExtra(Post.class.getSimpleName()));

        tvDescriptionDetails.setText(post.getDescription());
        //calculate the time since the post was made
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeDetails.setText(timeAgo);

    }
}