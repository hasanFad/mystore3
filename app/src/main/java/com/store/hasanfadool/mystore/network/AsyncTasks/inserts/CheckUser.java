package com.store.hasanfadool.mystore.network.AsyncTasks.inserts;

import android.os.AsyncTask;
import android.os.Bundle;

import com.store.hasanfadool.mystore.fragments.user.SignInUser;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CheckUser extends AsyncTask<Void,Void,String> {


    private Bundle getUserMail = new Bundle();
    private Bundle getUserPass = new Bundle();


    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private  final String url = myIp + "/insertProductDataWS/InsertWS?WSDL"; // http://localhost:8080/MyWorkerApp/RegisterVIP?WSDL

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "insertProductsWS"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP

    @Override
    protected String doInBackground(Void... voids) {

        String mail = getUserMail.getString("UserMAil");
        String pass = getUserPass.getString("userPAss");

        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //1
            PropertyInfo proMail = new PropertyInfo();
            proMail.setName("userMail");
            proMail.setValue(mail);
            proMail.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proMail);

            //2
            PropertyInfo proPass = new PropertyInfo();
            proPass.setName("userPass");
            proPass.setValue(pass);
            proPass.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proPass);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.setOutputSoapObject(request);
            envelope.dotNet = false;

            HttpTransportSE ht = new HttpTransportSE(url);
            ht.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

            return response.toString();
        }catch (Exception ex){
            ex.printStackTrace();
        }



        return "error";
    }
}
