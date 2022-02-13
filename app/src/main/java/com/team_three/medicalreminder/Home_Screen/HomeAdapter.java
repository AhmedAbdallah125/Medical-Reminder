package com.team_three.medicalreminder.Home_Screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team_three.medicalreminder.Home_Screen.model.MedicineDetails;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.HomeScreenRecycleViewLayoutBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    final private Context _context;
    private List<MedicineDetails> medicines;

    public HomeAdapter(Context context, List<MedicineDetails> medicines) {
        Log.i("TAG", "HomeAdapter: ");
        _context = context;
        this.medicines = medicines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        HomeScreenRecycleViewLayoutBinding binding;
        public ViewHolder(HomeScreenRecycleViewLayoutBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
            Log.i("TAG", "ViewHolder: ");
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder: ");
        return new ViewHolder(HomeScreenRecycleViewLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder: ");
        holder.binding.recycleHomImageView.setImageResource(medicines.get(position).getIconResource());
        holder.binding.txtHourHomeRecycle.setText(medicines.get(position).getTime());
        holder.binding.txtRecycleMedicine.setText(medicines.get(position).getMedicinePart());
        holder.binding.txtMedicineDtails.setText(medicines.get(position).getMedicineDetails());
    }

    @Override
    public int getItemCount() {
        return medicines.size();
    }
}
