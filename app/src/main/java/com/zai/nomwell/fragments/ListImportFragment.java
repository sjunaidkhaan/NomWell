package com.zai.nomwell.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ListImportFragment extends Fragment {

    public ListImportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list_import, container, false);
    }
}
