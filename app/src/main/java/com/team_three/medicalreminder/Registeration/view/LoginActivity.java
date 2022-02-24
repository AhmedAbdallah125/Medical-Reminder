package com.team_three.medicalreminder.Registeration.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
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
    String idToken="";

    private static final int RC_SIGN_IN = 258120;
    private static final int EMAIL_LOGIN = 1;
    private static final int GOOGLE_LOGIN = 2;
    private static final int STATE_CODE = 1;
    private static final String STATE = "STATE";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initShared();

        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initPresenter();
        binding.btnLogin.setOnClickListener(v -> {
            email = binding.textInputEditEmailLogIn.getEditableText().toString();
            password = binding.textInputEditPasswordLogIn.getEditableText().toString();
            makeLoginRequest(email, password, EMAIL_LOGIN);
        });
        // handle GoogleCard
        binding.cardWigninGoogle.setOnClickListener(view -> {
            makeLoginRequest(null, null, GOOGLE_LOGIN);
        });
        //handle register
        binding.txtNewRegister.setOnClickListener(v -> {
            editor.putInt(STATE, STATE_CODE);
            editor.apply();
            finish();
        });
        // handle back
        binding.imageSignBack.setOnClickListener(view -> {
            finish();
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
        String userName;
        String email;
        binding.progressBarLogin.setVisibility(View.GONE);
        userName = getCurrentUser().getDisplayName();
        email = getCurrentUser().getEmail();
//        initShared();
//        if (userName == null) {
            myPresenter.getUserFromDB(getCurrentUser().getEmail());
            Log.d("TAG", "request: User");
//        } else {
//            storeUserInformation(email, userName);
//            finish();
//            handleVisibility(false);
//
//        }

    }


    private void handleErrorResponse(String error) {
        binding.textInputEditEmailLogIn.setError(error);

    }

    @Override
    public void setFailureResponse(String errormessge) {
        binding.progressBarLogin.setVisibility(View.GONE);
        binding.textInputEditEmailLogIn.requestFocus();
        handleErrorResponse(errormessge);
        handleVisibility(false);

    }


    @Override
    public void setSuccessfulReturnResponse(String userName) {
        handleVisibility(false);
        Log.i("TAG", "successfully: ");
        // return from requesting username
        storeUserInformation(getCurrentUser().getEmail(),
                userName);
        finish();

    }

    @Override
    public void setResponse(boolean response) {
//        if(response){
//            handleVisibility(false);
//            storeUserInformation(getCurrentUser().getEmail(),
//                    getCurrentUser().getDisplayName());
//            finish();
//        }
//        else {
            myPresenter.signWithGoogle(idToken);
//        }
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

                // check first if already exist or not
                idToken=account.getIdToken();
                myPresenter.isAlreadySignedWithGoogle(account.getEmail());
//                myRepository.signInUsingGoogle(account.getEmail(),account.getIdToken());

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

    private FirebaseUser getCurrentUser() {
        return myRepository.getCurrentUser();
    }

    private void initShared() {
        sharedPref = getSharedPreferences(
                RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
    }

    private void storeUserInformation(String email, String name) {
        editor.putString(RegisterFragment.USER_EMAIL, email);
        editor.putString(RegisterFragment.USER_NAME, name);
        editor.apply();
    }

}