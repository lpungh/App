package com.example.administrator.ekapp;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by YunJungsu on 2017-07-07.
 */

public class Fragment_NCS extends Fragment {
    Context context;
    View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_ncs, container, false);
        context = getActivity();

        return rootView;
    }
}
