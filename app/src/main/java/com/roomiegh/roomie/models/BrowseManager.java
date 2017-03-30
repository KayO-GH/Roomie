package com.roomiegh.roomie.models;

import com.roomiegh.roomie.R;

import java.util.ArrayList;

/**
 * Created by Kwadwo Agyapon-Ntra on 27/07/2015.
 */
public class BrowseManager {
    private ArrayList<BrowseEntities>allBrowseEntities;

    public BrowseManager(){
        allBrowseEntities = new ArrayList<>();
    }

    public  ArrayList<BrowseEntities>getAllBrowseEntities(){

        allBrowseEntities.add(new BrowseEntities(R.drawable.blue_location,"By Location"));
        allBrowseEntities.add(new BrowseEntities(R.drawable.blue_recent,"ByType"));
        allBrowseEntities.add(new BrowseEntities(R.drawable.blue_report_problem,"Report A Problem"));
        allBrowseEntities.add(new BrowseEntities(R.drawable.blue_announcement,"ByName"));
        allBrowseEntities.add(new BrowseEntities(R.drawable.blue_news,"ByPrice"));

        return allBrowseEntities;
    }
}
