package org.cleartrip.entities;

public class User{
    int userId;
    String name;
    String preferredGenre;

    public User(int userId, String name, String preferredGenre){
        this.userId = userId;
        this.name = name;
        this.preferredGenre = preferredGenre;
    }
}

//getters and setters, encapsulation is missing