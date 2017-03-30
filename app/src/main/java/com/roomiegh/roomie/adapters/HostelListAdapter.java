package com.roomiegh.roomie.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.Hostel;

import java.util.ArrayList;

/**
 * Created by KayO on 30/03/2017.
 */

public class HostelListAdapter extends BaseAdapter{
    private static final String TAG = "adapter";
    private Context ctx;
    private ArrayList<Hostel> allHostels;
    private ImageView ivHostelThumbnail;
    //change rating to later use a pictorial view with stars
    private TextView tvHostelListName, tvHostelListLocation, tvHostelListRating;

    public HostelListAdapter(Context ctx, ArrayList<Hostel> allHostels) {
        this.ctx = ctx;
        this.allHostels = allHostels;
    }

    @Override
    public int getCount() {
        return allHostels.size();
    }

    @Override
    public Object getItem(int position) {
        return allHostels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Hostel hostel = (Hostel) getItem(position);
        Log.d(TAG, "getView: "+hostel.getName()+"// position: "+position);

        ViewHolder viewHolder;
        if(convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater =
                    (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //convertView = inflater.inflate(R.layout.custom_hostel_row,null);
            convertView = inflater.inflate(R.layout.custom_hostel_row,parent,false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tvHostelListName);
            viewHolder.rating = (TextView) convertView.findViewById(R.id.tvHostelListRating);

            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //ivHostelThumbnail = (ImageView) convertView.findViewById(R.id.ivHostelThumbnail);
        //tvHostelListName = (TextView) convertView.findViewById(R.id.tvHostelListName);
        //tvHostelListRating = (TextView) convertView.findViewById(R.id.tvHostelListRating);
        //tvHostelListLocation = (TextView) convertView.findViewById(R.id.tvHostelListLocation);

        /*tvHostelListName.setText(hostel.getName());
        tvHostelListRating.setText(String.valueOf(hostel.getRating()));*/

        viewHolder.name.setText(hostel.getName());
        viewHolder.rating.setText(String.valueOf(hostel.getRating()));

        return convertView;
    }

    public void setAllHostels(ArrayList<Hostel> allHostels) {
        this.allHostels = allHostels;
    }

    private class ViewHolder {
        ImageView ivHostelThumb;
        TextView name, rating;
    }
}
