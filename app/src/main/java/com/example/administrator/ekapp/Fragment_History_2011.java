package com.example.administrator.ekapp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_History_2011 extends android.support.v4.app.Fragment{
    Context context;
    View rootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_history_2011, container, false);
        context = getActivity();

        return rootView;
    }
}
