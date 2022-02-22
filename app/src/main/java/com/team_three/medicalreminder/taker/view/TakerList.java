package com.team_three.medicalreminder.taker.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.FragmentTakerListBinding;
import com.team_three.medicalreminder.helpRequest.presenter.HelpRequestPresenter;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;
import com.team_three.medicalreminder.network.FireBaseNetwork;
import com.team_three.medicalreminder.taker.presenter.TakerLIstPresenterInterface;
import com.team_three.medicalreminder.taker.presenter.TakerListPresenter;

import java.util.ArrayList;
import java.util.List;


public class TakerList extends Fragment implements TakerListViewInterface{

private FragmentTakerListBinding fragmentTakerListBinding;
TakerListAdabter adabter;
SharedPreferences sharedPreferences;
String myEmail;
TakerLIstPresenterInterface lIstPresenterInterface;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentTakerListBinding =FragmentTakerListBinding.inflate(inflater,container,false);
        return fragmentTakerListBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Repository repository =  Repository.getInstance(FireBaseNetwork.getInstance(this.getActivity()),this.getContext());


        ArrayList<RequestPojo> takers = new ArrayList<>();
        //takers.add(new RequestPojo(R.drawable.one,"abdo"));
        LinearLayoutManager layoutManager = new LinearLayoutManager(TakerList.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        fragmentTakerListBinding.takerRecyclerView.setLayoutManager(layoutManager);


        sharedPreferences = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        String[] email = sharedPreferences.getString(RegisterFragment.USER_EMAIL,"null").split("\\.");
        myEmail = email[0];

        lIstPresenterInterface = new TakerListPresenter(this.getContext(),repository,this);
        lIstPresenterInterface.sendEmail(myEmail);
        lIstPresenterInterface.loadTakers();

        fragmentTakerListBinding.btnAddTaker.setOnClickListener(view1 -> {
            Navigation.findNavController(view1).navigate(R.id.action_takerList2_to_addTaker);
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        fragmentTakerListBinding =null;
    }


    @Override
    public void loadTakers(List<TakerPOJO> takerPOJOS) {
        adabter =new TakerListAdabter(this.getContext(),takerPOJOS);
        fragmentTakerListBinding.takerRecyclerView.setAdapter(adabter);

    }
}