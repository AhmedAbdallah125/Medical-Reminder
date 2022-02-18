package com.team_three.medicalreminder.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

@Dao
public interface DAO {
    @Query("Select * from medications")
    LiveData<List<MedicationPOJO>> getAllMedication();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMedication(MedicationPOJO medication);


    //for specified medicine
    @Query("SELECT * FROM Medications WHERE id =:id")
    LiveData<MedicationPOJO> getMedications(int id);


    @Delete
    void deleteMedication(MedicationPOJO medicationPOJO);

    @Update()
    void updateMedications(MedicationPOJO medicationPOJO);


    //for specified medicine
    @Query("SELECT * FROM Medications WHERE (:data Between startDate AND endDate) AND isActive=1 ")
    LiveData<MedicationPOJO> getMedicationDay(long data);

}
