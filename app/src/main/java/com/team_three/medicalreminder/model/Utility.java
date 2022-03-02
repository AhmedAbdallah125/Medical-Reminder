package com.team_three.medicalreminder.model;


import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;

public class Utility {

    private static SharedPreferences sharedPref;
    private static String email;

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static String checkShared(Context context){
        sharedPref = context.getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
        return email;
    }

}
