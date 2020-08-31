package com.example.quizapplicaiton;

import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Random;

public class catAdaptor extends BaseAdapter {

    public catAdaptor(List<String> catList) {
        this.catList = catList;
    }

    private List<String> catList;

    @Override
    public int getCount() {
        return catList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        View view;

        if (convertView == null){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item,parent, false);
        }
        else {
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(),SetsActivity.class);
                intent.putExtra("title", catList.get(position));
                intent.putExtra("CATEGORY_ID", position + 1);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.cat_name)).setText(catList.get(position));
        //implementing random color for the category items
        //Random rand = new Random();
        //int color = Color.argb(255, rand.nextInt(255), rand.nextInt(255),rand.nextInt(255));
        //view.setBackgroundColor(color);

        return view;

    }
}