package com.team_three.medicalreminder.Registeration.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.GoogleApiManager;
import com.team_three.medicalreminder.databinding.FragmentLoginBinding;
import com.team_three.medicalreminder.Registeration.presenter.NetworkPresenter;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;


public class SignINFragment extends Fragment implements NetworkViewInterface {
    FragmentLoginBinding binding;
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
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initPresenter();
        binding.btnLogin.setOnClickListener(v -> {
            email = binding.textInputEditEmailLogIn.getEditableText().toString();
            password = binding.textInputEditPasswordLogIn.getEditableText().toString();
            makeRegisterRequest(email, password);
            handleVisibility(true);
        });


    }

    private void makeRegisterRequest(String email, String password) {
        if (checkEmail(email) && checkPassword(password)) {
            request(email, password);
        }
    }

    private boolean checkEmail(String emailString) {
        boolean check;
        if (emailString.trim().isEmpty()) {
            binding.textInputEditEmailLogIn.setError("You must Enter Email");
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailString).matches()) {
            binding.textInputEditEmailLogIn.setError("You must Enter Valid Email");
            binding.textInputEditEmailLogIn.requestFocus();
            check = false;
        } else {
            check = true;
        }
        return check;

    }


    private boolean checkPassword(String passwordString) {
        boolean check;

        if (passwordString.isEmpty()) {
            binding.textInputLayoutPasswordLogIn.setError("Password is Required");
            binding.textInputEditPasswordLogIn.requestFocus();
            check = false;
        } else if (passwordString.length() < 8) {
            binding.textInputEditPasswordLogIn.setError("Password must be at least 8 character");
            binding.textInputEditPasswordLogIn.requestFocus();
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
        myPresenter.signInWithEmailAndPass(getActivity(), email, password);
    }

    @Override
    public void setSuccessfulResponse() {
        binding.progressBarLogin.setVisibility(View.GONE);
        handleVisibility(false);

    }

    private void handleErrorResponse(String error) {
        binding.textInputEditEmailLogIn.setError(error);
    }

    @Override
    public void setFailureResponse(String errorMessage) {
        binding.progressBarLogin.setVisibility(View.GONE);
        binding.textInputEditEmailLogIn.requestFocus();
        handleErrorResponse(errorMessage);
        handleVisibility(false);

    }
    private void handleVisibility(boolean visible){
        if(!visible){
            binding.constraintLogin.setVisibility(View.VISIBLE);
            binding.progressBarLogin.setVisibility(View.GONE);
        }else {
            binding.constraintLogin.setVisibility(View.GONE);
            binding.progressBarLogin.setVisibility(View.VISIBLE);
        }

    }

}