package com.team_three.medicalreminder.Registeration.view;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.team_three.medicalreminder.databinding.FragmenetRegisterBinding;
import com.team_three.medicalreminder.databinding.FragmentLoginBinding;
import com.team_three.medicalreminder.Registeration.presenter.NetworkPresenter;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;


public class RegisterFragment extends Fragment implements NetworkViewInterface {
    FragmenetRegisterBinding binding;
    NetworkPresenter myPresenter;
    NetworkInterface myNetwork;
    Repository myRepository;
    String email = "";
    String password = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmenetRegisterBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();


        binding.btnSignUp.setOnClickListener(v -> {
            email = binding.textInputEditEmailSignUp.getEditableText().toString();
            password = binding.textInputEditPasswordSignUp.getEditableText().toString();
            makeRegisterRequest(email, password);
        });


    }

    private void makeRegisterRequest(String email, String password) {
        if (checkEmail(email) && checkPassword(password)) {
            request(email, password);
            handleVisibility(true);
        }
    }



    private boolean checkEmail(String emailString) {
        boolean check;
        if (emailString.trim().isEmpty()) {
            binding.textInputLayoutEmailSignUp.setError("You must Enter Email");
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            binding.textInputLayoutEmailSignUp.setError("You must Enter Valid Email");
            binding.textInputLayoutEmailSignUp.requestFocus();
            check = false;
        } else {
            check = true;
        }
        return check;

    }


    private boolean checkPassword(String passwordString) {
        boolean check;

        if (passwordString.isEmpty()) {
            binding.textInputEditPasswordSignUp.setError("Password is Required");
            binding.textInputEditPasswordSignUp.requestFocus();
            check = false;
        } else if (passwordString.length() < 8) {
            binding.textInputEditPasswordSignUp.setError("Password must be at least 8 character");
            binding.textInputEditPasswordSignUp.requestFocus();
            check = false;
        } else {
            check = true;
        }
        return check;
    }

    private void initPresenter() {
        myNetwork = FireBaseNetwork.getInstance(getActivity());
        myRepository = Repository.getInstance(myNetwork, this.getContext());
        myPresenter = new NetworkPresenter(getActivity(), this, myRepository);
    }


    public void request(String email, String password) {
        myPresenter.registerWithEmailAndPass(getActivity(), email, password);
    }

    @Override
    public void setSuccessfulResponse() {
        // can get User
        handleVisibility(false);
    }

    private void handleErrorResponse(String error) {
        binding.textInputEditPasswordSignUp.setError(error);
    }

    @Override
    public void setFailureResponse(String errorMessage) {
        // can get User
        binding.progressBarRegister.setVisibility(View.GONE);
        binding.textInputEditPasswordSignUp.requestFocus();
        handleErrorResponse(errorMessage);
        handleVisibility(false);
    }

    private void handleVisibility(boolean visible) {
        if (!visible) {
            binding.constraintRegister.setVisibility(View.VISIBLE);
            binding.progressBarRegister.setVisibility(View.GONE);
        } else {
            binding.constraintRegister.setVisibility(View.GONE);
            binding.progressBarRegister.setVisibility(View.VISIBLE);
        }

    }
}