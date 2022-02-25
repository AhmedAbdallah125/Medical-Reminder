package com.team_three.medicalreminder.displaymedicationdrug.view;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.FragmentDisplayMedicationDrugBinding;
import com.team_three.medicalreminder.displaymedicationdrug.presenter.MedicationDrugDisplayPresenter;
import com.team_three.medicalreminder.displaymedicationdrug.presenter.MedicationDrugDisplayPresenterInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;
import com.team_three.medicalreminder.network.FireBaseNetwork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class DisplayMedicationDrug extends Fragment implements DisplayMedicationDrugViewInterface {
    private FragmentDisplayMedicationDrugBinding binding;
    private MedicationPOJO medication;
    private String time;
    private String dose;
    public static String displayTag = "displayMed";
    public static String editTag = "editMed";
    private ConcreteLocalClass localClass;
    private FireBaseNetwork fireBaseNetwork;
    private Repository repository;
    SharedPreferences sharedPref;
    String name = "";
    String email = "";

    private MedicationDrugDisplayPresenterInterface presenterInterface;

    //dialogs
    private MaterialAlertDialogBuilder dialogBuilder;
    private View customAlertDialogView;
    private TextInputEditText refillNumber;
    private TextView txtDoseTime;
    private ImageView imgPlus1;
    private ImageView imgMinus1;
    private TextView txtDoseNumber;
    int counter = 1;

    public DisplayMedicationDrug() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDisplayMedicationDrugBinding.inflate(inflater, container, false);
        initRepository();
        presenterInterface = new MedicationDrugDisplayPresenter(repository, this, this.getContext());
        dialogBuilder = new MaterialAlertDialogBuilder(this.getContext());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            medication = getArguments().getParcelable(displayTag);
            setDisplay();
        }

        binding.imageEdit.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putParcelable(editTag, medication);
            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_add_Medication, bundle);
        });

        binding.imageBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_home);
        });

        binding.imageDelete.setOnClickListener(v -> {
            lunchDeleteDialog();
        });

        binding.btnSuspend.setOnClickListener(v -> {
            medication.setActive(!medication.isActive());
            updateMedication(medication);
            binding.btnSuspend.setText(medication.isActive() ? "SUSPEND" : "RESUME");
            Toast.makeText(this.getContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
        });

        binding.btnRefill.setOnClickListener(v -> {
            lunchRefillDialog();
        });
        binding.btnAddDose.setOnClickListener(v -> {
            setDoseDialogView();
            lunchDoseDialog();
        });
    }

    private void lunchDeleteDialog() {
        dialogBuilder.setTitle("Delete Medication")
                .setMessage("Do you want to delete " + medication.getMedicationName() + "!")
                .setPositiveButton("DELETE", (dialog, i) -> {
                    deleteMedication(medication);
                    Toast.makeText(this.getContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_displayMedicationDrug_to_fragment_home);
                })
                .setNegativeButton("cancel", (dialog, i) -> {
                    dialog.dismiss();
                }).show();
    }

    @Override
    public void deleteMedication(MedicationPOJO medication) {
        presenterInterface.deleteMedication(medication);
    }

    @Override
    public void updateMedication(MedicationPOJO medication) {
        presenterInterface.updateMedication(medication);
    }

    private void setDisplay() {
        binding.imageDrug.setImageResource(medication.getImageID());
        binding.txtDrugName.setText(medication.getMedicationName());
        binding.btnSuspend.setText(medication.isActive() ? "SUSPEND" : "RESUME");
        String drugDose = medication.getStrength() + " " + medication.getWeight();
        binding.txtDrugDose.setText(drugDose);
        String date = medication.getRecurrence() + ", until " + getDate();
        binding.txtReminderDuration.setText(date);
        printMap(medication.getTimeAndDose());
        binding.txtReminderDoseTime.setText(time);
        binding.txtReminderPills.setText(dose);
        setPrescriptionsRefillText();
        binding.txtHowToUse.setText(medication.getInstruction());
    }

    private void printMap(Map mp) {
        time = "";
        dose = "";
        if (mp != null) {
            Iterator it = mp.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                time = time.concat(pair.getKey() + "\n");
                dose = dose.concat("take " + pair.getValue() + "" + medication.getFormat() + "(s)\n");
            }
        }
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(medication.getEndDate());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return df.format(calendar.getTime());
    }

    private void lunchRefillDialog() {
        customAlertDialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.refill_dialog, null, false);
        refillNumber = customAlertDialogView.findViewById(R.id.txtDialogRefillNumber);
        dialogBuilder.setView(customAlertDialogView)
                .setTitle("Do you refill your medicine?")
                .setMessage("Enter refill number")
                .setPositiveButton("Add", (dialog, i) -> {
                    int num = medication.getLeftNumber();
                    num += Integer.parseInt(refillNumber.getEditableText().toString());
                    medication.setLeftNumber(num);
                    updateMedication(medication);
                    setPrescriptionsRefillText();
                    dialog.dismiss();
                    Toast.makeText(this.getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss()).show();
    }

    public void setPrescriptionsRefillText() {
        String prescriptionRefill = medication.getLeftNumber()
                + " " + medication.getFormat()
                + "(s) left\nRefill reminder: When I have "
                + medication.getLeftNumberReminder() + " meds remaining";
        binding.txtPrescriptionRefill.setText(prescriptionRefill);
    }

    public void lunchDoseDialog() {
        dialogBuilder.setView(customAlertDialogView)
                .setTitle("Add Dose")
                .setMessage("Set Your new Dose: ")
                .setPositiveButton("Add", (dialog, i) -> {
                    updateMedication(medication);
                    printMap(medication.getTimeAndDose());
                    binding.txtReminderDoseTime.setText(time);
                    binding.txtReminderPills.setText(dose);
                    dialog.dismiss();
                    Toast.makeText(this.getContext(), "Successfully Added!", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Cancel", (dialog, i) -> dialog.dismiss()).show();
    }

    private void setDoseDialogView() {
        customAlertDialogView = LayoutInflater.from(this.getContext()).inflate(R.layout.choose_time_card, null, false);
        txtDoseTime = customAlertDialogView.findViewById(R.id.txtDoseTime);
        txtDoseNumber = customAlertDialogView.findViewById(R.id.txtDoseNumber);
        imgPlus1 = customAlertDialogView.findViewById(R.id.imgPlus1);
        imgMinus1 = customAlertDialogView.findViewById(R.id.imgMinus1);

        txtDoseTime.setText("Choose Time");

        txtDoseTime.setOnClickListener(v -> TimePicker(counter));

        imgPlus1.setOnClickListener(v -> {
            counter++;
            txtDoseNumber.setText(counter + "");
            if (medication.getTimeAndDose().containsKey(txtDoseTime.getText().toString())) {
                medication.getTimeAndDose().replace(txtDoseTime.getText().toString(), counter);
                medication.setTimeAndDose(medication.getTimeAndDose());
            }
        });

        imgMinus1.setOnClickListener(v -> {
            if (counter > 1) {
                counter--;
                txtDoseNumber.setText(counter + "");
                if (medication.getTimeAndDose().containsKey(txtDoseTime.getText().toString())) {
                    medication.getTimeAndDose().replace(txtDoseTime.getText().toString(), counter);
                    medication.setTimeAndDose(medication.getTimeAndDose());
                }
            }
        });
    }

    private void TimePicker(int n) {
        final Calendar myCalender = Calendar.getInstance();
        int hour = myCalender.get(Calendar.HOUR_OF_DAY);
        int minute = myCalender.get(Calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
            String format;
            String result;

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
                    result = hourOfDay + ":" + minute + " " + format;
                    medication.getTimeAndDose().put(result, n);
                    medication.setTimeAndDose(medication.getTimeAndDose());
                    txtDoseTime.setText(result);
                }
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this.getContext(), android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
                myTimeListener, hour, minute, false);
        timePickerDialog.setTitle("Choose time");
        timePickerDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        timePickerDialog.show();
    }

    private boolean isSharedPrefNull() {
        sharedPref = this.getContext().getSharedPreferences(RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);
        email = sharedPref.getString(RegisterFragment.USER_EMAIL, "null");
        name = sharedPref.getString(RegisterFragment.USER_NAME, "null");
        Log.i("TAG", "checkShared: " + name);
        Log.i("TAG", "checkShared: " + email);
        return (name.equals("null") && email.equals("null"));
    }

    private void initRepository() {
        localClass = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        if (isSharedPrefNull()) {
            repository = Repository.getInstance(localClass, this.getContext());
        } else {
            fireBaseNetwork = FireBaseNetwork.getInstance(this.getActivity());
            repository = Repository.getInstance(localClass, fireBaseNetwork, this.getContext());
        }
    }
}