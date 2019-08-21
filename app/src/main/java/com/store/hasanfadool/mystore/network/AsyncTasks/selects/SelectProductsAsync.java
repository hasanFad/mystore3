package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.network.GetDomin;
import com.store.hasanfadool.mystore.utils.Loader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;



public class SelectProductsAsync extends AsyncTask<Void, Void, String> {
    private static final String TAG = "SelectProductsAsync";

    public AsyncResponseString delegate = null;

    private GetDomin getDomin = new GetDomin();


    String myIp = getDomin.myIpPort();
    String URLSt = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "getAllProducts";
    private static final String SOAP_ACTION = "http://it.pro.com/getAllProducts";

    private Context context;
    private Loader loader;


    public SelectProductsAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");

    }



    @Override
    protected void onPreExecute() {
        loader.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        Log.d(TAG, "doInBackground + my URL is: " + URLSt);

        try {

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            Log.d(TAG, "doInBackground: the URLSt is: " + URLSt);
            HttpTransportSE ht = new HttpTransportSE(URLSt);
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();


        }catch (Exception e){
            Log.d(TAG, "doInBackground: eror" + e.getMessage());

            e.printStackTrace();

        }

        return "error";

        }

    private void checkIp(String myIpz) {

       char m = myIpz.charAt(myIpz.length() - 6);
        int s = Character.getNumericValue(m);
        Log.d(TAG, "checkIp: s is: " + s);

        for (int i = 1; i < 7; i++){
             URLSt = myIp.substring(0,17) + i + URLSt.substring(18);

             doInBackground();
            Log.d(TAG, "myIp2 is : " + myIp);
        }

    }




    @SuppressLint("WrongThread")
    @Override
    protected void onPostExecute(String jsonString) {
        loader.dismiss();

        if (jsonString != null && !jsonString.equals("error")){
            delegate.processFinish(jsonString);
        }else {
            checkIp(myIp);

            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();



        }

    }

}
