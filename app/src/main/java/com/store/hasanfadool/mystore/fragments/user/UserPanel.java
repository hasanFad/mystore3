package com.store.hasanfadool.mystore.fragments.user;

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
import android.widget.CheckBox;
import android.widget.EditText;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.ProductsListFragment;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.User;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectUserInfoAsync;
import com.store.hasanfadool.mystore.network.AsyncTasks.updates.UpdateUserInfoAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserPanel extends Fragment implements AsyncResponseString {

    // 9 param-->  userFName userLName userEmail  userPhone  userCity
    // userStreet userhomeNumber userPostelCode  userPOpost

    Context context;
    EditText userFName, userLName, userEmail, userPhone, userCity, userStreet ,userHomeNumber, userPostelCode, userPOpost;
    CheckBox smsAgree,mailAgree;

    Button cancelBtn,updateBtn;

    String  myMail, sendMail2Asnyc;
    int sms,mail;


    FragmentManager fragmentManager;
    AsyncResponseString responseAfterUpdate ;

    SelectUserInfoAsync selectUserInfo = new SelectUserInfoAsync();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_panel, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();

        userCity = view.findViewById(R.id.userCityET_userPanel);
        userFName = view.findViewById(R.id.userFNameET_userPanel);
        userLName = view.findViewById(R.id.userLNameET_userPanel);
        userEmail = view.findViewById(R.id.mailET_userPanel);
        userPhone = view.findViewById(R.id.userPhoneET_userPanel);
        userStreet = view.findViewById(R.id.userStreetET_userPanel);
        userHomeNumber = view.findViewById(R.id.userHomeNumberET_userPanel);
        userPostelCode = view.findViewById(R.id.userPostelCodeET_userPanel);
        userPOpost = view.findViewById(R.id.userPOpostET_userPanel);
        smsAgree = view.findViewById(R.id.userAgreeSMS_userPanel);
        mailAgree = view.findViewById(R.id.userAgreeMail_userPanel);
        cancelBtn = view.findViewById(R.id.cancelButton_userPanel);
        updateBtn = view.findViewById(R.id.updateButton_userPanel);

        SelectUserInfoAsync userInfoAsync = new SelectUserInfoAsync();
        userInfoAsync.setMail(myMail);
        userInfoAsync.execute();

        getTheAsyncSelected();

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the user update nothing will go to main activity

                ProductsListFragment productsListFragment = new ProductsListFragment();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productsListFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // the user update something will check what it is and send it to server


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

                    User updateUser = new User(userFName.getText().toString(),userLName.getText().toString(),
                            userEmail.getText().toString(),userPhone.getText().toString(),
                            userCity.getText().toString(),userStreet.getText().toString(),
                            Integer.parseInt(userHomeNumber.getText().toString()),Integer.parseInt(userPostelCode.getText().toString()),
                            userPOpost.getText().toString(),sms,mail);

                UpdateUserInfoAsync updateUserInfoAsync = new UpdateUserInfoAsync();
                updateUserInfoAsync.setMyUser(updateUser);
                updateUserInfoAsync.execute();
                getTheAsyncUpdate();

            }
        });

    }

    private void getTheAsyncUpdate() {
        responseAfterUpdate = this;

    }

    private void getTheAsyncSelected() {
        selectUserInfo.mySelectUserResponse = this;
    }


    public void setUser(String myMail){
        this.myMail = myMail;
    }

    @Override
    public void processFinish(String result) {
        try {
            JSONArray ary = new JSONArray(result);

            for (int i = 0;i<ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);

              User  info =  new User(object.getString("userName"),object.getString("userLNAme"), object.getString("userMAil"),
                        object.getString("userPhone"),object.getString("userCity"), object.getString("street"),
                        object.getInt("uHomeNum"), object.getInt("uPstelCode"), object.getString("uPO_post"),
                        object.getInt("smsAgree"), object.getInt("mailAgree"));

                setUserInfoAtET(info);
            }

        }catch (JSONException j){
            j.printStackTrace();
        }

    }

    public void setUserInfoAtET(User info) {
        userFName.setText(info.getUserName());
        userLName.setText(info.getUserLName());
        userEmail.setText(info.getUserMail());
        userPhone.setText(info.getUserPhone());
        userCity.setText(info.getUserCity());
        userStreet.setText(info.getuStreet());
        userHomeNumber.setText(info.getuHomeNum());
        userPostelCode.setText(info.getuPstelCode());
        userPOpost.setText(info.getuPO_post());
        if (info.getSmsAgree() == 1){
            smsAgree.isChecked();
        }
        if (info.getMailAgree() == 1){
            mailAgree.isChecked();
        }
    }


}
