package com.example.instagramapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.instagramapp.LoginActivity;
import com.example.instagramapp.Post;
import com.example.instagramapp.ProfilePostsAdapter;
import com.example.instagramapp.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;
/**
 * Fragment for Bottom Navigation Bar that allows user to see their profile, and the feed of posts they have authored.
 */
public class ProfileFragment extends Fragment {
    private RecyclerView rvProfile;
    public static final String TAG = "PostsFragment";
    public static final String KEY_PROFILE = "profilePicture";
    protected ProfilePostsAdapter adapter;
    protected List<Post> allPosts;
    TextView tvUsernameProfile;
    ImageView ivUserProfileImage;
    Button btnLogOutProfile;

    /** Constructor */
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    /** Sets up Profile Fragment. Populates recyclerView of posts from current user, and sets up information about user profile. */
    /**This event is triggered shortly after the onCreateView. View setup is here*/
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfile = view.findViewById(R.id.rvProfilePosts);
        tvUsernameProfile = view.findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(ParseUser.getCurrentUser().getUsername());
        ivUserProfileImage = view.findViewById(R.id.ivProfileImage);
        btnLogOutProfile = view.findViewById(R.id.btnLogOutProfile);

        allPosts = new ArrayList<>();
        //create the adapter
        adapter = new ProfilePostsAdapter(getContext(), allPosts);
        //set the adapter on the recycler view
        rvProfile.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // query posts from Instagram App
        queryPosts();

        //set profile image for the signed in user
        ParseFile profileImage = ParseUser.getCurrentUser().getParseFile(KEY_PROFILE);
        Glide.with(getContext())
                .load(profileImage.getUrl())
                .circleCrop()
                .error(R.drawable.ufi_heart)
                .into(ivUserProfileImage);

        btnLogOutProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ParseUser.logOut();
                ParseUser currentUser = ParseUser.getCurrentUser(); // this will now be null
                //send user back to the log in page
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
            }
        });
    }

    /** Begins a Parse Query in a background thread, getting all of the posts the user has authored. */
    /**The posts are added to a list, and the adapter is notified of the data change.*/
    protected void queryPosts() {
        // specify what type of data we want to query - Post.class
        ParseQuery<Post> query = ParseQuery.getQuery(Post.class);
        // include data referred by user key
        query.include(Post.KEY_USER);
        //only query posts of the currently signed in user
        query.whereEqualTo(Post.KEY_USER, ParseUser.getCurrentUser());
        // limit query to latest 20 items
        query.setLimit(20);
        // order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");
        // start an asynchronous call for posts
        query.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> posts, ParseException e) {
                // check for errors
                if (e != null) {
                    Log.e(TAG, "Issue with getting posts", e);
                    return;
                }

                // for debugging purposes let's print every post description to logcat
                for (Post post : posts) {
                    Log.i(TAG, "Post: " + post.getDescription() + ", username: " + post.getUser().getUsername());
                }

                // save received posts to list and notify adapter of new data
                allPosts.addAll(posts);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
