package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;

import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class CheckUserAsync extends AsyncTask<Void,Void,String> {



    public AsyncResponseString resultInterFace = null;

      User myUser;

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private  final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "compareUserMailPass";
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME;



    @Override
    protected String doInBackground(Void... voids) {

            if (myUser.getUserMail() != null && myUser.getUserPass() != null) {
                try {
                    SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                    //1 userMail
                    PropertyInfo proMail = new PropertyInfo();
                    proMail.setName("userMail");
                    proMail.setValue(myUser.getUserMail());
                    proMail.setType(PropertyInfo.STRING_CLASS);
                    request.addProperty(proMail);

                    //2 userPass
                    PropertyInfo proPass = new PropertyInfo();
                    proPass.setName("userPass");
                    proPass.setValue(myUser.getUserPass());
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

            return "error: the user not send yet!";

    }


    @Override
    protected void onPostExecute(String jsonResult) {
        super.onPostExecute(jsonResult);
        if (jsonResult != null && !jsonResult.isEmpty()){
            resultInterFace.processFinish(jsonResult);
        }
    }

    public void setUser(User myUser){
        this.myUser = myUser;
    }
}
