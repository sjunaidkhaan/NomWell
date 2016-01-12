package com.zai.nomwell.fragments;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.baoyz.actionsheet.ActionSheet;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.zai.nomwell.AddSpotsActivity;
import com.zai.nomwell.MySpotsActivity;
import com.zai.nomwell.R;
import com.zai.nomwell.SelectSpotActivity;
import com.zai.nomwell.TabbedMapActivity;
import com.zai.nomwell.adapter.DividerItemDecoration;
import com.zai.nomwell.adapter.MySpotsAdapter;
import com.zai.nomwell.adapter.holder.OnRecyclerViewClickListener;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.dialog.NomwellFilterDialog;
import com.zai.nomwell.dialog.NomwellInfoDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.dialog.NomwellMultipleChoiceListDialog;
import com.zai.nomwell.dialog.NomwellStarsDialog;
import com.zai.nomwell.util.Util;
import com.zai.nomwell.view.NomwellHalfClickableTextView;
import com.zai.nomwell.view.NomwellSearchView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MySpotFragment extends Fragment implements View.OnClickListener, OnRecyclerViewClickListener {

    public static final String TAG = "my_spot_fragment";

    private TabLayout tabLayout;

    private NomwellHalfClickableTextView layoutSuggestion;
    private NomwellHalfClickableTextView layoutSortBy;

    public SuperRecyclerView rcvwSpots;
    private LinearLayoutManager layoutManager;
    private MySpotsAdapter adapter;

    private boolean dataSet = false;

    private boolean isFiltered = false;

    private MySpotsActivity activity;

    private NomwellSearchView searchView;

    public MySpotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MySpotsActivity) context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_spot, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tabLayout = (TabLayout) view.findViewById(R.id.tabs);
        tabLayout.setOnTabSelectedListener(tabSelectedListener);

        layoutSuggestion = new NomwellHalfClickableTextView(view.findViewById(R.id.layoutSuggestion));
        layoutSuggestion.setNormalText("0 near");
        layoutSuggestion.setClickableText("Seattle, WA");

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

        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getActivity());
        rcvwSpots = (SuperRecyclerView) view.findViewById(R.id.rcvwSpots);
        rcvwSpots.addItemDecoration(itemDecoration);
        layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvwSpots.setLayoutManager(layoutManager);

        rcvwSpots.hideProgress();

        searchView = new NomwellSearchView(view.findViewById(R.id.layoutSearch));

        setViews();

        view.findViewById(R.id.llShare).setOnClickListener(this);
        view.findViewById(R.id.llAddSpots).setOnClickListener(this);
        view.findViewById(R.id.llAddToLists).setOnClickListener(this);
    }

    public void showIOSDialog(String topItems[], ActionSheet.ActionSheetListener listener) {
        ActionSheet.createBuilder(getContext(), getFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(topItems)
                .setCancelableOnTouchOutside(true)
                .setListener(listener).show();
    }

    private void setViews() {
        if (!dataSet) {
            tabLayout.addTab(tabLayout.newTab().setText("ALL"));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_pin_24));
            tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_done_white_24dp));

            tabLayout.getTabAt(1).getIcon().setColorFilter(ContextCompat.getColor(getContext(), R.color.white), PorterDuff.Mode.SRC_IN);
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

    private TabLayout.OnTabSelectedListener tabSelectedListener = new TabLayout.OnTabSelectedListener() {
        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            switch (tab.getPosition()) {
                case 0:
                    adapter = new MySpotsAdapter(activity.getMySpotsDummyData(), false, MySpotFragment.this);
                    break;
                case 1:
                    adapter = new MySpotsAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_WANT_TO_GO,
                            ContextCompat.getColor(getContext(), R.color.colorPrimary)), false, MySpotFragment.this);
                    break;
                case 2:
                    adapter = new MySpotsAdapter(activity.getFilteredDummyData(MySpotsData.STATUS_GONE,
                            ContextCompat.getColor(getContext(), R.color.colorPrimary)), false, MySpotFragment.this);
                    break;
            }
            rcvwSpots.setAdapter(adapter);
            setEmptyViewVisibility();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

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
                if (isFiltered) {
                    map.putExtra(TabbedMapActivity.EXTRA_TITLE, "Filter Result");
                    map.putExtra(TabbedMapActivity.EXTRA_MODE, TabbedMapActivity.MODE_FILTER);
                } else {
                    map.putExtra(TabbedMapActivity.EXTRA_TITLE, "My Spots");
                    map.putExtra(TabbedMapActivity.EXTRA_MODE, TabbedMapActivity.MODE_NORMAL);
                }
                startActivity(map);
                break;
            case R.id.action_populate_list:
                adapter = new MySpotsAdapter(activity.getMySpotsDummyData(), false, this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                getView().findViewById(R.id.llBottomButtons).setVisibility(View.VISIBLE);
                getView().findViewById(R.id.layoutSearch).setVisibility(View.VISIBLE);
                break;

            case R.id.action_populate_list_with_check:
                adapter = new MySpotsAdapter(activity.getMySpotsDummyData(), true, this);
                rcvwSpots.setAdapter(adapter);
                setEmptyViewVisibility();
                getView().findViewById(R.id.llBottomButtons).setVisibility(View.GONE);
                getView().findViewById(R.id.layoutSearch).setVisibility(View.GONE);
                break;
            case R.id.action_filter:
                showFilterDialog();
                break;
        }


        return true;
    }

    private void showFilterDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Spot Filter");
        final NomwellFilterDialog nomwellFilterDialog = new NomwellFilterDialog(getContext());
        nomwellFilterDialog.setCuisineText("Add");
        nomwellFilterDialog.setTagText("Add");
        nomwellFilterDialog.addClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.txtCuisine:
                        showCuisinesDialog(nomwellFilterDialog);
                        break;
                    case R.id.txtTag:
                        showTagsDialog(nomwellFilterDialog);
                        break;
                }
            }
        });
        builder.setView(nomwellFilterDialog.getView());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                isFiltered = true;
                activity.getSupportActionBar().setTitle("Filter Results");
                getView().findViewById(R.id.llAddSpots).setVisibility(View.INVISIBLE);
            }
        });
        builder.setNegativeButton("Back", null);
        builder.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        setEmptyViewVisibility();
    }

    private void setEmptyViewVisibility() {
        if (adapter != null) {
            getView().findViewById(R.id.emptyView).setVisibility(adapter.getItemCount() > 0
                    ? View.GONE : View.VISIBLE);
        } else {
            getView().findViewById(R.id.emptyView).setVisibility(View.VISIBLE);
        }
    }

    public void showEmptyListDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage(getContext().getString(R.string.must_add_places));
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(new String[]{"OK"}, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });
        builder.setView(nomwellListDialog.getView());

        dialog.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        adapter = null;
    }

    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        switch (view.getId()) {
            case R.id.llShare:
                if (rcvwSpots.getAdapter() != null &&
                        rcvwSpots.getAdapter().getItemCount() > 0) {
                    Intent intent = new Intent(getActivity(), SelectSpotActivity.class);
                    intent.putExtra(SelectSpotActivity.EXTRA_TITLE, "Select Spot");
                    intent.putExtra(SelectSpotActivity.EXTRA_SHOW_BOTTOM_BUTTONS, false);
                    startActivity(intent);
                } else {
                    showInfoDialog("You must add places to My Spots before using this features!");
                }
                break;


            case R.id.llAddSpots:
                if (rcvwSpots.getAdapter() != null &&
                        rcvwSpots.getAdapter().getItemCount() > 0) {
                    startActivity(new Intent(getActivity(), AddSpotsActivity.class));
                } else {
                    showEmptyListDialog();
                }
                break;

            case R.id.llAddToLists:
                break;
        }
    }

    private String CUISINES[] = new String[]{
            "American (New)", "American (Traditional)", "Caribbean", "Chinese", "Indian",
            "Polish", "Southern", "Vegan", "Vegetarian"
    };

    private void showCuisinesDialog(final NomwellFilterDialog dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Cuisines");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i)) {
                        sb.append(CUISINES[i]);
                        sb.append(" or ");
                    }
                }
                sb.replace(sb.length() - 4, sb.length(), "");
                dialog.setCuisineText(sb.toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(CUISINES, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.log(TAG, "Item Checked: " + i + " -> " + nomwellListDialog.isChecked(i));
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }

    private String TAGS[] = new String[]{
            "Beer Gardens", "BYOB", "Clubs", "Date Spots", "Duke Bar",
            "Good For Groups", "Late Night", "MSU Bar", "Pool"
    };

    private void showTagsDialog(final NomwellFilterDialog dialog) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose Tags");
        final NomwellMultipleChoiceListDialog nomwellListDialog = new NomwellMultipleChoiceListDialog(getContext());
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                SparseBooleanArray array = nomwellListDialog.getCheckedItems();
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < array.size(); i++) {
                    if (array.get(i)) {
                        sb.append(TAGS[i]);
                        sb.append(" or ");
                    }
                }
                sb.replace(sb.length() - 4, sb.length(), "");
                dialog.setTagText(sb.toString());
            }
        });
        builder.setNegativeButton("Cancel", null);
        nomwellListDialog.setMultipleOptions(TAGS, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Util.log(TAG, "Item Checked: " + i + " -> " + nomwellListDialog.isChecked(i));
                nomwellListDialog.setChecked(i, nomwellListDialog.isChecked(i));
            }
        });
        builder.setView(nomwellListDialog.getView());
        builder.create().show();

    }

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.imvwWantToGo:
                break;
            case R.id.imvwGone:
                showStarsDialog();
                break;
            case R.id.imvwCheck:
                boolean checked = false;
                if (view.getTag() != null) {
                    checked = (boolean) view.getTag();
                }
                if (checked) {
                    ((ImageView) view).setImageResource(R.drawable.ic_sendmode_unchecked_24);
                    view.setTag(false);
                } else {
                    ((ImageView) view).setImageResource(R.drawable.ic_sendmode_checked_24);
                    view.setTag(true);
                }
                break;
        }
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    /**
     * page 77
     */
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

    /**
     * page 132, 141
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
     * page 109
     */
    public void showSendToFriendDialog(String spotName, String option) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Save all these places to " + spotName);
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(getContext());
        nomwellInfoDialog.setMessage("They'll be added as " + option + " by default.");
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Yes", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showInfoDialog("Spots Sent");
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
     * page 62
     *
     * @param spotName
     */

    private void showSpotAddedDialog(String spotName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        NomwellListDialog nomwellListDialog = new NomwellListDialog(getContext());
        nomwellListDialog.setMessage("Spot added to " + spotName);
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(new String[]{"OK"}, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
            }
        });
        builder.setView(nomwellListDialog.getView());

        dialog.show();
    }
}
