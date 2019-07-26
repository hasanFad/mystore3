package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

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

public class SelectOrdersNumAsync extends AsyncTask<Void,Void,String > {
    private static final String TAG = "SelectOrdersNumAsync";

    public AsyncResponseString delegate = null;

    private static final String NAMESPACE = "http://it.pro.com/";

    private GetDomin getDomin = new GetDomin(); // to get the ip and port from/ GetDomin class
    private String myIp = getDomin.myIpPort();

    private final String URL = myIp + "/Selects/Selects?WSDL";
    private static final String METHOD_NAME = "getLastOrderNumber";
    private static final String SOAP_ACTION = "http://it.pro.com/getAllProducts";

    private Context context;
    private Loader loader;



    public SelectOrdersNumAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");
    }


    @Override
    protected void onPreExecute() {

        loader.show();
    }


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
        loader.dismiss();
        if (jsonString != null && !jsonString.equals("error")){
            delegate.processFinish(jsonString);
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }
}
