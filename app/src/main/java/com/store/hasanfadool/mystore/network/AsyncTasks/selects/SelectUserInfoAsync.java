package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.network.GetDomin;
import com.store.hasanfadool.mystore.utils.Loader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class SelectUserInfoAsync extends AsyncTask<Void,Void,String > {

    public AsyncResponseString mySelectUserResponse = null;

    String myMail;

        private GetDomin getDomin = new GetDomin();
        private String myIp = getDomin.myIpPort();
        private  final String url = myIp + "/Selects/Selects?WSDL";

        private static final String NAMESPACE = "http://it.pro.com/";
        private static final String METHOD_NAME = "selectUserInfo";
        private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME;

        private Context context;
        private Loader loader;

        public SelectUserInfoAsync(Context context){
            this.context = context;
            this.loader = new Loader(context, "טוען...");
        }

    @Override
    protected void onPreExecute() {
        loader.show();
    }

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
        return "error";
    }

    public void setMail(String myMail){
        this.myMail = myMail;
    }

    @Override
    protected void onPostExecute(String s) {
            loader.dismiss();
        if (s != null && !s.isEmpty() && !s.equals("error") ){
            mySelectUserResponse.processFinish(s);
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }
}
