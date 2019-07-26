package com.store.hasanfadool.mystore.fragments.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseInteger;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.CheckUserAsync;
import com.store.hasanfadool.mystore.sharedPrfrncs.ShPUsers;
import com.store.hasanfadool.mystore.utils.HashMD5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class SignInUser extends Fragment implements AsyncResponseInteger {


    ShPUsers shPUsers;
    Context context;
    FragmentManager fragmentManager;
    EditText mail, pass;
    Button sendData, forgetPass, newUser;
    String userPassFromFile,userMailFromFile;
    User myUser;
    CheckUserAsync checkUserAsync;

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

        checkUserAsync = new CheckUserAsync(context);
        checkUserAsync.resultInterFace = this;

        shPUsers = new ShPUsers(context);
        // Edit Text
        mail = view.findViewById(R.id.mailET_signInUser);
        pass = view.findViewById(R.id.passwordET_signInUser);
            // Buttons
        sendData = view.findViewById(R.id.signInButton_signInUser);
        forgetPass = view.findViewById(R.id.forgetPassButton_signInUser);
        newUser = view.findViewById(R.id.registerNewUser_signInUser);

        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterNewUser registerNewUser = new RegisterNewUser();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container,registerNewUser);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });

        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // send data to WS and equals at the DB

                if (mail.getText() == null || mail.getText().toString().isEmpty()){
                    Toast.makeText(context, "המיל שלך לא יכול להיות ריק", Toast.LENGTH_SHORT).show();
                }else if (mail.getText().length() < 10){
                    Toast.makeText(context, "מיל שלך לא יכול קטן מ-10 אותיות!", Toast.LENGTH_SHORT).show();

                }else {
                  // it's O.K

                    HashMD5 hashMD5 = new HashMD5();
                    String passHashed = hashMD5.hashPassword(pass.getText().toString());

                    myUser =  new User(mail.getText().toString(), passHashed);

                        // save the values to bundle to send them to check user Async

                    checkUserAsync.setUser(myUser);
                    checkUserAsync.execute();

                }


            }
        });


        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // forget password search by mail

                readMailFromFile();
                readMailFromFile();
                if (userPassFromFile != null && userMailFromFile !=null){
                    Toast.makeText(context, "your password is " + userPassFromFile, Toast.LENGTH_SHORT).show();
                    pass.setText(userPassFromFile);
                    mail.setText(userMailFromFile);

                }else {
                    // file not exist then not register yet/ the user was delete the app
                    Toast.makeText(context, "משתמש לא קיים,נא להירשם !", Toast.LENGTH_SHORT).show();
                }


            }
        });



    }





    private void goToUserPanel() {

        // the mail & pass ok now will go to user panel
        // the shared preferences will be at here
        UserPanel userPanel = new UserPanel();
        userPanel.setUser(myUser.getUserMail());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, userPanel);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }


    private void readMailFromFile() {

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

        userMailFromFile = String.valueOf(st);

        readPassFromFile();

    }

    private void readPassFromFile() {
        StringBuilder stB = new StringBuilder();
        try{
            FileInputStream fIS = context.openFileInput("userPassLocalFile.txt");
            InputStreamReader iSR = new InputStreamReader(fIS);
            char[] inputBuffer = new char[100];
            int charRead;
            while ((charRead = iSR.read(inputBuffer)) > 0){
             String read =  String.copyValueOf(inputBuffer,0,charRead);
             stB.append(read);
             inputBuffer = new char[100];
            }
            iSR.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        userPassFromFile = String.valueOf(stB);
    }

    public User getUser(){
        return this.myUser;
    }

    @Override
    public void processFinishInt(int outPut) {

        switch (outPut){
            case -1:
                // a problem with the mail& pass
                Toast.makeText(context, "בעיית תקשורת! נא נסה שינית במועד מאוחר יותר!", Toast.LENGTH_SHORT).show();

            case 0:
                // login failed
                Toast.makeText(context, "שם משתמש או סיסמה לא נכונים!", Toast.LENGTH_SHORT).show();

            case 1:
                // login is successful
                shPUsers.saveUser(myUser.getUserMail(),myUser.getUserPass());
                goToUserPanel();

        }
    }
}
