package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
    private MedicationPOJO medicine;
    private Map<String, Integer> timeAndDose;
    private OnClickListener onClickListener;


    public TimeAdapter(Context context, MedicationPOJO medicines,OnClickListener onClickListener) {
        if (medicines == null) {
            medicine = new MedicationPOJO();
            this.timeAndDose = new HashMap<>();
        } else {
            this.medicine = medicines;
            this.timeAndDose = medicines.getTimeAndDose();
        }
        _context = context;
        this.onClickListener=onClickListener;

    }

    public void setMedicine(MedicationPOJO medicines) {
        this.medicine = medicines;
        timeAndDose=medicines.getTimeAndDose();
        Log.i("TAG", "setMedicine: "+medicines.getTimeAndDose().entrySet().iterator().next().getKey());
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
        List<String> keyList = new ArrayList<String>(timeAndDose.keySet());
        String key = keyList.get(position);
        Integer value = timeAndDose.get(key);

        Log.d("TAG", "onBindViewHolder: fdddd");
        holder.timeBinding.recycleHour.setText(key);
        holder.timeBinding.txtPillCount.setText(medicine.getFormat() + " : " +value);
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
            this.timeBinding=timeBinding;
        }
    }
}
