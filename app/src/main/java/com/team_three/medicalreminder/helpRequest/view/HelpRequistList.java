package com.team_three.medicalreminder.helpRequest.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.FragmentHelpRequistListBinding;
import com.team_three.medicalreminder.helpRequest.presenter.HelpRequestPresenter;
import com.team_three.medicalreminder.helpRequest.presenter.HelpRequestPresenterInterface;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.FireBaseNetwork;

import java.util.ArrayList;
import java.util.List;


public class HelpRequistList extends Fragment implements HelpRequestViewInterface,OnClickRequest {

FragmentHelpRequistListBinding fragmentHelpRequistListBinding;
HelpRequestAdapter adapter;
SharedPreferences sharedPreferences;
String myEmail;
HelpRequestPresenterInterface helpRequestPresenterInterface;
   // private FirebaseRecyclerAdapter adapter;
    private List<RequestPojo> takerList;
    boolean flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        takerList = new ArrayList<>();
        fragmentHelpRequistListBinding = FragmentHelpRequistListBinding.inflate(inflater,container,false);
        return fragmentHelpRequistListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository =  Repository.getInstance(FireBaseNetwork.getInstance(this.getActivity()),this.getContext());

        ArrayList<RequestPojo> requestPojos = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(HelpRequistList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        fragmentHelpRequistListBinding.recyclerView.setLayoutManager(layoutManager);
        sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        String[] email = sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null").split("\\.");
        myEmail = email[0];
        helpRequestPresenterInterface = new HelpRequestPresenter(this.getContext(),repository,this);
        helpRequestPresenterInterface.sendEmail(myEmail);

        helpRequestPresenterInterface.loadHelpRequest();

    }


    @Override
    public void loadHelpRequest(List<RequestPojo> requestPojos) {
        adapter=new HelpRequestAdapter(this.getContext(),requestPojos,this);
        fragmentHelpRequistListBinding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClickAccept(TakerPOJO takerPOJO, PatientPojo patientPojo) {
        helpRequestPresenterInterface.onAccept(takerPOJO,patientPojo);
    }

    @Override
    public void onClickReject(String key) {

    }
}