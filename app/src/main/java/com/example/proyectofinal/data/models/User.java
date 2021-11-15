package com.example.proyectofinal.data.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "uid")
    public int uid;
    @NonNull
    @ColumnInfo(name = "first_name")
    public String firstName;
    @ColumnInfo(name = "last_name")
    public String lastName;
    @NonNull
    @ColumnInfo(name = "user_name")
    public String userName;
    @NonNull
    @ColumnInfo(name = "password")
    public String password;

    public User(int uid, String firstName, String lastName, String userName, String password) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.password = password;
    }

    @Ignore
    public User(int uid, String firstName, String lastName) {
        this.uid = uid;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
