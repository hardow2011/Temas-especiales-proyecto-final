package com.example.proyectofinal.data.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectofinal.data.models.User;

import java.util.List;

@Dao
public interface UserDao {

    // The conflict strategy defines what happens,
    // if there is an existing entry.
    // The default action is ABORT.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Insert
    void insertAll(User... users);

    // Update multiple entries with one call.
    @Update
    public void updateAll(User... users);

    @Update
    public void update(User user);

    // Simple query that does not take parameters and returns nothing.
    @Query("DELETE FROM user")
    void deleteAll();

    @Delete
    void delete(User user);

    // Simple query without parameters that returns values.
    @Query("SELECT * from user ORDER BY uid ASC")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> getAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE user_name = :userName AND password = :password")
    User login(String userName, String password);


}
