package com.team_three.medicalreminder.dataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;

@Dao
public interface DAO {
    @Query("Select * from medications" )
    LiveData<List<MedicationPOJO>>  getAllMedication();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertMedication(MedicationPOJO medication);

    @Delete
    void deleteMedication(MedicationPOJO medication);
}
