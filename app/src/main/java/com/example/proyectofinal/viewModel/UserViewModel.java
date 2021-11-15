package com.example.proyectofinal.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyectofinal.database.dao.UserDao;
import com.example.proyectofinal.database.models.User;
import com.example.proyectofinal.repositories.UserRepository;

import java.util.List;

public class UserViewModel extends AndroidViewModel {

    private UserRepository userRepository;
    private LiveData<List<User>> allUsers;

    public UserViewModel(@NonNull Application application) {
        super(application);
        userRepository = new UserRepository(application);
        allUsers = userRepository.findAllUsers();
    }

    public void insertUser(User user) {
        userRepository.insert(user);
    }

    public void updateUser(User user) {
        userRepository.update(user);
    }

    public void deleteUser(User user) {
        userRepository.delete(user);
    }

    public void deleteAllUsers() {
        userRepository.deleteAllUsers();
    }

    public LiveData<List<User>> findAllUsers() {
        return allUsers;
    }

}
