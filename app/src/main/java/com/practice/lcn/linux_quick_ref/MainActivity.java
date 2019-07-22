package com.practice.lcn.linux_quick_ref;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = "MainActivity";
    private SearchView searchView;
    private CommandAdapter adapter;
    private RecyclerView results;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        results = findViewById(R.id.results);
        results.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CommandAdapter(new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.cmds))));
        results.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(MainActivity.this, "onCreateOptionsMenu()", Toast.LENGTH_SHORT).show();
        getMenuInflater().inflate(R.menu.search, menu);
        MenuItem searchViewItem = menu.findItem(R.id.searchbar);
        this.searchView = (SearchView) searchViewItem.getActionView();
        this.searchView.setIconifiedByDefault(true);
        this.searchView.setQueryHint(getResources().getString(R.string.search_hint));
        this.searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.i(MainActivity.TAG, String.format(Locale.ENGLISH, "submitted query \"%s\"", query));
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.i(MainActivity.TAG, String.format(Locale.ENGLISH, "change to \"%s\"", newText));
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}
