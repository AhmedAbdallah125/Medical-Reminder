package com.team_three.medicalreminder.dataBase;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.medicationList.view.AcrtiveMedsFragment;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;


public class ConcreteLocalClass implements LocalSourceInterface {

    private final DAO dao;
    private final LiveData<List<MedicationPOJO>> storedMedications;
    private static ConcreteLocalClass concreteLocalClass = null;

    private ConcreteLocalClass(Context context) {
        /*MovieDataBase movieDataBase = MovieDataBase.getMovieDataBaseInstance(context.getApplicationContext());
        movieDao = movieDataBase.getMovieDao();
        storedMedications = movieDao.getAllMovies();*/
        ApplicationDataBase applicationDatabase = ApplicationDataBase.getInstance(context);
        dao = applicationDatabase.getDao();
        storedMedications = dao.getAllMedication();
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
        Log.i("TAG", "insertMedication: "+medication.getMedicationName());
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.insertMedication(medication);
                Log.i("TAG", "run: ");
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

    @Override
    public LiveData<MedicationPOJO> getMedications(int id) {
        return dao.getMedications(id);
    }

    @Override
    public void updateMedications(MedicationPOJO medicationPOJO) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                dao.updateMedications(medicationPOJO);
            }
        }.start();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getActiveMedications() {
        return dao.getActiveMedications();
    }

    @Override
    public LiveData<List<MedicationPOJO>> getInactiveMedications() {
        return dao.getInactiveMedications();
    }




}
