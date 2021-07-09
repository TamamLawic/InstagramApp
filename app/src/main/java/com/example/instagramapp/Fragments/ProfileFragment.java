package com.example.instagramapp.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class ProfileFragment extends Fragment {
    private RecyclerView rvProfile;
    public static final String TAG = "PostsFragment";
    public static final String KEY_PROFILE = "profilePicture";
    protected ProfilePostsAdapter adapter;
    protected List<Post> allPosts;
    TextView tvUsernameProfile;
    ImageView ivUserProfileImage;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    // Runs shortly after onCreateView is complete, sets up Recycler
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvProfile = view.findViewById(R.id.rvProfilePosts);
        tvUsernameProfile = view.findViewById(R.id.tvUsernameProfile);
        tvUsernameProfile.setText(ParseUser.getCurrentUser().getUsername());
        ivUserProfileImage = view.findViewById(R.id.ivProfileImage);

        allPosts = new ArrayList<>();
        //create the adapter
        adapter = new ProfilePostsAdapter(getContext(), allPosts);
        //set the adapter on the recycler view
        rvProfile.setAdapter(adapter);
        // set the layout manager on the recycler view
        rvProfile.setLayoutManager(new GridLayoutManager(getContext(), 3));
        // query posts from Parstagram
        queryPosts();

        //set profile image for the signed in user
        ParseFile profileImage = ParseUser.getCurrentUser().getParseFile(KEY_PROFILE);
        Glide.with(getContext())
                .load(profileImage.getUrl())
                .circleCrop()
                .error(R.drawable.ufi_heart)
                .into(ivUserProfileImage);
    }

    //get all of the users posts
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
