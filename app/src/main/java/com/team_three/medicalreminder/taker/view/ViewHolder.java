package com.team_three.medicalreminder.taker.view;


import android.view.View;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.team_three.medicalreminder.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    public ConstraintLayout root;
    public TextView txtTitle;
    public TextView txtDesc;
    public ImageView img;
    public CardView cardView;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);

        root = itemView.findViewById(R.id.takerRequestConstrainyt);

        txtTitle = itemView.findViewById(R.id.txtinvitorName);
        txtDesc = itemView.findViewById(R.id.txtInvite);
        img = itemView.findViewById(R.id.requestImage);
        cardView = itemView.findViewById(R.id.requestRow);

    }

    public void setTxtTitle(String string) {
        txtTitle.setText(string);
    }


    public void setTxtDesc(String string) {
        txtDesc.setText(string);
    }
    public  void  setImg(int image){
        img.setImageResource(image);
    }
    public void disableRequest(){
        cardView.setVisibility(View.GONE);
    }
}