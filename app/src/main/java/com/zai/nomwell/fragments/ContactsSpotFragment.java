package com.zai.nomwell.fragments;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.actionsheet.ActionSheet;
import com.zai.nomwell.ContactsSpotActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.view.NomwellHalfClickableTextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class ContactsSpotFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener {

    public static final String TAG = "contact_spot_fragment";

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

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


    private ContactsSpotActivity activity;

    public ContactsSpotFragment() {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (ContactsSpotActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragments = new ArrayList<>();
        fragments.add(new MySpotPinnedFragment());
        fragments.add(new MySpotVisitedFragment());
        fragments.add(new ContactListsFragment());

        return inflater.inflate(R.layout.fragment_contacts_spot, container, false);
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

        layoutSuggestion = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        layoutSuggestion.setNormalText("0 near");
        layoutSuggestion.setClickableText("Seattle, WA");
        layoutSuggestion.addClickListener(this);

        layoutSortBy = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSortBy));
        layoutSortBy.setNormalText("Sort by:");
        layoutSortBy.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showIOSDialog(new String[]{"Name", "Rating (Gone Only)", "Near Me"}, new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                    }
                });
            }
        });
        layoutSortBy.setClickableText("Name");

        setViews();
    }

    public void showIOSDialog(String topItems[], ActionSheet.ActionSheetListener listener) {
        ActionSheet.createBuilder(getContext(), getFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(topItems)
                .setCancelableOnTouchOutside(true)
                .setListener(listener).show();
    }

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    private void setViews() {
        if (!dateSet) {
            mViewPager.setAdapter(mSectionsPagerAdapter);
            tabLayout.setupWithViewPager(mViewPager);

            tabLayout.getTabAt(0).setIcon(R.drawable.ic_pin_24);
            tabLayout.getTabAt(0).getIcon().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
            tabLayout.getTabAt(1).setIcon(R.drawable.ic_done_white_24dp);
            tabLayout.getTabAt(2).setText("Lists");

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtClickable:
                showIOSDialog(new String[]{"Chicago, IL (47)", "Detroit, MI (19", "San Francisco, CA (1)"}, new ActionSheet.ActionSheetListener() {
                    @Override
                    public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                    }

                    @Override
                    public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                    }
                });
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {

    }

    @Override
    public void onItemLongClick(View view, int position) {

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
