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

public class SelectProductRangeAsync extends AsyncTask<Void,Void,String> {


    public AsyncResponseString delegate = null;

     String productCode;
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();
    private final String url = myIp + "/Selects/Selects?WSDL";

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private static final String METHOD_NAME = "getProductRanges"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP

    private Context context;
    private Loader loader;


    public SelectProductRangeAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");

    }

    @Override
    protected void onPreExecute() {
        loader.show();
    }

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
        loader.dismiss();
        if (jsonString != null && !jsonString.equals("error")){
            delegate.processFinish(jsonString);
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }

    public void setProCode(String productCode){
        this.productCode = productCode;
    }

}
