package com.team_three.medicalreminder.addmedication.view;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.addmedication.presenter.AddMedicationPresenter;
import com.team_three.medicalreminder.addmedication.presenter.AddMedicationPresenterInterface;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.FragmentAddMedicationBinding;
import com.team_three.medicalreminder.displaymedicationdrug.view.DisplayMedicationDrug;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.workmanger.MyPeriodicWorkManger;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

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
    private boolean chooseTimesFlag = false;

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

    //dialog
    MaterialAlertDialogBuilder dialogBuilder;

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
        localClass = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        repository = Repository.getInstance(localClass, this.getContext());
        presenterInterface = new AddMedicationPresenter(repository, this);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        previousDestination = Navigation.findNavController(view).getPreviousBackStackEntry().getDestination().getId();
        if (previousDestination == R.id.displayMedicationDrug) {
            binding.txtTitle.setText(R.string.edit_medication);
            binding.btnAdd.setVisibility(View.GONE);
            if (getArguments() != null) {
                medication = getArguments().getParcelable(DisplayMedicationDrug.editTag);
                isAdd = false;
                handleEditScreen();
            }
        } else {
            medication = new MedicationPOJO();
            isAdd = true;
            handleAddScreen();
        }
        initRecycleView();
        handleSameButtons();
    }

    private void handleSameButtons() {

        binding.imageExit.setOnClickListener(v -> lunchExitDialog());

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
                if (i == 5)
                    i = 0;

                setDosePerTime(i);

                if (chooseTimesFlag) {
                    timeAndDoseAdapter.setTimeAndDose();
                }

                chooseTimesFlag = true;

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
                if (i != 0) {
                    format = binding.spinneMedicationType.getSelectedItem().toString();
                    setFormatImage(i);
                }
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

        binding.btnStartDate.setOnClickListener(v -> showDatePicker(binding.startDateSelected, "start"));

        binding.btnEndDate.setOnClickListener(v -> showDatePicker(binding.endDateSelected, "end"));
    }

    private void setFormatImage(int i) {
        switch (i) {
            case 1:
                binding.imgType.setImageResource(R.drawable.pill);
                break;
            case 2:
                binding.imgType.setImageResource(R.drawable.solution);
                break;
            case 3:
                binding.imgType.setImageResource(R.drawable.injection);
                break;
            case 4:
                binding.imgType.setImageResource(R.drawable.powder);
                break;
            case 5:
                binding.imgType.setImageResource(R.drawable.drops);
                break;
            case 6:
                binding.imgType.setImageResource(R.drawable.inhaler);
                break;
        }
    }

    private void lunchExitDialog() {
        dialogBuilder = new MaterialAlertDialogBuilder(this.getContext());
        dialogBuilder.setTitle("Do you want to cancel ?")
                .setMessage("Your changes will not be save!")
                .setPositiveButton("Yes", (dialog, i) -> {
                    dialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_fragment_add_Medication_to_fragment_home);
                })
                .setNegativeButton("No", (dialog, i) -> {
                    dialog.dismiss();
                }).show();
    }

    private void handleEditScreen() {
        setSpinnerResult();
        setEditText();
        binding.txtDone.setOnClickListener(v -> {
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
                onClick(medication);
                setWorkTimer();
                Bundle bundle = new Bundle();
                bundle.putParcelable(DisplayMedicationDrug.displayTag, medication);
                Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_displayMedicationDrug, bundle);
                Toast.makeText(this.getContext(), "Successfully Updated!", Toast.LENGTH_SHORT).show();
            }
        });
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
                onClick(medication);
                setWorkTimer();
                Navigation.findNavController(v).navigate(R.id.action_fragment_add_Medication_to_fragment_home);
                Toast.makeText(this.getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
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
    public void addMedication(MedicationPOJO medication) {
        presenterInterface.addToDatabase(medication);
    }

    private void setEditTextResultToPOJO() {
        medication.setMedicationName(medicationName);
        if (!strength.isEmpty())
            medication.setStrength(Integer.parseInt(strength));
        if (!leftNumber.isEmpty())
            medication.setLeftNumber(Integer.parseInt(leftNumber));
        if (!leftNumberReminder.isEmpty())
            medication.setLeftNumberReminder(Integer.parseInt(leftNumberReminder));
        medication.setMedicationReason(reason);
    }

    private void setSpinnerResultToPOJO() {
        medication.setFormat(format);
        setImage(format);
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
    }

    private void setImage(String format) {
        switch (format) {
            case "Pill":
                medication.setImageID(R.drawable.pill);
                break;
            case "Solution":
                medication.setImageID(R.drawable.solution);
                break;
            case "Injection":
                medication.setImageID(R.drawable.injection);
                break;
            case "Powder":
                medication.setImageID(R.drawable.powder);
                break;
            case "Drops":
                medication.setImageID(R.drawable.drops);
                break;
            case "Inhaler":
                medication.setImageID(R.drawable.inhaler);
                break;
            default:
                medication.setImageID(-1);
        }
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

    private String getDateString(Long date) {
        Date d = new Date(date);
        DateFormat f = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return f.format(d);
    }

    private void initRecycleView() {
        int n = 0;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (medication.getTimeAndDose() != null && medication.getTimeAndDose().size() != 0) {
            n = medication.getTimeAndDose().size();
        }
        timeAndDoseAdapter = new TimeAndDoseAdapter(n, this.getContext(), medication.getTimeAndDose());
        binding.chooseTimeRecyclerView.setLayoutManager(layoutManager);
        binding.chooseTimeRecyclerView.setAdapter(timeAndDoseAdapter);
    }

    private void setDosePerTime(int n) {
        timeAndDoseAdapter.setDosePerDay(n);
        timeAndDoseAdapter.notifyDataSetChanged();
    }

    private void setSpinnerResult() {
        if (!medication.getFormat().isEmpty())
            binding.spinneMedicationType.setSelection(((ArrayAdapter) binding.spinneMedicationType.getAdapter())
                    .getPosition(medication.getFormat()));

        if (!medication.getWeight().isEmpty())
            binding.spinnerMedicationStrengthWeight.setSelection(((ArrayAdapter) binding.spinnerMedicationStrengthWeight.getAdapter())
                    .getPosition(medication.getWeight()));

        if (!medication.getTakeTimePerDay().isEmpty())
            binding.spinnerMedicationNoOfTimes.setSelection(((ArrayAdapter) binding.spinnerMedicationNoOfTimes.getAdapter())
                    .getPosition(medication.getTakeTimePerDay()));

        if (!medication.getInstruction().isEmpty())
            binding.spinnerMedicationInstructions.setSelection(((ArrayAdapter) binding.spinnerMedicationInstructions.getAdapter())
                    .getPosition(medication.getInstruction()));

        if (!medication.getRecurrence().isEmpty())
            binding.spinnerMedicationRecurrence.setSelection(((ArrayAdapter) binding.spinnerMedicationRecurrence.getAdapter())
                    .getPosition(medication.getRecurrence()));
    }

    private void setEditText() {
        if (!medication.isFillReminder())
            binding.txtReminderRefill.setVisibility(View.INVISIBLE);
        binding.txtMedicneName.setText(medication.getMedicationName());
        binding.startDateSelected.setText(getDateString(medication.getStartDate()));
        startDate = medication.getStartDate();
        binding.endDateSelected.setText(getDateString(medication.getEndDate()));
        endDate = medication.getEndDate();
        binding.txtMedicationStrengthNumber.setText(medication.getStrength() + "");
        binding.txtReason.setText(medication.getMedicationReason().isEmpty() ? "" : medication.getMedicationReason());
        binding.txtPillsLeft.setText(medication.getLeftNumber() + "");
        binding.txtfillReminderPills.setText(medication.getLeftNumberReminder() + "");
        binding.reminderFillSwitch.setChecked(medication.isFillReminder());
        isFillReminder = medication.isFillReminder();
    }

    private void setWorkTimer() {
        Constraints constraints = new Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .build();

        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(MyPeriodicWorkManger.class,
                3, TimeUnit.HOURS)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(this.getContext()).enqueueUniquePeriodicWork("Counter", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest);
//        WorkManager.getInstance(this.getContext()).enqueue(periodicWorkRequest);
    }

}