package com.store.hasanfadool.mystore.fragments.user;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectUserPasswordAsync;
import com.store.hasanfadool.mystore.network.GetDomin;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignInUser extends Fragment {


    // ET mail , pass
    //btn sendData, forgetPass, forgetMail

    EditText mail, pass;
    Button sendData, forgetPass,forgetMail;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signin_user, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
                // to get the interface
        final SelectUserPasswordAsync selectUserPasswordAsync = new SelectUserPasswordAsync();
        selectUserPasswordAsync.delegate = (AsyncResponse) this;

        final Context context = getActivity();
        // Edit Text
        mail = view.findViewById(R.id.mailET_signInUser);
        pass = view.findViewById(R.id.passwordET_signInUser);
            // Buttons
        sendData = view.findViewById(R.id.signInButton_signInUser);
        forgetPass = view.findViewById(R.id.forgetPassButton_signInUser);
        forgetMail = view.findViewById(R.id.forgetMailButton_signInUser);

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send data to WS and equals at the DB

                if (mail.getText() == null || mail.getText().toString().isEmpty()){
                    Toast.makeText(context, "המיל שלך לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
                }else if (mail.getText().length() < 10){
                    Toast.makeText(context, "מיל שלך לא יכול קטן מ-10 אותיות!", Toast.LENGTH_SHORT).show();
                }else if (pass.getText().length() < 6){
                    Toast.makeText(context, "הסיסמה שלך לא יכולה להיות פחות מ-6 אותיות", Toast.LENGTH_SHORT).show();
                }else {
                  new checkUser().doInBackground();
                }


                
            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forget password search by mail

                selectUserPasswordAsync.execute();

            }
        });

        forgetMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forget Email >> an idea the mail has in a file ?
               // readMailFromFile();
                Toast.makeText(getActivity(), "readMailFromFile", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private StringBuilder readMailFromFile() {
        Context context = getActivity();
        StringBuilder st = new StringBuilder();
        try{
            FileInputStream fileInputStream = context.openFileInput("userMailLocalFile.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);

            char[] inputBuffer = new char[100];
            int charRead;

            while ((charRead = inputStreamReader.read(inputBuffer)) > 0){
                String readString = String.copyValueOf(inputBuffer,0, charRead);
                st.append(readString);
                inputBuffer = new char[100];

            }
            inputStreamReader.close();
            // the file is exist

        }catch (IOException e){
            e.printStackTrace();
        }
        return st;
    }

    GetDomin getDomin = new GetDomin();
    private String myIp = getDomin.myIpPort();

    private static final String NAMESPACE = "http://it.pro.com/"; // http://vip_register/
    private  final String url = myIp + "/insertProductDataWS/InsertWS?WSDL"; // http://localhost:8080/MyWorkerApp/RegisterVIP?WSDL
    private static final String METHOD_NAME = "insertProductsWS"; // RegisterVIP
    private static final String SOAP_ACTION =  NAMESPACE + METHOD_NAME; // http://vip_register/RegisterVIP

    public class checkUser extends AsyncTask<Void,Void,String>{

        @Override
        protected String doInBackground(Void... voids) {

            try {
                SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

                //1
                PropertyInfo proMail = new PropertyInfo();
                proMail.setName("mail");
                proMail.setValue(mail.getText().toString());
                proMail.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proMail);

                //2
                PropertyInfo proPass = new PropertyInfo();
                proPass.setName("proPass");
                proPass.setValue(pass.getText().toString());
                proPass.setType(PropertyInfo.STRING_CLASS);
                request.addProperty(proPass);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
                envelope.setOutputSoapObject(request);

                HttpTransportSE ht = new HttpTransportSE(url);
                ht.call(SOAP_ACTION, envelope);
                SoapPrimitive response = (SoapPrimitive) envelope.getResponse();

                return response.toString();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return "error";
        }
    }

}
