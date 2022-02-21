package com.team_three.medicalreminder.homeScreen.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
;

import com.google.android.material.navigation.NavigationBarView;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.LoginActivity;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;
import com.team_three.medicalreminder.databinding.HomeDrawerBinding;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding homeBinding;
    NavController navController;
    NavHostFragment navHostFragment;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String name = "";
    String email = "";


    @Override
    protected void onStart() {
        super.onStart();

        handleDrawer();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        handleToolBar();
        initNavigation();

        //
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.fragment_medication_list || navDestination.getId() == R.id.fragment_home || navDestination.getId() == R.id.fragment_taker_list || navDestination.getId() == R.id.helpRequistList) {
                    homeBinding.toolbar.setVisibility(View.VISIBLE);
                    homeBinding.navigatorViewHome.setVisibility(View.VISIBLE);

                } else {
                    homeBinding.toolbar.setVisibility(View.GONE);
                    homeBinding.navigatorViewHome.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        navController.navigateUp();
        return super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (homeBinding.homeActivityDrawer.isOpen()) {
            homeBinding.homeActivityDrawer.close();
        } else {
            super.onBackPressed();
        }
    }

    private void initNavigation() {
        navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.fragmentContainerView);

        navController =
                navHostFragment.getNavController();

        NavigationUI.setupWithNavController(homeBinding.navigatorViewHome,
                navController);
    }

    private void handleDrawer() {
        View v = homeBinding.drawerNav.getHeaderView(0);
        HomeDrawerBinding binding = HomeDrawerBinding.bind(v);
        if (!checkShared()) {
            binding.drawerName.setText(name);
            binding.drawerEditText.setText(email);
            binding.drawerEditText.setClickable(false);
        } else {
            binding.drawerName.setText(R.string.gusest);
            binding.drawerEditText.setText(R.string.create_profile);
            binding.drawerEditText.setClickable(true);
            binding.drawerEditText.setOnClickListener(view -> {
                homeBinding.homeActivityDrawer.close();
                Intent outComing = new Intent(this, LoginActivity.class);
                startActivity(outComing);
            });
        }

    }

    private void handleToolBar() {
        setSupportActionBar(homeBinding.toolbar);
        getSupportActionBar().setTitle("");
        if (checkShared()) {
            homeBinding.userNameToolBar.setText(R.string.gusest);
        } else {
            homeBinding.userNameToolBar.setText(name);

        }

        homeBinding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (homeBinding.homeActivityDrawer.isOpen()) {
                    homeBinding.homeActivityDrawer.close();
                } else {
                    homeBinding.homeActivityDrawer.openDrawer(GravityCompat.START);
                }


            }
        });

    }

    private boolean checkShared() {
        sharedPref = getSharedPreferences(RegisterFragment.SHAREDfILE, MODE_PRIVATE);
        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
        name = sharedPref.getString(RegisterFragment.USER_NAME, "null");
        Log.i("TAG", "checkShared: " + name);
        Log.i("TAG", "checkShared: " + email);
        return (name.equals("null") && email.equals("null"));
    }


}



