package com.store.hasanfadool.mystore.network.AsyncTasks.inserts;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseInteger;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.GetDomin;
import com.store.hasanfadool.mystore.utils.Loader;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class InsertNewUserAsync extends AsyncTask<Void,Void,Integer> {

    // 12 param-->  userFName userLName userEmail  userPhone  userCity
    // userStreet userhomeNumber userPostelCode  userPOpost userPass agreeSms agreeMail

//    the object not null, i was check it with log but at the WS we got the userFName is null

    public User newUser;

    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private final  String url = myIp + "/insertDataWS/InsertWS?WSDL";
    private static final String NAMESPACE = "http://it.pro.com/";
    private static final String METHOD_NAME = "insertUserWS";
    private static final String SOAP_ACTION = "http://it.pro.com/insertUserWS";

    private Context context;
    private Loader loader;


    public InsertNewUserAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");

    }



    @Override
    protected void onPreExecute() {
        loader.show();
    }


    public AsyncResponseInteger asyncResponseInteger = null;
    @Override
    protected Integer doInBackground(Void... voids) {
        if (newUser.getUserName() != null){
            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                //1 userFName
                PropertyInfo proFName = new PropertyInfo();
                proFName.setName("userFName");
                proFName.setValue(newUser.getUserName());
                proFName.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proFName);

                //2
                PropertyInfo proLName = new PropertyInfo();
                proLName.setName("userLName");
                proLName.setValue(newUser.getUserLName());
                proLName.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proLName);

                //3
                PropertyInfo proMail = new PropertyInfo();
                proMail.setName("userEmail");
                proMail.setValue(newUser.getUserMail());
                proMail.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proMail);

                //4
                PropertyInfo proUPhone = new PropertyInfo();
                proUPhone.setName("userPhone");
                proUPhone.setValue(newUser.getUserPhone());
                proUPhone.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proUPhone);

                //5
                PropertyInfo proCity = new PropertyInfo();
                proCity.setName("userCity");
                proCity.setValue(newUser.getUserCity());
                proCity.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proCity);

                //6
                PropertyInfo proUStreet = new PropertyInfo();
                proUStreet.setName("userStreet");
                proUStreet.setValue(newUser.getuStreet());
                proUStreet.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proUStreet);

                //7
                PropertyInfo proUHNumber = new PropertyInfo();
                proUHNumber.setName("userhomeNumber");
                proUHNumber.setValue(newUser.getuHomeNum());
                proUHNumber.setType(PropertyInfo.INTEGER_CLASS);
                request.addProperty(proUHNumber);

                //8
                PropertyInfo proPostelCode = new PropertyInfo();
                proPostelCode.setName("userPostelCode");
                proPostelCode.setValue(newUser.getuPstelCode());
                proPostelCode.setType(PropertyInfo.INTEGER_CLASS);
                request.addProperty(proPostelCode);

                //9
                PropertyInfo proPOPost = new PropertyInfo();
                proPOPost.setName("userPOpost");
                proPOPost.setValue(newUser.getuPO_post());
                proPOPost.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proPOPost);

                //10
                PropertyInfo proPass = new PropertyInfo();
                proPass.setName("userPassword");
                proPass.setValue(newUser.getUserPass());
                proPass.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proPass);

                //11
                PropertyInfo proSMS = new PropertyInfo();
                proSMS.setName("smsAgree");
                proSMS.setValue(newUser.getSmsAgree());
                proSMS.setType(PropertyInfo.INTEGER_CLASS);
                request.addProperty(proSMS);

                //12
                PropertyInfo pro_mail = new PropertyInfo();
                pro_mail.setName("mail_agree");
                pro_mail.setValue(newUser.getMailAgree());
                pro_mail.setType(PropertyInfo.INTEGER_CLASS);
                request.addProperty(pro_mail);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);
                envelope.dotNet = false;

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();


                return Integer.parseInt(response.toString());
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return -1;
    }

    @Override
    protected void onPostExecute(Integer s) {
        loader.dismiss();
        if (s != null){
            asyncResponseInteger.processFinishInt(s); // here nullPointerException
        }else {
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();
        }
    }

    public void setUser(User newUser) {
        this.newUser = newUser;
    }
}
