package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductRangeAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;


public class ProductInfoFragment extends Fragment  implements AsyncResponse{

    Context context;

    TextView productName, productColor, companyName, gender, productPrice, productCheap;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;
    Array ranges;




    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_details, null);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        Bundle getProInfoBundle = getArguments();

        Product productInfo = null;
        if (getProInfoBundle != null) {
            productInfo = (Product) getProInfoBundle.getSerializable("proInfo");
        }


        SelectProductRangeAsync selectProductRangeAsync = new SelectProductRangeAsync();
        selectProductRangeAsync.execute();
        selectProductRangeAsync.delegate = this;

        productName = view.findViewById(R.id.productNameTV_productDetails);
        productColor = view.findViewById(R.id.productColorTV_productDetails);
        companyName = view.findViewById(R.id.companyNameTV_productDetails);
        gender = view.findViewById(R.id.genderTV_productDetails);
        productPrice = view.findViewById(R.id.productPriceTV_productDetails);
        productCheap = view.findViewById(R.id.productCheapTV_productDetails);
        productPicture = view.findViewById(R.id.productIV_productDetails);

        rangeSpinner = view.findViewById(R.id.rangeSpinner_productDetails);
        if (productInfo != null) {
            productName.setText(productInfo.getProName());
            productColor.setText(productInfo.getProColor());
            companyName.setText(productInfo.getCompName());
            gender.setText(productInfo.getGender());
            productPrice.setText(productInfo.getProPrice());
            // if the product don't has cheap it's will disappear from the xml
            if (productInfo.getCheap()  == 0 ){
                productCheap.setText("");
            }else {
                // this is the format for the double
                productCheap.setText(String.format("הנחה ב %.2f", productInfo.getCheap()));
            }
            Bitmap bm = StringToBitmap(productInfo.getProPic()); // the string we got from the  object
            productPicture.setImageBitmap(bm);

        }

//        ArrayAdapter<Product> adapter = new ArrayAdapter<>(
//                context ,android.R.layout.simple_spinner_item, ranges);
//        rangeSpinner.setAdapter(adapter);


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


    @Override
    public void processFinish(String outPut) {

        readRangeJson(outPut);
    }

    public int readRangeJson(String outPut) {
        try {
            JSONArray ary = new JSONArray(outPut);
            for (int i =0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);

                Product productRange = new Product(object.getInt("range"));
//                ranges = productRange.getRange();

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private Bitmap StringToBitmap(String encodedString){

        byte[] encodeByte = Base64.decode(encodedString,  Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }
}
