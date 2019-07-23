package com.practice.lcn.linux_quick_ref;

import android.app.Application;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;

public class App extends Application {
    static Manpages manpages = null;

    void loadManpages() throws IOException {
        if (manpages != null)
            return;

        InputStreamReader isr = null;
        try {
            isr = new InputStreamReader(getAssets().open("man.json"));
            manpages = new Gson().fromJson(isr, Manpages.class);
        }
        finally {
            if (isr != null)
                isr.close();
        }
    }
}
