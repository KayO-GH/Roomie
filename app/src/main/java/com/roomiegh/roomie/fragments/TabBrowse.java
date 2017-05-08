package com.roomiegh.roomie.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.activities.browseActivities.ByName;
import com.roomiegh.roomie.activities.browseActivities.ByLocation;
import com.roomiegh.roomie.activities.browseActivities.ByPrice;
import com.roomiegh.roomie.activities.browseActivities.ByType;
import com.roomiegh.roomie.adapters.BrowseAdapter;
import com.roomiegh.roomie.models.BrowseManager;

/**
 * Created by Kwadwo Agyapon-Ntra on 06/10/2015.
 */
public class TabBrowse extends Fragment{
    /*private ListView lvBrowse;*/
    private Button btBrowseName, btBrowseLocation, btBrowsePrice, btBrowseType;
    private Context ctx;
    private BrowseManager browseManager;
    private BrowseAdapter browseAdapter;
    private String currentUserEmail; //to be initialized and used later
    private Intent openIntent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_browse,container,false);

        //get current user email
        //currentUserEmail = this.getArguments().getString(new PushUserUtil().USER_EMAIL);


        init(view);
        //Take user to activity and arrange hostels by name
        btBrowseName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntent = new Intent(ctx, ByName.class);
                startActivity(openIntent);
            }
        });
        //Take user to activity and arrange hostels according to their locations
        btBrowseLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntent = new Intent(ctx, ByLocation.class);
                startActivity(openIntent);
            }
        });
        //Take user to activity with max and min price sliders and arrange hostels alphabetically or by rating with rooms in this range
        btBrowsePrice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntent = new Intent(ctx, ByPrice.class);
                startActivity(openIntent);
            }
        });
        //Take user to activity with hostels that have these room types
        btBrowseType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openIntent = new Intent(ctx, ByType.class);
                startActivity(openIntent);
            }
        });
        /*lvBrowse.setAdapter(browseAdapter);
        lvBrowse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            Intent openIntent;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: {
                        openIntent = new Intent(ctx, ByLocation.class);

                        Bundle pushUser = new Bundle();
                        pushUser.putString(PushUserUtil.USER_EMAIL, currentUserEmail);
                        openIntent.putExtra(PushUserUtil.PUSH_INTENT_KEY, pushUser);

                        startActivity(openIntent);
                        break;
                    }
                    case 1: {
                        openIntent = new Intent(ctx, ByType.class);
                        startActivity(openIntent);
                        break;
                    }
                    case 2: {
                        openIntent = new Intent(ctx, ReportAProblem.class);
                        startActivity(openIntent);
                        break;
                    }
                    case 3: {
                        openIntent = new Intent(ctx, ByName.class);
                        startActivity(openIntent);
                        break;
                    }
                    case 4: {
                        openIntent = new Intent(ctx, ByPrice.class);
                        startActivity(openIntent);
                        break;
                    }
                }
            }
        });*/

        return view;
    }

    private void init(View view) {
        ctx = getActivity().getApplicationContext();
        btBrowseName = (Button) view.findViewById(R.id.btBrowseName);
        btBrowseLocation = (Button) view.findViewById(R.id.btBrowseLocation);
        btBrowsePrice = (Button) view.findViewById(R.id.btBrowsePrice);
        btBrowseType = (Button) view.findViewById(R.id.btBrowseType);
        /*lvBrowse = (ListView) view.findViewById(R.id.lvBrowse);
        browseManager = new BrowseManager();
        browseAdapter = new BrowseAdapter(ctx,browseManager.getAllBrowseEntities());*/

    }
}
