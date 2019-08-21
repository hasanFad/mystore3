package com.store.hasanfadool.mystore.utils;


import android.support.v7.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.store.hasanfadool.mystore.R;

public class Loader extends AlertDialog {

    public Loader(Context context){
        this(context, null);
    }


    public Loader(Context context, String msg) {
        super(context);


        View layout = LayoutInflater.from(context).inflate(R.layout.loader_layout, null, false);

        TextView msgTV = layout.findViewById(R.id.messageTextView_Loader);

        // if have the msgTV null or not
        if (msg != null) {
            msgTV.setText(msg);
        }else {
            msgTV.setVisibility(View.GONE);
        }

        // set the layout to alertDialog
        this.setView(layout);
        // don't can click outside the dialog to close it
        this.setCancelable(false);

    }

}
