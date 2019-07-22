package com.practice.lcn.linux_quick_ref;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.CustomViewHolder> implements Filterable {
    /* read-only dataset */
    private final List<String> dataset;
    /* filtered dataset */
    private List<String> filteredDataset;

    public CommandAdapter(final List<String> dataset) {
        this.dataset = dataset;
        this.filteredDataset = dataset;
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public CustomViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView;
        }
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        Log.i(MainActivity.TAG, "create");
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder cvh, int pos) {
        cvh.setIsRecyclable(false);
        if (pos % 2 == 1)
            cvh.title.setBackgroundColor(0xFF484848);
        cvh.title.setText(filteredDataset.get(pos));
        Log.i(MainActivity.TAG, String.format("%d, %d, %d, %d: \"%s\"", pos, cvh.getAdapterPosition(), cvh.getLayoutPosition(), filteredDataset.size(), cvh.title.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return filteredDataset.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                Log.i(MainActivity.TAG, String.format("constraint: \"%s\"", constraint));
                String constraintStr = constraint.toString();
                constraintStr = constraintStr.trim().toLowerCase();
                List<String> result = null;
                if (constraintStr.isEmpty()) {
                    result = new ArrayList<>(dataset);
                }
                else {
                    result = new ArrayList<>();
                    for (String fruit : dataset) {
                        if (fruit.toLowerCase().contains(constraintStr))
                            result.add(fruit);
                    }
                }
                FilterResults fr = new FilterResults();
                fr.count = result.size();
                fr.values = result;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults fr) {
                filteredDataset = (ArrayList<String>) fr.values;
                notifyDataSetChanged();
            }
        };
    }
}
