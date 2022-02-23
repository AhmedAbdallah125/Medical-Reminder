package com.team_three.medicalreminder.homeScreen.view;


import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
;

import com.google.android.material.navigation.NavigationView;
import com.team_three.medicalreminder.MainActivity;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.LoginActivity;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;
import com.team_three.medicalreminder.databinding.HomeDrawerBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.impl.model.Preference;

import java.util.concurrent.TimeUnit;


public class HomeActivity extends AppCompatActivity {
    private ActivityHomeBinding homeBinding;
    NavController navController;
    NavHostFragment navHostFragment;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String name = "";
    String email = "";
    Repository myRepository;
    //
    private static final int RC_OVERLAY = 33;


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
        ///


        //2
        if (ContextCompat.checkSelfPermission(
                this, Settings.ACTION_MANAGE_OVERLAY_PERMISSION) ==
                PackageManager.PERMISSION_GRANTED) {
            // may be wrong
            setWorkTimer();

        } else if (shouldShowRequestPermissionRationale(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)) {
            Toast.makeText(this, "should accept permission to start Alarm ", Toast.LENGTH_SHORT).show();
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            requestPermissionLauncher.launch(
                    Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
        }
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

///////////////////////////////



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
                    } else if (item.getItemId() == R.id.patients) {

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

    // return after requesting Permission

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    setWorkTimer();
                } else {
                    final Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                            Uri.parse("package:" + getPackageName()));
                    try {
                        startActivityForResult(intent, RC_OVERLAY);
                    } catch (ActivityNotFoundException e) {
                        Log.e("MainActivity", e.getMessage());
                    }
                }
            });

    private void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWorkManger.class,
                6, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
//        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                           int[] grantResults) {
//
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case 33:
//                // If request is cancelled, the result arrays are empty.
//                if (grantResults.length > 0 &&
//                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission is granted. Continue the action or workflow
//                    // in your app.
//                } else {
//                    // Explain to the user that the feature is unavailable because
//                    // the features requires a permission that the user has denied.
//                    // At the same time, respect the user's decision. Don't link to
//                    // system settings in an effort to convince the user to change
//                    // their decision.
//                }
//                return;
//        }
//        // Other 'case' lines to check for other
//        // permissions this app might request.
//    }
//}
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    switch (requestCode) {
        case RC_OVERLAY:
            final boolean overlayEnabled = Settings.canDrawOverlays(this);
            if (overlayEnabled) {
                Toast.makeText(this, "xxxxxxx", Toast.LENGTH_SHORT).show();
                setWorkTimer();
            } else {
//                    switchCyberBulling.setChecked(false);
            }
            // Do something...
            break;
    }
}

}



