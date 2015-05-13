package com.example.aleksandrevsetefeev.testprime;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class LevelTableAdapter extends BaseAdapter {
    Context context;
    LayoutInflater lInflater;
    ArrayList<Level> levels;

    LevelTableAdapter(Context _context, ArrayList<Level> _levels) {
        context = _context;
        levels = _levels;
        lInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @Override
    public int getCount() {
        return levels.size();
    }


    @Override
    public Object getItem(int position) {
        return levels.get(position);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.level_row, parent, false);
        }

        Level level = levels.get(position);


        ((TextView) view.findViewById(R.id.number)).setText(Integer.toString(position + 1));
        ((TextView) view.findViewById(R.id.score_per_click)).setText(Integer.toString(level.getScorePerClick()));
        ((TextView) view.findViewById(R.id.score_to_next_level)).setText(Integer.toString(level.getScoreToNextLevel()));


        return view;
    }


}
