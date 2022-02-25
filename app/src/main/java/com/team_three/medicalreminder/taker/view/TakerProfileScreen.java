package com.team_three.medicalreminder.taker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.FragmentTakerProfileScreenBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.taker.presenter.TakerProfilePresenter;
import com.team_three.medicalreminder.taker.presenter.TakerProfilePresenterInterface;

public class TakerProfileScreen extends Fragment {
    FragmentTakerProfileScreenBinding binding;
    SharedPreferences sharedPreferences;
    String myEmail;
    TakerProfilePresenterInterface takerProfilePresenterInterface;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =FragmentTakerProfileScreenBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if(bundle!=null){
            TakerPOJO taker = bundle.getParcelable("profileData");
            binding.txtFirstNameProfileTaker.setText(taker.getName());
            binding.txtEmailProfileTaker.setText(taker.getEmail());
            binding.imageView.setImageResource(taker.getImg());

            Repository repository =  Repository.getInstance(FireBaseNetwork.getInstance(this.getActivity()),this.getContext());

            sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
            String[] email = sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null").split("\\.");
            myEmail = email[0];

            takerProfilePresenterInterface = new TakerProfilePresenter(this.getContext(),repository);

            binding.deleteTaker.setOnClickListener(view1 -> {
                Log.i("TAG", "onViewCreated: delete");
                if(!myEmail.equals("null")){
                    Log.i("TAG", "onViewCreated: atms7");
                    takerProfilePresenterInterface.deleteTaker(taker.getEmail(),myEmail);

                }
            });
        }

    }


}