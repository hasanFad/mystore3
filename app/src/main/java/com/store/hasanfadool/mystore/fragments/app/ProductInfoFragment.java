package com.store.hasanfadool.mystore.fragments.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;

import java.util.ArrayList;

public class ProductInfoFragment extends Fragment {

    Context context;

    TextView productName, productColor, companyName, gender, productPrice, productCheap;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;
    String[] ranges = {};



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_details, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        productName = view.findViewById(R.id.productNameTV_productDetails);
        productColor = view.findViewById(R.id.productColorTV_productDetails);
        companyName = view.findViewById(R.id.companyNameTV_productDetails);
        gender = view.findViewById(R.id.genderTV_productDetails);
        productPrice = view.findViewById(R.id.productPriceTV_productDetails);
        productCheap = view.findViewById(R.id.productCheapTV_productDetails);
        productPicture = view.findViewById(R.id.productIV_productDetails);

        rangeSpinner = view.findViewById(R.id.rangeSpinner_productDetails);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                context ,android.R.layout.simple_spinner_item, ranges);
        rangeSpinner.setAdapter(adapter);


                // go to buy page
        goToBayPage = view.findViewById(R.id.goToBayPageButton_productDetails);
        goToBayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        // go to page for pay
                Toast.makeText(context, getString(R.string.goT), Toast.LENGTH_SHORT).show();
            }
        });



    }


    private void initFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }


}
