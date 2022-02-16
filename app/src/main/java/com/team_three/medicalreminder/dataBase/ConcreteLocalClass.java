package com.team_three.medicalreminder.dataBase;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

public class ConcreteLocalClass implements LocalSourceInterface{

    private final DAO dao = null;
    private final LiveData<List<MedicationPOJO>> storedMedications;
    private static ConcreteLocalClass concreteLocalClass = null;

    private ConcreteLocalClass(Context context) {
        /*MovieDataBase movieDataBase = MovieDataBase.getMovieDataBaseInstance(context.getApplicationContext());
        movieDao = movieDataBase.getMovieDao();
        storedMedications = movieDao.getAllMovies();*/
        storedMedications=dao.getAllMedication();
    }

    public static ConcreteLocalClass getConcreteLocalClassInstance(Context context) {
        if (concreteLocalClass == null) {
            concreteLocalClass = new ConcreteLocalClass(context);
        }
        return concreteLocalClass;
    }

    @Override
    public LiveData<List<MedicationPOJO>> getAllMedication() {
        return storedMedications;
    }

    @Override
    public void insertMedication(MedicationPOJO medication) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.insertMedication(medication);
            }
        }.start();
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.deleteMedication(medication);
            }
        }.start();
    }
}
