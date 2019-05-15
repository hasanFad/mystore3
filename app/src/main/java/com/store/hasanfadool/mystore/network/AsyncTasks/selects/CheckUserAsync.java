package com.store.hasanfadool.mystore.network.AsyncTasks.selects;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CheckUserAsync extends AsyncTask<Void,Void,String> {



    public AsyncResponse resultInterFace = null;
     private User myUser ;
      @SuppressLint("StaticFieldLeak")
      private Context context;

      Bundle sendUser;

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private  final String url = myIp + "/insertProductDataWS/InsertWS?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "insertProductsWS";
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME;



    @Override
    protected String doInBackground(Void... voids) {


        if (sendUser != null) {

            String mail = sendUser.getString("myUserMail");
            String pass = sendUser.getString("myUserPass");


            if (mail != null && pass != null) {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    //1 userMail
                    PropertyInfo proMail = new PropertyInfo();
                    proMail.setName("userMail");
                    proMail.setValue(mail);
                    proMail.setType(PropertyInfo.STRING_CLASS);
                    request.addProperty(proMail);

                    //2 userPass
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
                } catch (Exception ex) {
                    ex.printStackTrace();
                }

            }
        }else {
            Toast.makeText(context, "No Bundle", Toast.LENGTH_SHORT).show();
        }
            return "error";

    }


    @Override
    protected void onPostExecute(String jsonResult) {
        super.onPostExecute(jsonResult);
        if (jsonResult != null && !jsonResult.isEmpty()){
            resultInterFace.processFinish(jsonResult);
        }
    }

    public void execute(Bundle sendUser) {
        this.sendUser = sendUser;
    }
}
