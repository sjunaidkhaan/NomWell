package com.zai.nomwell.dialog;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.zai.nomwell.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IOSDialog extends DialogFragment {

    public static void show(FragmentManager fragmentManager, String topItems[]) {
        IOSDialog iosDialog = new IOSDialog();
        Bundle bundle = new Bundle();
        bundle.putStringArray(EXTRA_TOP_ITEMS, topItems);
        iosDialog.setArguments(bundle);
        fragmentManager.beginTransaction().add(iosDialog, TAG).commit();
    }

    public IOSDialog() {
        // Required empty public constructor
    }

    public static final String EXTRA_TOP_ITEMS = "top_items";
    public static final String TAG = "ios_dialog";

    private String topItems[];

    private AppCompatButton btnPositive;
    private AppCompatButton btnNegative;
    private LinearLayout llTopContent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle args = getArguments();
        topItems = args.getStringArray(EXTRA_TOP_ITEMS);

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iosdialog, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        llTopContent = (LinearLayout) view.findViewById(R.id.llTopContent);
        btnPositive = (AppCompatButton) view.findViewById(R.id.btnPositive);
        btnNegative = (AppCompatButton) view.findViewById(R.id.btnNegative);

        for (int i = 0; i < topItems.length; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            AppCompatButton button = new AppCompatButton(getContext());
            button.setTag(topItems[i]);
            button.setLayoutParams(params);
            button.setText(topItems[i]);
            llTopContent.addView(button, i);
        }
    }
}
