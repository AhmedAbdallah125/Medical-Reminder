package com.team_three.medicalreminder.taker.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentAddTakerBinding;
import com.team_three.medicalreminder.model.Taker;

public class AddTaker extends Fragment {

    FragmentAddTakerBinding binding;
    Taker taker;
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

        binding.btnTakerAdded.setOnClickListener(view1 -> {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Request");
            String id = databaseReference.child("email").push().getKey();
            taker=new Taker(binding.txtEmail.getEditableText().toString(),R.drawable.one,binding.txtName.getEditableText().toString(),id);

            databaseReference.child("email").push().setValue(taker);

        });
    }

}