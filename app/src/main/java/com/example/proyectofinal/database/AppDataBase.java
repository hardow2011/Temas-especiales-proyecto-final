package com.example.proyectofinal.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.proyectofinal.database.dao.CategoryDao;
import com.example.proyectofinal.database.dao.ProductDao;
import com.example.proyectofinal.database.models.Carousel;
import com.example.proyectofinal.database.models.Category;
import com.example.proyectofinal.database.models.Product;

@Database(entities = {Category.class, Product.class, Carousel.class}, version = 3)
public abstract class AppDataBase extends RoomDatabase {
    private static final String DATABASE_NAME = "csti";
    private static final Object LOCK = new Object();
    private static AppDataBase sIntance;

    public static AppDataBase getInstance(Context context) {
        if (sIntance == null) {
            synchronized (LOCK) {
                sIntance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .build();
            }
        }
        return sIntance;
    }

    public abstract CategoryDao categoryDao();

    public abstract ProductDao productDao();


}
