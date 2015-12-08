package com.zai.nomwell.view;

import android.support.v7.widget.AppCompatEditText;
import android.view.View;
import android.widget.ImageView;

import com.zai.nomwell.R;

/**
 * This class handles custom view created in xml
 * Created by chitta on 12/6/15.
 */
public class NomwellSearchView {

    /**
     * main container which has search.xml as included layout
     */
    private View container;

    private ImageView imvwSearch;
    private ImageView imvwRemove;
    private AppCompatEditText txtSearch;
    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.imvwSearch:
                    break;
                case R.id.imvwRemove:
                    txtSearch.setText(null);
                    break;
            }
        }
    };

    public NomwellSearchView(View container) {
        this.container = container;
        init();
    }

    /**
     * init view, set click listeners
     */
    private void init() {
        imvwSearch = (ImageView) container.findViewById(R.id.imvwSearch);
        imvwSearch.setOnClickListener(clickListener);
        imvwRemove = (ImageView) container.findViewById(R.id.imvwRemove);
        imvwRemove.setOnClickListener(clickListener);
        txtSearch = (AppCompatEditText) container.findViewById(R.id.txtSearch);
    }

    /**
     * set text in main AppCompatEditText
     *
     * @return
     */
    public String getText() {
        return txtSearch.getText().toString();
    }

    /**
     * get current text from AppCompatEditText
     *
     * @param text
     */
    public void setText(String text) {
        txtSearch.setText(text);
    }

}
