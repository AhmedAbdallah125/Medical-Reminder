package com.team_three.medicalreminder.addmedication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

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
import java.util.List;
import java.util.Locale;

public class AddAndEditMedication extends Fragment implements onClickAddMedication, AddAndEditMedicationInterface {

    private FragmentAddMedicationBinding binding;

    private MedicationPOJO medication;
    private long startDate;
    private long endDate;

    private Repository repository;
    private ConcreteLocalClass localClass;
    private AddMedicationPresenterInterface presenterInterface;

    private boolean isFillReminder = false;
    private int previousDestination;
    private boolean isAdd = false;

    //spinner
    private String format = "";
    private String weight = "";
    private String dosePerDay = "";
    private String instruction = "";
    private String recurrence = "";

    //editable
    private String medicationName;
    private String start_date;
    private String end_date;
    private String strength = "";
    private String leftNumber = "";
    private String leftNumberReminder = "";
    private String reason = "";

    private TimeAndDoseAdapter timeAndDoseAdapter;

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
        localClass = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        repository = Repository.getInstance(localClass, this.getContext());
        presenterInterface = new AddMedicationPresenter(repository, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previousDestination = Navigation.findNavController(view).getPreviousBackStackEntry().getDestination().getId();
        initRecycleView();
        if (previousDestination == R.id.displayMedicationDrug) {
            binding.txtTitle.setText(R.string.edit_medication);
            binding.btnAdd.setVisibility(View.INVISIBLE);
            isAdd = false;
        } else {
            isAdd = true;
            handleAddScreen();
        }
    }

    private void handleAddScreen() {
        binding.txtTitle.setText(R.string.add_medication);
        binding.txtDone.setVisibility(View.INVISIBLE);
        binding.txtReminderRefill.setVisibility(View.INVISIBLE);

        binding.btnAdd.setOnClickListener(v -> {
            getEditableText();
            if (medicationName.isEmpty()) {
                binding.txtMedicneName.setError("Medication name required");
                binding.txtMedicneName.requestFocus();
            } else if (start_date.isEmpty()) {
                binding.startDateSelected.setError("Start date required");
                binding.startDateSelected.requestFocus();
            } else if (end_date.isEmpty()) {
                binding.endDateSelected.setError("End date required");
                binding.btnEndDate.requestFocus();
            } else {
                setEditTextResultToPOJO();
                setSpinnerResultToPOJO();
                setDateAndTimeResultToPOJO();
                setBooleanResultToPOJO();
                onClickAdd(medication);
                Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home);
            }
        });

        binding.btnStartDate.setOnClickListener(v -> showDatePicker(binding.startDateSelected, "start"));

        binding.btnEndDate.setOnClickListener(v -> showDatePicker(binding.endDateSelected, "end"));

        binding.imageExit.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home));

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

        binding.spinnerMedicationNoOfTimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i==5)
                    i=0;

                setDosePerTime(i);

                if (i != 0)
                    dosePerDay = binding.spinnerMedicationNoOfTimes.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinneMedicationType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    format = binding.spinneMedicationType.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerMedicationInstructions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    instruction = binding.spinnerMedicationInstructions.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerMedicationRecurrence.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    recurrence = binding.spinnerMedicationRecurrence.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        binding.spinnerMedicationStrengthWeight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0)
                    weight = binding.spinnerMedicationStrengthWeight.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getEditableText() {
        medicationName = binding.txtMedicneName.getEditableText().toString().trim();
        start_date = binding.startDateSelected.getText().toString().trim();
        end_date = binding.endDateSelected.getText().toString().trim();
        strength = binding.txtMedicationStrengthNumber.getEditableText().toString();
        leftNumber = binding.txtPillsLeft.getEditableText().toString();
        leftNumberReminder = binding.txtfillReminderPills.getEditableText().toString();
        reason = binding.txtReason.getEditableText().toString();
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
    public void onClickAdd(MedicationPOJO medication) {
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
        medication.setMedicationName(medicationName);
        medication.setStrength(strength.isEmpty() ? -1 : Integer.parseInt(strength));
        medication.setLeftNumber(leftNumber.isEmpty() ? -1 : Integer.parseInt(leftNumber));
        medication.setLeftNumberReminder(leftNumberReminder.isEmpty() ? -1 : Integer.parseInt(leftNumberReminder));
        medication.setMedicationReason(reason);
    }

    private void setSpinnerResultToPOJO() {
        medication.setFormat(format);
        medication.setWeight(weight);
        medication.setTakeTimePerDay(dosePerDay);
        medication.setInstruction(instruction);
        medication.setRecurrence(recurrence);
    }

    private void setDateAndTimeResultToPOJO() {
        medication.setStartDate(startDate);
        medication.setEndDate(endDate);
        medication.setTimeAndDose(timeAndDoseAdapter.getTimeAndDose().isEmpty() ? null : timeAndDoseAdapter.getTimeAndDose());
    }

    private void setBooleanResultToPOJO() {
        medication.setFillReminder(isFillReminder);
        medication.setIsTakenList(isTakenList(timeAndDoseAdapter.getTimeAndDose().size()));
        medication.setActive(!timeAndDoseAdapter.getTimeAndDose().isEmpty());
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

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        timeAndDoseAdapter = new TimeAndDoseAdapter(0, this.getContext());
        binding.chooseTimeRecyclerView.setLayoutManager(layoutManager);
        binding.chooseTimeRecyclerView.setAdapter(timeAndDoseAdapter);
    }

    private void setDosePerTime(int n) {
        timeAndDoseAdapter.setDosePerDay(n);
        timeAndDoseAdapter.notifyDataSetChanged();
    }

}