package com.team_three.medicalreminder.taker.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.LoginActivity;
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
    private int previousDestination;


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

        previousDestination = Navigation.findNavController(view).getPreviousBackStackEntry().getDestination().getId();
        binding.icBackInAddTaker.setOnClickListener(view1 -> {
            if(previousDestination==R.id.fragment_taker_list){
                Navigation.findNavController(view).navigate(R.id.action_addTaker_to_fragment_taker_list);
            }else {
                Navigation.findNavController(view).navigate(R.id.action_addTaker_to_fragment_home);
            }
        });

        binding.btnTakerAdded.setOnClickListener(view1 -> {
            if(checkEmail(binding.txtEmail.getEditableText().toString())){
                addTakerPresenerInterface.isLoggedIn();

            }else {
                Toast.makeText(getContext(), "please inter invaled email", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void isLogedIn(boolean response) {


        if(checkShared()){

//            sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
//            String[] myEmail = sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null" ).split("\\.");

           String[] inputedEmail = binding.txtEmail.getEditableText().toString().split("\\.");

            addTakerPresenerInterface.userExistance(inputedEmail[0]);
            
           


        }
        else{
            Toast.makeText(this.getContext(), "you must login first ", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this.getActivity(), LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void isUserExist(boolean existance) {
        if(existance){
            sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
            String[] myEmail = sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null" ).split("\\.");
            String[] inputedEmail = binding.txtEmail.getEditableText().toString().split("\\.");
            if(!myEmail[0].equals(inputedEmail[0]) ){
                RequestPojo requestPojo = new RequestPojo(R.drawable.ic_doctor_male
                        ,sharedPreferences.getString(RegisterFragment.USER_NAME,"null")
                        ,binding.txtEmail.getEditableText().toString()
                        ,sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null")
                        ,0
                );
                // requestPojo.setMyEmail();
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_addTaker_to_fragment_taker_list);

                addTakerPresenerInterface.sendRequest(requestPojo);
                Toast.makeText(this.getContext(), "requested to add successfuly", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this.getContext(),   "you can't send request to yourself", Toast.LENGTH_SHORT).show();
            }

        }
        else {
            Toast.makeText(this.getContext(), "this user is not exist", Toast.LENGTH_SHORT).show();
        }
        
    }


    private boolean checkEmail(String emailString) {
        boolean check;
        if (emailString.trim().isEmpty()) {
            binding.txtEmail.setError("You must Enter Email");
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            binding.txtEmail.setError("You must Enter Valid Email");
            binding.txtEmail.requestFocus();
            check = false;
        } else {
            check = true;
        }
        return check;

    }


    private boolean checkShared() {
        sharedPreferences = getActivity().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString(RegisterFragment.USER_EMAIL, "null");

        return ( !email.equals("null"));
    }
}