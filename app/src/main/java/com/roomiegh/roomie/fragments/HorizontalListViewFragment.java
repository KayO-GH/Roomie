package com.roomiegh.roomie.fragments;

/**
 * Created by anonymous on 11/4/16.
 */

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.activities.browseActivities.ByLocation;
import com.roomiegh.roomie.models.Location;
import com.roomiegh.roomie.volley.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HorizontalListViewFragment extends Fragment {

    private static final String LOG_TAG = "HorizontalListView";
    private static final String REQUEST_TAG = "hostels_by_name_request";
    ArrayList<Location> listItems = new ArrayList<>();
    ArrayList<Location> allLocations = new ArrayList<>();
    RecyclerView mRecyclerView;
    String fruits[] = {"Mango","Apple","Grapes","Papaya","WaterMelon"};
    int images[] = {R.drawable.mango, R.drawable.apple,R.drawable.grapes,R.drawable.papaya,R.drawable.watermelon};

    String url = "http://roomiegh.herokuapp.com/location";
    LocationAdapter mLocationAdapter = new LocationAdapter();
    ProgressBar pbLocations;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //listItems.clear();
        pbLocations = ((ByLocation) getActivity()).getPbLocations();
        //do API call here and populate list view
        callForLocations();

    }

    private void callForLocations() {
        pbLocations.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOG_TAG, response.toString());

                        if (response.length() != 0) {
                            JSONObject jsonData;
                            Location location;
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    jsonData = response.getJSONObject(i);
                                    location = new Location();//make sure to redifine location inside loop to avoid filling the arraylist with the same elements
                                    location.setId(jsonData.getInt("id"));
                                    location.setName(jsonData.getString("name"));
                                    location.setRegion(jsonData.getString("region"));
                                    /*if (jsonData.getString("photoPath") != null)
                                        location.setPhotopath(jsonData.getString("photoPath"));*/

                                    //add location to list
                                    allLocations.add(location);
                                    Log.d(LOG_TAG, "Added: " + location.getName());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Exception encountered: " + e.toString());
                            } finally {
                                //Toast.makeText(ByName.this, ""+allLocations.toString(), Toast.LENGTH_LONG).show();
                                mLocationAdapter.setList(allLocations);
                                mLocationAdapter.notifyDataSetChanged();
                                pbLocations.setVisibility(View.GONE);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse: Error listener fired: " + error.toString());
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
                pbLocations.setVisibility(View.GONE);
            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getActivity()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_horizontal_list_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        //mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        //if (allLocations.size() > 0 & mRecyclerView != null) {
            mRecyclerView.setAdapter(mLocationAdapter);
        //}
        mRecyclerView.setLayoutManager(mLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    public class LocationAdapter extends RecyclerView.Adapter<LocationViewHolder> {
        private ArrayList<Location> list;

        public LocationAdapter() {
            list = new ArrayList<Location>();        }

        public LocationAdapter(ArrayList<Location> Data) {
            list = Data;
        }

        public void setList(ArrayList<Location> list) {
            this.list = list;
        }

        @Override
        public LocationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.recycle_items, parent, false);
            LocationViewHolder holder = new LocationViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final LocationViewHolder holder, int position) {

            holder.titleTextView.setText(list.get(position).getName());
            holder.coverImageView.setImageResource(R.drawable.apple);
            holder.coverImageView.setTag(R.drawable.apple);
            holder.likeImageView.setTag(R.drawable.ic_like);

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public class LocationViewHolder extends RecyclerView.ViewHolder {

        public TextView titleTextView;
        public ImageView coverImageView;
        public ImageView likeImageView;
        public ImageView shareImageView;

        public LocationViewHolder(View v) {
            super(v);
            titleTextView = (TextView) v.findViewById(R.id.titleTextView);
            coverImageView = (ImageView) v.findViewById(R.id.coverImageView);
            likeImageView = (ImageView) v.findViewById(R.id.likeImageView);
            shareImageView = (ImageView) v.findViewById(R.id.shareImageView);
            likeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    int id = (int)likeImageView.getTag();
                    if( id == R.drawable.ic_like){

                        likeImageView.setTag(R.drawable.ic_liked);
                        likeImageView.setImageResource(R.drawable.ic_liked);

                        Toast.makeText(getActivity(),titleTextView.getText()+" added to favourites",Toast.LENGTH_SHORT).show();

                    }else{

                        likeImageView.setTag(R.drawable.ic_like);
                        likeImageView.setImageResource(R.drawable.ic_like);
                        Toast.makeText(getActivity(),titleTextView.getText()+" removed from favourites",Toast.LENGTH_SHORT).show();


                    }

                }
            });



            shareImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE +
                            "://" + getResources().getResourcePackageName(coverImageView.getId())
                            + '/' + "drawable" + '/' + getResources().getResourceEntryName((int)coverImageView.getTag()));

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM,imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, getResources().getText(R.string.send_to)));
                }
            });



        }
    }
}