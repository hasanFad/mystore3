package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;

import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectUserInfoAsync extends AsyncTask<Void,Void,String > {

    public AsyncResponse mySelectUserResponse = null;

    String myMail;

        private GetDomin getDomin = new GetDomin();
        private String myIp = getDomin.myIpPort();
        private  final String url = myIp + "/Selects/Selects?WSDL";

        private static final String NAMESPACE = "http://it.pro.com/";
        private static final String METHOD_NAME = "selectUserInfo";
        private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME;

    @Override
    protected String doInBackground(Void... voids) {

        if (myMail != null && !myMail.equals("")){
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                //1
                PropertyInfo proMail = new PropertyInfo();
                proMail.setName("mail");
                proMail.setValue(myMail);
                proMail.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proMail);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE httpTransportSE = new HttpTransportSE(url);
                httpTransportSE.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        return "error: the mail not send yet!";
    }

    public void setMail(String myMail){
        this.myMail = myMail;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if (s != null && !s.isEmpty()){
            mySelectUserResponse.finishProces(s);
        }
    }
}
