package com.example.b_project;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class Custom_Adapter extends BaseAdapter {
    Activity a;
    int layout;
    ArrayList<commentInfo> data;
    LayoutInflater in;

    public Custom_Adapter(Activity a, int layout, ArrayList<commentInfo> data){
        this.a = a;
        this.layout = layout;
        this.data = data;
        in = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setDatas(ArrayList<commentInfo> arrayList){
        data = arrayList;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        if (view==null) {
            view = in.inflate(layout, viewGroup, false);
        }
        final RatingBar coment_getstar =(RatingBar)view.findViewById(R.id.coment_getstar);
        final TextView coment_id =(TextView)view.findViewById(R.id.coment_id);
        final TextView coment_LP=(TextView)view.findViewById(R.id.coment_LP);
        final TextView coment_cal =(TextView)view.findViewById(R.id.coment_cal);

        coment_getstar.setRating(Float.parseFloat(data.get(position).rating));
        coment_id.setText(data.get(position).id);
        coment_LP.setText(data.get(position).comment);
        coment_cal.setText(data.get(position).date);


        return view;
    }
}
