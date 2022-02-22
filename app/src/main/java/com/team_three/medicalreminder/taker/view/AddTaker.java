package com.team_three.medicalreminder.taker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.FragmentAddTakerBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.taker.presenter.AddTakerPresenerInterface;
import com.team_three.medicalreminder.taker.presenter.AddTakerPresenter;

public class AddTaker extends Fragment implements AddTakerViewInterface{

    FragmentAddTakerBinding binding;
    RequestPojo taker;
    AddTakerPresenerInterface addTakerPresenerInterface;
    Repository repository;
    SharedPreferences sharedPreferences;
    boolean response;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTakerBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = Repository.getInstance(FireBaseNetwork.getInstance(this.getActivity()),this.getContext());
        addTakerPresenerInterface = new AddTakerPresenter(this.getContext(),this,repository);
        binding.btnTakerAdded.setOnClickListener(view1 -> {
            addTakerPresenerInterface.isLoggedIn();

        });
    }



    @Override
    public void isLogedIn(boolean response) {
        if(response){

            sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);

            RequestPojo requestPojo = new RequestPojo(R.drawable.one
                    ,sharedPreferences.getString(RegisterFragment.USER_NAME,"null")
                    ,binding.txtEmail.getEditableText().toString()
                    ,sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null")
                    ,0
                    );
           // requestPojo.setMyEmail();
            addTakerPresenerInterface.sendRequest(requestPojo);

        }
    }
}