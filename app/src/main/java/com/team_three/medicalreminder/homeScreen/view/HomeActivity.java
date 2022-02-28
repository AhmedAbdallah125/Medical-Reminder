package com.team_three.medicalreminder.homeScreen.view;


import android.Manifest;
import android.annotation.TargetApi;
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

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.team_three.medicalreminder.MainActivity;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.LoginActivity;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.ActivityHomeBinding;
import com.team_three.medicalreminder.databinding.HomeDrawerBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
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
    private static final int REQUEST_PERMISSION = 14;
    public final static int REQUEST_CODE = -1010101;

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
        checkDrawOverlayPermission();

        //2
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
                    }else if(item.getItemId() == R.id.patients){

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
        if(Utility.isOnline(this)){
            Log.i("TAG", "setSignOut:Activty ");
            // sign out
            handleSignOutCondition();
            Toast.makeText(this, "logout successfully", Toast.LENGTH_SHORT).show();
            // update all
        }else
            Toast.makeText(this, "You must connect to Network first", Toast.LENGTH_SHORT).show();


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

    private void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWorkManger.class,
                3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

//        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.KEEP, periodicWorkRequest);
        WorkManager.getInstance(this).enqueue(periodicWorkRequest);
    }

    @Override
    public void onRequestPermissionsResult(int permissionRequestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(permissionRequestCode, permissions, grantResults);
        if (permissionRequestCode == REQUEST_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setWorkTimer();
            } else {
                // Ask the user to grant the permission
                Toast.makeText(this, "please we need your permission to have all our features", Toast.LENGTH_LONG).show();
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkDrawOverlayPermission() {
        // Check if we already  have permission to draw over other apps
        if (!Settings.canDrawOverlays(this)) {
            // if not construct intent to request permission
            MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(this);
            alertDialogBuilder.setTitle("We need Your Permission")
                    .setMessage("Let's enjoy our features")
                    .setPositiveButton("Let's Go", (dialog, i) -> {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                                Uri.parse("package:" + getApplicationContext().getPackageName()));
                        // request permission via start activity for result
                        startActivityForResult(intent, REQUEST_CODE); //It will call onActivityResult Function After you press Yes/No and go Back after giving permission
                        dialog.dismiss();
                    }).setNegativeButton("Cancel", (dialog, i) -> {
                dialog.dismiss();
            }).show();
        } else {
            setWorkTimer();
            // disablePullNotificationTouch();
            // Do your stuff, we got permission captain
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.v("App", "OnActivity Result.");
        //check if received result code
        //  is equal our requested code for draw permission
        if (requestCode == REQUEST_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(this)) {
                    // Permission Granted by Overlay
                    // Do your Stuff
                    setWorkTimer();
                }
            }
        }
    }

}



