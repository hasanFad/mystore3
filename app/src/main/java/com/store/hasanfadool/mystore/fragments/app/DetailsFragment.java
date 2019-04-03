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

public class DetailsFragment extends Fragment {

    Context context;

    TextView productName, productColor, companyName, gender, productPrice, productCheap;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;
    String[] ranges = {};
    String stPic;
    ArrayList<String> arrayList;

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

        Bundle getProductInfo = getArguments();
        arrayList.clear();
        arrayList =  getProductInfo.getStringArrayList("productDetails");
        arrayList = new ArrayList<>();

        for (int i = 0; i < arrayList.size(); i++){
            productName.setText(arrayList.get(0));
            productColor.setText(arrayList.get(1));
            companyName.setText(arrayList.get(2));
            gender.setText(arrayList.get(3));
            productPrice.setText(arrayList.get(4));
            productCheap.setText(arrayList.get(5));
            stPic = arrayList.get(6);
                // decode the picture
            byte[] byt = Base64.decode(stPic, Base64.DEFAULT);
            Bitmap bitmap;
            bitmap = BitmapFactory.decodeByteArray(byt,0, byt.length);

            productPicture.setImageBitmap(bitmap);
        }





        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            int index = rangeSpinner.getSelectedItemPosition();
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(context, "בחרת מידה " + ranges[index], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






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
