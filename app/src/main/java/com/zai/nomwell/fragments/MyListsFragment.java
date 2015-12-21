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

import com.zai.nomwell.R;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;
import com.zai.nomwell.view.NomwellTab;

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

    private NomwellTab tab1;
    private NomwellTab tab2;
    private NomwellTab tab3;

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
        halfClickableTextView.setNormalText("For City:");
        halfClickableTextView.setClickableText("Seattle, WA");

        view.findViewById(R.id.llShare).setOnClickListener(this);
        view.findViewById(R.id.llCreateLists).setOnClickListener(this);

        setViews();
    }

    private void updateTabs(int position) {
        switch (position) {
            case 0:
                tab1.setSelected(true);
                tab2.setSelected(false);
                tab3.setSelected(false);
                break;
            case 1:
                tab1.setSelected(false);
                tab2.setSelected(true);
                tab3.setSelected(false);
                break;
            case 2:
                tab1.setSelected(false);
                tab2.setSelected(false);
                tab3.setSelected(true);
                break;
        }
    }

    private void setViews() {
        if (!dateSet) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
//            tabLayout.setupWithViewPager(mViewPager);

            tab1 = new NomwellTab(getContext(), R.drawable.left_selected, R.drawable.left_normal);
            tab1.setText("MINE");
            tab2 = new NomwellTab(getContext(), R.drawable.center_selected, R.drawable.center_normal);
            tab2.setText("FOLLOWING");
            tab3 = new NomwellTab(getContext(), R.drawable.right_selected, R.drawable.right_normal);
            tab3.setText("SPOT RECS");
//            tabLayout.addTab(tabLayout.newTab());
//            tabLayout.addTab(tabLayout.newTab());
//            tabLayout.addTab(tabLayout.newTab());
            tabLayout.addTab(tabLayout.newTab().setCustomView(tab1.getView()));
            tabLayout.addTab(tabLayout.newTab().setCustomView(tab2.getView()));
            tabLayout.addTab(tabLayout.newTab().setCustomView(tab3.getView()));
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
        nomwellListDialog.setOptions(new String[]{"OK"});
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


    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                break;

            case R.id.llCreateLists:
                showEmptyMustHaveListDialog();
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
