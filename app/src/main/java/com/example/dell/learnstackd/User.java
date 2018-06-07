package com.example.dell.learnstackd;

import android.content.Context;
import android.content.SharedPreferences;

public class User {
    Context context;
    SharedPreferences sharedPreferences;
    private String email;

    public String getFname() {
        fname=sharedPreferences.getString("fname","");
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
        sharedPreferences.edit().putString("fname",fname);
        sharedPreferences.edit().apply();
    }

    public String getUser_id() {
        user_id=sharedPreferences.getString("user_id","");
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
        sharedPreferences.edit().putString("user_id",user_id);
        sharedPreferences.edit().apply();
    }

    private String fname;
    private String user_id;

    public String getEmail() {
        email=sharedPreferences.getString("email","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("email",email);
        sharedPreferences.edit().apply();
    }

    public User(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("Login Details",Context.MODE_PRIVATE);

    }
    public void logOut(){
        sharedPreferences.edit().clear().commit();
    }
}
