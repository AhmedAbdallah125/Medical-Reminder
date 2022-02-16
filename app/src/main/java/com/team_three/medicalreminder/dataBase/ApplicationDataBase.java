package com.team_three.medicalreminder.dataBase;


import android.content.Context;

import com.team_three.medicalreminder.model.Converters;
import com.team_three.medicalreminder.model.MedicationPOJO;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@TypeConverters(Converters.class)
@Database(entities = {MedicationPOJO.class}, version = 1, exportSchema = false)
public abstract class ApplicationDataBase extends RoomDatabase {
    private static ApplicationDataBase instance;
    public static final String DATABASE_NAME = "project";

    public static ApplicationDataBase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    ApplicationDataBase.class, DATABASE_NAME).build();
        }
        return instance;
    }

    public abstract DAO getDao();
}