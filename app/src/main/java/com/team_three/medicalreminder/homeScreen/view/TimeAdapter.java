package com.team_three.medicalreminder.homeScreen.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.R;
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
    private OnMedicationTimeListener onMedicationTimeListener;


    public TimeAdapter(Context context, MedicationPOJO medicines,OnMedicationTimeListener onMedicationTimeListener) {
        if (medicines == null) {
            medicine = new MedicationPOJO();
            this.timeAndDose = new HashMap<>();
        } else {
            this.medicine = medicines;
            this.timeAndDose = medicines.getTimeAndDose();
        }
        _context = context;
        this.onMedicationTimeListener=onMedicationTimeListener;

    }

    public void setMedicine(MedicationPOJO medicines) {
        this.medicine = medicines;
        timeAndDose=medicines.getTimeAndDose();
//        Log.i("TAG", "setMedicine: "+medicines.getTimeAndDose().entrySet().iterator().next().getKey());
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

        holder.timeBinding.recycleHour.setText(key);
        holder.timeBinding.txtPillCount.setText( value +" " +medicine.getFormat());
        boolean take =medicine.getIsTakenList().get(position);
        bindViews(holder.timeBinding,take);
        holder.timeBinding.imageTake.setOnClickListener(v->{
            onMedicationTimeListener.onTakeClick(position,take,medicine);
        });
        holder.timeBinding.imageSkip.setOnClickListener(v->{
            onMedicationTimeListener.onTakeClick(position,take,medicine);
        });
    }

    @Override
    public int getItemCount() {
        if(timeAndDose==null){
            return 0;
        }
        return timeAndDose.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TimeCardBinding timeBinding;

        public ViewHolder(TimeCardBinding timeBinding) {
            super(timeBinding.getRoot());
            this.timeBinding=timeBinding;
        }
    }
    private void bindViews(TimeCardBinding timeBinding,boolean take){
        if(!take){
            bindImagesBasedOnTaken(R.drawable.ic_baseline_done_24,R.drawable.ic_baseline_close_24,timeBinding);
            bindTextBasedOnTaken(R.string.take,R.string.skip,timeBinding);
        }else{
            bindImagesBasedOnTaken(R.drawable.ic_baseline_replay_24,R.drawable.ic_baseline_replay_24,timeBinding);
            bindTextBasedOnTaken(R.string.untake,R.string.unSkip,timeBinding);
        }

    }
    private void bindImagesBasedOnTaken(int takeImage,int skipImage,TimeCardBinding timeBinding){
        timeBinding.imageTake.setImageResource(takeImage);
        timeBinding.imageSkip.setImageResource(skipImage);
    }
    private void bindTextBasedOnTaken(int txtTake,int txtSkip,TimeCardBinding timeBinding){
        timeBinding.txtSkip.setText(_context.getString(txtSkip));
        timeBinding.textTake.setText(_context.getString(txtTake));
    }
}
