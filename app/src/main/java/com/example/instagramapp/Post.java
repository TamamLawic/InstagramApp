package com.example.instagramapp;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("Post")
public class Post extends ParseObject {
    public static final String KEY_DESCRIPTION = "description";
    public static final String KEY_IMAGE = "image";
    public static final String KEY_USER = "user";

    //returns post's description
    public String getDescription() {
        return getString(KEY_DESCRIPTION);
    }

    //sets the description for the post
    public void setDescription(String description) {
        put(KEY_DESCRIPTION, description);
    }

    //returns the image for the post
    public ParseFile getImage() {
        return getParseFile(KEY_IMAGE);
    }

    //sets the image for the post
    public void setImage(ParseFile parseFile) {
        put(KEY_IMAGE, parseFile);
    }

    //returns the user that created the post
    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    //set the user that created the post
    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }
}
