package com.example.lenovo.mpesaapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lenovo on 12/19/2017.
 */


public class CustomAdapter extends ArrayAdapter<DataModel> implements View.OnClickListener{

    private ArrayList<DataModel> dataSet;
    Context mContext;
    DatabaseHandler db;

    // View lookup cache
    private static class ViewHolder {
        TextView txtName;
        TextView txtVersion;
        ImageView item_del;
    }

    public CustomAdapter(ArrayList<DataModel> data, Context context) {
        super(context, R.layout.item_row, data);
        this.dataSet = data;
        this.mContext=context;

    }

    @Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        final DataModel dataModel=(DataModel)object;

        switch (v.getId())
        {
            case R.id.item_del:
        break;
        }
    }

    private int lastPosition = -1;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        DataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_row, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtVersion =convertView.findViewById(R.id.version_number);
            viewHolder.item_del =convertView.findViewById(R.id.item_del);

            result=convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
//        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtVersion.setText(dataModel.getVersion_number());
        viewHolder.item_del.setOnClickListener(this);
        viewHolder.item_del.setTag(position);
        // Return the completed view to render on screen
        return convertView;
    }
}
