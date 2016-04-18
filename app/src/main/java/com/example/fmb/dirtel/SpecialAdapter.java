package com.example.fmb.dirtel;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by fmb on 12/04/2016.
 */
public class SpecialAdapter<String> extends ArrayAdapter<String> {

    public SpecialAdapter(Context context, int resource, List<String> objects){
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = super.getView(position, convertView, parent);
        if (view != null && position >= 0)
            view.setBackgroundColor(position % 2 == 0 ? Color.LTGRAY : Color.CYAN);
        return view;

    }
}