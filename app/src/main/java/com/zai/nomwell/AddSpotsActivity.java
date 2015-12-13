package com.zai.nomwell;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.zai.nomwell.dialog.NomwellImageDialog;

public class AddSpotsActivity extends BaseActivity {

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

}
