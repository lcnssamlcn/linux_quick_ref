package com.practice.lcn.linux_quick_ref;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;

public class CommandLinkSpan extends ClickableSpan {
    private Context context;
    private String cmdName;

    public CommandLinkSpan(Context context, String cmdName) {
        this.context = context;
        this.cmdName = cmdName;
    }

    @Override
    public void onClick(@NonNull View v) {
        Intent intent = new Intent(context, ManpageActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(MainActivity.EXTRA_CMD_NAME, cmdName);
        context.startActivity(intent);
    }
}
