package com.team_three.medicalreminder.medicationList.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.databinding.ActiveMedsRowBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;
public class MedsListAdapter extends RecyclerView.Adapter<MedsListAdapter.ViewHolder> {
    final private Context _context;
    private List<MedicationPOJO> medicines;

    public MedsListAdapter(Context context, List<MedicationPOJO> medicines) {
        Log.i("TAG", "MedsListAdapter: abdoooo");
        _context = context;
        this.medicines = medicines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ActiveMedsRowBinding binding;
        public ViewHolder(ActiveMedsRowBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
            Log.i("TAG", "ViewHolder: abdooooooooo");

        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder: ABDOOOOOOOO");
        return new ViewHolder(ActiveMedsRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MedsListAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:ABDOOOOOOOOOO ");
        holder.binding.imgActiveMed.setImageResource(medicines.get(position).getImageID());
        holder.binding.txtActiveMedName.setText(medicines.get(position).getMedicationName());
        holder.binding.txtActiveStrengthNumberOfTheMed.setText(String.valueOf(medicines.get(position).getStrength()));
        holder.binding.txtActiveStrengthWeightOfTheMed.setText(medicines.get(position).getWeight());
        holder.binding.txtNumberOfUnits.setText(String.valueOf(medicines.get(position).getLeftNumber()));
        holder.binding.txtFormOfMed.setText(medicines.get(position).getFormat());
        holder.binding.txtLeft.setText("left");
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
}

