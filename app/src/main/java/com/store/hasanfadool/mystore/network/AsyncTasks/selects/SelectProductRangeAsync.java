package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;
import android.os.Bundle;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectProductRangeAsync extends AsyncTask<Void,Void,String> {

    private Bundle getProductCode = new Bundle();

    public AsyncResponse delegate = null;

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "";

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "insertProductsWS"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP

    @Override
    protected String doInBackground(Void... voids) {
        String productCode = "From BUNDLE";

        try{
            SoapObject rquest = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo proCode = new PropertyInfo();
            proCode.setName("productCode");
            proCode.setValue(productCode);
            proCode.setType(PropertyInfo.STRING_CLASS);
            rquest.addProperty(proCode);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(rquest);
            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(url);
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
