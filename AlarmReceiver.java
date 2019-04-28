package com.example.qlass.gdziejestkanar;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    List<String> Dates = new ArrayList<>();
    List<String> Streets = new ArrayList<>();
    List<String> Lines = new ArrayList<>();
    String[] items;
    String check = "False";
    String[] date;
    Thread t;

    @Override
    public void onReceive(Context context, Intent intent) {

        final NotificationHelper helper;
        helper = new NotificationHelper(context);

        // For our recurring task, we'll just display a message
        Log.i("MyTestService", "Service running");
        String title;
        String content;
        getWebsite();
        try { t.join(); } catch (InterruptedException e) { e.printStackTrace(); }
        if(Dates.size() <= 1) {
            Dates.add("a");
            Dates.add("a");
        }
        else
        {
            check = "True";
        }
        if (check.equals("False"))
        {
            Dates = loadArray("com.example.qlass.gdziejestkanar.date", context);
            Streets = loadArray("com.example.qlass.gdziejestkanar.streets",context);
            Lines = loadArray("com.example.qlass.gdziejestkanar.lines",context);
        }
        else
        {
            saveArray(Dates,"com.example.qlass.gdziejestkanar.date",context);
            saveArray(Streets,"com.example.qlass.gdziejestkanar.streets",context);
            saveArray(Lines,"com.example.qlass.gdziejestkanar.lines",context);
        }
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat mdformat = new SimpleDateFormat("dd.MM.yyyy");
        String strDate = mdformat.format(calendar.getTime());
        Log.i("MyTestService", "Service runningaaqqq");
        int i;
        for(i=0;i < Dates.size();i++)
        {
            if (Dates.get(i).equals(strDate))
            {
                break;
            }
        }
        if (i == Dates.size())
        {
            i -= 1;
        }
        title = Dates.get(i);
        content = Streets.get(i) + " " + Lines.get(i);
        if (Build.VERSION.SDK_INT >= 26)
        {
            Notification.Builder builder = helper.getEDMTChannelNotification(title, content);
            helper.getManager().notify(new Random().nextInt(), builder.build());
        }
    }
    void getWebsite()
    {
        t = new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Log.i("MyTestService", "Service runningaa");
                    Document doc = Jsoup.connect("http://mpk.wroc.pl/kontrole-biletow").get();
                    Log.i("MyTestService", "Service runningaaaaaz");
                    Elements dates = doc.getElementsByClass("views-field views-field-field-inspection-date");
                    Elements streets = doc.getElementsByClass("views-field views-field-title");
                    Elements lines = doc.getElementsByClass("views-field-field-inspection-routes");
                    check = "True";
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
    boolean saveArray(List<String> array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }
}
