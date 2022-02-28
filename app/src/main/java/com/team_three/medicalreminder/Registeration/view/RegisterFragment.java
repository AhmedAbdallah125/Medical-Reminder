package com.team_three.medicalreminder.Registeration.view;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmenetRegisterBinding;
import com.team_three.medicalreminder.databinding.FragmentLoginBinding;
import com.team_three.medicalreminder.Registeration.presenter.NetworkPresenter;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.Utility;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;


public class RegisterFragment extends Fragment implements NetworkViewInterface {
    public static final String SHAREDfILE = "SHAREDfILE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_NAME = "USER_NAME";

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    FragmenetRegisterBinding binding;
    NetworkPresenter myPresenter;
    NetworkInterface myNetwork;
    Repository myRepository;
    String email = "";
    String password = "";
    String name = "";

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
            name = binding.textInputEditNameSignUp.getEditableText().toString();
            makeRegisterRequest(email, password, name);
        });
        // handle back button

        binding.imageRegisterBackArrow.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_registerFragment_to_fragment_home);

        });

    }

    private void makeRegisterRequest(String email, String password, String name) {
        if (Utility.isOnline(this.getContext())) {
            if (checkEmail(email) && checkPassword(password) && checkName(name)) {
                request(email, password, name);
                handleVisibility(true);
            }
        } else
            Toast.makeText(this.getContext(), "You must connect to Network first", Toast.LENGTH_SHORT).show();

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

    private boolean checkName(String userName) {
        boolean check;

        if (userName.isEmpty()) {
            binding.textInputEditNameSignUp.setError("User Name is Required");
            binding.textInputEditNameSignUp.requestFocus();
            check = false;
            // continue
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


    public void request(String email, String password, String name) {
        myPresenter.registerWithEmailAndPass(getActivity(), email, password, name);
    }

    @Override
    public void setSuccessfulResponse() {
        // can get User
        handleVisibility(false);
        initShared();
        storeUserInformation();
        // back to home
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_registerFragment_to_fragment_home);

    }

    private FirebaseUser getCurrentUser() {
        return myRepository.getCurrentUser();
    }

    private void initShared() {
        Context context = getActivity();
        sharedPref = context.getSharedPreferences(
                SHAREDfILE, Context.MODE_PRIVATE);
        editor = sharedPref.edit();

    }

    private void storeUserInformation() {
        editor.putString(USER_EMAIL, email);
        editor.putString(USER_NAME, name);
        editor.apply();
    }

    private void handleErrorResponse(String error) {
        binding.textInputEditPasswordSignUp.setError(error);
    }

    @Override
    public void setFailureResponse(String errorMessge) {
        // can get User
        binding.progressBarRegister.setVisibility(View.GONE);
        binding.textInputEditPasswordSignUp.requestFocus();
        handleErrorResponse(errorMessge);
        handleVisibility(false);
    }

    @Override
    public void setSuccessfulReturnResponse(String userName) {

    }

    @Override
    public void setResponse(boolean response) {

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