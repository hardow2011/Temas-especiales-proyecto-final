package com.example.proyectofinal.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.proyectofinal.database.models.Carousel;
import com.example.proyectofinal.database.models.Product;
import com.example.proyectofinal.database.models.relationships.ProductWithCarousel;

import java.util.List;

@Dao
public interface ProductDao {

    //CRUD
    @Query("SELECT * FROM product ORDER BY itemCode")
    LiveData<List<ProductWithCarousel>> findAll();


    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Product product);

    @Query("DELETE FROM carousel WHERE product = :uid")
    void deleteCarousels(int uid);

    @Insert
    void insertCarousels(List<Carousel> carousels);

    @Transaction
    @Update
    void update(Product product);

    @Update
    void updateCarousels(List<Carousel> carousels);

    @Transaction
    @Delete
    void delete (Product product);

    @Delete
    void deleteCarousels(List<Carousel> carousels);

}
