package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.databinding.TimeCardBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder> {
    final private Context _context;
    private MedicationPOJO medicines;
    private Map<String, Integer> timeAndDose;
    private OnClickListener onClickListener;


    public TimeAdapter(Context context, MedicationPOJO medicines,OnClickListener onClickListener) {
        if (medicines == null) {
            medicines = new MedicationPOJO();
            this.timeAndDose = new HashMap<>();
        } else {
            this.medicines = medicines;
            this.timeAndDose = medicines.getTimeAndDose();
        }
        _context = context;
        this.onClickListener=onClickListener;

    }

    public void setMedicine(MedicationPOJO medicines) {
        this.medicines = medicines;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeAdapter.ViewHolder(TimeCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Map.Entry<String, Integer> entry = timeAndDose.entrySet().iterator().next();

        holder.timeBinding.recycleHour.setText(entry.getKey());
        holder.timeBinding.txtPillCount.setText(medicines.getFormat() + " :" + entry.getValue());
        holder.timeBinding.cardTimeView.setOnClickListener(view -> {
            onClickListener.onClick(view,position);
        });
    }

    @Override
    public int getItemCount() {
        return timeAndDose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TimeCardBinding timeBinding;

        public ViewHolder(TimeCardBinding timeBinding) {
            super(timeBinding.getRoot());
        }
    }
}
