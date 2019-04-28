package com.example.qlass.gdziejestkanar;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

public class BusActivity extends AppCompatActivity {

    String[] items = {"100", "101", "102", "103", "104", "105", "106", "107", "108", "109", "110", "111", "112",
            "113", "114", "115", "116", "117", "118","119","120","121","122","123","124","125","126","127","128",
            "129","130","131","132","133","134","136","137","138","140","141","142","144","145","146","147","148",
            "149","150","206","240","241","243","245","246","247","248","249","250","251","253","255","257","259",
            "319","325","602","607","609","612","708","714","723","732","812","852","852A","862","872","882","892",
            "892A","900L","900P","901","904","908","910","911","914","917","920","921","923","924","930","931","934",
            "936","937","938","A","AB","C","D","K","N","N62","S1","S2"};
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
