package com.team_three.medicalreminder.medicationList.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.databinding.InactiveMedsRowBinding;
import com.team_three.medicalreminder.medicationList.model.MedicinesActive;

import java.util.List;
public class InactiveListAdapter extends RecyclerView.Adapter<InactiveListAdapter.ViewHolder> {
    final private Context _context;
    private List<MedicinesActive> medicines;

    public InactiveListAdapter(Context context, List<MedicinesActive> medicines) {
        Log.i("TAG", "InactiveListAdapter: 222222222222");
        _context = context;
        this.medicines = medicines;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder:22222 ");
        return new ViewHolder(InactiveMedsRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:2222 ");
        holder.binding.imgInactive.setImageResource(medicines.get(position).getIconResource());
        holder.binding.txtInactiveMedName.setText(medicines.get(position).getName());
        holder.binding.txtInactiveStrengthNumberOfTheMed.setText(String.valueOf(medicines.get(position).getStrengthNumber()));
        holder.binding.txtInactiveStrengthWeightOfTheMed.setText(medicines.get(position).getStrengthWeight());

    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        InactiveMedsRowBinding binding;
        public ViewHolder(InactiveMedsRowBinding sbinding) {
            super(sbinding.getRoot());
            Log.i("TAG", "ViewHolder: 22222222222");
            binding = sbinding;
        }
    }

}



