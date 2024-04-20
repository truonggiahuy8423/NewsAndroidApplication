package com.example.newsandroidproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class FilterSpinnerAdapterArray extends ArrayAdapter<String> {

    private static final int LAYOUT_DEFAULT = R.layout.spinner_item;
    private static final int LAYOUT_NEWEST = R.layout.spinner_item_newest; // Assuming this layout exists

    public FilterSpinnerAdapterArray(Context context, ArrayList<String> items) {
        super(context, LAYOUT_DEFAULT, items); // Set default layout
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view;

        int layoutId = (position == 0) ? LAYOUT_NEWEST : LAYOUT_DEFAULT;
        view = inflater.inflate(layoutId, parent, false); // Use correct layout based on position

        TextView textView = view.findViewById(R.id.spinner_item); // Ensure consistent TextView ID
        textView.setText(getItem(position));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent); // Use the same logic for drop-down view
    }
}
