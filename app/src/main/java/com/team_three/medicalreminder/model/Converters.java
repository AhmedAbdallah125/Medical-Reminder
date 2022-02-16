package com.team_three.medicalreminder.model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.room.TypeConverter;

public class Converters {

    Gson gson = new Gson();

    // hash map String int
    // array

    @TypeConverter
    public String fromIsTakenListToString(List<Boolean> isTakenList) {
        return gson.toJson(isTakenList);
    }

    @TypeConverter
    public List<Boolean> fromStringToGenre(String isTakenListString) {
        if (isTakenListString == null) {
            return Collections.emptyList();
        } else {
            Type list = new TypeToken<List<Boolean>>() {
            }.getType();
            return gson.fromJson(isTakenListString, list);
        }
    }
    // for hash map
    @TypeConverter
    public String fromHashMapToString(Map<String, Integer> timeAndDose) {
        return gson.toJson(timeAndDose);
    }

    @TypeConverter
    public Map<String, Integer> fromStringToHashMap(String timeAndDoseString) {
        if (timeAndDoseString == null) {
            return  Collections.<String, Integer>emptyMap();
        } else {
            Type list = new TypeToken<Map<String, Integer>>() {
            }.getType();
            return gson.fromJson(timeAndDoseString, list);
        }
    }
}