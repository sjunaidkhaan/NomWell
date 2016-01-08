package com.zai.nomwell.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ns.developer.tagview.entity.Tag;
import com.ns.developer.tagview.widget.TagCloudLinkView;
import com.zai.nomwell.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class AddTagFragment extends Fragment {

    private TagCloudLinkView tagViewTagged;
    private TagCloudLinkView tagViewAll;
    private AppCompatTextView txtAddIt;
    private AppCompatEditText txtSearch;

    public AddTagFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_tag, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tagViewTagged = (TagCloudLinkView) view.findViewById(R.id.chipViewTagged);

        tagViewAll = (TagCloudLinkView) view.findViewById(R.id.chipViewAll);
        txtAddIt = (AppCompatTextView) view.findViewById(R.id.txtAddIt);
        txtSearch = (AppCompatEditText) view.findViewById(R.id.txtSearch);
        txtAddIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (txtSearch.length() < 1) {
                    return;
                }

                String text = txtSearch.getText().toString();
                tagViewTagged.add(new Tag(1, text));
                tagViewTagged.drawTags();
            }
        });
    }
}
