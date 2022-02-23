package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.team_three.medicalreminder.homeScreen.model.MedicineDetails;
import com.team_three.medicalreminder.databinding.HomeScreenRecycleViewLayoutBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>  {
    final private Context _context;
    private List<MedicationPOJO> medicines;
    private OnClickListener onClickListener;
    public void setMedicines(List<MedicationPOJO> medicines) {
        this.medicines = medicines;
    }

    public HomeAdapter(Context context, List<MedicationPOJO> medicines,OnClickListener onClickListener) {
        if (medicines == null) {
            medicines = new ArrayList<>();
        } else {
            this.medicines = medicines;
        }
        this.onClickListener=onClickListener;
        _context = context;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomeScreenRecycleViewLayoutBinding binding;

        public ViewHolder(HomeScreenRecycleViewLayoutBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(HomeScreenRecycleViewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }
    // don't forget go to another fragment

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {

        holder.binding.recycleHomImageView.setImageResource(medicines.get(position).getImageID());
        holder.binding.txtRecycleMedicine.setText(medicines.get(position).getMedicationName());
        holder.binding.txtMedicineDtails.setText(
                medicines.get(position).getFormat() +" ,"+
                medicines.get(position).getStrength()+" "+
                medicines.get(position).getWeight());
        holder.binding.txtTimes.setText(medicines.get(position).getTakeTimePerDay());
        holder.binding.btnGoToDetails.setOnClickListener(view -> {
            onClickListener.onClick(view,position);
        });
    }

    @Override
    public int getItemCount() {
        if(medicines==null){
            return 0;
        }else {
            return medicines.size();

        }
    }
}
