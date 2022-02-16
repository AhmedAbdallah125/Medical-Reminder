package com.team_three.medicalreminder.model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.team_three.medicalreminder.dataBase.DAO;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;

import java.util.List;

public class Repository implements RepositoryInterface{
    private Context context;
    private DAO movieDAO;
    LocalSourceInterface localSourceInterface;
    private static Repository repo= null;
    private Repository(LocalSourceInterface localSourceInterface,Context context){
        this.context = context;
        this.localSourceInterface = localSourceInterface;
    }

    public static Repository getInstance(LocalSourceInterface localSourceInterface,Context context){
        if(repo==null){
            repo = new Repository(localSourceInterface,context);
        }
        return repo;
    }
    @Override
    public LiveData<List<MedicationPOJO>> getAllMedication() {

        return localSourceInterface.getAllMedication();
    }

    @Override
    public void insertMedication(MedicationPOJO medication) {
        localSourceInterface.insertMedication(medication);
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        localSourceInterface.deleteMedication(medication);
    }
}
