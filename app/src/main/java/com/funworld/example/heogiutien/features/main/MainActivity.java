 package com.funworld.example.heogiutien.features.main;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;

import com.funworld.example.heogiutien.data.dao.Expense;
import com.funworld.example.heogiutien.MainExpandableAdapter;
import com.funworld.example.heogiutien.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

 public class MainActivity extends AppCompatActivity {

     private static final String TAG = "MainActivity";
     HashMap<String, List<Expense>> mData;
     private ExpandableListView elv_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        elv_main = (ExpandableListView) findViewById(R.id.elv_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    void initRecentExpenseList(){

        //init data
        mData = new HashMap<>();

        final List<String> dates = new ArrayList<>();
        dates.add("2017-06-24");
        dates.add("2017-06-23");
        dates.add("2017-06-22");
        dates.add("2017-06-21");

        List<Expense> expenses = new ArrayList<>();
        for(int i=0; i<5; i++)
            expenses.add(new Expense(i, "using", "", i*10+5, "an sang", new java.sql.Date(System.currentTimeMillis()), ""));

        for(int i=0; i < dates.size(); i++)
            mData.put(dates.get(i), expenses);

        //init adapter
        MainExpandableAdapter mAdapter = new MainExpandableAdapter(this, dates, mData);
        elv_main.setAdapter(mAdapter);

        elv_main.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Log.e(TAG, "onChildClick: " + dates.get(groupPosition) + ", "
                        + mData.get(dates.get(groupPosition)).get(childPosition).getAmount());
                return false;
            }
        });

        elv_main.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                Log.e(TAG, "onGroupClick: " + groupPosition);
                return false;
            }
        });

        elv_main.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                Log.e(TAG, "onGroupCollapse: " + groupPosition);
            }
        });

        elv_main.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                Log.e(TAG, "onGroupExpand: " + groupPosition);
            }
        });
    }
}
