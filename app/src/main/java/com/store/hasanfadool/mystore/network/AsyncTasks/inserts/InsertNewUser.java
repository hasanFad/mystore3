package com.store.hasanfadool.mystore.network.AsyncTasks.inserts;

import android.content.Context;
import android.os.AsyncTask;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

public class InsertNewUser extends AsyncTask<Void,Void,String> {

    Context context;


    public AsyncResponse delegate = null;

    private static final String NAMESPACE = "";
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private final  String URL = myIp + "/insert";
    private static final String METHOD_NAME = "";
    private static final String SOAP_ACTION = "";


    @Override
    protected String doInBackground(Void... voids) {
        return null;
    }
}
