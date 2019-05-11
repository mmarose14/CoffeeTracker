package com.coffeetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.coffeetracker.R;

import java.util.List;

public class SelectionAdapter extends ArrayAdapter<String> {

    public SelectionAdapter(Context context, int resource, List<String> list) {
        super(context, resource, list);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.selection_item, null);
        } else {
            view = convertView;
        }

        String name = getItem(position);

        ((TextView)view.findViewById(R.id.item)).setText(name);

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.selection_item, null);
        } else {
            view = convertView;
        }

        String name = getItem(position);

        ((TextView)view.findViewById(R.id.item)).setText(name);

        return view;
    }
}
