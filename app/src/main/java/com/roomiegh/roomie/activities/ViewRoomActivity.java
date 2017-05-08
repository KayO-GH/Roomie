package com.roomiegh.roomie.activities;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.roomiegh.roomie.R;
import com.roomiegh.roomie.util.PushUserUtil;

public class ViewRoomActivity extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room);

        Bundle receivedUserName = getIntent().getBundleExtra(PushUserUtil.PUSH_INTENT_KEY);
        String userName = receivedUserName.getString(PushUserUtil.NAME_KEY);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("http://www.roomiegh.com/table.php?user=" + userName);

        //Test to check if username has been found
        //Toast.makeText(getApplicationContext(),userName,Toast.LENGTH_SHORT).show();

    }


}
