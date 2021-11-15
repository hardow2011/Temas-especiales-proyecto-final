package com.example.proyectofinal.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectofinal.R;
import com.example.proyectofinal.database.models.User;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder>{

    private List<User> users = new ArrayDeque<>();

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_item, parent, false);
        return new UserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User currentUser = users.get(position);
        holder.firstNameTextView.setText(currentUser.getFirstName());
        holder.lastNameTextView.setText(currentUser.getLastName());
        holder.usernameTextView.setText(currentUser.getUserName());
        holder.passwordTextView.setText(currentUser.getPassword());
        holder.isAdminCheckBox.setChecked(currentUser.getAdmin());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<User> users) {
        this.users = users;
        notifyDataSetChanged();
    }

    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    public class UserHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView firstNameTextView;
        public TextView lastNameTextView;
        public TextView usernameTextView;
        public TextView passwordTextView;
        public CheckBox isAdminCheckBox;

        public UserHolder(View itemView) {
            super(itemView);
            firstNameTextView = itemView.findViewById(R.id.text_view_firstName);
            lastNameTextView = itemView.findViewById(R.id.text_view_lastName);
            usernameTextView = itemView.findViewById(R.id.text_view_username);
            passwordTextView = itemView.findViewById(R.id.text_view_password);
            isAdminCheckBox = itemView.findViewById(R.id.checkBox_isAdmin);
        }

    }

}
