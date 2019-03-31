package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectUserPasswordAsync extends AsyncTask<Void,Void,String> {

    public AsyncResponse delegate = null;


    private static final String NAMESPACE = "http://it.pro.com/";

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private final String URL = myIp + "";
    private static final String METHOD_NAME = "";
    private static final String SOAP_ACTION = "";

    @Override
    protected String doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(NAMESPACE,METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(URL);
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        return "";
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null && !s.isEmpty()){
            delegate.processFinish(s);
        }
    }
}
