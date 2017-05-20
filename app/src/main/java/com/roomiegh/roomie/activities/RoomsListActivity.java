package com.roomiegh.roomie.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.roomiegh.roomie.R;
import com.roomiegh.roomie.adapters.HostelListAdapter;
import com.roomiegh.roomie.adapters.RoomListAdapter;
import com.roomiegh.roomie.models.Hostel;
import com.roomiegh.roomie.models.Room;
import com.roomiegh.roomie.volley.AppSingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by KayO on 20/05/2017.
 */

public class RoomsListActivity extends AppCompatActivity {
    private static final String LOG_TAG = "RoomListLog";
    private static final String REQUEST_TAG = "rooms_by_hostel_request";
    Toolbar toolbar;
    String url = "http://roomiegh.herokuapp.com/hostelroom/";//hostel id to be appended
    ProgressBar pbHostelRooms;
    ArrayList<Room> allRooms;
    ListView lvRoomsByHostel;
    RoomListAdapter roomListAdapter;
    String browseType = "";
    int hostelID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_by_name);//reuse ByName layout

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);
        }

        final Bundle receivedInfo = getIntent().getBundleExtra("type_bundle");
        browseType = receivedInfo.getString("browse_type");
        hostelID = receivedInfo.getInt("hostel_id");
        //TODO set title to HostelName

        init();

        lvRoomsByHostel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        callForRooms(hostelID);
    }

    private void init() {
        pbHostelRooms = (ProgressBar) findViewById(R.id.pbByName);
        lvRoomsByHostel = (ListView) findViewById(R.id.lvHostelNames);
        allRooms = new ArrayList<>();
        roomListAdapter = new RoomListAdapter(getApplicationContext(), allRooms);
        lvRoomsByHostel.setAdapter(roomListAdapter);
    }

    private void callForRooms(int hostelID) {
        pbHostelRooms.setVisibility(View.VISIBLE);
        JsonArrayRequest jsonArrayReq = new JsonArrayRequest(Request.Method.GET, url+hostelID, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(LOG_TAG, response.toString());

                        if (response.length() != 0) {
                            JSONObject jsonData;
                            JSONArray hostelRoomsArray;
                            JSONObject roomObj;
                            Room room;
                            try {
                                for (int i = 0; i < response.length(); i++) {
                                    jsonData = response.getJSONObject(i);

                                    //TODO parse JSON right to get images
                                    hostelRoomsArray = jsonData.getJSONArray("hostel_rooms");
                                    for (int j = 0; j < hostelRoomsArray.length(); j++) {
                                        roomObj = hostelRoomsArray.getJSONObject(j);
                                        room = new Room();//make sure to redefine room inside loop to avoid filling the arraylist with the same elements
                                        room.setId(roomObj.getInt("id"));
                                        room.setRoomNum(roomObj.getString("roomNo"));
                                        room.setType(roomObj.getInt("type"));
                                        room.setPrice(roomObj.getInt("price"));

                                        //add hostel to list
                                        allRooms.add(room);
                                        Log.d(LOG_TAG, "Added: " + room.getRoomNum());
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(LOG_TAG, "Exception encountered: " + e.toString());
                            } finally {
                                //Toast.makeText(ByName.this, ""+allHostels.toString(), Toast.LENGTH_LONG).show();
                                roomListAdapter.setAllRooms(allRooms);
                                roomListAdapter.notifyDataSetChanged();
                                pbHostelRooms.setVisibility(View.GONE);
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
                pbHostelRooms.setVisibility(View.GONE);
            }
        });
        // Adding JsonObject request to request queue
        AppSingleton.getInstance(getApplicationContext()).addToRequestQueue(jsonArrayReq, REQUEST_TAG);

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
        } else if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
