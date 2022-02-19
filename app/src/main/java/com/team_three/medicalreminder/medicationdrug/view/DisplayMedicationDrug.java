package com.team_three.medicalreminder.medicationdrug.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team_three.medicalreminder.R;
import com.team_three.medicalreminder.dataBase.ConcreteLocalClass;
import com.team_three.medicalreminder.databinding.FragmentDisplayMedicationDrugBinding;
import com.team_three.medicalreminder.medicationdrug.presenter.MedicationDrugDisplayPresenter;
import com.team_three.medicalreminder.medicationdrug.presenter.MedicationDrugDisplayPresenterInterface;
import com.team_three.medicalreminder.model.MedicationPOJO;
import com.team_three.medicalreminder.model.Repository;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class DisplayMedicationDrug extends Fragment implements DisplayMedicationDrugViewInterface {
    private FragmentDisplayMedicationDrugBinding binding;
    private MedicationPOJO medication;
    private String time = "";
    private String dose = "";
    public static String displayTag = "displayMed";
    public static String editTag = "editMed";

    private Repository repository;
    private ConcreteLocalClass localClass;
    private MedicationDrugDisplayPresenterInterface presenterInterface;

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
        localClass = ConcreteLocalClass.getConcreteLocalClassInstance(this.getContext());
        repository = Repository.getInstance(localClass, this.getContext());
        presenterInterface = new MedicationDrugDisplayPresenter(repository, this);
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
            bundle.putParcelable(editTag,  medication);
            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_add_Medication,bundle);
        });

        binding.imageBack.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_home);
        });

        binding.imageDelete.setOnClickListener(v -> {
            deleteMedication(medication);
            Toast.makeText(this.getContext(), "Successfully Deleted!", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(v).navigate(R.id.action_displayMedicationDrug_to_fragment_home);
        });

        binding.btnSuspend.setOnClickListener(v->{
            medication.setActive(!medication.isActive());
            updateMedication(medication);
            binding.btnSuspend.setText(medication.isActive()?"SUSPEND":"RESUME");
            Toast.makeText(this.getContext(), "Successfully updated!", Toast.LENGTH_SHORT).show();
        });
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
        binding.btnSuspend.setText(medication.isActive()?"SUSPEND":"RESUME");
        String drugDose = medication.getStrength() + " " + medication.getWeight();
        binding.txtDrugDose.setText(drugDose);
        String date = medication.getRecurrence() + ", until " + getDate();
        binding.txtReminderDuration.setText(date);
        printMap(medication.getTimeAndDose());
        binding.txtReminderDoseTime.setText(time);
        binding.txtReminderPills.setText(dose);
        String prescriptionRefill = medication.getLeftNumber()
                + " " + medication.getFormat()
                + "(s) left\nRefill reminder: When I have "
                + medication.getLeftNumberReminder() + " meds remaining";
        binding.txtPrescriptionRefill.setText(prescriptionRefill);
        binding.txtHowToUse.setText(medication.getInstruction());
    }

    private void printMap(Map mp) {
        Iterator it = mp.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            time = time.concat(pair.getKey() + "\n");
            dose = dose.concat("take " + pair.getValue() + "" + medication.getFormat() + "(s)\n");
            it.remove(); // avoids a ConcurrentModificationException
        }
    }

    private String getDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(medication.getEndDate());
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        return df.format(calendar.getTime());
    }

}