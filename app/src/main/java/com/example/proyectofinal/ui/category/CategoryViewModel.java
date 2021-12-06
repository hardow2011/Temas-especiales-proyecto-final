package com.example.proyectofinal.ui.category;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectofinal.database.AppDataBase;
import com.example.proyectofinal.database.models.Category;

import java.util.List;

public class CategoryViewModel extends ViewModel {

    private final LiveData<List<Category>> listLiveData;


    public CategoryViewModel(@NonNull AppDataBase dataBase) {

        listLiveData = dataBase.categoryDao().findAll();
    }

    public LiveData<List<Category>> getListLiveData() {
        return listLiveData;
    }
}
