package com.roomiegh.roomie.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.BrowseEntities;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 27/07/2015.
 */
public class BrowseAdapter extends BaseAdapter{
    private Context ctx;
    private ArrayList<BrowseEntities>allBrowseEntities;

    public BrowseAdapter(Context ctx, ArrayList<BrowseEntities> allBrowseEntities) {
        this.ctx = ctx;
        this.allBrowseEntities = allBrowseEntities;
    }

    @Override
    public int getCount() {
        return allBrowseEntities.size();
    }

    @Override
    public Object getItem(int position) {
        return allBrowseEntities.get(position);
    }

    @Override
    public long getItemId(int position) {
        return allBrowseEntities.get(position).getIcon();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        BrowseEntities browseEntities = allBrowseEntities.get(position);

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(ctx.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.browse,null);

        ImageView ivBrowseIcon = (ImageView) convertView.findViewById(R.id.ivBrowseIcon);
        TextView tvBrowseLink = (TextView) convertView.findViewById(R.id.tvBrowseLink);

        ivBrowseIcon.setImageResource(browseEntities.getIcon());
        tvBrowseLink.setText(browseEntities.getLink());

        return convertView;
    }

}
