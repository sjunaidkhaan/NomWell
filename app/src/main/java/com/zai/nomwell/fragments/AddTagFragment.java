package com.zai.nomwell.fragments;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTagFragment extends Fragment {

    public AddTagFragment() {
    }

    private static final String TAGS[] = {"Tagg"};

    private ListViewCompat listTags;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
