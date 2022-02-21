package com.team_three.medicalreminder.helpRequest;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentHelpRequistListBinding;
import com.team_three.medicalreminder.medicationList.view.ActiveListAdapter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Taker;
import com.team_three.medicalreminder.taker.view.ViewHolder;

import java.util.ArrayList;
import java.util.List;


public class HelpRequistList extends Fragment {

FragmentHelpRequistListBinding fragmentHelpRequistListBinding;
helpRequestAdapter adapter;
   // private FirebaseRecyclerAdapter adapter;
    private List<Taker> takerList;
    boolean flag = false;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        takerList = new ArrayList<>();
        fragmentHelpRequistListBinding = FragmentHelpRequistListBinding.inflate(inflater,container,false);
        return fragmentHelpRequistListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<Taker> medicinesActiveList = new ArrayList<>();
        medicinesActiveList.add(new Taker(1,"das"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(HelpRequistList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);

        fragmentHelpRequistListBinding.recyclerView.setLayoutManager(layoutManager);
//        adapter=new helpRequestAdapter(this.getContext(),medicinesActiveList);
//        fragmentHelpRequistListBinding.recyclerView.setAdapter(adapter);

        Load(this.getContext());


    }
    private void Load(Context context) {


        Query query = FirebaseDatabase.getInstance().getReference().child("Request").child("email");

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                takerList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if(dataSnapshot.child("email").getValue().toString().equals("mohab@gmail.com")){
                    Taker taker =new Taker(dataSnapshot.child("email").getValue().toString(),
                            (Integer.parseInt(String.valueOf(dataSnapshot.child("img").getValue()))),
                            dataSnapshot.child("name").getValue().toString(),
                            dataSnapshot.child("id").getValue().toString());
                    takerList.add(taker);
                    }
                }
                adapter=new helpRequestAdapter(context,takerList);
                fragmentHelpRequistListBinding.recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//
//        FirebaseRecyclerOptions<Taker> options =new FirebaseRecyclerOptions.Builder<Taker>()
//                .setQuery(query, new SnapshotParser<Taker>() {
//                    @NonNull
//                    @Override
//                    public Taker parseSnapshot(@NonNull DataSnapshot snapshot) {
//
//
//
//                                //flag =true;
////                        for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
//                            return  new Taker(snapshot.child("email").getValue().toString(),
//                                    (Integer.parseInt(String.valueOf(snapshot.child("img").getValue()))),
//                                    snapshot.child("name").getValue().toString(),
//                                    snapshot.child("id").getValue().toString()
//
//                                    );
//
////                        }
//
//
//                    }
//                }).build();
//
//            adapter =new FirebaseRecyclerAdapter<Taker, ViewHolder>(options) {
//                @Override
//                protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Taker model) {
//                   if (model.getEmail().equals("mohab@gmail.com")){
//                       holder.setImg(model.getImg());
//                       holder.setTxtTitle(model.getName());
//                       holder.setTxtDesc("I invite you to help me");
//                   }else{
//                      holder.disableRequest();
//                   }
//
//
//                }
//
//
//
//
//                @NonNull
//                @Override
//                public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                    View view =LayoutInflater.from(parent.getContext())
//                            .inflate(R.layout.taker_request_row,parent,false);
//                    return new ViewHolder(view);
//                }
//
//
//            };
//            fragmentHelpRequistListBinding.recyclerView.setAdapter(adapter);
//            adapter.startListening();


    }
}