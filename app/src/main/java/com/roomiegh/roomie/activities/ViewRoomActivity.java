package com.roomiegh.roomie.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.models.Room;
import com.roomiegh.roomie.util.PushUserUtil;

public class ViewRoomActivity extends AppCompatActivity {
    String url = "http://roomiegh.herokuapp.com/room/";//append room id
    Room thisRoom;
    TextView tvRoomDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        Bundle receivedId = getIntent().getBundleExtra("room_bundle");
        thisRoom = (Room) receivedId.getSerializable("this_room");

    }


}
