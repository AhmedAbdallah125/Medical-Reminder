package com.team_three.medicalreminder.Home_Screen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.Home_Screen.model.MedicineDetails;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentHomeBinding;

import java.util.ArrayList;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding fragmentHomeBinding;
    HomeAdapter homeAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("TAG", "onCreateView:Fragment ");

        ArrayList<MedicineDetails> medicineDetails = new ArrayList<>();
        medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am"));
        medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am")); medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        homeAdapter = new HomeAdapter(this.getContext(), medicineDetails);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setLayoutManager(layoutManager);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentHomeBinding = null;
    }
}