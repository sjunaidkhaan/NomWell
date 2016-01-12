package com.zai.nomwell.dialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellPaddedListDialog {

    private View view;

    private String[] options;

    private ListView listOptions;

    public NomwellPaddedListDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_padded_list, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        listOptions = (ListView) view.findViewById(R.id.listOptions);
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options, AdapterView.OnItemClickListener listener) {
        listOptions.setAdapter(new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_list_item_1,
                options));
        this.options = options;
        listOptions.setOnItemClickListener(listener);
    }
}
