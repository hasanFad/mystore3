package com.store.hasanfadool.mystore.network.AsyncTasks.selects;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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

public class SelectProductPicturesAsync extends AsyncTask<Void,Void, String> {


    public AsyncResponseString delegate = null;

    String productCode;
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "getProductPictures";
    private static final String SOAP_ACTION = "http://it.pro.com/getProductPictures";


    private Context context;
    private Loader loader;

    public SelectProductPicturesAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");
    }

    @Override
    protected void onPreExecute() {
        loader.show();
    }

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
        loader.dismiss();

        if (st != null && !st.equals("error")){
            delegate.processFinish(st);
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }

    public void setProductCode(String productCode){
        this.productCode = productCode;
        Log.d("select", "productCode: " + productCode );
    }



}
