package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentHomeBinding;
import com.team_three.medicalreminder.homeScreen.presenter.HomeScreenPresenter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class HomeFragment extends Fragment implements HomeFragmentInterface, OnClickListener {

    private FragmentHomeBinding fragmentHomeBinding;
    HomeAdapter homeAdapter;
    HomeScreenPresenter myPresenter;
    Repository myRepository;
    boolean isClicked = false;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private Long timeNow;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadAnimation();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        //must check
        fragmentHomeBinding.btnFabAddMedication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAddButtonClicked(isClicked);
                isClicked = !isClicked;
            }
        });


        // handle floatActionButton
        fragmentHomeBinding.secondfloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_fragment_home_to_addMedication);
            }
        });
        return fragmentHomeBinding.getRoot();
    }

    private void loadAnimation() {
        rotateOpen = AnimationUtils.loadAnimation(getActivity().getApplicationContext(), R.anim.rotate_open_anim);
        rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_anim);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_bottom_anim);
        toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_bottom_anim);
    }

    private void onAddButtonClicked(boolean isClicked) {
        setVisibility(isClicked);
        setAnimation(isClicked);
        setClickable(isClicked);

    }

    private void setVisibility(boolean clicked) {
        if (!clicked) {
            fragmentHomeBinding.secondfloatingActionButton.setVisibility(View.VISIBLE);
            fragmentHomeBinding.thirdfloatingActionButton2.setVisibility(View.VISIBLE);

        } else {
            fragmentHomeBinding.secondfloatingActionButton.setVisibility(View.GONE);
            fragmentHomeBinding.thirdfloatingActionButton2.setVisibility(View.GONE);
        }


    }

    private void setAnimation(boolean clicked) {
        if (!clicked) {
            fragmentHomeBinding.secondfloatingActionButton.startAnimation(fromBottom);
            fragmentHomeBinding.thirdfloatingActionButton2.startAnimation(fromBottom);
            fragmentHomeBinding.btnFabAddMedication.startAnimation(rotateOpen);

        } else {
            fragmentHomeBinding.secondfloatingActionButton.startAnimation(toBottom);
            fragmentHomeBinding.thirdfloatingActionButton2.startAnimation(toBottom);
            fragmentHomeBinding.btnFabAddMedication.startAnimation(rotateClose);
        }

    }

    private void setClickable(boolean clicked) {
        if (!clicked) {
            fragmentHomeBinding.secondfloatingActionButton.setClickable(false);
            fragmentHomeBinding.thirdfloatingActionButton2.setClickable(false);
        }
        fragmentHomeBinding.secondfloatingActionButton.setClickable(true);
        fragmentHomeBinding.thirdfloatingActionButton2.setClickable(true);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initCalendar(view);
        initRecycleView();
        initRepository();
//        requestDataFromPresenter(timeNow);
        // make presenter


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
        myRepository = Repository.getInstance(myLocal, this.getContext());
        myPresenter = new HomeScreenPresenter(this, myRepository);
    }

    private void initCalendar(View view) {
        //
        /* starts before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);

        /* ends after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        // get Time Now
        timeNow = endDate.getTimeInMillis();
        // request Data from presenter

        endDate.add(Calendar.MONTH, 1);
        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(view, R.id.calendarView).startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                timeNow = date.getTime();
                requestDataFromPresenter(timeNow);
            }


            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView,
                                         int dx, int dy) {
            }

        });
    }

    private void requestDataFromPresenter(long timeNow) {
        myPresenter.getMedicationDay(this, timeNow);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentHomeBinding = null;
    }


    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMedications(List<MedicationPOJO> storedMedications) {
        homeAdapter.setMedicines(storedMedications);
        homeAdapter.notifyDataSetChanged();
    }


    @Override
    public void onClick(View view, int position) {
        myPresenter.updatePosition(position);
        Navigation.findNavController(view).navigate(R.id.action_fragment_home_to_medicationTimeFragment);
    }
}

