package com.example.proyectofinal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectofinal.database.models.User;

import java.util.List;

@Dao
public interface UserDao {

    // The conflict strategy defines what happens,
    // if there is an existing entry.
    // The default action is ABORT.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(User user);

    @Insert
    void insertAllUsers(User... users);

    // Update multiple entries with one call.
    @Update
    public void updateAllUsers(User... users);

    @Update
    public void updateUser(User user);

    // Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM user")
    void deleteAllUsers();

    @Delete
    void deleteUser(User user);

    // Simple query without parameters that returns values.
    @Query("SELECT * from user ORDER BY id ASC")
    LiveData<List<User>> findAllUsers();

    @Query("SELECT * FROM user WHERE id IN (:userIds)")
    LiveData<List<User>> findAllUsersByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE userName = :userName AND password = :password")
    User login(String userName, String password);


}
