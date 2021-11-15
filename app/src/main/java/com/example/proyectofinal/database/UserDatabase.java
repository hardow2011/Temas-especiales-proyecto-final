package com.example.proyectofinal.database;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.proyectofinal.database.dao.UserDao;
import com.example.proyectofinal.database.models.User;

@Database(entities = {User.class}, version = 4)
public abstract class UserDatabase extends RoomDatabase {
    private static final String DATABASE_NAME = "userDatabase";
    private static final Object LOCK = new Object();
    private static UserDatabase sIntance;

    public static UserDatabase getInstance(Context context) {
        if (sIntance == null) {
            synchronized (LOCK) {
                sIntance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DATABASE_NAME)
                        .fallbackToDestructiveMigration()
                        .addCallback(roomCallback)
                        .build();
            }
        }
        return sIntance;
    }

    public abstract UserDao userDao();

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(sIntance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao userDao;

        private PopulateDbAsyncTask(UserDatabase db) {
            userDao = db.userDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            userDao.insertUser(new User("Louvens", "Raphael", "admin", "admin", true));
            userDao.insertUser(new User("Pedro", "García", "p.garcia", "1234567", false));
            userDao.insertUser(new User("Selena", "Caracas", "s_caracas", "qwerty", false));
            return null;
        }
    }


}