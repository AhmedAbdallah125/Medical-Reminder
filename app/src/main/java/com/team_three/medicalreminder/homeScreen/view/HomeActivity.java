package com.team_three.medicalreminder.homeScreen.view;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
;

import com.google.android.material.navigation.NavigationView;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.LoginActivity;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;
import com.team_three.medicalreminder.databinding.HomeDrawerBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
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
    Repository myRepository;


    // synchronisation when network is connected
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeBinding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(homeBinding.getRoot());

        handleToolBar();
        initNavigation();
        handleDrawerMenu();
//        setSignOut(true);
        //
        navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
            @Override
            public void onDestinationChanged(@NonNull NavController navController, @NonNull NavDestination navDestination, @Nullable Bundle bundle) {
                if (navDestination.getId() == R.id.fragment_medication_list ||
                        navDestination.getId() == R.id.fragment_home ||
                        navDestination.getId() == R.id.fragment_taker_list ||
                        navDestination.getId() == R.id.helpRequistList) {
                    homeBinding.toolbar.setVisibility(View.VISIBLE);
                    homeBinding.navigatorViewHome.setVisibility(View.VISIBLE);
                    handleDrawer();
                    handleToolBar();

                } else {
                    homeBinding.toolbar.setVisibility(View.GONE);
                    homeBinding.navigatorViewHome.setVisibility(View.GONE);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        handleDrawer();
        handleDrawerMenu();
        handleToolBar();
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
            // handle menu
//            homeBinding.drawerNav.getMenu().addSubMenu("");

        } else {
            binding.drawerName.setText(R.string.gusest);
            binding.drawerEditText.setText(R.string.create_profile);
            binding.drawerEditText.setClickable(true);
//            homeBinding.drawerNav.getMenu().removeItem(R.id.nav_logout);

            binding.drawerEditText.setOnClickListener(view -> {
                homeBinding.homeActivityDrawer.close();
                Intent outComing = new Intent(this, LoginActivity.class);
                startActivity(outComing);
            });
        }


    }

    private void handleDrawerMenu() {
        View v = homeBinding.drawerNav.getHeaderView(0);
        HomeDrawerBinding binding = HomeDrawerBinding.bind(v);
        if (!binding.drawerEditText.getText().equals(R.string.create_profile)) {
            homeBinding.drawerNav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Log.i("TAG", "onNavigationItemSelected: abdddddddd");
                    if (item.getItemId() == R.id.nav_logout) {
                        setSignOut();
                        return true;
                    }
                    return false;

                }
            });
        } else {

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


    private void setSignOut() {
        Log.i("TAG", "setSignOut:Activty ");
        // sign out
        handleSignOutCondition();
        // update all
        handleDrawer();
        handleToolBar();
    }



    private void handleSignOutCondition() {
        editor = sharedPref.edit();
        editor.putString(RegisterFragment.USER_EMAIL, "null");
        editor.putString(RegisterFragment.USER_NAME, "null");
        editor.apply();
    }
}



