package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.os.AsyncTask;
import android.util.Log;

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

     String productCode;
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "getProductRanges"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP


    @Override
    protected String doInBackground(Void... voids) {
        if (productCode!= null) {
            try {

                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                // product code
                PropertyInfo propCode = new PropertyInfo();
                propCode.setName("productCode");
                propCode.setValue(productCode);
                propCode.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(propCode);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }

        }
            return "error";

    }

    @Override
    protected void onPostExecute(String jsonString) {
        super.onPostExecute(jsonString);
        if (jsonString != null){
        Log.d("my codehhh", "my codehh  : " + jsonString);
            delegate.processFinish(jsonString);
        }
    }

    public void setProCode(String productCode){
        this.productCode = productCode;
    }

}
