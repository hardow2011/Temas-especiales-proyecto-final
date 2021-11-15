package com.example.proyectofinal.viewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectofinal.database.dao.UserDao;
import com.example.proyectofinal.database.models.User;

import java.util.List;

public class UserViewModel extends ViewModel {

    private LiveData<List<User>> userListLiveData;

    public UserViewModel(@NonNull UserDao userDao){

        userListLiveData = userDao.findAllUsers();
    }

    public LiveData<List<User>> getUserListLiveData() {
        return userListLiveData;
    }

}
