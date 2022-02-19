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
import com.team_three.medicalreminder.databinding.ActiveMedsRowBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.List;
public class ActiveListAdapter extends RecyclerView.Adapter<ActiveListAdapter.ViewHolder> {
    final private Context _context;
    private List<MedicationPOJO> medicines;

    public ActiveListAdapter(Context context, List<MedicationPOJO> medicines) {
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
    public void onBindViewHolder(@NonNull ActiveListAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:ABDOOOOOOOOOO ");
        holder.binding.imgActiveMed.setImageResource(medicines.get(position).getImageID());
        holder.binding.txtActiveMedName.setText(medicines.get(position).getMedicationName());
        holder.binding.txtActiveStrengthNumberOfTheMed.setText(String.valueOf(medicines.get(position).getStrength()));
        holder.binding.txtActiveStrengthWeightOfTheMed.setText(medicines.get(position).getWeight());
        holder.binding.txtNumberOfUnits.setText(String.valueOf(medicines.get(position).getLeftNumber()));
        holder.binding.txtFormOfMed.setText(medicines.get(position).getFormat());
        holder.binding.txtLeft.setText("left");
        holder.binding.activeConstraint.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable("displayMed",  medicines.get(position));
            Navigation.findNavController(view)
                    .navigate(R.id.action_fragment_medication_list_to_displayMedicationDrug,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
}

