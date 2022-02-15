package com.team_three.medicalreminder.homeScreen.view;


import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.material.navigation.NavigationBarView;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;
import com.team_three.medicalreminder.databinding.FragmentHomeBinding;
import com.team_three.medicalreminder.medicationList.view.MedicationListFragment;
import com.team_three.medicalreminder.taker.view.AddTaker;
import com.team_three.medicalreminder.databinding.HomeDrawerBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding homeBinding;
    Fragment selectFragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());
        handleToolBar();

        selectFragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(
                R.id.HomeDyanmicFragmentContainer, selectFragment)
                .commit();
        homeBinding.navigatorViewHome.setOnItemSelectedListener(itemSelectedListener);



    }
    @Override
    public void onBackPressed() {
        if(homeBinding.homeActivityDrawer.isOpen()){
            homeBinding.homeActivityDrawer.close();
        }else{
            finish();
        }
    }


    private NavigationBarView.OnItemSelectedListener itemSelectedListener
            = item -> {


        switch (item.getItemId()) {
            case R.id.nav_menu_home:
                selectFragment = new HomeFragment();
                break;
            case R.id.nav_menu_medication:
                selectFragment = new MedicationListFragment();
                break;
            case R.id.nav_menu_updates:
                selectFragment = new AddTaker();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(
                R.id.HomeDyanmicFragmentContainer, selectFragment)
                .commit();
        return true;

    };



    private void handleToolBar() {
        setSupportActionBar(homeBinding.toolbar);
        getSupportActionBar().setTitle("");
        homeBinding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeBinding.homeActivityDrawer.openDrawer(GravityCompat.START);

                homeBinding.navigatorViewHome.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                return false;
                            }
                        });

            }
        });

    }



}



