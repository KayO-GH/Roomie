package com.roomiegh.roomie.activities.browseActivities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.adapters.HostelListAdapter;
import com.roomiegh.roomie.models.Hostel;
import com.roomiegh.roomie.volley.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ByName extends ActionBarActivity {
    private static final String LOG_TAG = "ByNameLog";
    private static final String REQUEST_TAG = "hostels_by_name_request";

    Toolbar toolbar;
    String url = "http://roomiegh.herokuapp.com/hostel";
    ProgressBar pbByName;
    ArrayList<Hostel> allHostels;
    ListView lvHostelsByName;
    HostelListAdapter hostelListAdapter;

    public ProgressBar getPbByName() {
        return pbByName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_name);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        init();

        callForDetails();
    }

    private void callForDetails() {
        pbByName.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOG_TAG, response.toString());

                        if (response.length() != 0) {
                            JSONObject jsonData;
                            Hostel hostel;
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    jsonData = response.getJSONObject(i);
                                    hostel = new Hostel();//make sure to redifine hostel inside loop to avoid filling the arraylist with the same elements
                                    hostel.setId(jsonData.getInt("id"));
                                    hostel.setName(jsonData.getString("name"));
                                    hostel.setLocationId(jsonData.getInt("locations_location_id"));
                                    hostel.setNoOfRooms(jsonData.getInt("noOfRooms"));
                                    hostel.setRating(jsonData.getDouble("rating"));
                                    /*if (jsonData.getString("photoPath") != null)
                                        hostel.setPhotopath(jsonData.getString("photoPath"));*/

                                    //add hostel to list
                                    allHostels.add(hostel);
                                    Log.d(LOG_TAG, "Added: " + hostel.getName());
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Exception encountered: " + e.toString());
                            } finally {
                                //Toast.makeText(ByName.this, ""+allHostels.toString(), Toast.LENGTH_LONG).show();
                                hostelListAdapter.setAllHostels(allHostels);
                                hostelListAdapter.notifyDataSetChanged();
                                pbByName.setVisibility(View.GONE);
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse: Error listener fired: " + error.getMessage());
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
                pbByName.setVisibility(View.GONE);
            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    private void init() {
        pbByName = (ProgressBar) findViewById(R.id.pbByName);
        lvHostelsByName = (ListView) findViewById(R.id.lvHostelNames);
        allHostels = new ArrayList<Hostel>();
        hostelListAdapter = new HostelListAdapter(getApplicationContext(), allHostels);
        lvHostelsByName.setAdapter(hostelListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_announcements, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
