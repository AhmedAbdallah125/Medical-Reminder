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
import com.team_three.medicalreminder.databinding.FragmentInactiveMedsBinding;
import com.team_three.medicalreminder.medicationList.presenter.ActivePresenter;
import com.team_three.medicalreminder.medicationList.presenter.InactivePresenter;
import com.team_three.medicalreminder.medicationList.presenter.InactivePresenterInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.ArrayList;
import java.util.List;


public class InactiveMedsFragment extends Fragment implements InactiveViewInterface{
    FragmentInactiveMedsBinding fragmentInactiveMedsBinding;
    InactivePresenterInterface inactivePresenterInterface;
    InactiveListAdapter adapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentInactiveMedsBinding = FragmentInactiveMedsBinding.inflate(inflater,container,false);
        return fragmentInactiveMedsBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<MedicationPOJO> medInactiveList =new ArrayList<>();

        fragmentInactiveMedsBinding.inActiveRecyclerView.setVisibility(View.GONE);
        fragmentInactiveMedsBinding.inactiveTxt.setVisibility(View.GONE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(InactiveMedsFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        fragmentInactiveMedsBinding.inActiveRecyclerView.setLayoutManager(layoutManager);

        inactivePresenterInterface= new InactivePresenter(this,Repository.getInstance(ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext()),this.getContext()));
        inactivePresenterInterface.getInactiveMeds(this);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentInactiveMedsBinding=null;
    }

    @Override
    public void getInactiveMeds(List<MedicationPOJO> medications) {
        if(medications.size()==0){
            fragmentInactiveMedsBinding.inActiveRecyclerView.setVisibility(View.GONE);
            fragmentInactiveMedsBinding.inactiveTxt.setVisibility(View.GONE);
        }
        else {
            fragmentInactiveMedsBinding.inActiveRecyclerView.setVisibility(View.VISIBLE);
            fragmentInactiveMedsBinding.inactiveTxt.setVisibility(View.VISIBLE);


            adapter =new InactiveListAdapter(this.getContext(),medications);

            fragmentInactiveMedsBinding.inActiveRecyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}