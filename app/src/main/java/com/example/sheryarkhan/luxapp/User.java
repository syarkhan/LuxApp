package com.example.sheryarkhan.luxapp;

/**
 * Created by Sheryar Khan on 7/27/2017.
 */

public class User {

    private String Name;
    private String Email;


    public User(String Name,String Email)
    {
        this.Name = Name;
        this.Email = Email;
    }

    public String getName()
    {
        return this.Name;
    }

    public String getEmail()
    {
        return this.Email;
    }

    public void setName(String Name)
    {
        this.Name=Name;
    }

    public void setEmail(String Email)
    {
        this.Email=Email;
    }



}
