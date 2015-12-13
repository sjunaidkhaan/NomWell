package com.zai.nomwell.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.dialog.NomwellInputDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;
import com.zai.nomwell.view.NonSwipeableViewPager;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpotFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "my_spot_fragment";

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private NonSwipeableViewPager mViewPager;
    private TabLayout tabLayout;

    private ArrayList<BaseFragment> fragments;

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

    private boolean dateSet = false;

    public MySpotFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new MySpotAllFragment());
        fragments.add(new MySpotPinnedFragment());
        fragments.add(new MySpotVisitedFragment());

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_spot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (NonSwipeableViewPager) view.findViewById(R.id.container);
        mViewPager.setSwipeable(false);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);

        layoutSuggestion = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        layoutSuggestion.setNormalText("0 near");
        layoutSuggestion.setClickableText("Seattle, WA");

        layoutSortBy = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSortBy));
        layoutSortBy.setNormalText("Sort by:");
        layoutSortBy.setClickableText("Name");

        view.findViewById(R.id.llShare).setOnClickListener(this);
        view.findViewById(R.id.llAddSpots).setOnClickListener(this);
        view.findViewById(R.id.llAddToLists).setOnClickListener(this);

        setViews();
    }


    private void setViews() {
        if (!dateSet) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_pin_drop_white_24dp);
            tabLayout.getTabAt(2).setIcon(R.drawable.ic_done_white_24dp);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_my_spots, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_map:
                Intent map = new Intent(getActivity(), TabbedMapActivity.class);
                startActivity(map);
                break;
            case R.id.action_confirm_email:
                showConfirmEmailDialog();
                break;
        }


        return true;
    }


    private void showConfirmEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.confirm_email_address));
        final NomwellInputDialog nomwellInputDialog = new NomwellInputDialog(getContext());
        nomwellInputDialog.setMessage(getString(R.string.where_is_your_list_located_));
        nomwellInputDialog.setHint(getString(R.string.enter_email_address));
        builder.setView(nomwellInputDialog.getView());
        final AlertDialog dialog = builder.create();

        nomwellInputDialog.setPositive("Submit", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        nomwellInputDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                break;

            case R.id.llAddSpots:
                startActivity(new Intent(getActivity(), AddSpotsActivity.class));
                break;

            case R.id.llAddToLists:
                break;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getFragmentTitle(getContext());
        }
    }

}
