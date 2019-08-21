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

public class SelectShoppingCardAsync extends AsyncTask<Void,Void,String> {

    public AsyncResponseString delegate = null;

    private GetDomin getDomin = new GetDomin(); // to get the ip and port from/ GetDomin class
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "getAllProducts";
    private static final String SOAP_ACTION = "http://it.pro.com/getAllProducts";

    private Context context;
    private Loader loader;

    private String uMail;

    public SelectShoppingCardAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");
    }

    @Override
    protected void onPreExecute() {
        loader.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if(uMail != null){
            try{
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                PropertyInfo proMail = new PropertyInfo();
                proMail.setName("userMail");
                proMail.setValue(uMail);
                proMail.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proMail);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE ht =new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return "error";
    }


    @Override
    protected void onPostExecute(String s) {
        loader.dismiss();
        if (s != null && !s.equals("error")){
            delegate.processFinish(s);
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }

    public void setUMail (String mail){
        this.uMail = mail;
    }
}
