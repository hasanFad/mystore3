package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectProductRangeAsync extends AsyncTask<Void,Void,String> {


    public AsyncResponse delegate = null;
    Context context;

    String proCode;
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "";

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "insertProductsWS"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP


    @Override
    protected String doInBackground(Void... voids) {
        if (proCode != null) {
            try {


                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // product code
                PropertyInfo propCode = new PropertyInfo();
                propCode.setName("proCode");
                propCode.setValue(proCode);
                propCode.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(propCode);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                String status =  response.toString();
                if (status.isEmpty()){
                    // no user
                    Toast.makeText(context, "no product", Toast.LENGTH_SHORT).show();
                }else {
                    // product ok
                    Toast.makeText(context, "yse product", Toast.LENGTH_SHORT).show();

                return response.toString();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

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

    public void execute(String proCode) {
    this.proCode = proCode;
    }

}
