package com.team_three.medicalreminder.login.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentLoginBinding;
import com.team_three.medicalreminder.login.presenter.LoginPresenter;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;

import java.util.regex.Pattern;


public class LoginFragment extends Fragment implements LoginViewInterface {
    FragmentLoginBinding binding;
    LoginPresenter myPresenter;
    NetworkInterface myNetwork;
    Repository myRepository;
    String email = "";
    String name = "";

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
            request(binding.textInputEditEmailLogIn.getEditableText().toString()
                    , binding.textInputEditPasswordLogIn.getEditableText().toString());
        });


    }

    private void checkEmail(String emailString) {
        if (emailString.trim().isEmpty()){
            binding.textInputEditEmailLogIn.setError("You must Enter Email");
        }else if(! Patterns.EMAIL_ADDRESS.matcher(emailString).matches()){
            binding.textInputEditEmailLogIn.setError("You must Enter Valid Email");
            binding.textInputEditEmailLogIn.requestFocus();
        }

    }

    private void checkPassword(String passwordString) {
        if (passwordString.isEmpty()) {
            binding.loginTxtForgetPassword.setError("Password is Required");
            binding.loginTxtForgetPassword.requestFocus();
        }else if(passwordString.length()<8){
            binding.loginTxtForgetPassword.setError("Password must be at least 8 character");
            binding.loginTxtForgetPassword.requestFocus();
        }

    }

    private void initPresenter() {
        myNetwork = FireBaseNetwork.getInstance(getActivity());
        myRepository = Repository.getInstance(myNetwork, this.getContext());
        myPresenter = new LoginPresenter(getActivity(), this, myRepository);
    }

    @Override
    public void setResponse(boolean response) {
        // make change
        Toast.makeText(this.getContext(), "succeed", Toast.LENGTH_SHORT).show();
    }

    public void request(String email, String password) {
        myPresenter.signInWithEmailAndPass(getActivity(), email, password);
    }
}