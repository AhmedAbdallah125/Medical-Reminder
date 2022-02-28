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

import io.reactivex.Flowable;
import io.reactivex.Single;

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

    @Query("SELECT * FROM Medications WHERE isActive =1")
    LiveData<List<MedicationPOJO>> getActiveMedications();

    @Query("SELECT * FROM Medications WHERE isActive =0")
    LiveData<List<MedicationPOJO>> getInactiveMedications();

    //for specified medicine
    @Query("SELECT * FROM Medications WHERE (:data Between startDate AND endDate) AND isActive=1 ")
    LiveData<List<MedicationPOJO>> getMedicationDay(long data);

    // get observable DB
    @Query("SELECT * FROM Medications WHERE (:data Between startDate AND endDate) AND isActive=1 ")
    Single<List<MedicationPOJO>> getMedicationDayWorkManger(long data);

    @Query("SELECT * FROM Medications WHERE (:time Between startDate AND endDate) AND fillReminder =1 AND isActive=1")
    Single<List<MedicationPOJO>> getRefilReminderList(long time);
}