package com.zai.nomwell.fragments;


import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.ListImportActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.dialog.NomwellInputDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.dialog.NomwellStarsDialog;
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
            case R.id.action_lets_started:
                showLetsStartedDialog();
                break;
            case R.id.action_fantastic:
                showFantasticDialog();
                break;
            case R.id.action_confirm_email:
                showConfirmEmailDialog();
                break;
            case R.id.action_ask_location:
                showLocationAccessDialog();
                break;
        }


        return true;
    }

    /**
     * shows the first dialog
     */
    private void showLetsStartedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.now_lets_get_started));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage(getString(R.string.lets_get_started_message));
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.lets_started_options));
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * shows Fantastic dialog
     */

    private void showFantasticDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getString(R.string.fantastic_));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage(getString(R.string.where_is_your_list_located_));
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.list_location_options));
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(getActivity(), ListImportActivity.class));
                }
                dialog.dismiss();
            }
        });

        dialog.show();
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

    private void showStarsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Congrats on trying ");
        final NomwellStarsDialog nomwellStarsDialog = new NomwellStarsDialog(getContext());
        nomwellStarsDialog.setMessage(getContext().getString(R.string.howd_you_like_it));
        nomwellStarsDialog.setNote("(Tap twice for full-stars)");
        builder.setView(nomwellStarsDialog.getView());
        final AlertDialog dialog = builder.create();

        nomwellStarsDialog.setPositive("Done", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        nomwellStarsDialog.setNegative("Add More Details", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void showLocationAccessDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(getContext().getString(R.string.ask_location_tile));
        builder.setMessage(getContext().getString(R.string.ask_location_description));
        builder.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                askForLocation();
            }
        });
        builder.setNegativeButton("Don't Allow", null);
        builder.create().show();
    }

    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                break;

            case R.id.llAddSpots:
                showLocationAccessDialog();
                break;

            case R.id.llAddToLists:
                break;
        }
    }

    private void askForLocation() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MySpotsActivity.PERMISSIONS_REQUEST_LOCATION);
        } else {
            startActivity(new Intent(getActivity(), AddSpotsActivity.class));
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
