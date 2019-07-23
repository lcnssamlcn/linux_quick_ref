package com.practice.lcn.linux_quick_ref;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CommandAdapter extends RecyclerView.Adapter<CommandAdapter.CommandViewHolder> implements Filterable {
    private Context context;
    /* read-only commands */
    private final List<String> commands;
    /* filtered commands */
    private List<String> filteredCommands;

    public CommandAdapter(Context context, final List<String> commands) {
        this.context = context;
        this.commands = commands;
        this.filteredCommands = commands;
    }

    public static class CommandViewHolder extends RecyclerView.ViewHolder {
        public TextView title;

        public CommandViewHolder(View itemView) {
            super(itemView);
            this.title = (TextView) itemView;
        }
    }

    @NonNull
    @Override
    public CommandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.command_list_item, parent, false);
        Log.i(MainActivity.TAG, "create");
        return new CommandViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CommandViewHolder cvh, int pos) {
        cvh.setIsRecyclable(false);
        if (pos % 2 == 1)
            cvh.title.setBackgroundColor(0xFF484848);
        cvh.title.setText(filteredCommands.get(pos));
        cvh.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView title = (TextView) v;
                try {
                    ((App) context.getApplicationContext()).loadManpages();
                }
                catch (IOException e) {
                    AlertDialog dlg = new AlertDialog.Builder(context)
                        .setMessage(R.string.err_manual_missing)
                        .setPositiveButton(R.string.err_dlg_ok, null)
                        .create();
                    dlg.show();
                    Log.getStackTraceString(e);
                    return;
                }
                Intent intent = new Intent(context, ManpageActivity.class);
                intent.putExtra(MainActivity.EXTRA_CMD_NAME, title.getText());
                context.startActivity(intent);
            }
        });
        Log.i(MainActivity.TAG, String.format("%d, %d, %d, %d: \"%s\"", pos, cvh.getAdapterPosition(), cvh.getLayoutPosition(), filteredCommands.size(), cvh.title.getText().toString()));
    }

    @Override
    public int getItemCount() {
        return filteredCommands.size();
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
                    result = new ArrayList<>(commands);
                }
                else {
                    result = new ArrayList<>();
                    for (String command : commands) {
                        if (command.toLowerCase().contains(constraintStr))
                            result.add(command);
                    }
                }
                FilterResults fr = new FilterResults();
                fr.count = result.size();
                fr.values = result;
                return fr;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults fr) {
                filteredCommands = (ArrayList<String>) fr.values;
                notifyDataSetChanged();
            }
        };
    }
}
