package com.practice.lcn.linux_quick_ref;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.BulletSpan;
import android.widget.TextView;

import java.util.List;

public class ManpageActivity extends AppCompatActivity {
    TextView cmdName;
    TextView summary;
    RecyclerView examples;
    TextView tips;
    TextView relatedCommands;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manpage);
        Intent intent = getIntent();
        String cmdName = intent.getStringExtra(MainActivity.EXTRA_CMD_NAME);
        setTitle(cmdName);

        Command command = App.manpages.find(cmdName);
        this.cmdName = findViewById(R.id.manpage_cmd_name);
        this.cmdName.setText(command.getName());

        summary = findViewById(R.id.manpage_cmd_summary);
        summary.setText(command.getSummary());

        examples = findViewById(R.id.manpage_cmd_examples);
        examples.setLayoutManager(new LinearLayoutManager(this));
        examples.setNestedScrollingEnabled(false);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider);
        if (dividerDrawable != null) {
            divider.setDrawable(dividerDrawable);
            examples.addItemDecoration(divider);
        }
        examples.setAdapter(new CommandExampleAdapter(command.getExamples()));

        tips = findViewById(R.id.manpage_cmd_tips);
        CharSequence tipsSpanStr = new SpannableString("");
        List<String> tipsList = command.getTips();
        for (int i = 0; i < tipsList.size(); i++) {
            SpannableString tipSpanStr = new SpannableString(tipsList.get(i));
            tipSpanStr.setSpan(new BulletSpan(10, 0xFFEFEFEF), 0, tipSpanStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            tipsSpanStr = TextUtils.concat(tipsSpanStr, tipSpanStr);
            if (i != tipsList.size() - 1)
                tipsSpanStr = TextUtils.concat(tipsSpanStr, "\n");
        }
        tips.setText(tipsSpanStr);

        relatedCommands = findViewById(R.id.manpage_related_commands);
        CharSequence relatedCommandsSpanStr = new SpannableString("");
        List<String> relatedCommandList = command.getRelatedCommands();
        for (int i = 0; i < relatedCommandList.size(); i++) {
            String rc = relatedCommandList.get(i);
            SpannableString relatedCommandSpanStr = new SpannableString(rc);
            relatedCommandSpanStr.setSpan(new CommandLinkSpan(rc), 0, relatedCommandSpanStr.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            relatedCommandsSpanStr = TextUtils.concat(relatedCommandsSpanStr, relatedCommandSpanStr);
            if (i != relatedCommandList.size() - 1)
                relatedCommandsSpanStr = TextUtils.concat(relatedCommandsSpanStr, ", ");
        }
        relatedCommands.setText(relatedCommandsSpanStr);
    }
}
