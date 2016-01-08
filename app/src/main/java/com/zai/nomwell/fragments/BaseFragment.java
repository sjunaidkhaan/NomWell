package com.zai.nomwell.fragments;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * Created by chitta on 12/6/15.
 */
public abstract class BaseFragment extends Fragment {

    public abstract String getFragmentTitle(Context context);

    public abstract int getAdapterTotal();
}
