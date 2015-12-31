package com.zai.nomwell;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zai.nomwell.db.MyListsData;
import com.zai.nomwell.dialog.NomwellImageDialog;
import com.zai.nomwell.util.Util;

import java.util.ArrayList;

public class AddSpotsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_spots);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        showTutorialDialog();


    }

    private void showTutorialDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.swipe_right));
        NomwellImageDialog nomwellImageDialog = new NomwellImageDialog(this);
        nomwellImageDialog.setMessage(getString(R.string.to_quickly_add_spots_to_your_list));
        nomwellImageDialog.setImage(R.drawable.search_tutorial_image);
        builder.setView(nomwellImageDialog.getView());
        final AlertDialog dialog = builder.create();
        nomwellImageDialog.setPositive("Got it", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        dialog.show();
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
    public void onClick(View view) {

    }
}
