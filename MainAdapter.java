package com.example.qlass.gdziejestkanar;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class MainAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<String> Dates;
    private List<String> Streets;
    private List<String> Lines;
    private Context mContext;

    MainAdapter(Context c, List<String> dates, List<String> streets, List<String> lines) {
        Dates = dates;
        Streets = streets;
        Lines = lines;
        mContext = c;
        mInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override

    public int getCount() {
        return Dates.size();
    }

    @Override
    public Object getItem(int position) {
        return Dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = mInflater.inflate(R.layout.my_listview_detail,null);
        TextView dateTextView = (TextView) v.findViewById(R.id.dateTextView);
        TextView linesTextView = (TextView) v.findViewById(R.id.linesTextView);
        TextView streetTextView = (TextView) v.findViewById(R.id.streetTextView);

        String date = Dates.get(position);
        String street = Streets.get(position);
        String line = Lines.get(position);


        dateTextView.setText(date);
        linesTextView.setText(line);
        streetTextView.setText(street);

        saveArray(Dates,"com.example.qlass.gdziejestkanar.date",mContext);
        saveArray(Streets,"com.example.qlass.gdziejestkanar.streets",mContext);
        saveArray(Lines,"com.example.qlass.gdziejestkanar.lines",mContext);
        return v;
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
