package com.team_three.medicalreminder.helpRequest;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.ActiveMedsRowBinding;
import com.team_three.medicalreminder.databinding.TakerRequestRowBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Taker;

import java.util.List;


public class helpRequestAdapter extends RecyclerView.Adapter<helpRequestAdapter.ViewHolder> {
    final private Context _context;
    private List<Taker> medicines;

    public helpRequestAdapter(Context context, List<Taker> medicines) {
        Log.i("TAG", "MedsListAdapter: abdoooo");
        _context = context;
        this.medicines = medicines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TakerRequestRowBinding binding;
        public ViewHolder(TakerRequestRowBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
            Log.i("TAG", "ViewHolder: abdooooooooo");

        }
    }

    @NonNull
    @Override
    public helpRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder: ABDOOOOOOOO");
        return new helpRequestAdapter.ViewHolder(TakerRequestRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull helpRequestAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:ABDOOOOOOOOOO ");


    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
}