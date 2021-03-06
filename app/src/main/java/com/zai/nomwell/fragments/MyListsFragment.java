package com.zai.nomwell.fragments;


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
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.baoyz.actionsheet.ActionSheet;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.dialog.NomwellInfoDialog;
import com.zai.nomwell.dialog.NomwellInputDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyListsFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "my_lists_fragment";

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
    private ViewPager mViewPager;
    private TabLayout tabLayout;

    private ArrayList<BaseFragment> fragments;

    private NomwellHalfClickableTextView halfClickableTextView;

    private boolean dateSet = false;

    public MyListsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragments = new ArrayList<>();
        fragments.add(new MyListsMineFragment());
        fragments.add(new MyListsFollowingFragment());
        fragments.add(new MyListsSpotRecsFragment());

        setHasOptionsMenu(true);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_lists, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) view.findViewById(R.id.container);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                updateTabs(position);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(tabSelectedListener);

        halfClickableTextView = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        halfClickableTextView.setNormalText("For:");
        halfClickableTextView.setClickableText("Seattle, WA");
        halfClickableTextView.addClickListener(this);

        view.findViewById(R.id.llShare).setOnClickListener(this);
        view.findViewById(R.id.llCreateLists).setOnClickListener(this);

        setViews();
    }


    private void updateTabs(int position) {
        switch (position) {
            case 0:

                break;
            case 1:
                break;
            case 2:
                break;
        }
    }

    private void setViews() {
        if (!dateSet) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);
        }
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            updateTabs(tab.getPosition());
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if (menu != null) {
            menu.clear();
        }
        inflater.inflate(R.menu.menu_my_lists, menu);
    }


    private void showEmptyMustHaveListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage(getContext().getString(R.string.must_have_list));
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(new String[]{"OK"}, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                showListInputDialog();
            }
        });
        builder.setView(nomwellListDialog.getView());

        dialog.show();
    }

    private String OPTIONS[] = new String[]{"Chicago, IL (46)", "Nashville, TN (9)", "San Francisco, CA (15)"
            , "Settle, WA(25)", "Add City"};


    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                BaseFragment fragment = (BaseFragment) mSectionsPagerAdapter.getItem(mViewPager.getCurrentItem());
                if (fragment.getAdapterTotal() > 0) {
                    showSendToFriendDialog();
                } else {
                    showInfoDialog("You must have list before using this feature!");
                }
                break;

            case R.id.llCreateLists:
                showListInputDialog();
                break;

            case R.id.txtClickable:
                MySpotsActivity msa = (MySpotsActivity) getActivity();
                msa.showIOSDialog(OPTIONS, new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                        if (index >= OPTIONS.length - 1) {

                        } else {
                            halfClickableTextView.setClickableText(OPTIONS[index]);
                        }
                    }
                });
                break;
        }
    }

    /**
     * page 88
     */
    public void showListInputDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final NomwellInputDialog nomwellInputDialog = new NomwellInputDialog(getContext());
        nomwellInputDialog.setMessage("What's the name of your list?");
        builder.setView(nomwellInputDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInputDialog.setPositive("OK", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                String text = nomwellInputDialog.getInputText();
                showInfoDialog("Spot added to " + text);
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

    /**
     * page 93
     */
    public void showSendToFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Want to send this list to friends?");
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(getContext());
        nomwellInfoDialog.setMessage("The will be able to view it in browser and follow it in Nomwell.");
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInfoDialog("List Sent");
            }
        });
        nomwellInfoDialog.setNegative("Cancel", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * page 96, 115
     *
     * @param info
     */
    private void showInfoDialog(String info) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(info);
        builder.setPositiveButton("OK", null);
        builder.create().show();
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
