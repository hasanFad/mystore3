package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectOrdersNumAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderInfo extends Fragment implements AsyncResponseString {

    Context context;
    FragmentManager fragmentManager;
    TextView orderNum, changedOrderNum;
    Button shoppingCard, goMainProducts, userOrders;

    int userOrderNumber = 0;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.order__info, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();

        orderNum = view.findViewById(R.id.orderNumberTV_orderInfo);
        changedOrderNum = view.findViewById(R.id.changedOrderNumTV_orderInfo);
        shoppingCard = view.findViewById(R.id.shoppingCardButton_orderInfo);
        goMainProducts = view.findViewById(R.id.continueToProductsButton_orderInfo);
        userOrders = view.findViewById(R.id.userOrdersButton_orderInfo);

        SelectOrdersNumAsync selectOrdersNumAsync = new SelectOrdersNumAsync();
        selectOrdersNumAsync.execute();
        selectOrdersNumAsync.delegate = this;


        if (userOrderNumber != 0){
            changedOrderNum.setText(getString(R.string.noOrderNum));

            changedOrderNum.setText("");
//            orderNum.setText("");
        }

        shoppingCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to user shopping card
                Toast.makeText(context, "go to user shopping card", Toast.LENGTH_SHORT).show();
            }
        });

        goMainProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to main product's list
                Toast.makeText(context, "go to main product's list", Toast.LENGTH_SHORT).show();
            }
        });

        userOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to user orders
                Toast.makeText(context, "go to user orders", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public void processFinish(String outPut) {

        try{
            JSONArray ary = new JSONArray(outPut);

            for (int i =0; i<ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);

                userOrderNumber = object.getInt("lastOrderNumber");

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
