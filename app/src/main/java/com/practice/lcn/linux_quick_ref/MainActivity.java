package com.practice.lcn.linux_quick_ref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    SearchView searchView;
    CommandAdapter adapter;
    RecyclerView commandList;

    public static final String EXTRA_CMD_NAME = "com.practice.lcn.linux_quick_ref.MainActivity.EXTRA_CMD_NAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        commandList = findViewById(R.id.commandList);
        commandList.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommandAdapter(this, new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cmds))));
        commandList.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.searchbar);
        this.searchView = (SearchView) searchViewItem.getActionView();
        this.searchView.setIconifiedByDefault(true);
        this.searchView.setQueryHint(getResources().getString(R.string.search_hint));
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
