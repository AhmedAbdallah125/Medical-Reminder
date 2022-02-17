package com.team_three.medicalreminder.homeScreen.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.homeScreen.model.MedicineDetails;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentHomeBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
        //must check
        fragmentHomeBinding.btnFabAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_home_to_addMedication);
            }
        });

        return fragmentHomeBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.i("TAG", "onCreateView:Fragment ");

        //
//        Repository mRepository=Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(getContext()),getContext());
//       MedicationPOJO medicationPOJO=new MedicationPOJO();
//        medicationPOJO.setEndDate("88");
//        medicationPOJO.setActive(true);
//        List<Boolean> l = new ArrayList<>();
//        l.add(true);
//        medicationPOJO.setIsTakenList(l);
//        Map<String,Integer> m = new HashMap<>();
//        m.put("8:00 AM" , 3);
//        medicationPOJO.setTimeAndDose(m);
//        medicationPOJO.setStartDate("22/2/99");
//        medicationPOJO.setEndDate("27/2/99");
//        medicationPOJO.setMedicationName("ahmed");
//        mRepository.insertMedication(medicationPOJO);
        //

        ArrayList<MedicineDetails> medicineDetails = new ArrayList<>();
        medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am"));
        medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am"));
        medicineDetails.add(new MedicineDetails("8:00 am", R.drawable.ic_baseline_add_24,
                "dore", "50 g take it at 1 am"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(HomeFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        homeAdapter = new HomeAdapter(this.getContext(), medicineDetails);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setLayoutManager(layoutManager);
        fragmentHomeBinding.fragmntHomeScreenRecycleVie.setAdapter(homeAdapter);
        homeAdapter.notifyDataSetChanged();

        //
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.MONTH, 1);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view,R.id.calendarView).startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                Log.e("TAG", "onDateSelected: "+date);

            }


            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
                Log.e("TAG", "onCalendarScroll: "+"dx="+dx   +"dy   "+dy);

            }

        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentHomeBinding = null;
    }
}