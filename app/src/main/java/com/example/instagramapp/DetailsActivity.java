package com.example.instagramapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseUser;

import org.parceler.Parcels;

import java.util.Date;
/**
 * Activity that shows the post's description and user information for the Post clicked, unwrapping the data using Parcels.
 */
public class DetailsActivity extends AppCompatActivity {
    Post post;

    public static final String KEY_PROFILE = "profilePicture";
    TextView tvDescriptionDetails;
    TextView tvTimeDetails;
    TextView tvUsernameDetails;
    ImageView ivProfileDetails;

    /** Sets up the Detailed view of post, unwrapping the data about the clicked post using Parcels. */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        tvDescriptionDetails = findViewById(R.id.tvDescriptionDetails);
        tvTimeDetails = findViewById(R.id.tvTimeDetails);
        tvUsernameDetails = findViewById(R.id.tvUsernameDetails);
        ivProfileDetails = findViewById(R.id.ivProfileDetails);

        //unwrap post's data from the pass
        post = (Post) Parcels
                .unwrap(getIntent()
                        .getParcelableExtra(Post.class.getSimpleName()));

        tvDescriptionDetails.setText(post.getDescription());
        //calculate the time since the post was made and set in time text view
        Date createdAt = post.getCreatedAt();
        String timeAgo = Post.calculateTimeAgo(createdAt);
        tvTimeDetails.setText(timeAgo);
        //set rest of comments/details page
        tvUsernameDetails.setText(post.getUser().getUsername());
        //set profile image for the signed in user
        ParseFile profileImage = post.getUser().getParseFile(KEY_PROFILE);
        Glide.with(this)
                .load(profileImage.getUrl())
                .circleCrop()
                .error(R.drawable.ufi_heart)
                .into(ivProfileDetails);

    }
}