package com.example.qlass.gdziejestkanar;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

public class TramAdapter extends BaseAdapter {

    private String[] items;
    private Context mContext;
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;

    public TramAdapter(String[] items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Button button;
        if (convertView == null) {
            button = new Button(mContext);


            button.setLayoutParams(new GridView.LayoutParams(250, 150));
            button.setPadding(8, 8, 8, 8);
            button.setClickable(true);

        } else
            button = (Button) convertView;
        button.setText(items[position]);

        String saved_lines = sharedPreferences.getString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", "default");

        if(saved_lines.contains("-" + items[position]+ "-"))
        {
            button.setBackgroundColor(Color.DKGRAY);
            button.setTextColor(Color.YELLOW);
        }
        else
        {
            button.setBackgroundColor(Color.WHITE);
            button.setTextColor(Color.DKGRAY);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String saved_lines = sharedPreferences.getString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", "default");
                Toast.makeText(mContext, button.getText().toString(), Toast.LENGTH_SHORT).show();
                int color = ((ColorDrawable) button.getBackground()).getColor();
                if (color == Color.DKGRAY)
                {
                    button.setBackgroundColor(Color.WHITE);
                    button.setTextColor(Color.DKGRAY);
                    String new_string = saved_lines.replace("-" + items[position] + "-","");
                    editor.putString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", new_string);
                    editor.commit();
                }
                else
                {
                    button.setBackgroundColor(Color.DKGRAY);
                    button.setTextColor(Color.YELLOW);
                    String new_string = saved_lines + "-" + items[position] + "-";
                    editor.putString("com.example.qlass.gdziejestkanar.sharedpreferences.saved.lines", new_string);
                    editor.commit();
                }
            }
        });
        return button;
    }

}
