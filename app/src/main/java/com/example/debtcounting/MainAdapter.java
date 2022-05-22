package com.example.debtcounting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;
    String[] userid;
    String[] username;
    String[] amount;

    public MainAdapter(Context context, String[] userid, String[] username, String[] amount) {
        this.context = context;
        this.userid = userid;
        this.username = username;
        this.amount = amount;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView User, Amount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            User = itemView.findViewById(R.id.UserRow);
            Amount = itemView.findViewById(R.id.AmountRow);
        }
    }

    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.user_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.User.setText(username[position]);
        holder.Amount.setText(amount[position]);
    }

    @Override
    public int getItemCount() {
        return userid.length;
    }


}
