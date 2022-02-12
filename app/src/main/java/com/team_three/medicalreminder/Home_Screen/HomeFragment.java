package com.team_three.medicalreminder.Home_Screen;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

private FragmentHomeBinding fragmentHomeBinding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         fragmentHomeBinding=FragmentHomeBinding.inflate(inflater,container,false);
         return fragmentHomeBinding.getRoot();
    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentHomeBinding=null;
    }
}