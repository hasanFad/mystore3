package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.user.RegisterNewUser;
import com.store.hasanfadool.mystore.fragments.user.SignInUser;

public class AlertDialogFragment extends DialogFragment {

    TextView myText;
    String st;
    Button noBtn, yesBtn;
    Context context;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.yse_no_dialog,  null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        this.setCancelable(false); // cannot clicking outside the dialog

        Bundle getMAilBundle = getArguments();
        if (getMAilBundle != null) {
            st =  getMAilBundle.getString("mailInBundle");

        }

        myText = view.findViewById(R.id.yourMail_yesNoDialog);
        noBtn = view.findViewById(R.id.noButton_yesNoDialog);
        yesBtn = view.findViewById(R.id.yesButton_yesNoDialog);
        if (st != null) {
            myText.setText(st);

        }else {
            st = "נסה לשלוח שכחתי מיל דרך דף הרשמה";
            myText.setText(st);
        }
        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, " לא המיל שלי", Toast.LENGTH_SHORT).show();
                    // go to register page
                RegisterNewUser registerNewUser = new RegisterNewUser();
                initFragment(registerNewUser);
            }
        });

        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "כן, זהו המיל שלי", Toast.LENGTH_SHORT).show();
                    // go to sign in page and set the mail at the box Edit text
                SignInUser signInUser = new SignInUser();
                initFragment(signInUser);
            }
        });

    }


    private void initFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction;

        if (fragment.getFragmentManager() != null) {
            fragmentTransaction = fragment.getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.container, fragment);
            fragmentTransaction.addToBackStack("fragment");
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            fragmentTransaction.commit();
        } else {

            Toast.makeText(context, "getFragmentManager is null", Toast.LENGTH_SHORT).show();

        }
    }
}
