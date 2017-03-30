package com.roomiegh.roomie.tabs;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.roomiegh.roomie.R;

/**
 * Created by Kwadwo Agyapon-Ntra on 06/10/2015.
 */
public class TabHome extends Fragment{
    private WebView wvHome;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_home,container,false);

        init(view);

        wvHome.setWebViewClient(new WebViewClient());
        wvHome.loadUrl("http://www.roomiegh.com");

        return view;
    }

    private void init(View view) {
        wvHome = (WebView) view.findViewById(R.id.wvHome);
    }
}
