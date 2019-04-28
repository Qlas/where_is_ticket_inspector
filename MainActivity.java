package com.example.qlass.gdziejestkanar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> Dates = new ArrayList<>();
    List<String> Streets = new ArrayList<>();
    List<String> Lines = new ArrayList<>();
    String[] items;
    String check = "True";
    String[] date;
    String[] street;
    String[] line;
    int is_changed = 0;
    SharedPreferences mPreferences;
    SharedPreferences.Editor mEditor;
    Thread t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageButton refreshBtn = (ImageButton) findViewById(R.id.refreshBtn);
        refreshBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recreate();
            }
        });

        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();
        String my_lines_check = mPreferences.getString(getString(R.string.show_my_lines), "False");

        ImageButton settingBtn = (ImageButton) findViewById(R.id.settingBtn);
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setting = new Intent(getApplicationContext(),SecondActivity.class);
                startActivity(setting);
            }
        });

        Dates = loadArray("com.example.qlass.gdziejestkanar.date", this);
        if(Dates.size() <= 1)
        {
            Dates.add("a");
            Dates.add("a");
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = mdformat.format(calendar.getTime());

        if(Dates.get(1).equals(strDate) && my_lines_check.equals("True"))
        {
            Streets = loadArray("com.example.qlass.gdziejestkanar.streets",this);
            Lines = loadArray("com.example.qlass.gdziejestkanar.lines",this);
        }
        else {
            Dates.clear();
            getWebsite();
            try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
            if(Dates.size() <= 1)
            {
                Dates.add("a");
                Dates.add("a");
                check = "False";
                Toast.makeText(this, "Nie można odświeżyć listy", Toast.LENGTH_SHORT).show();
            }
            if (check.equals("False"))
            {
                Dates = loadArray("com.example.qlass.gdziejestkanar.date", this);
                Streets = loadArray("com.example.qlass.gdziejestkanar.streets",this);
                Lines = loadArray("com.example.qlass.gdziejestkanar.lines",this);
            }
            else {
                date = new String[Dates.size()];
                street = new String[Streets.size()];
                line = new String[Lines.size()];
            }

        }
        while(Dates.size() > 2 && !Dates.get(1).equals(strDate)) {
            Dates.remove(1);
            Streets.remove(1);
            Lines.remove(1);

        }
        if(my_lines_check.equals("True"))
        {
            String saved_lines = mPreferences.getString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", "default");
            String lines = saved_lines.replaceAll("--","-");
            if(lines.length() > 2) {
                lines = lines.substring(1);
                lines = lines.substring(0, lines.length() - 1);
            }
            this.items = lines.split("-",-1);
            List<String> lin = new ArrayList<>(Arrays.asList(items));
            if (lin.get(0).equals(""))
            {
                lin.set(0,"Placeholder");
            }
            List<String> Line = new ArrayList<String>(Lines);
            for(int i=1;i < Lines.size();i++)
            {
                for(int j=0; j < lin.size();j++)
                {
                    String a = Lines.get(i);
                    String b = lin.get(j);
                    if(a.contains(b))
                    {
                        is_changed = 1;
                        if(!Lines.get(i).equals(Line.get(i))) {
                            Line.set(i, Line.get(i) + ", " + b);
                        }
                        else
                            Line.set(i,b);
                        Log.i("MyTestService", "Service running");
                    }
                }
                if(is_changed == 1)
                {
                    Lines.set(i,Line.get(i));
                    is_changed = 0;
                }
                else{
                    Lines.set(i,"");
                }
            }


        }
        ListView myListView = (ListView) findViewById(R.id.myListView);
        MainAdapter mainAdapter = new MainAdapter(this, Dates, Streets, Lines);
        myListView.setAdapter(mainAdapter);



    }


    void getWebsite()
    {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect("http://mpk.wroc.pl/kontrole-biletow").get();
                    Elements dates = doc.getElementsByClass("views-field views-field-field-inspection-date");
                    Elements streets = doc.getElementsByClass("views-field views-field-title");
                    Elements lines = doc.getElementsByClass("views-field-field-inspection-routes");

                    for (Element datee : dates) {
                        Dates.add(datee.text());
                    }

                    for(Element streett : streets){
                        Streets.add(streett.text());
                    }
                    for(Element linee : lines){
                        Lines.add(linee.text());
                    }
                } catch (IOException e) {
                    check = "False";

                }
            }
        });
        t.start();
    }


    public List<String> loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        List<String> array = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            array.add(prefs.getString(arrayName + "_" + i, null));
        }
        return array;
    }
}


