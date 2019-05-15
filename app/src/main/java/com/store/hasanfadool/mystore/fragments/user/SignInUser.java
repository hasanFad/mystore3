package com.store.hasanfadool.mystore.fragments.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.CheckUserAsync;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignInUser extends Fragment implements AsyncResponse {

    Context context;
    FragmentManager fragmentManager;
    EditText mail, pass;
    Button sendData, forgetPass;

    CheckUserAsync checkUserAsync = new CheckUserAsync();

    String userPassFromFile;
    User myUser;

    Bundle sendUser = new Bundle();

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signin_user, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();


        // Edit Text
        mail = view.findViewById(R.id.mailET_signInUser);
        pass = view.findViewById(R.id.passwordET_signInUser);
            // Buttons
        sendData = view.findViewById(R.id.signInButton_signInUser);
        forgetPass = view.findViewById(R.id.forgetPassButton_signInUser);

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

                    myUser =  new User(mail.getText().toString(), pass.getText().toString());

                        // save the values

                    sendUser.putString("myUserMail", mail.getText().toString());
                    sendUser.putString("myUserPass", pass.getText().toString());


                    checkUserAsync.execute(sendUser);
                    getTheAsync();

                }


            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forget password search by mail

                readMailFromFile();
                if (userPassFromFile != null){
                    Toast.makeText(context, "your password is " + userPassFromFile, Toast.LENGTH_SHORT).show();
                    pass.setText(userPassFromFile);
                }


            }
        });



    }

    private void getTheAsync() {
        checkUserAsync.resultInterFace = this;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void processFinish(String outPut) {

        if (outPut.isEmpty()){
            mail.setTextColor(R.color.red);
            pass.setTextColor(R.color.red);
        }else {
            // the mail & pass are ok , go to user panel
            goToUserPanel();
        }

    }

    private void goToUserPanel() {

        // the mail & pass ok now will go to user panel
        // the shared prenfesses will be at here
        Toast.makeText(context, "the mail and the pass are okay", Toast.LENGTH_SHORT).show();
    }


    private void readMailFromFile() {

        StringBuilder st = new StringBuilder();
        try{
            FileInputStream fileInputStream = context.openFileInput("userPassLocalFile.txt");
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

        userPassFromFile = String.valueOf(st);

    }

}
