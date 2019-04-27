package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;
import android.os.Bundle;

import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectUserMailAsync extends AsyncTask<Void,Void,String> {

    private Bundle getPhoneBundle = new Bundle();
    private String getPhoneSt = getPhoneBundle.getString("");

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private  final String url = myIp + "/insertProductDataWS/InsertWS?WSDL"; // http://localhost:8080/MyWorkerApp/RegisterVIP?WSDL

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "insertProductsWS"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP



    @Override
    protected String doInBackground(Void... voids) {

        if (getPhoneSt != null){
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    // send the phone number
                PropertyInfo proPhone = new PropertyInfo();
                proPhone.setName("userPhone");
                proPhone.setValue(getPhoneSt);
                proPhone.setType(PropertyInfo.STRING_CLASS);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return "error";
    }
}
