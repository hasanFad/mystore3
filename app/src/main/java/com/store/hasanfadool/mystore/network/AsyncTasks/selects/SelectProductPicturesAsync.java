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

public class SelectProductPicturesAsync extends AsyncTask<Void,Void, String> {


    public AsyncResponse delegate = null;

    String productCode;
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "getProductPictures";
    private static final String SOAP_ACTION = "http://it.pro.com/getProductPictures";


    @Override
    protected String doInBackground(Void... voids) {
        if (productCode != null){
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // product code
                PropertyInfo proCode = new PropertyInfo();
                proCode.setName("productCode");
                proCode.setValue(productCode);
                proCode.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proCode);

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

    // send the response.toString() to the interface
    @Override
    protected void onPostExecute(String st) {
        super.onPostExecute(st);
        if (st != null && !st.isEmpty()){
            delegate.processFinish(st);
        }
    }

    public void setProductCode(String productCode){
        this.productCode = productCode;
    }



}
