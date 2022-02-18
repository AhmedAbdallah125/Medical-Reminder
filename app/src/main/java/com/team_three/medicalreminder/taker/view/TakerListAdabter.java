package com.team_three.medicalreminder.taker.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.databinding.ActiveMedsRowBinding;
import com.team_three.medicalreminder.databinding.TakerRowBinding;

import com.team_three.medicalreminder.taker.model.Taker;

import java.util.List;


public class TakerListAdabter extends RecyclerView.Adapter<TakerListAdabter.ViewHolder> {
    final private Context _context;
    private List<Taker> takers;

    public TakerListAdabter(Context context, List<Taker> medicines) {
        Log.i("TAG", "MedsListAdapter: abdoooo");
        _context = context;
        this.takers = medicines;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TakerRowBinding binding;
        public ViewHolder(TakerRowBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
            Log.i("TAG", "ViewHolder: abdooooooooo");

        }
    }

    @NonNull
    @Override
    public TakerListAdabter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder: ABDOOOOOOOO");
        return new TakerListAdabter.ViewHolder(TakerRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull TakerListAdabter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:ABDOOOOOOOOOO ");
        holder.binding.imageView.setImageResource(takers.get(position).getImg());
        holder.binding.txttakerName.setText(takers.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return takers.size();
    }
}
