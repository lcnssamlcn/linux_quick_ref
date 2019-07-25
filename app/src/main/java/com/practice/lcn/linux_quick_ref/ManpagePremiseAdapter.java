package com.practice.lcn.linux_quick_ref;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.TextView;

import java.util.List;

public class ManpagePremiseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<ManpagePremiseSegment> segments;

    private static final int LAYOUT_CODE_BLOCK = 1;
    private static final int LAYOUT_PLAIN_TEXT = 2;

    public static class ManpagePremiseCodeBlockViewHolder extends RecyclerView.ViewHolder {
        public HorizontalScrollView frame;
        public TextView code;

        public ManpagePremiseCodeBlockViewHolder(View itemView) {
            super(itemView);
            frame = (HorizontalScrollView) itemView;
            code = itemView.findViewById(R.id.manpage_premise_code);
        }
    }

    public static class ManpagePremisePlainTextViewHolder extends RecyclerView.ViewHolder {
        public TextView text;

        public ManpagePremisePlainTextViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView;
        }
    }

    public ManpagePremiseAdapter(Context context, List<ManpagePremiseSegment> segments) {
        this.context = context;
        this.segments = segments;
    }

    @Override
    public int getItemViewType(int pos) {
        ManpagePremiseSegment segment = segments.get(pos);
        if (segment.isCodeSnippet())
            return ManpagePremiseAdapter.LAYOUT_CODE_BLOCK;
        else
            return ManpagePremiseAdapter.LAYOUT_PLAIN_TEXT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ManpagePremiseAdapter.LAYOUT_CODE_BLOCK) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manpage_premise_code_block_list_item, parent, false);
            return new ManpagePremiseCodeBlockViewHolder(v);
        }
        else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.manpage_premise_plain_text_list_item, parent, false);
            return new ManpagePremisePlainTextViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder vh, int pos) {
        ManpagePremiseSegment segment = segments.get(pos);
        if (vh.getItemViewType() == ManpagePremiseAdapter.LAYOUT_CODE_BLOCK) {
            ManpagePremiseCodeBlockViewHolder mpcbvh = (ManpagePremiseCodeBlockViewHolder) vh;
            mpcbvh.code.setText(segment.getParagraph());
        }
        else {
            ManpagePremisePlainTextViewHolder mpptvh = (ManpagePremisePlainTextViewHolder) vh;
            mpptvh.text.setText(segment.getParagraph());
        }
    }

    @Override
    public int getItemCount() {
        return segments.size();
    }
}
