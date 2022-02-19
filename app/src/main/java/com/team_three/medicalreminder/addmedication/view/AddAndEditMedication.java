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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.addmedication.presenter.AddMedicationPresenter;
import com.team_three.medicalreminder.addmedication.presenter.AddMedicationPresenterInterface;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.FragmentAddMedicationBinding;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class AddAndEditMedication extends Fragment implements onClickAddMedication, AddAndEditMedicationInterface {

    private FragmentAddMedicationBinding binding;

    private int pillCounter;
    private Map<String, Integer> timeAndDose;
    private MedicationPOJO medication;
    private long startDate;
    private long endDate;
    private Repository repository;
    private ConcreteLocalClass localClass;
    private AddMedicationPresenterInterface presenterInterface;
    private boolean isFillReminder = false;
    private int previousDestination;
    private boolean isAdd = false;
    private String format;

    public AddAndEditMedication() {
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
        medication = new MedicationPOJO();
        timeAndDose = new HashMap<String, Integer>();
        localClass = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        repository = Repository.getInstance(localClass, this.getContext());
        presenterInterface = new AddMedicationPresenter(repository, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previousDestination = Navigation.findNavController(view).getPreviousBackStackEntry().getDestination().getId();
        Log.i("TAG", "onViewCreated: " + R.id.fragment_medication_list);
        if (previousDestination == R.id.displayMedicationDrug) {
            binding.txtTitle.setText(R.string.edit_medication);
            binding.btnAdd.setVisibility(View.INVISIBLE);
            isAdd = false;
        } else {
            isAdd = true;
            handleDisplayScreen();
        }
    }

    private void handleDisplayScreen() {
        binding.txtTitle.setText(R.string.add_medication);
        binding.txtDone.setVisibility(View.INVISIBLE);
        binding.btnChooseTime.setVisibility(View.INVISIBLE);
        binding.txtReminderRefill.setVisibility(View.INVISIBLE);
        binding.btnAdd.setOnClickListener(v -> {
            setEditTextResultToPOJO();
            setSpinnerResultToPOJO();
            setDateAndTimeResultToPOJO();
            setBooleanResultToPOJO();
            onClick(medication);
            Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home);
        });

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
                    binding.btnChooseTime.setVisibility(View.VISIBLE);
                    pillCounter = Integer.parseInt(editable.toString());
                }
            }
        });

        binding.reminderFillSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked)
                    binding.txtReminderRefill.setVisibility(View.VISIBLE);
                else {
                    binding.txtReminderRefill.setVisibility(View.INVISIBLE);
                }
                isFillReminder = isChecked;
            }
        });
    }

    private void showHourPicker(int s) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);

        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
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
                    timeAndDose.put(hourOfDay + ":" + minute + " " + format, s);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hour, minute, false);
        String title = "Choose (" + s + ") pill time";
        timePickerDialog.setTitle(title);

        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private void showDatePicker(TextView v, String s) {
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
                    if (s.equals("start")) {
                        startDate = getDateMillis(date);
                    } else {
                        endDate = getDateMillis(date);
                    }
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

    @Override
    public void onClick(MedicationPOJO medication) {
        if (isAdd)
            addMedication(medication);
        else
            updateMedication(medication);
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        presenterInterface.updateToDatabase(medication);
    }

    @Override
    public void showMedication() {
        medication = presenterInterface.showMedication();
    }

    @Override
    public void addMedication(MedicationPOJO medication) {
        presenterInterface.addToDatabase(medication);
        Log.i("TAG", "addMedication: ");
    }

    private void setEditTextResultToPOJO() {
        medication.setMedicationName(Objects.requireNonNull(binding.txtMedicneName.getEditableText()).toString());
        medication.setStrength(Integer.parseInt(binding.txtMedicationStrengthNumber.getEditableText().toString()));
        medication.setLeftNumber(Integer.parseInt(binding.txtPillsLeft.getEditableText().toString()));
        medication.setLeftNumberReminder(Integer.parseInt(binding.txtfillReminderPills.getEditableText().toString()));
        medication.setMedicationReason(binding.txtReason.getEditableText().toString());
    }

    private void setSpinnerResultToPOJO() {
        medication.setFormat(binding.spinneMedicationType.getSelectedItem().toString());
        medication.setWeight(binding.spinnerMedicationStrengthWeight.getSelectedItem().toString());
        medication.setTakeTimePerDay(binding.spinnerMedicationNoOfTimes.getSelectedItem().toString());
        medication.setInstruction(binding.spinnerMedicationInstructions.getSelectedItem().toString());
        medication.setRecurrence(binding.spinnerMedicationRecurrence.getSelectedItem().toString());
    }

    private void setDateAndTimeResultToPOJO() {
        medication.setStartDate(startDate);
        medication.setEndDate(endDate);
        medication.setTimeAndDose(timeAndDose);
    }

    private void setBooleanResultToPOJO() {
        medication.setFillReminder(isFillReminder);
        medication.setIsTakenList(isTakenList(timeAndDose.size()));
        medication.setActive(true);
        medication.setImageID(R.drawable.ic_pill);
    }

    private List<Boolean> isTakenList(int length) {
        List<Boolean> list = new ArrayList<Boolean>();
        for (int i = 0; i < length; i++) {
            list.add(false);
        }
        return list;
    }

    private Long getDateMillis(String date) {
        long milliseconds = -1;
        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        try {
            Date d = f.parse(date);
            milliseconds = d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return milliseconds;
    }

}