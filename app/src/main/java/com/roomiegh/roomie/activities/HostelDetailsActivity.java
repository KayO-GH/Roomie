package com.roomiegh.roomie.activities;

import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.Hostel;
import com.roomiegh.roomie.volley.AppSingleton;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HostelDetailsActivity extends AppCompatActivity {
    private static final String LOG_TAG = "HostelDetailsLog";
    private static final String REQUEST_TAG = "hostel_details_request";
    String browseType = "";
    int hostelID = -1;
    NestedScrollView nSView;
    ImageView ivHostelDetailsImage;
    TextView tvHostelDescription, tvHostelName;
    RatingBar rbHostelDetailsRating;
    ProgressBar pbHostelDetails;

    Toolbar toolbar;
    String url = "http://roomiegh.herokuapp.com/hostel/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_details);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        Bundle receivedInfo = getIntent().getBundleExtra("type_bundle");
        browseType = receivedInfo.getString("browse_type");
        hostelID = receivedInfo.getInt("hostel_id");

        init();

        callForDetails(hostelID);
    }

    private void callForDetails(int hostelID) {
        pbHostelDetails.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, url + hostelID, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOG_TAG, response.toString());

                        if (response.length() != 0) {
                            JSONObject jsonData;
                            JSONArray hostelPicsArray, hostelDescriptionArray;
                            Hostel hostel = null;
                            try {
                                //for (int i = 0; i < response.length(); i++) {
                                //we're expecting only one result at index 0
                                jsonData = response.getJSONObject(0);
                                hostel = new Hostel();//make sure to redefine hostel inside loop to avoid filling the arraylist with the same elements
                                hostel.setId(jsonData.getInt("id"));
                                hostel.setName(jsonData.getString("name"));
                                hostel.setLocationId(jsonData.getInt("locations_location_id"));
                                hostel.setNoOfRooms(jsonData.getInt("noOfRooms"));
                                hostel.setRating(jsonData.getDouble("rating"));

                                //parse JSON to extract pics and description
                                hostelPicsArray = jsonData.getJSONArray("hostel_pics");
                                hostelDescriptionArray = jsonData.getJSONArray("hostel_description");

                                if (hostelPicsArray.length() > 0)
                                    hostel.setPhotopath(hostelPicsArray.getJSONObject(0).getString("image_url"));
                                if (hostelDescriptionArray.length() > 0)
                                    hostel.setDescription(hostelDescriptionArray.getJSONObject(0).getString("description"));


                                //add hostel to list
                                //allHostels.add(hostel);
                                Log.d(LOG_TAG, "Received: " + hostel.getName());
                                // }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Exception encountered: " + e.toString());
                            } finally {

                                pbHostelDetails.setVisibility(View.GONE);
                                nSView.setVisibility(View.VISIBLE);

                                //set views using hostel data
                                if (hostel != null){
                                    rbHostelDetailsRating.setNumStars((int) hostel.getRating());
                                    tvHostelDescription.setText(hostel.getDescription());
                                    tvHostelName.setText(hostel.getName());
                                    Log.d(LOG_TAG, "Hostel Description: "
                                            +hostel.getDescription());
                                    Picasso.with(HostelDetailsActivity.this)
                                            .load(hostel.getPhotopath())
                                            .fit()
                                            .centerCrop()
                                            .error(R.drawable.ic_home_black)
                                            .placeholder(R.drawable.white_bkgrnd)
                                            .into(ivHostelDetailsImage);
                                    Log.d(LOG_TAG, "Hostel Image: "+hostel.getPhotopath());

                                }else{
                                    Log.d(LOG_TAG, "onResponse: hostel is null");
                                }

                            }
                        } else {
                            // TODO: 09/05/2017 Show that no response matches the request
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(LOG_TAG, "onErrorResponse: Error listener fired: " + error.getMessage());
                VolleyLog.d(LOG_TAG, "Error: " + error.getMessage());
                pbHostelDetails.setVisibility(View.GONE);
            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);
    }

    private void init() {
        pbHostelDetails = (ProgressBar) findViewById(R.id.pbHostelDetails);
        ivHostelDetailsImage = (ImageView) findViewById(R.id.ivHostelDetailsImage);
        rbHostelDetailsRating = (RatingBar) findViewById(R.id.rbHostelDetailsRating);
        tvHostelDescription = (TextView) findViewById(R.id.tvHostelDetailsDescription);
        tvHostelName = (TextView) findViewById(R.id.tvHostelName);
        nSView = (NestedScrollView) findViewById(R.id.nsView);
    }
}
