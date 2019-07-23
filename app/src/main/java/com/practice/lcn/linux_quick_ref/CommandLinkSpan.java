package com.practice.lcn.linux_quick_ref;

import android.support.annotation.NonNull;
import android.text.style.ClickableSpan;
import android.view.View;

public class CommandLinkSpan extends ClickableSpan {
    private String cmdName;

    public CommandLinkSpan(String cmdName) {
        this.cmdName = cmdName;
    }

    /* under construction
     */
    @Override
    public void onClick(@NonNull View v) {
        /* TODO
           intent + CLEAR_TOP
         */
    }
}
