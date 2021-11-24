package com.hyt.bmc208_assignment;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.common.api.Batch;

public class AvailableBatchesAdapter extends RecyclerView.Adapter<AvailableBatchesAdapter.AvailableBatchesViewHolder> {

    AvailableBatches[] availableBatches;

    public AvailableBatchesAdapter(AvailableBatches[] availableBatches) {
        this.availableBatches = availableBatches;
    }

    @Override
    public int getItemCount() {
        return availableBatches.length;
    }

    @NonNull
    @Override
    public AvailableBatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_available_batches, parent, false);
        return new AvailableBatchesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableBatchesViewHolder holder, int position) {
        holder.bind(availableBatches[position]);
    }

    public static class AvailableBatchesViewHolder extends RecyclerView.ViewHolder {

        TextView batchNo;
        TextView expiryDate;
        TextView quantityAvailable;

        public AvailableBatchesViewHolder(@NonNull View itemView) {
            super(itemView);
            batchNo = itemView.findViewById(R.id.text_view_request_batch_batchNo);
            expiryDate = itemView.findViewById(R.id.text_view_request_batch_expiryDate);
            quantityAvailable = itemView.findViewById(R.id.text_view_request_batch_quantityAvailable);
        }

        @SuppressLint("SetTextI18n")
        public void bind(AvailableBatches availableBatches) {
            batchNo.setText("Batch No. " + availableBatches.batchNo);
            expiryDate.setText("Expiry Date " + availableBatches.batchExpiryDate);
            quantityAvailable.setText("Quantity Available " + availableBatches.quantityAvailable);

        }
    }
}