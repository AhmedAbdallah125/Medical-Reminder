package com.team_three.medicalreminder.medicationList.view;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.InactiveMedsRowBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;
public class InactiveListAdapter extends RecyclerView.Adapter<InactiveListAdapter.ViewHolder> {
    final private Context _context;
    private List<MedicationPOJO> medicines;

    public InactiveListAdapter(Context context, List<MedicationPOJO> medicines) {
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
        holder.binding.imgInactive.setImageResource(medicines.get(position).getImageID());
        holder.binding.txtInactiveMedName.setText(medicines.get(position).getMedicationName());
        holder.binding.txtInactiveStrengthNumberOfTheMed.setText(String.valueOf(medicines.get(position).getStrength()));
        holder.binding.txtInactiveStrengthWeightOfTheMed.setText(medicines.get(position).getWeight());
        holder.binding.inactiveConstraint.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("displayMed", medicines.get(position));
            Navigation.findNavController(view).navigate(R.id.action_fragment_medication_list_to_displayMedicationDrug,bundle);

        });
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



