package com.team_three.medicalreminder.addmedication.view;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.databinding.FragmentAddMedicationBinding;

import java.util.Calendar;

public class AddMedication extends Fragment {
    private FragmentAddMedicationBinding binding;
    private String pillCounter = "";

    public AddMedication() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentAddMedicationBinding.inflate(inflater, container, false);
        binding.btnChooseTime.setVisibility(View.INVISIBLE);

        //must check
        binding.btnAdd.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home));
        binding.btnChooseTime.setOnClickListener(v -> {
            showHourPicker(pillCounter);
            binding.btnChooseTime.setVisibility(View.INVISIBLE);
            binding.txtMedicationTakePillsNo.setText("");
        });
        binding.btnStartDate.setOnClickListener(v -> showDatePicker(binding.startDateSelected, "start"));
        binding.btnEndDate.setOnClickListener(v -> showDatePicker(binding.endDateSelected, "end"));
        binding.imageExit.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home));
        binding.txtMedicationTakePillsNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().isEmpty()) {
//                    binding.btnChooseTime.setEnabled(true);
                    binding.btnChooseTime.setVisibility(View.VISIBLE);
                    pillCounter = editable.toString();
                }

            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.Medication_Type,android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        binding.spinneMedicationType.setAdapter(adapter);
//        binding.spinneMedicationType.getSelectedItem().toString();
    }

    public void showHourPicker(String s) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if (view.isShown()) {
                    myCalender.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalender.set(Calendar.MINUTE, minute);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hour, minute, true);
        String title = "Choose (" + s + ") pill item";
        timePickerDialog.setTitle(title);

        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    public void showDatePicker(TextView v, String s) {
        final Calendar myCalender = Calendar.getInstance();
        int year = myCalender.get(Calendar.YEAR);
        int month = myCalender.get(Calendar.MONTH);
        int day = myCalender.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                if (view.isShown()) {
                    month = month + 1;
                    String date = day + "/" + month + "/" + year;
                    v.setText(date);
                }

            }
        };
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myDateListener, year, month, day);
        String title = "Choose " + s + " date";
        datePickerDialog.setTitle(title);

        datePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        datePickerDialog.show();
    }
}