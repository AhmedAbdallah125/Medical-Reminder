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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.databinding.FragmentMedicationTimeBinding;
import com.team_three.medicalreminder.databinding.FragmentPatientMedicationfragmentBinding;
import com.team_three.medicalreminder.homeScreen.view.HomeAdapter;
import com.team_three.medicalreminder.homeScreen.view.OnClickListener;
import com.team_three.medicalreminder.medicationPatient.presenter.PatientMedPresenter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;

import java.util.Calendar;
import java.util.List;


public class PatientMedFragment extends Fragment implements PatientMedViewInterface, OnClickListener {

    private FragmentPatientMedicationfragmentBinding binding;
    HomeAdapter homeAdapter;
    PatientMedPresenter myPresenter;
    Repository myRepository;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
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
        binding = FragmentPatientMedicationfragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initRecycleView();
        initRepository();
        // should get email
        // should ask whn internet exist first
        if(isOnline()){
            requestDataFromPresenter("timeNow");
        }else{
            // should back
            // asked for connect internet
        }
//        requestDataFromPresenter(timeNow);
        // make presenter
//        checkWorking();
    }

    private void initRecycleView() {
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
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

        // response ya ngm

    @Override
    public void onSuccessReturnMedicationList(List<MedicationPOJO> medicationPOJOList) {

    }

    @Override
    public void onFailure(String errorMessage) {

    }

    // for responsing
    @Override
    public void onClick(View view, int position) {

    }
    public void onDestroy() {
        super.onDestroy();
        binding = null;
        }
}
/*

public class HomeFragment extends Fragment implements HomeFragmentInterface, OnClickListener {

    private FragmentHomeBinding fragmentHomeBinding;
    HomeAdapter homeAdapter;
    HomeScreenPresenter myPresenter;
    Repository myRepository;
    //
    boolean isClicked = false;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Long timeNow;
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    String email;







    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        initRecycleView();
        initRepository();
       requestDataFromPresenter(timeNow);
//        requestDataFromPresenter(timeNow);
        // make presenter
        checkWorking();



    }

    @Override
    public void onResume() {
        super.onResume();
        sharedPref =getActivity().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        handleRegistration();
    }

    private void checkWorking(){
        fragmentHomeBinding.thirdfloatingActionButton2.setOnClickListener(v->{
//            Navigation.findNavController(v).navigate(R.id.action_fragment_home_to_loginFragment);
        });
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        homeAdapter = new HomeAdapter(this.getContext(), null, this);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setLayoutManager(layoutManager);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setAdapter(homeAdapter);
//        homeAdapter.notifyDataSetChanged();
    }


    private void initRepository() {
        LocalSourceInterface myLocal = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        NetworkInterface  networkInterface= FireBaseNetwork.getInstance(this.getActivity());
        myRepository = Repository.getInstance(myLocal,networkInterface,this.getContext());
        myPresenter = new HomeScreenPresenter(this, myRepository);
    }

    private void initCalendar(View view) {
        //
        /* starts before 1 month from now */
//Calendar startDate = Calendar.getInstance();
//        startDate.add(Calendar.MONTH, -1);
//
//                /* ends after 1 month from now */
//                Calendar endDate = Calendar.getInstance();
//                // get Time Now
//                timeNow = endDate.getTimeInMillis();
//                // request Data from presenter
//
//                endDate.add(Calendar.MONTH, 1);
//                HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView).startDate(startDate.getTime())
//                .endDate(endDate.getTime())
//                .build();
//                horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
//@Override
//public void onDateSelected(Date date, int position) {
//        timeNow = date.getTime();
//        requestDataFromPresenter(timeNow);
//        }
//
//
//@Override
//public void onCalendarScroll(HorizontalCalendarView calendarView,
//        int dx, int dy) {
//        }
//
//        });
//        }
//
//private void requestDataFromPresenter(long timeNow) {
//        myPresenter.getMedicationDay(this, timeNow);
//
//        }
//
//@Override
//public void onDestroy() {
//        super.onDestroy();
//        fragmentHomeBinding = null;
//        }
//
//
//@SuppressLint("NotifyDataSetChanged")
//@Override
//public void showMedications(List<MedicationPOJO> storedMedications) {
//        homeAdapter.setMedicines(storedMedications);
//        homeAdapter.notifyDataSetChanged();
//        Log.i("TAG", "showMedications: "+storedMedications.size());
//        // sned data to firebase
////        sharedPref =getActivity().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
//
//        if(storedMedications.size()>0&&checkShared()){
//        String[] mail = email.split("\\.");
//        String myEmail = mail[0];
//        myPresenter.addMedicationListViaNetwork(storedMedications,myEmail);
//
//        }
//
//        }
//
//
//@Override
//public void onClick(View view, int position) {
//        myPresenter.updatePosition(position);
//        myPresenter.updateTime(timeNow);
//        Navigation.findNavController(view).navigate(R.id.action_fragment_home_to_medicationTimeFragment);
//        }
//private void handleRegistration() {
//
//        if (sharedPref.getInt("STATE", 2) == 1) {
//        editor = sharedPref.edit();
//        editor.putInt("STATE", 2);
//        editor.apply();
//        Navigation.findNavController(fragmentHomeBinding.getRoot()).navigate(R.id.action_fragment_home_to_registerFragment);
//        }
//
//        }
////
//private boolean checkShared() {
//        sharedPref = getActivity().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
//        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
//
//        return ( !email.equals("null"));
//        }
//        }
//

