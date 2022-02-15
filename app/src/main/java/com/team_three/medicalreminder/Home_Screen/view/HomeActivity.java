package com.team_three.medicalreminder.Home_Screen.view;


import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationBarView;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;

import androidx.appcompat.app.AppCompatActivity;
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

    private NavigationBarView.OnItemSelectedListener itemSelectedListener
            = item -> {

        switch (item.getItemId()) {
            case R.id.nav_menu_home:
                selectFragment = new HomeFragment();
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(
                R.id.HomeDyanmicFragmentContainer, selectFragment)
                .commit();
        return true;

    };

    private void handleToolBar() {
        getSupportActionBar().hide();
        homeBinding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomeActivity.this, "ss", Toast.LENGTH_SHORT).show();

            }
        });
    }
}


