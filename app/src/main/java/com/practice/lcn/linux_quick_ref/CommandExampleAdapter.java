package com.practice.lcn.linux_quick_ref;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CommandExampleAdapter extends RecyclerView.Adapter<CommandExampleAdapter.CommandExampleViewHolder> {
    private List<CommandExample> examples;

    public static class CommandExampleViewHolder extends RecyclerView.ViewHolder {
        public TextView code;
        public TextView description;

        public CommandExampleViewHolder(View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.command_example_code);
            description = itemView.findViewById(R.id.command_example_description);
        }
    }

    public CommandExampleAdapter(List<CommandExample> examples) {
        this.examples = examples;
    }

    @NonNull
    @Override
    public CommandExampleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.command_example_list_item, parent, false);
        return new CommandExampleViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandExampleViewHolder cevh, int pos) {
        CommandExample example = examples.get(pos);
        cevh.code.setText(example.getCode());
        SpannableString desc = new SpannableString(example.getDescription());
        ManpageActivity.applyStyleOnCommands(desc);
        cevh.description.setText(desc);
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }

}
