package com.hyt.bmc208_assignment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PatientBatchAdapter extends RecyclerView.Adapter<PatientBatchAdapter.PatientBatchIdViewHolder>{

    ArrayList<SelectedBatchNo> patientBatches;

    public PatientBatchAdapter(ArrayList<SelectedBatchNo> batches){
        this.patientBatches = batches;
    }

    @NonNull
    @Override
    public PatientBatchIdViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_available_batches, parent, false);
        return new PatientBatchAdapter.PatientBatchIdViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientBatchIdViewHolder holder, int position) {
        holder.bind(patientBatches.get(position));
    }

    @Override
    public int getItemCount() {
        return patientBatches.size();
    }


    public static class PatientBatchIdViewHolder extends RecyclerView.ViewHolder{
        TextView batchNo;
        TextView batchExpiryDate;
        TextView batchQuantityAvailable;

        public PatientBatchIdViewHolder(@NonNull View itemView) {
            super(itemView);
            batchNo = itemView.findViewById(R.id.text_view_request_batch_batchNo);
            batchExpiryDate = itemView.findViewById(R.id.text_view_request_batch_expiryDate);
            batchQuantityAvailable = itemView.findViewById(R.id.text_view_request_batch_quantityAvailable);
        }

        public void bind(SelectedBatchNo selectedBatchNo){
            batchNo.setText(selectedBatchNo.batchNo);
            batchExpiryDate.setText(selectedBatchNo.batchStatus);
            batchQuantityAvailable.setText(selectedBatchNo.batchQuantityAvailable);
        }
    }
}