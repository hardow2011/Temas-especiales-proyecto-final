package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.proyectofinal.adapters.UserAdapter;
import com.example.proyectofinal.database.models.User;
import com.example.proyectofinal.viewModel.UserViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView userRecyclerView = findViewById(R.id.user_recycler_view);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setHasFixedSize(true);

        final UserAdapter userAdapter = new UserAdapter();
        userRecyclerView.setAdapter(userAdapter);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        userViewModel.findAllUsers().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userAdapter.setUsers(users);
            }
        });

    }
}