package com.example.proyectofinal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.proyectofinal.database.models.Category;

import java.util.List;

@Dao
public interface CategoryDao {

    //CRUD
    @Query("SELECT * FROM category ORDER BY uid")
    LiveData<List<Category>> findAll();
//
//    @Query("SELECT * FROM category WHERE active = 'TRUE' ORDER BY uid")
//    List<Category> findAllActive();

    @Query("SELECT * FROM category WHERE uid = :uid")
    Category find(int uid);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Category category);

    @Update
    void update(Category category);

    @Delete
    void delete (Category category);
}
