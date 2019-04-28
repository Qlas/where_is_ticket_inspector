package com.example.qlass.gdziejestkanar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class SecondAdapter extends BaseAdapter {
    private String[] items;
    private Context mContext;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public SecondAdapter(Context mContext) {
        this.mContext = mContext;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
        String saved_lines = sharedPreferences.getString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", "default");
        String lines = saved_lines.replaceAll("--","-");
        if(lines.length() > 2) {
            lines = lines.substring(1);
            lines = lines.substring(0, lines.length() - 1);
        }
        this.items = lines.split("-",-1);
    }

    public SecondAdapter(String[] items) {
        this.items = items;

    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Button button;
        button = new Button(mContext);
        if (convertView == null) {
                button = new Button(mContext);
        }
         else
            button = (Button) convertView;
        button.setText(items[position]);
        button.setBackgroundColor(Color.DKGRAY);
        button.setTextColor(Color.YELLOW);
        button.setLayoutParams(new GridView.LayoutParams(250, 150));
        button.setPadding(8, 8, 8, 8);
        button.setClickable(true);
        if (items[position] == "")
        {
            button.setVisibility(View.GONE);
            button.setPadding(0,0,0,0);
            button.setLayoutParams(new GridView.LayoutParams(0,0));

        }
        return button;
    }
}
