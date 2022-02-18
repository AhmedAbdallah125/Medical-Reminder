package com.team_three.medicalreminder.medicationList.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.FragmentAcrtiveMedsBinding;
import com.team_three.medicalreminder.medicationList.presenter.ActivePresenter;
import com.team_three.medicalreminder.medicationList.presenter.ActivePresenterInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.ArrayList;
import java.util.List;


public class AcrtiveMedsFragment extends Fragment implements ActiveViewInterface {
    private FragmentAcrtiveMedsBinding fragmentAcrtiveMedsBinding;
    ActivePresenterInterface activePresenterInterface;
    MedsListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentAcrtiveMedsBinding = FragmentAcrtiveMedsBinding.inflate(inflater,container,false);
        return fragmentAcrtiveMedsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<MedicationPOJO> medicinesActiveList = new ArrayList<>();

        fragmentAcrtiveMedsBinding.activeStr.setVisibility(View.GONE);
        fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(AcrtiveMedsFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setLayoutManager(layoutManager);

        activePresenterInterface =new ActivePresenter(this, Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext()),this.getContext()));
        activePresenterInterface.getActiveMeds(AcrtiveMedsFragment.this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentAcrtiveMedsBinding =null;
    }


    @Override
    public void getActiveMeds(List<MedicationPOJO> medications) {
        if(medications.size()==0){
            fragmentAcrtiveMedsBinding.activeStr.setVisibility(View.GONE);
            fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setVisibility(View.GONE);
        }else {
            fragmentAcrtiveMedsBinding.activeStr.setVisibility(View.VISIBLE);
            fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setVisibility(View.VISIBLE);

            adapter=new MedsListAdapter(this.getContext(),medications);
            fragmentAcrtiveMedsBinding.activeRecyclerVeiw.setAdapter(adapter);

        }
    }
}