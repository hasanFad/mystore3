package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;
import android.util.Log;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public class SelectProductsAsync extends AsyncTask<Void, Void, String> {
    private static final String TAG = "SelectProductsAsync";

    public AsyncResponse delegate = null;

    private GetDomin getDomin = new GetDomin(); // to get the ip and port from/ GetDomin class
    private String myIp = getDomin.myIpPort();
    private final String URL = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "getAllProducts";
    private static final String SOAP_ACTION = "http://it.pro.com/getAllProducts";



    @Override
    protected String doInBackground(Void... voids) {


        Log.d(TAG, "doInBackground");


        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
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


        return "error";
        }



    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        if (jsonString != null && !jsonString.isEmpty()){
            delegate.processFinish(jsonString);

        }

    }

}
