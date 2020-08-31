package com.example.quizapplicaiton;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

//Adapter for the sets Grid view
public class setsAdapter extends BaseAdapter {

    private int numSets;

    public setsAdapter(int numSets) {

        this.numSets = numSets;
    }

    @Override
    public int getCount() {

        return numSets;
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
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.set_item_layout,parent,false);
        }
        else{
            view = convertView;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(parent.getContext(), questionActivity.class);
                intent.putExtra("SETNO", position+1);
                parent.getContext().startActivity(intent);
            }
        });

        ((TextView) view.findViewById(R.id.setNum_txtV)).setText(String.valueOf(position+1));

        return view;
    }
}
