package com.team_three.medicalreminder.medicationPatient.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.databinding.FragmentPatientMedBinding;
import com.team_three.medicalreminder.databinding.FragmentPatientMedDetailsBinding;
import com.team_three.medicalreminder.homeScreen.view.OnMedicationTimeListener;
import com.team_three.medicalreminder.homeScreen.view.TimeAdapter;
import com.team_three.medicalreminder.medicationPatient.presenter.PatientMedPresenter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PatientMedDetailsFragment extends Fragment implements PatientMedViewInterface, OnMedicationTimeListener {
    TimeAdapter timeAdapter;
    PatientMedPresenter myPresenter;
    Repository myRepository;
    FragmentPatientMedDetailsBinding binding;
    MedicationPOJO medicationPOJO;
    String email = "";
    int position = 0;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentPatientMedDetailsBinding.inflate(inflater, container, false);
        email = getArguments().getString("email");
        position = getArguments().getInt("index");
        return binding.getRoot();
    }

    private void handleVisibility(boolean visible) {
        if (!visible) {
            binding.progressBarPMedDetails.setVisibility(View.GONE);
            binding.cardPatientView.setVisibility(View.VISIBLE);
            binding.imgDeleteMed.setVisibility(View.VISIBLE);
            binding.iconTimeMedication.setVisibility(View.VISIBLE);
            binding.timeTxtStrength.setVisibility(View.VISIBLE);
            binding.txtInstruction.setVisibility(View.VISIBLE);
            binding.imgDeleteMed.setVisibility(View.VISIBLE);
            binding.timeTxtMedicationName.setVisibility(View.VISIBLE);
            binding.timeRecycleView.setVisibility(View.VISIBLE);
        } else {

            binding.progressBarPMedDetails.setVisibility(View.VISIBLE);
            binding.cardPatientView.setVisibility(View.GONE);
            binding.imgDeleteMed.setVisibility(View.GONE);
            binding.iconTimeMedication.setVisibility(View.GONE);
            binding.timeTxtStrength.setVisibility(View.GONE);
            binding.txtInstruction.setVisibility(View.GONE);
            binding.imgDeleteMed.setVisibility(View.GONE);
            binding.timeTxtMedicationName.setVisibility(View.GONE);
            binding.timeRecycleView.setVisibility(View.GONE);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRecycleView();
        initRepository();
        if (isOnline()) {
            requestDataFromPresenter(email);

        } else {
            Toast.makeText(getContext(), "You must connect to internet to see Patient'sMedications", Toast.LENGTH_SHORT).show();
            // asked for connect internet
        }

        binding.imageBack.setOnClickListener(v -> {
            handleBack();
        });


    }

    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }


    private void bindViews() {
        binding.iconTimeMedication.setImageResource(medicationPOJO.getImageID());
        binding.timeTxtMedicationName.setText(medicationPOJO.getMedicationName());
        binding.txtInstruction.setText(medicationPOJO.getInstruction());
        binding.timeTxtStrength.setText(medicationPOJO.getStrength() + " :" + medicationPOJO.getWeight());
//         handling delete medication
        binding.imgDeleteMed.setOnClickListener(view -> {
//            Navigation.findNavController(view).navigate(R.id.action_medicationTimeFragment_to_fragment_home);
            myPresenter.deleteMedication(email, String.valueOf(medicationPOJO.getId()));
            Toast.makeText(this.getContext(), medicationPOJO.getMedicationName() + " is Deleted", Toast.LENGTH_SHORT).show();
            handleBack();
        });
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(PatientMedDetailsFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        timeAdapter = new TimeAdapter(this.getContext(), null, this);
        // don't forget
        binding.timeRecycleView.setLayoutManager(layoutManager);
        binding.timeRecycleView.setAdapter(timeAdapter);

    }

    private void initRepository() {
        NetworkInterface myRemote = FireBaseNetwork.getInstance(getActivity());
        LocalSourceInterface myLocal = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        myRepository = Repository.getInstance(myLocal, myRemote, this.getContext());
        myPresenter = new PatientMedPresenter(this, myRepository);
    }

    private void requestDataFromPresenter(String email) {
        myPresenter.getPatientMedicationList(email);
        handleVisibility(true);

    }

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {
        medicationPOJO = medicationPOJOList.get(position);
        if (medicationPOJOList.isEmpty()) {
            Toast.makeText(this.getContext(), "there is no medication for this patient", Toast.LENGTH_SHORT).show();
        } else {
            bindViews();
            timeAdapter.setMedicine(medicationPOJOList.get(position));
            timeAdapter.notifyDataSetChanged();
        }
        handleVisibility(false);

    }

    @Override
    public void onFailure(String errorMessage) {
        Toast.makeText(getContext(), "there is a problem in " + errorMessage, Toast.LENGTH_SHORT).show();
        handleVisibility(false);

    }

    @Override
    public void onTakeClick(int position, boolean take, MedicationPOJO medicationPOJO) {

        handleTakeClick(position, take, medicationPOJO);
        myPresenter.updateMedication(email,medicationPOJO);
        // back to home

    }

    private void handleBack() {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        Navigation.findNavController(binding.getRoot()).navigate
                (R.id.action_patientMedDetailsFragment_to_patientMedicationfragment, bundle);
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void handleTakeClick(int position, boolean take, MedicationPOJO medicationPOJO) {
        Map<String, Integer> timeAndDose = medicationPOJO.getTimeAndDose();
        List<String> keyList = new ArrayList<>(timeAndDose.keySet());
        String key = keyList.get(position);
        Integer value = timeAndDose.get(key);

        updateMedicationTake(medicationPOJO, position, take);
        updateMedicationPillsLeft(medicationPOJO, take, value);
        int takeString = (!take) ? R.string.take : R.string.untake;
        showUpdatedMessage(takeString, medicationPOJO, key);
        handleBack();
    }

    private void updateMedicationPillsLeft(MedicationPOJO pojo, boolean take, int value) {
        int left = medicationPOJO.getLeftNumber();
        left = (!take) ? (left - value) : (left + value);
        medicationPOJO.setLeftNumber(left);

    }

    private void updateMedicationTake(MedicationPOJO medicationPOJO, int position, boolean take) {
        List<Boolean> takenList = medicationPOJO.getIsTakenList();
        takenList.set(position, !take);
        medicationPOJO.setIsTakenList(takenList);

    }

    private void showUpdatedMessage(int takeString, MedicationPOJO medicationPOJO, String key) {
        Toast.makeText(this.getContext(), "You " + this.getString(takeString) + " " +
                medicationPOJO.getMedicationName() + "" +
                " which at  " + key, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSkipClick(int position, boolean take, MedicationPOJO medicationPOJO) {
        handleBack();

    }


}
