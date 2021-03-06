package com.zai.nomwell;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.baoyz.actionsheet.ActionSheet;
import com.bumptech.glide.Glide;
import com.zai.nomwell.adapter.NavigationArrayAdapter;
import com.zai.nomwell.db.MyListsData;
import com.zai.nomwell.db.MySpotsData;
import com.zai.nomwell.dialog.NomwellInfoDialog;
import com.zai.nomwell.dialog.NomwellInputDialog;
import com.zai.nomwell.dialog.NomwellListDialog;
import com.zai.nomwell.dialog.NomwellPaddedListDialog;
import com.zai.nomwell.fragments.MyListsFragment;
import com.zai.nomwell.fragments.MySpotFragment;
import com.zai.nomwell.util.Util;

import java.util.ArrayList;

public class MySpotsActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener, View.OnClickListener {

    private static final String TAG = "MySpotsActivity";

    public static final int PERMISSIONS_REQUEST_LOCATION = 1;

    private MySpotFragment mySpotFragment;
    private MyListsFragment myListsFragment;

    private NavigationView navigationView;
    private ListView listOptions;
    private AppCompatImageView imvwGear;

    private static final String OPTIONS[] = {"My Spots  ", "My Lists",
            "Explore Friends' Spots", "Switch Cities", "Import an Existing List"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_spots);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        startActivity(new Intent(this, IntroActivity.class));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        AppCompatImageView imvwIcon = (AppCompatImageView) navigationView.findViewById(R.id.imageView);
        Glide.with(this).load(R.drawable.horiz_color_with_icon).into(imvwIcon);
        listOptions = (ListView) navigationView.findViewById(R.id.listOptions);
        listOptions.setAdapter(new NavigationArrayAdapter(this, R.layout.navigation_simple_text_item_1,
                android.R.id.text1, OPTIONS));
        listOptions.setOnItemClickListener(this);
//        navigationView.setCheckedItem(R.id.nav_my_spots);

        int navigationBarHeight = Util.getNavBarHeight(this);
        Util.log(TAG, "NavigationBar Height: " + navigationBarHeight);
//        if (navigationBarHeight > 0) {
//            View bottomButtons = findViewById(R.id.content);
//            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) bottomButtons.getLayoutParams();
//            params.setMargins(0, 0, 0, navigationBarHeight);
//            int padding = getResources().getDimensionPixelSize(R.dimen.padding);
//            bottomButtons.setPadding(0, 0, 0, padding);
//            bottomButtons.requestLayout();
//        }

        if (mySpotFragment == null) {
            mySpotFragment = new MySpotFragment();
        }
        if (myListsFragment == null) {
            myListsFragment = new MyListsFragment();
        }

        imvwGear = (AppCompatImageView) findViewById(R.id.imvwGear);
        imvwGear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSettingsDialog();
            }
        });

        setCurrentFragment(mySpotFragment);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateNavigationDrawer();
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Settings");
        final NomwellPaddedListDialog nomwellListDialog = new NomwellPaddedListDialog(this);
        builder.setNeutralButton("Cancel", null);
        builder.setView(nomwellListDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(new String[]{
                "Send Us Feedback", "Edit Profile", "Profile Visibility", "Spot Note Default", "Log Out"
        }, new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                switch (i) {
                    case 0:
                        break;
                    case 1:
                        Intent intent = new Intent(MySpotsActivity.this, LoginActivity.class);
                        intent.putExtra(LoginActivity.EXTRA_MODE, LoginActivity.MODE_EDIT);
                        startActivity(intent);
                        break;
                    case 2:
                        showProfileVisibilityDialog();
                        break;
                    case 3:
                        showNoteVisibilityDialog();
                        break;
                }
            }
        });
        dialog.show();
    }

    private void showProfileVisibilityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Profile Visibility");
        final NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(this);
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setMessage(noTrailingWhiteLines(Html.fromHtml(String.format("<p>A visible user can be discovered by friends who " +
                "have him/her in their phone\'s contact. Those friends can view the user\'s spots, lists, " +
                "and visible notes.</p><p>A visible user can also discover and view his/her friends the same way</p>"))));
        nomwellInfoDialog.setBoldMessage("Your profile is currently: Visible");
        nomwellInfoDialog.setPositive("Turn Invisible", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        nomwellInfoDialog.setNegative("Leave Visible", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showNoteVisibilityDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Note Visibility");
        final NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(this);
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setMessage(noTrailingWhiteLines(Html.fromHtml(String.format("<p>when User\'s note for a spot is visible," +
                " that note can be viewed by friends when they are exploring the user\'s spots or lists.</p>"))));
        nomwellInfoDialog.setBoldMessage("Your default for new notes: Visible");
        nomwellInfoDialog.setPositive("Turn Invisible", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        nomwellInfoDialog.setNegative("Leave Visible", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private CharSequence noTrailingWhiteLines(CharSequence text) {
        while (text.charAt(text.length() - 1) == '\n') {
            text = text.subSequence(0, text.length() - 1);
        }
        return text;
    }

    private void updateNavigationDrawer() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (fragment == mySpotFragment) {
            navigationView.setCheckedItem(R.id.nav_my_spots);
            getSupportActionBar().setTitle("My Spots");
        } else if (fragment == myListsFragment) {
            navigationView.setCheckedItem(R.id.nav_my_lists);
            getSupportActionBar().setTitle("My Lists");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {

                }
                return;
            }
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_my_spots) {
            setCurrentFragment(mySpotFragment);
            getSupportActionBar().setTitle("My Spots");
        } else if (id == R.id.nav_my_lists) {
            setCurrentFragment(myListsFragment);
            getSupportActionBar().setTitle("My Lists");
        } else if (id == R.id.nav_switch_cities) {
            startActivity(new Intent(this, ChooseCityActivity.class));
        } else if (id == R.id.nav_import_an_existing_list) {
            showLetsStartedDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.content);
    }

    private void setCurrentFragment(Fragment fragment) {
        if (fragment != getCurrentFragment()) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.content, fragment, MyListsFragment.TAG)
                    .commit();
        }
    }

    /**
     * shows the first dialog
     */
    private void showLetsStartedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.now_lets_get_started));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(this);
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setMessage(getString(R.string.lets_get_started_message));
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.lets_started_options), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialog.dismiss();
                if (i == 0) {
                    showFantasticDialog();
                }
                updateNavigationDrawer();
            }
        });
        builder.setView(nomwellListDialog.getView());

        dialog.show();
    }

    private void showConfirmEmailDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm_email_address));
        final NomwellInputDialog nomwellInputDialog = new NomwellInputDialog(this);
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


    /**
     * shows Fantastic dialog
     */

    private void showFantasticDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.fantastic_));
        NomwellListDialog nomwellListDialog = new NomwellListDialog(this);
        nomwellListDialog.setMessage("Where is your list located");
        final AlertDialog dialog = builder.create();
        nomwellListDialog.setOptions(getResources().getStringArray(R.array.list_location_options), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {
                    startActivity(new Intent(MySpotsActivity.this, ListImportActivity.class));
                } else if (i == 1) {
                    showConfirmEmailDialog();
                }
                dialog.dismiss();
            }
        });
        builder.setView(nomwellListDialog.getView());

        dialog.show();
    }

    /**
     * Friend's spots
     * page 2
     */
    private void showSendToFriendDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("You must provide access to your phone contacts to use this feature.");
        NomwellInfoDialog nomwellInfoDialog = new NomwellInfoDialog(this);
        nomwellInfoDialog.setMessage("Allow Nomwell to access contacts?");
        builder.setView(nomwellInfoDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInfoDialog.setPositive("Allow", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                showLastStepDialog();
            }
        });
        nomwellInfoDialog.setNegative("Don't Allow", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /**
     * Friend's spots
     * page 3
     */
    private void showLastStepDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Last Step");
        final NomwellInputDialog nomwellInputDialog = new NomwellInputDialog(this);
        nomwellInputDialog.setHint("XXX-XXX-XXXX");
        nomwellInputDialog.setMessage(getString(R.string.last_step_desc));
        builder.setView(nomwellInputDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellInputDialog.setPositive("Submit", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                startActivity(new Intent(MySpotsActivity.this, NomwellContactsActivity.class));
            }
        });
        nomwellInputDialog.setNegative("Back", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public ArrayList<MySpotsData> getMySpotsDummyData() {
        ArrayList<MySpotsData> mySpotsData = new ArrayList<>();

        MySpotsData msd = new MySpotsData();
        msd.header = "Ada Street";
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Try the quiche";
        msd.rating = 5;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Alinea";
        msd.info = "$$ - Japanese";
        msd.subInfo = "";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_NO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Au Cheval";
        msd.info = "$$ - American (New)";
        msd.subInfo = "Note: Burgers, Rec'd by Claydia w...";
        msd.rating = 0;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Avec";
        msd.info = "$$ - Fusion";
        msd.subInfo = "Note: Don't come on the weekends...";
        msd.rating = 4;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Balena";
        msd.info = "$$ - Oyster Bars";
        msd.subInfo = "Note: Oyster were mah";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Girls & the Goat";
        msd.info = "$$ - New American";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_GONE;
        mySpotsData.add(msd);

        msd = new MySpotsData();
        msd.header = "Haymarket Pub & Brewery";
        msd.info = "$$ - Brewery";
        msd.subInfo = "";
        msd.rating = 3;
        msd.status = MySpotsData.STATUS_WANT_TO_GO;
        mySpotsData.add(msd);

        return mySpotsData;
    }

    public ArrayList<MySpotsData> getFilteredDummyData(int status, int colorFilter) {
        ArrayList<MySpotsData> all = getMySpotsDummyData();
        ArrayList<MySpotsData> filtered = new ArrayList<>();
        for (MySpotsData msd : all) {
            if (msd.status == status) {
                msd.colorFilter = colorFilter;
                filtered.add(msd);
            }
        }
        return filtered;
    }

    public ArrayList<MyListsData> getMyListsDummyData() {
        ArrayList<MyListsData> mlds = new ArrayList<>();

        MyListsData mld = new MyListsData();
        mld.header = "Breakfast of Champions";
        mld.info = "22 spots, 13 followers";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Demo";
        mld.info = "11 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Great Coffee";
        mld.info = "17 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Mad Picks";
        mld.info = "14 spots, 13 followers";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Nab it and Go Eats";
        mld.info = "17 spots";
        mlds.add(mld);

        mld = new MyListsData();
        mld.header = "Taco Flavoured Kisses";
        mld.info = "22 spots (13 in Chicago, IL)";
        mlds.add(mld);

        return mlds;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private String[] SHEETITEMS = new String[]{"Settle, WA (0)", "Add City"};

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        if (position == 0) {
            setCurrentFragment(mySpotFragment);
            getSupportActionBar().setTitle("My Spots");
        } else if (position == 1) {
            setCurrentFragment(myListsFragment);
            getSupportActionBar().setTitle("My Lists");
        } else if (position == 2) {
            showSendToFriendDialog();
        } else if (position == 3) {
//            startActivity(new Intent(this, ChooseCityActivity.class));
            showIOSDialog(SHEETITEMS, new ActionSheet.ActionSheetListener() {
                @Override
                public void onDismiss(ActionSheet actionSheet, boolean isCancel) {

                }

                @Override
                public void onOtherButtonClick(ActionSheet actionSheet, int index) {
                    if (index >= SHEETITEMS.length - 1) {
                        Intent intent = new Intent(getBaseContext(), ChooseCityActivity.class);
                        intent.putExtra(ChooseCityActivity.EXTRA_TITLE, "Add City");
                        startActivity(intent);
                    } else {

                    }
                }
            });
        } else if (position == 4) {
            showLetsStartedDialog();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public void showIOSDialog(String topItems[], ActionSheet.ActionSheetListener listener) {
        ActionSheet.createBuilder(this, getSupportFragmentManager())
                .setCancelButtonTitle("Cancel")
                .setOtherButtonTitles(topItems)
                .setCancelableOnTouchOutside(true)
                .setListener(listener).show();
    }

    @Override
    public void onClick(View view) {
        Util.log(TAG, "Clicked");
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.content);
        switch (view.getId()) {
            case R.id.llShare:
                if (fragment == mySpotFragment) {
                    mySpotFragment.showSendToFriendDialog("Test", "Test");
                } else if (fragment == myListsFragment) {
                    myListsFragment.showSendToFriendDialog();
                }
                break;

            case R.id.llAddSpots:
                if (fragment == mySpotFragment) {
                    if (mySpotFragment.rcvwSpots.getAdapter() != null &&
                            mySpotFragment.rcvwSpots.getAdapter().getItemCount() > 0) {
                        startActivity(new Intent(this, AddSpotsActivity.class));
                    } else {
                        mySpotFragment.showEmptyListDialog();
                    }
                } else if (fragment == myListsFragment) {
                    myListsFragment.showListInputDialog();
                }

                break;

            case R.id.llAddToLists:
                break;
        }
    }
}
