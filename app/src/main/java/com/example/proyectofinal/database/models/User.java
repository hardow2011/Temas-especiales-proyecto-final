package com.example.proyectofinal.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private int id;
    @NonNull
    private String firstName;
    private String lastName;
    @NonNull
    private String userName;
    @NonNull
    private String password;
    @NonNull
    private Boolean isAdmin;

    @Ignore
    public User(@NonNull String firstName, String lastName, @NonNull String userName, @NonNull String password, @NonNull Boolean isAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public User(int id, @NonNull String firstName, String lastName, @NonNull String userName, @NonNull String password, @NonNull Boolean isAdmin) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
        this.isAdmin = isAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public String getUserName() {
        return userName;
    }

    public void setUserName(@NonNull String userName) {
        this.userName = userName;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    @NonNull
    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(@NonNull Boolean admin) {
        isAdmin = admin;
    }
}
