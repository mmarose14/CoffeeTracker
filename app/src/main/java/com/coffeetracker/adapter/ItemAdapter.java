package com.coffeetracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.coffeetracker.R;
import com.coffeetracker.models.MealItem;
import com.coffeetracker.util.DeleteMealListener;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<MealItem> {
    private final List<MealItem> items;
    private DeleteMealListener deleteMealListener;

    public ItemAdapter(Context context, int resource, List<MealItem> items, DeleteMealListener listener) {
        super(context, resource, items);
        this.items = items;
        this.deleteMealListener = listener;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)getContext().getSystemService
                    (Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.meal_item, parent, false);
        } else {
            view = convertView;
        }

        final MealItem mealItem = items.get(position);

        TextView label = (TextView)view.findViewById(R.id.label);
        label.setText(mealItem.getFormattedOutputString());

        TextView metabolizedTextView = (TextView)view.findViewById(R.id.metabolized);
        if (mealItem.getMetabolized()) {
            metabolizedTextView.setVisibility(View.GONE);
        } else {
            metabolizedTextView.setVisibility(View.VISIBLE);
        }

        ProgressBar progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
        double caffeineLeft = mealItem.getCaffeineLeft();
        int progress = 0;
        if (caffeineLeft > 0 && mealItem.getMetabolized()) {
            progress = (int)((caffeineLeft / 200) * 100);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
        progressBar.setProgress(progress);

        //Temporarily remove
        /*
        ImageView close = (ImageView)view.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMealListener.deleteMealCallback(mealItem.getXid());
            }
        });
        */

        return view;
    }
}
