package com.store.hasanfadool.mystore.fragments.user;

import android.content.Context;
import android.content.SharedPreferences;
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
import com.store.hasanfadool.mystore.network.AsyncTasks.inserts.CheckUser;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectUserPasswordAsync;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignInUser extends Fragment {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

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
                  // it's O.K


                        // save the values

                    Bundle sendMail = new Bundle();
                    sendMail.putString("UserMAil", mail.getText().toString());

                    Bundle sendPass = new Bundle();
                    sendPass.putString("userPAss", pass.getText().toString());

                    CheckUser checkUser = new CheckUser();
                    checkUser.execute();

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
                String m = readMailFromFile().toString();

                Bundle sendMAilBundle = new Bundle();
                sendMAilBundle.putString("mailInBundle", m);


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

}
