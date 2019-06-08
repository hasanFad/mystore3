package com.store.hasanfadool.mystore.fragments.user;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseInteger;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.AsyncTasks.inserts.InsertNewUserAsync;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RegisterNewUser extends Fragment implements AsyncResponseInteger {

    final static private String TAG = "RegisterNewUser";
 // 10 param-->  userFName userLName userEmail  userPhone  userCity
    // userStreet userhomeNumber userPostelCode  userPOpost userPass

    Context context;
    EditText userFName, userLName, userEmail, userPhone, userCity, userStreet ,userHomeNumber, userPostelCode, userPOpost, userPass;
    Button sendData;

    CheckBox smsAgree,mailAgree;
    int sms, mail,userHN;
    User newUser;
    FragmentManager fragmentManager;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register_new_user, null);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();

        userFName = view.findViewById(R.id.userFNameET_registerNewUser);
        userLName = view.findViewById(R.id.userLNameET_registerNewUser);
        userEmail = view.findViewById(R.id.userEmailET_registerNewUser);
        userPhone = view.findViewById(R.id.userPhoneET_registerNewUser);
        userCity = view.findViewById(R.id.userCityET_registerNewUser);
        userStreet = view.findViewById(R.id.userStreetET_registerNewUser);
        userHomeNumber = view.findViewById(R.id.userHomeNumberET_registerNewUser);
        userPostelCode = view.findViewById(R.id.userPostelCodeET_registerNewUser);
        userPOpost = view.findViewById(R.id.userPOpostET_registerNewUser);
        userPass = view.findViewById(R.id.userPasswordET_registerNewUser);
        smsAgree = view.findViewById(R.id.userAgreeSMS_registerNewUser);
        mailAgree = view.findViewById(R.id.userAgreeMail_registerNewUser);



        sendData = view.findViewById(R.id.sendUserDataButton_registerNewUser);
        sendData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toast.makeText(context, getString(R.string.pleaseWait), Toast.LENGTH_SHORT).show();

                if (smsAgree.isChecked()){
                    sms = 1;
                }else {
                    sms = 0;
                }
                if (mailAgree.isChecked()){
                    mail = 1;
                }else {
                    mail = 0;
                }
                if (userFName.getText() ==null || userFName.getText().toString().equals("")){
                    Toast.makeText(context, "שם שלך לא יכול להיות ריק!", Toast.LENGTH_SHORT).show();
                }else if (userFName.getText().length() < 3){
                    Toast.makeText(context, "שם שלך לא יכול פחות מ-3 אותיות", Toast.LENGTH_SHORT).show();
                }else if (userFName.getText() == userLName.getText()){
                    Toast.makeText(context, "לא יכול שם ומשפחה אותו דבר!", Toast.LENGTH_SHORT).show();
                }else if (userLName.getText().length() < 1 || userLName.getText() == null){
                    Toast.makeText(context, "שם משפחה לא יכול להיות ריק!", Toast.LENGTH_SHORT).show();
                }else if (userLName.getText().length() < 3){
                    Toast.makeText(context, "שם משפחה לא יכול להיות פחות מ-3 אותיות!", Toast.LENGTH_SHORT).show();
                }else if (userEmail.getText() == null || userEmail.getText().length() < 0 ){
                    Toast.makeText(context, "המיל שלך לא יכול להיות ריק!", Toast.LENGTH_SHORT).show();
                }else if (userEmail.getText().length() < 10){
                    Toast.makeText(context, "מיל שלך לא יכול קטן מ-10 אותיות!", Toast.LENGTH_SHORT).show();
                }else if (userPhone.getText().length() < 10){
                    // the mail is O.K

                    Toast.makeText(context, "טלפון לא תקין!", Toast.LENGTH_SHORT).show();
                }else if (userCity.getText().length() < 3){
                    Toast.makeText(context, "עיר לא תקין!", Toast.LENGTH_SHORT).show();
                }else if (userStreet.getText() == null || userStreet.getText().toString().equals("")){
                    Toast.makeText(context, "השארתם שדה שד' ריק!", Toast.LENGTH_SHORT).show();
                }else if (userHomeNumber.getText().toString().equals("")){
                    Toast.makeText(context, "מס' בית ריק!", Toast.LENGTH_SHORT).show();
                }else if (userPostelCode.getText().toString().equals("")){
                    userHN  = Integer.parseInt(userHomeNumber.getText().toString());
                    Toast.makeText(context, "מס' ת.ד ריק!", Toast.LENGTH_SHORT).show();
                }else if (userPass.getText().length() < 6 ){
                    Toast.makeText(context, "הסיסמה שלך לא יכולה להיות פחות מ-6 אותיות ", Toast.LENGTH_SHORT).show();
                }else if (userPass.getText().toString().equals(userFName.getText().toString())){
                    Toast.makeText(context, "סיסמה לא יכולה להיות בדומה לשם שלך!", Toast.LENGTH_SHORT).show();
                }else{
                            // it's O.K
                    Toast.makeText(context, "ההרשמה מתבצעת..תועבר בהמשך...", Toast.LENGTH_SHORT).show();

                    writeMailToFile(userEmail.getText().toString(),userPass.getText().toString());

                    newUser = new User(userFName.getText().toString(),userLName.getText().toString(), userEmail.getText().toString(),
                            userPhone.getText().toString(), userCity.getText().toString(),
                            userStreet.getText().toString(),Integer.parseInt(userHomeNumber.getText().toString()),
                            Integer.parseInt(userPostelCode.getText().toString())
                            ,userPOpost.getText().toString(),userPass.getText().toString(),sms,mail);

                    InsertNewUserAsync insertNewUserAsync = new InsertNewUserAsync();
                    insertNewUserAsync.setUser(newUser);
                    insertNewUserAsync.execute();




                }
            }
        });
    }

    private void writeMailToFile(String mailString,String pas) {

        try{
            FileOutputStream fOut = new FileOutputStream(  "userMailLocalFile.txt");

            OutputStreamWriter opsw = new OutputStreamWriter(fOut);
            opsw.write(mailString);
            opsw.close();
            Log.d(TAG, "the mail was saved!");

            writePassToFile(pas);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writePassToFile(String pas) {
        try{
            FileOutputStream fileOutputStream = new FileOutputStream("userPassLocalFile.txt");
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
            outputStreamWriter.write(pas);
            Log.d(TAG, "the pass was saved");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

            // after send new user will get the response if the saved the user or not

    private void getResponseInsertUser(int outPut) {

        switch (outPut){
            // the user was insert to the system
            case -1:
//                the mail was insert into the system in past
                Toast.makeText(context, "המיל הזה רשום במערכת", Toast.LENGTH_SHORT).show();
                return;
            case 0:
//                we have an error at the SQL code(SQLException)
                Toast.makeText(context, "בעיית תקשורת, נסה הרשמה במועד מאוחר יותר!", Toast.LENGTH_SHORT).show();
                return;
            case 1:
//                insert the user into the system successful
            Log.d(TAG, "the user was insert into the system");
            UserPanel userPanel = new UserPanel();
            userPanel.setUserInfoAtET(newUser); // send the user information to the user panel

            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container, userPanel);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void processFinishInt(int outPut) {
        Log.d(TAG, "the response is: " + outPut);
        getResponseInsertUser(outPut);
    }
}
