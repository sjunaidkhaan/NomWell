package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zai.nomwell.R;
import com.zai.nomwell.util.Util;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellMultipleChoiceListDialog {

    private View view;

    private String message;
    private String[] options;

    private AppCompatTextView txtMessage;
    private ListView listOptions;

    public NomwellMultipleChoiceListDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_message_multiple_choice_list, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        txtMessage.setVisibility(View.GONE);
        listOptions = (ListView) view.findViewById(R.id.listOptions);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        txtMessage.setVisibility(message != null ? View.VISIBLE : View.GONE);
        txtMessage.setText(message);
        this.message = message;
    }

    public String[] getOptions() {
        return options;
    }

    public void setMultipleOptions(String[] options, AdapterView.OnItemClickListener listener) {
        listOptions.setAdapter(new ArrayAdapter<>(view.getContext(),
                R.layout.item_filter,
//                android.R.layout.simple_list_item_multiple_choice,
                options));
        this.options = options;
        for (int i = 0; i < options.length; i++) {
            listOptions.setItemChecked(i, false);
        }
        listOptions.setOnItemClickListener(listener);
    }

    public void setChecked(int position, boolean checked) {
        Util.log("NomwellMultipleChoiceList", position + ":" + checked);
        listOptions.setItemChecked(position, checked);
    }

    public boolean isChecked(int position) {
        return listOptions.isItemChecked(position);
    }

    public SparseBooleanArray getCheckedItems() {
        return listOptions.getCheckedItemPositions();
    }
}
