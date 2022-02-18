package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
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
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.dataBase.LocalSourceInterface;
import com.team_three.medicalreminder.databinding.FragmentMedicationTimeBinding;
import com.team_three.medicalreminder.homeScreen.presenter.HomeScreenPresenter;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.util.List;


public class MedicationTimeFragment extends Fragment implements HomeFragmentInterface, OnClickListener {
    TimeAdapter timeAdapter;
    HomeScreenPresenter myPresenter;
    Repository myRepository;
    FragmentMedicationTimeBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMedicationTimeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRepository();
        initRecycleView();

    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(MedicationTimeFragment.this.getContext());
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        timeAdapter = new TimeAdapter(this.getContext(), null, this);
        // don't forget
        binding.timeRecycleView.setLayoutManager(layoutManager);
        binding.timeRecycleView.setAdapter(timeAdapter);
        requestDataFromPresenter();
//        homeAdapter.notifyDataSetChanged();
    }

    private void initRepository() {
        LocalSourceInterface myLocal = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        myRepository = Repository.getInstance(myLocal, this.getContext());
        myPresenter = new HomeScreenPresenter(this, myRepository);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void showMedications(List<MedicationPOJO> storedMedications) {
        timeAdapter.setMedicine(storedMedications.get(HomeScreenPresenter.position));
        timeAdapter.notifyDataSetChanged();
    }

    private void requestDataFromPresenter() {
        myPresenter.getMedicationDay(this, 0);

    }

    @Override
    public void onClick(View view, int position) {
        Navigation.findNavController(view).navigate(R.id.action_medicationTimeFragment_to_displayMedicationDrug);

    }
}