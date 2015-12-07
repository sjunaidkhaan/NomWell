package com.zai.nomwell.dialog;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.zai.nomwell.R;

/**
 * Created by chitta on 12/6/15.
 */
public class NomwellListDialog {

    private View view;

    private String message;
    private String[] options;

    private AppCompatTextView txtMessage;
    private ListView listOptions;

    public NomwellListDialog(Context context) {
        this.view = LayoutInflater.from(context).inflate(R.layout.dialog_message_list, null);
        init();
    }

    public View getView() {
        return view;
    }

    private void init() {
        txtMessage = (AppCompatTextView) view.findViewById(R.id.txtMessage);
        listOptions = (ListView) view.findViewById(R.id.listOptions);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        txtMessage.setText(message);
        this.message = message;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        listOptions.setAdapter(new ArrayAdapter<>(view.getContext(),
                R.layout.simple_text_item_1,
                options));
        this.options = options;
    }

    public void setItemClickListener(AdapterView.OnItemClickListener listener) {
        listOptions.setOnItemClickListener(listener);
    }
}
