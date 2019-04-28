package com.example.qlass.gdziejestkanar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

public class TramActivity extends AppCompatActivity {

    String[] items = {"0L", "0P", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "14", "15", "17", "20", "23", "24", "31", "32", "33", "LT", "T1", "T2", "T3"};
    GridView listGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tram);

        listGridView = (GridView) findViewById(R.id.listGridView);

        TramAdapter adapter = new TramAdapter(items, this);
        listGridView.setAdapter(adapter);

    }
}
