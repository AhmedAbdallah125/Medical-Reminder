package com.team_three.medicalreminder.network;


import android.app.Activity;
import android.os.Parcelable;
import android.util.Log;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseAuthInvalidUserException;

import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.ArrayList;
import java.util.List;

public class FireBaseNetwork implements NetworkInterface {

    Activity _activity;
    public static FirebaseAuth mAuth;
    private static FireBaseNetwork myFireBase;
    private NetworkDelegation myDelegation;

    private FireBaseNetwork(Activity myActivity) {
        _activity = myActivity;
    }

    public static FireBaseNetwork getInstance(Activity myActivity) {
        if (myFireBase == null) {
            mAuth = FirebaseAuth.getInstance();
            myFireBase = new FireBaseNetwork(myActivity);
        }
        return myFireBase;
    }


    @Override
    public void setNetworkDelegation(NetworkDelegation networkDelegation) {
        myDelegation = networkDelegation;
    }

    @Override
    public void isSignedIn() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            reload();
//        }
        if (currentUser != null) {

            myDelegation.onSuccess();
        } else {
            myDelegation.onFailure("no available");
        }
    }


    @Override
    public void registerWithEmailAndPass(Activity myActivity, String email, String password, String name) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(myActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // first make must add to database
                            User user = new User(email, name);
                            addUserInDB(user);

                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegation.onFailure(errorMessage);
                        }
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            myDelegation.onSuccess(true);
////                            updateUI(user);
//                        }
                    }
                });

    }



    @Override
    public void signInWithEmailAndPass(Activity activity, String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            myDelegation.onSuccess();
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegation.onFailure(errorMessage);
                        }
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
////                            updateUI(user);
//                            myDelegation.onSuccess(true);
//
//                        }
                    }
                });
    }

    @Override
    public void tryLoginGoogle(String email) {
        mAuth.fetchSignInMethodsForEmail(email)
                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                    @Override
                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().getSignInMethods().isEmpty()) {
                                myDelegation.onSuccess(true);
                            }else{
                                myDelegation.onSuccess(false);

                            }
                        } else {
                            handleFireBaseException(task);
                        }
                    }
                });

    }



    @Override
    public void signInUsingGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(_activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            String userName = getCurrentUser().getDisplayName();
                            String email = getCurrentUser().getEmail();
                            User user = new User(email, userName);
                           addUserInDB(user);
                            myDelegation.onSuccess();
                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = mAuth.getCurrentUser();
//                            updateUI(user);
                        } else {
                            String errorMessage = handleFireBaseException(task);
                            myDelegation.onFailure(errorMessage);
                        }
                    }
                });
    }

    @Override
    public FirebaseUser getCurrentUser() {
        // check if null or not
        if (mAuth == null) {
            // check
            mAuth = FirebaseAuth.getInstance();
        }
        return mAuth.getCurrentUser();
    }

    @Override
    public void sendRequest(RequestPojo requestPojo) {
        String[] uid = requestPojo.getEmail().split("\\.");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid[0]);
        String key = databaseReference.child("request").push().getKey();
        // taker=new RequestPojo(binding.txtEmail.getEditableText().toString(),R.drawable.one,binding.txtName.getEditableText().toString(),id);
        requestPojo.setId(key);
        databaseReference.child("request").child(key).setValue(requestPojo);
    }

    @Override
    public void loadHelpRequest( String myEmail) {
        List<RequestPojo> requestPojos = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(myEmail).child("request");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                requestPojos.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("myEmail").getValue() !=null && Integer.parseInt(String.valueOf(dataSnapshot.child("acceptance").getValue()))==0){
                        RequestPojo taker =new RequestPojo((Integer.parseInt(String.valueOf(dataSnapshot.child("img").getValue())))
                                , dataSnapshot.child("name").getValue().toString()
                                ,dataSnapshot.child("myEmail").getValue().toString()
                                ,dataSnapshot.child("email").getValue().toString()
                                ,Integer.parseInt(String.valueOf(dataSnapshot.child("acceptance").getValue()))
                                );

                        taker.setId(dataSnapshot.child("id").getValue().toString());
                        requestPojos.add(taker);
                    }

                }
                myDelegation.onSuccessRequest(requestPojos);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myDelegation.onFailure(error.getMessage());
                Log.i("TAG", "onCancelled: ");
            }
        });
    }

    @Override
    public void onAccept(TakerPOJO takerPOJO, PatientPojo patientPojo) {

        String[] uid = takerPOJO.getPatientEmail().split("\\.");
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(uid[0]);
        String key = databaseReference.child("taker").push().getKey();
        databaseReference.child("taker").child(key).setValue(takerPOJO);


        String[] takerId = takerPOJO.getEmail().split("\\.");
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users").child(takerId[0]);
        reference.child("request").child(takerPOJO.getRequestId()).child("acceptance").setValue(1);


        String[] myId = patientPojo.getEmail().split(("\\."));
        DatabaseReference patientReference = FirebaseDatabase.getInstance().getReference().child("users").child(myId[0]);
        String key1 = databaseReference.child("patient").push().getKey();
        patientPojo.setId(key1);
        patientReference.child("patient").child(key1).setValue(patientPojo);
    }

    @Override
    public void onReject(String key) {

    }

    @Override
    public void loadPatients(String email) {
        List<PatientPojo> patients = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference("users").child(email).child("patient");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patients.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if(dataSnapshot.child("patientEmail").getValue() !=null){
                        PatientPojo patient =new PatientPojo(dataSnapshot.child("email").getValue().toString()
                                ,dataSnapshot.child("patientEmail").getValue().toString()
                                ,(Integer.parseInt(String.valueOf(dataSnapshot.child("image").getValue())))
                                , dataSnapshot.child("name").getValue().toString()
                        );

                        patient.setId(dataSnapshot.child("id").getValue().toString());
                        patients.add(patient);
                    }

                }
                myDelegation.onSuccessPatient(patients);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                myDelegation.onFailure(error.getMessage());
                Log.i("TAG", "onCancelled: ");
            }
        });
    }

    @Override
    public void loadTakers(String email) {
        List<TakerPOJO> takers = new ArrayList<>();
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(email).child("taker");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                takers.clear();

                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    TakerPOJO taker =new TakerPOJO(dataSnapshot.child("patientEmail").getValue().toString()
                            ,dataSnapshot.child("name").getValue().toString()
                            , dataSnapshot.child("email").getKey().toString()
                            ,(Integer.parseInt(String.valueOf(dataSnapshot.child("img").getValue())))
                            ,dataSnapshot.child("requestId").getValue().toString()
                    );

                    takers.add(taker);


                }
                myDelegation.onSuccessTaker(takers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    @Override
    public void addUserInDB(User user) {
        String uid = user.getEmail().split("\\.")[0];
        // check if null or not
        FirebaseDatabase.getInstance().getReference("users")
                .child(uid)
                .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    myDelegation.onSuccess();
                } else {
                    String errorMessage = handleFireBaseException(task);
                    myDelegation.onFailure(errorMessage);
                }
            }
        });
    }

    @Override
    public void getUserFromRealDB(String email) {
        Log.i("TAG", "getUserFromRealDB: ");
        String uid = email.split("\\.")[0];
        Query query = FirebaseDatabase.getInstance().getReference().child("users").child(uid).child("name");
        query.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()) {
                    Log.i("TAG", "getUserFromRealDB: " + task.getResult().getValue().toString());

                    myDelegation.onSuccessReturn(task.getResult().getValue().toString());
                } else {
                    myDelegation.onFailure(task.getException().getMessage());
                    Log.i("TAG", "problem in getting name: " + task.getException().getMessage());

                }
            }
        });
    }


    private String handleFireBaseException(Task task) {
        String errorMessage = "";

        try {
            throw task.getException();
        } catch (FirebaseAuthWeakPasswordException e) {
            errorMessage = "weak Password";
        } catch (FirebaseAuthUserCollisionException e) {
            errorMessage = "user exists already";
        } catch (FirebaseAuthInvalidUserException e) {
            errorMessage = "problem in e-mail or password";
        } catch (Exception e) {
            errorMessage = e.getMessage();
        }


        return errorMessage;
    }


}
