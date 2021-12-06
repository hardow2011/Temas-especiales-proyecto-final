package com.example.proyectofinal.ui.product;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectofinal.database.AppDataBase;
import com.example.proyectofinal.database.models.relationships.ProductWithCarousel;

import java.util.List;

public class ProductViewModel extends ViewModel {

    private final LiveData<List<ProductWithCarousel>> listLiveData;


    public ProductViewModel(@NonNull AppDataBase dataBase) {

        listLiveData = dataBase.productDao().findAll();
    }

    public LiveData<List<ProductWithCarousel>> getListLiveData() {
        return listLiveData;
    }


}
