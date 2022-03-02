package com.team_three.medicalreminder.medicationPatient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.databinding.FragmentMedicationTimeBinding;
import com.team_three.medicalreminder.databinding.FragmentPatientMedBinding;
import com.team_three.medicalreminder.databinding.FragmentPatientMedDetailsBinding;
import com.team_three.medicalreminder.homeScreen.view.HomeAdapter;
import com.team_three.medicalreminder.homeScreen.view.OnClickListener;
import com.team_three.medicalreminder.medicationPatient.presenter.PatientMedPresenter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;
import com.team_three.medicalreminder.workmanger.MyOneTimeWorkManger;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;
import com.team_three.medicalreminder.workmanger.takerManager.TakerOneTmeWorkManager;
import com.team_three.medicalreminder.workmanger.takerManager.TakerPeriodicWorkManager;

import java.io.DataInput;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class PatientMedFragment extends Fragment implements PatientMedViewInterface, OnClickListener {

    private FragmentPatientMedBinding binding;
    HomeAdapter homeAdapter;
    PatientMedPresenter myPresenter;
    Repository myRepository;


    String email;


    public PatientMedFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientMedBinding.inflate(inflater, container, false);
        email = getArguments().getString("email");
        // check
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecycleView();
        initRepository();
        // should get email
        // should ask whn internet exist first
        if (Utility.isOnline(this.getContext())) {
            binding.patientMedScreenRecycleView.setVisibility(View.VISIBLE);
            binding.connectionAnimation.setVisibility(View.GONE);
            binding.PatTxtEmail.setText(email);
            requestDataFromPresenter(email);

        } else {
            binding.patientMedScreenRecycleView.setVisibility(View.GONE);
            binding.connectionAnimation.setVisibility(View.VISIBLE);
            // should back
            Toast.makeText(getContext(), "You must connect to internet to see Patient'sMedications", Toast.LENGTH_SHORT).show();
            // asked for connect internet
        }
        binding.imageBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_patientMedicationfragment_to_patientList);
        });

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void initRecycleView() {
        Log.i("AAA", "initRecycleView: ");
        LinearLayoutManager layoutManager = new LinearLayoutManager(PatientMedFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        homeAdapter = new HomeAdapter(this.getContext(), null, this);
        binding.patientMedScreenRecycleView.setLayoutManager(layoutManager);
        binding.patientMedScreenRecycleView.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();
    }


    private void initRepository() {
        NetworkInterface networkInterface = FireBaseNetwork.getInstance(this.getActivity());
        myRepository = Repository.getInstance(networkInterface, this.getContext());
        myPresenter = new PatientMedPresenter(this, myRepository);
    }

    private void requestDataFromPresenter(String email) {
        myPresenter.getPatientMedicationList(email);

    }


    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {
        if (medicationPOJOList.isEmpty()) {

            Toast.makeText(this.getContext(), "there is no medication for this patient", Toast.LENGTH_SHORT).show();
        } else {
            homeAdapter.setMedicines(medicationPOJOList);

            homeAdapter.notifyDataSetChanged();
            // call Work manager
            setPeriodicWorkManager(medicationPOJOList);
        }

    }

    private void setPeriodicWorkManager(List<MedicationPOJO> medicationPOJOList) {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();
        String tag = email;
        Data data = setInputData(medicationPOJOList);
        PeriodicWorkRequest
                periodicWorkRequest = new PeriodicWorkRequest.Builder(TakerPeriodicWorkManager.class,
                3, TimeUnit.HOURS)
                .setInitialDelay(1, TimeUnit.MINUTES)
                .setInputData(data)
                .setConstraints(constraints)
                .addTag(tag)
                .build();

        WorkManager.getInstance(this.getContext()).
                enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE,
                        periodicWorkRequest);
    }

    private Data setInputData(List<MedicationPOJO> medicationPOJOList) {
        return new Data.Builder()
                .putString("MED", serializeToJason(medicationPOJOList))
//                .putString("Medication",medicationPOJO.getMedicationName())
                .putString("EMAIL", email)
                .build();

    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getContext(), "there is a problem in " + errorMessage, Toast.LENGTH_SHORT).show();

    }

    // for responding
    @Override
    public void onClick(View view, int position) {
        if (Utility.isOnline(this.getContext())) {
            Bundle bundle = new Bundle();
            bundle.putString("email", email);
            bundle.putInt("index", position);
            Navigation.findNavController(view).navigate(R.id.action_patientMedicationfragment_to_patientMedDetailsFragment, bundle);
        } else {
            Toast.makeText(getContext(), "You must connect to internet to see Patient'sMedications", Toast.LENGTH_SHORT).show();

        }

    }

    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    private String serializeToJason(List<MedicationPOJO> medicationPOJOList) {
        Gson gson = new Gson();
        return gson.toJson(medicationPOJOList);
    }
}
