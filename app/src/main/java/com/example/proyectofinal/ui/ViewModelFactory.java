package com.example.proyectofinal.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.proyectofinal.database.AppDataBase;
import com.example.proyectofinal.ui.category.CategoryViewModel;
import com.example.proyectofinal.ui.product.ProductViewModel;

import org.jetbrains.annotations.NotNull;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final AppDataBase dataBase;

    public ViewModelFactory(@NonNull Context context) {
        this.dataBase = AppDataBase.getInstance(context);

    }

    @NonNull
    @NotNull
    @Override
    public <T extends ViewModel> T create(@NonNull @NotNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CategoryViewModel.class)) {
            return (T) new CategoryViewModel(dataBase);
        } else if (modelClass.isAssignableFrom(ProductViewModel.class)) {
            return (T) new ProductViewModel(dataBase);
        } else {
            throw new IllegalArgumentException("Unknown ViewModel Class");
        }
    }
}
