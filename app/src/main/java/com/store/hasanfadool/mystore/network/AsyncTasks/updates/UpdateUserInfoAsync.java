package com.store.hasanfadool.mystore.network.AsyncTasks.updates;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
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

public class UpdateUserInfoAsync extends AsyncTask<Void,Void,String> {

    User myUser;

    private static final String NAMESPACE = "";
    private GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private final  String url = myIp + "/insert";
    private static final String METHOD_NAME = "";
    private static final String SOAP_ACTION = "";

    AsyncResponseString responseAfterUpdate = null;

    private Context context;
    private Loader loader;

    public UpdateUserInfoAsync(Context context){
        this.context = context;
        this.loader = new Loader(context, "טוען...");

    }

    @Override
    protected void onPreExecute() {
        loader.show();
    }

    @Override
    protected String doInBackground(Void... voids) {
        if (myUser != null){
            try{
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            //1 userFName
            PropertyInfo proFName = new PropertyInfo();
            proFName.setName("userFName");
            proFName.setValue(myUser.getUserName());
            proFName.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proFName);

            //2
            PropertyInfo proLName = new PropertyInfo();
            proLName.setName("userLName");
            proLName.setValue(myUser.getUserLName());
            proLName.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proLName);

            //3
            PropertyInfo proMail = new PropertyInfo();
            proMail.setName("userEmail");
            proMail.setValue(myUser.getUserMail());
            proMail.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proMail);

            //4
            PropertyInfo proUPhone = new PropertyInfo();
            proUPhone.setName("userPhone");
            proUPhone.setValue(myUser.getUserPhone());
            proUPhone.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proUPhone);

            //5
            PropertyInfo proCity = new PropertyInfo();
            proCity.setName("userCity");
            proCity.setValue(myUser.getUserCity());
            proCity.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proCity);

            //6
            PropertyInfo proUStreet = new PropertyInfo();
            proUStreet.setName("userStreet");
            proUStreet.setValue(myUser.getuStreet());
            proUStreet.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proUStreet);

            //7
            PropertyInfo proUHNumber = new PropertyInfo();
            proUHNumber.setName("userhomeNumber");
            proUHNumber.setValue(myUser.getuHomeNum());
            proUHNumber.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(proUHNumber);

            //8
            PropertyInfo proPostelCode = new PropertyInfo();
            proPostelCode.setName("userPostelCode");
            proPostelCode.setValue(myUser.getuPstelCode());
            proPostelCode.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(proPostelCode);

            //9
            PropertyInfo proPOPost = new PropertyInfo();
            proPOPost.setName("userPOpost");
            proPOPost.setValue(myUser.getuPO_post());
            proPOPost.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proPOPost);

            //10
            PropertyInfo proPass = new PropertyInfo();
            proPass.setName("userPassword");
            proPass.setValue(myUser.getUserPass());
            proPass.setType(PropertyInfo.STRING_CLASS);
            request.addProperty(proPass);

            //11
            PropertyInfo proSMS = new PropertyInfo();
            proSMS.setName("smsAgree");
            proSMS.setValue(myUser.getSmsAgree());
            proSMS.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(proSMS);

            //12
            PropertyInfo pro_mail = new PropertyInfo();
            pro_mail.setName("mail_agree");
            pro_mail.setValue(myUser.getMailAgree());
            pro_mail.setType(PropertyInfo.INTEGER_CLASS);
            request.addProperty(pro_mail);

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

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    @Override
    protected void onPostExecute(String s) {
        loader.dismiss();
        if (s.equals("error")){
            Toast.makeText(context, context.getString(R.string.connectOurNetWork), Toast.LENGTH_SHORT).show();

        }else {
            responseAfterUpdate.processFinish(s);
        }
    }
}
