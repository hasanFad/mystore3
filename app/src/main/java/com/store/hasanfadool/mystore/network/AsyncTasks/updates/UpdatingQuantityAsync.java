package com.store.hasanfadool.mystore.network.AsyncTasks.updates;

import android.os.AsyncTask;

import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class UpdatingQuantityAsync extends AsyncTask<Void,Void,String> {
    private Product theQuantity;

    private static final String NAMESPACE = "";
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private final  String url = myIp + "/update";
    private static final String METHOD_NAME = "";
    private static final String SOAP_ACTION = "";

    @Override
    protected String doInBackground(Void... voids) {
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            PropertyInfo proCode = new PropertyInfo();
            proCode.setName("productCode");
            proCode.setValue(theQuantity.getProCode());
            proCode.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proCode);

            PropertyInfo proRange = new PropertyInfo();
            proRange.setName("range");
            proRange.setValue(theQuantity.getRange());
            proRange.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(proRange);

            PropertyInfo proQntity = new PropertyInfo();
            proQntity.setName("quantity");
            proQntity.setValue(theQuantity.getQuantity());
            proQntity.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(proQntity);

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

        return "error";
    }

    public void setQuantity(Product theQuantity){
        this.theQuantity = theQuantity;
    }
}
