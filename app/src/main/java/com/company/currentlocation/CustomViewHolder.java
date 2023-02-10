package com.company.currentlocation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class CustomViewHolder extends RecyclerView.ViewHolder {
    public TextView textName,textAge;
    public CardView cardView;


    public CustomViewHolder(@NonNull View itemView) {
        super(itemView);
        textName = itemView.findViewById(R.id.textName);
        textAge = itemView.findViewById(R.id.textAge);
        cardView = itemView.findViewById(R.id.main_container);

    }
}
