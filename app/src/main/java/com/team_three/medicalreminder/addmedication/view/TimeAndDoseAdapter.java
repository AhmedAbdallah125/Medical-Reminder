package com.team_three.medicalreminder.addmedication.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.databinding.ChooseTimeCardBinding;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class TimeAndDoseAdapter extends RecyclerView.Adapter<TimeAndDoseAdapter.ViewHolder> {
    private int dosePerDay = 0;
    private Context context;
    private Map<String, Integer> timeAndDose;

    public Map<String, Integer> getTimeAndDose() {
        return timeAndDose;
    }

    public TimeAndDoseAdapter(int n , Context context, Map<String,Integer> map) {
        dosePerDay = n;
        this.context = context;
        if(map==null)
            timeAndDose = new HashMap<>();
        else
            timeAndDose = map;
    }

    public void setDosePerDay(int n) {
        dosePerDay = n;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TimeAndDoseAdapter.ViewHolder(ChooseTimeCardBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimeAndDoseAdapter.ViewHolder holder, int position) {
        if(timeAndDose.isEmpty()) {
            holder.binding.txtDoseTime.setText("Choose Time");
            holder.binding.txtDoseNumber.setText(holder.counter + "");
        }else{
            holder.result= (String) timeAndDose.keySet().toArray()[position];
            holder.counter=timeAndDose.get(holder.result);
            holder.binding.txtDoseTime.setText(holder.result);
            holder.binding.txtDoseNumber.setText(holder.counter + "");
        }
        holder.binding.txtDoseFormat.setText("Dose:");

        holder.binding.txtDoseTime.setOnClickListener(v -> {
            final Calendar myCalender = Calendar.getInstance();
            int hour = myCalender.get(Calendar.HOUR_OF_DAY);
            int minute = myCalender.get(Calendar.MINUTE);
            TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                String format;

                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                    if (!holder.result.isEmpty())
                        timeAndDose.remove(holder.result);

                    if (view.isShown()) {
                        myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        myCalender.set(Calendar.MINUTE, minute);
                        if (hourOfDay == 0) {
                            hourOfDay += 12;
                            format = "AM";
                        } else if (hourOfDay == 12) {
                            format = "PM";
                        } else if (hourOfDay > 12) {
                            hourOfDay -= 12;
                            format = "PM";
                        } else {
                            format = "AM";
                        }
                        holder.result = hourOfDay + ":" + minute + " " + format;
                        timeAndDose.put(holder.result, holder.counter);
                        holder.binding.txtDoseTime.setText(holder.result);
                    }
                }
            };
            TimePickerDialog timePickerDialog = new TimePickerDialog(context, android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                    myTimeListener, hour, minute, false);
            timePickerDialog.setTitle("Choose time");
            timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            timePickerDialog.show();
        });
        holder.binding.imgPlus1.setOnClickListener(v -> {
            holder.counter++;
            holder.binding.txtDoseNumber.setText(holder.counter + "");
            if (timeAndDose.containsKey(holder.binding.txtDoseTime.getText().toString()))
                timeAndDose.replace(holder.binding.txtDoseTime.getText().toString(), holder.counter);
        });
        holder.binding.imgMinus1.setOnClickListener(v -> {
            if (holder.counter > 1) {
                holder.counter--;
                if (timeAndDose.containsKey(holder.binding.txtDoseTime.getText().toString()))
                    timeAndDose.replace(holder.binding.txtDoseTime.getText().toString(), holder.counter);
                holder.binding.txtDoseNumber.setText(holder.counter + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return dosePerDay;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ChooseTimeCardBinding binding;
        int counter;
        String result;
        public ViewHolder(@NonNull ChooseTimeCardBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            counter = 1;
            result="";
        }
    }
}
