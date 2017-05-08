package com.roomiegh.roomie.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roomiegh.roomie.R;

/**
 * Created by KayO on 23/03/2017.
 */

public class Tab1In1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_type_layout,container,false);//change layout

        init(view);

        return view;
    }

    private void init(View view) {

    }
}
