package com.team_three.medicalreminder.helpRequest.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.Registeration.view.RegisterFragment;
import com.team_three.medicalreminder.databinding.TakerRequestRowBinding;
import com.team_three.medicalreminder.model.PatientPojo;
import com.team_three.medicalreminder.model.RequestPojo;
import com.team_three.medicalreminder.model.TakerPOJO;

import java.util.List;


public class HelpRequestAdapter extends RecyclerView.Adapter<HelpRequestAdapter.ViewHolder> {
    final private Context _context;
    private List<RequestPojo> takers;
    SharedPreferences sharedPref;
    OnClickRequest onClickRequest;

    public HelpRequestAdapter(Context context, List<RequestPojo> medicines,OnClickRequest onClickRequest) {

        _context = context;
        this.takers = medicines;
        this.onClickRequest = onClickRequest;
        initShared();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TakerRequestRowBinding binding;
        public ViewHolder(TakerRequestRowBinding sbinding) {
            super(sbinding.getRoot());
            binding = sbinding;

        }
    }

    @NonNull
    @Override
    public HelpRequestAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new HelpRequestAdapter.ViewHolder(TakerRequestRowBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull HelpRequestAdapter.ViewHolder holder, int position) {
        holder.binding.txtinvitorName.setText(takers.get(position).getName());
        holder.binding.txtInvite.setText("I invite you to help me");
        holder.binding.requestImage.setImageResource(takers.get(position).getImg());
        holder.binding.btnAccept.setOnClickListener(view -> {

            TakerPOJO takerPOJO = new TakerPOJO();
            takerPOJO.setName(sharedPref.getString(RegisterFragment.USER_NAME,"null"));
            takerPOJO.setEmail(sharedPref.getString(RegisterFragment.USER_EMAIL,"null"));
            takerPOJO.setPatientEmail(takers.get(position).getEmail());
            takerPOJO.setImg(takers.get(position).getImg());
            takerPOJO.setRequestId(takers.get(position).getId());

            PatientPojo patientPojo = new PatientPojo(sharedPref.getString(RegisterFragment.USER_EMAIL,"null")
                    ,takers.get(position).getMyEmail()
                    ,takers.get(position).getImg()
                    ,takers.get(position).getName()
                    );

            onClickRequest.onClickAccept(takerPOJO,patientPojo);

        });
        holder.binding.btnReject.setOnClickListener(view -> {
            onClickRequest.onClickReject(takers.get(position).getId(),takers.get(position).getMyEmail());
        });

    }

    @Override
    public int getItemCount() {
        return takers.size();
    }


    private void initShared() {
        sharedPref = _context.getSharedPreferences(
                RegisterFragment.SHAREDfILE, Context.MODE_PRIVATE);

    }
}