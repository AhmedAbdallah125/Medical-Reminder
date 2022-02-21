package com.team_three.medicalreminder.Registeration.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.team_three.medicalreminder.Registeration.presenter.NetworkPresenter;
import com.team_three.medicalreminder.databinding.ActivityLoginBinding;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.network.NetworkInterface;

public class LoginActivity extends AppCompatActivity implements NetworkViewInterface {
    ActivityLoginBinding binding;
    NetworkPresenter myPresenter;
    NetworkInterface myNetwork;
    Repository myRepository;
    String email = "";
    String password = "";
    private static final int RC_SIGN_IN = 258120;
    private static final int EMAILLOGIN = 1;
    private static final int GOOGLELOGIN = 2;

    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initPresenter();
        binding.btnLogin.setOnClickListener(v -> {
            email = binding.textInputEditEmailLogIn.getEditableText().toString();
            password = binding.textInputEditPasswordLogIn.getEditableText().toString();
            makeLoginRequest(email, password, EMAILLOGIN);
        });
        // handle GoogleCard
        binding.cardWigninGoogle.setOnClickListener(view -> {
            makeLoginRequest(null, null, GOOGLELOGIN);
        });


    }

    private void makeLoginRequest(String email, String password, int log) {
        if (log == 2) {
            loginRequestUsingGoogle();
        } else if (log == 1) {
            if (checkEmail(email) && checkPassword(password)) {
                loginRequestUsingEmail(email, password);
                handleVisibility(true);
            }
        }
//        handleVisibility(true);
    }


    private void loginRequestUsingGoogle() {
        integrateGoogle();
        signInWithGoogle();
        handleVisibility(true);
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
        myNetwork = FireBaseNetwork.getInstance(this);
        myRepository = Repository.getInstance(myNetwork, this);
        myPresenter = new NetworkPresenter(this, this, myRepository);
    }


    public void loginRequestUsingEmail(String email, String password) {
        myPresenter.signInWithEmailAndPass(this, email, password);
    }

    @Override
    public void setSuccessfulResponse() {
        binding.progressBarLogin.setVisibility(View.GONE);
        handleVisibility(false);
        // handle here
        Toast.makeText(this, "current user is \n "+myRepository.getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();
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

    private void handleVisibility(boolean visible) {
        if (!visible) {
            binding.constraintLogin.setVisibility(View.VISIBLE);
            binding.progressBarLogin.setVisibility(View.GONE);
        } else {
            binding.constraintLogin.setVisibility(View.GONE);
            binding.progressBarLogin.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
//                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                myRepository.signInUsingGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }

    private void integrateGoogle() {
        FireBaseNetwork.getInstance(this);
        mAuth = FireBaseNetwork.mAuth;
        if (gso == null) {
            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken("987090001629-qs2g035me24hhq5fj1g4hdfhv66iiakc.apps.googleusercontent.com")
                    .requestEmail()
                    .build();
        }
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    private void signInWithGoogle() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}