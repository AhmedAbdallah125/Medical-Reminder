package com.team_three.medicalreminder.patientsList.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.PatientRowBinding;
import com.team_three.medicalreminder.databinding.TakerRequestRowBinding;
import com.team_three.medicalreminder.helpRequest.view.OnClickRequest;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;



public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.ViewHolder> {
    final private Context _context;
    private List<RequestPojo> patients;
    SharedPreferences sharedPref;
    PatientListViewInterface patientListViewInterface;

    public PatientListAdapter(Context context, List<RequestPojo> patients,PatientListViewInterface patientListViewInterface) {

        Log.i("TAG", "MedsListAdapter: abdoooo");
        _context = context;
        this.patients = patients;
        this.patientListViewInterface = patientListViewInterface;
        initShared();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PatientRowBinding binding;
        public ViewHolder(PatientRowBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;
            Log.i("TAG", "ViewHolder: abdooooooooo");

        }
    }

    @NonNull
    @Override
    public PatientListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.i("TAG", "onCreateViewHolder: ABDOOOOOOOO");
        return new PatientListAdapter.ViewHolder(PatientRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull PatientListAdapter.ViewHolder holder, int position) {
        Log.i("TAG", "onBindViewHolder:ABDOOOOOOOOOO ");
        holder.binding.patientImage.setImageResource(patients.get(position).getImg());
        holder.binding.patientName.setText(patients.get(position).getName());
//        holder.binding.txtinvitorName.setText(patients.get(position).getName());
//        holder.binding.txtInvite.setText("I invite you to help me");
//        holder.binding.requestImage.setImageResource(patients.get(position).getImg());
//        holder.binding.btnAccept.setOnClickListener(view -> {
//
//            TakerPOJO takerPOJO = new TakerPOJO();
//            takerPOJO.setName(sharedPref.getString(RegisterFragment.USER_NAME,"null"));
//            takerPOJO.setEmail(sharedPref.getString(RegisterFragment.USER_EMAIL,"null"));
//            takerPOJO.setPatientEmail(patients.get(position).getEmail());
//            onClickRequest.onClickAccept(takerPOJO);
//        });
//        holder.binding.btnReject.setOnClickListener(view -> {
//            onClickRequest.onClickReject(patients.get(position).getId());
//        });

    }

    @Override
    public int getItemCount() {
        return patients.size();
    }


    private void initShared() {
        sharedPref = _context.getSharedPreferences(
                RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);

    }
}