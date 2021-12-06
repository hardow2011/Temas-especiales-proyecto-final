package com.example.proyectofinal.database.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.Objects;

@Entity
public class User implements Serializable {

    public enum ROL {SELLER, CUSTOMER}

    public int uid;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public ROL rol;
    public String contact;
    public String birthday;
    public String photo;

    public  User() {
    }

    public int getUid() {
        return uid;
    }

    public User setUid(int uid) {
        this.uid = uid;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public ROL getRol() {
        return rol;
    }

    public User setRol(ROL rol) {
        this.rol = rol;
        return this;
    }

    public String getContact() {
        return contact;
    }

    public User setContact(String contact) {
        this.contact = contact;
        return this;
    }

    public String getBirthday() {
        return birthday;
    }

    public User setBirthday(String birthday) {
        this.birthday = birthday;
        return this;
    }

    public String getPhoto() {
        return photo;
    }

    public User setPhoto(String photo) {
        this.photo = photo;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getUid() == user.getUid();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUid());
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", rol=" + rol +
                ", contact='" + contact + '\'' +
                ", birthday='" + birthday + '\'' +
                ", photo='" + photo + '\'' +
                '}';
    }
}
