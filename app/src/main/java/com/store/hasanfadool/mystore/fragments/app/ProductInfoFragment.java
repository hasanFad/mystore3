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
import android.util.Log;
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

import java.util.List;


public class ProductInfoFragment extends Fragment  implements AsyncResponse {

    Context context;

    TextView productName, productColor,companyName, gender, productPrice, productCheap, finalPrice, shipp;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;

    Product productRange;

    List<Product> proRanges;

    String prodName,prodColor,companyNam,gndr,productPic, proCode;
    int proShipping,prodPrice;
    double prodCheap, proFinalPrice;





    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_details, null);
    }

    @SuppressLint({"DefaultLocale", "SetTextI18n"})
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        Bundle getProInfoBundle = getArguments();

        if (getProInfoBundle != null) {
            prodName = getProInfoBundle.getString("proName");
            prodColor = getProInfoBundle.getString("proColor");
            companyNam = getProInfoBundle.getString("companName");
            gndr = getProInfoBundle.getString("gnder");
            prodPrice = getProInfoBundle.getInt("proPrice");
            prodCheap = getProInfoBundle.getDouble("productCheap");
            proShipping = getProInfoBundle.getInt("proShipping");
            productPic = getProInfoBundle.getString("productPicture");
            proCode = getProInfoBundle.getString("proCode");
        }


        SelectProductRangeAsync selectProductRangeAsync = new SelectProductRangeAsync();
        selectProductRangeAsync.execute(proCode);
        selectProductRangeAsync.delegate = this;

        productName = view.findViewById(R.id.productNameTV_productDetails);
        productColor = view.findViewById(R.id.productColorTV_productDetails);
        companyName = view.findViewById(R.id.companyNameTV_productDetails);
        gender = view.findViewById(R.id.genderTV_productDetails);
        productPrice = view.findViewById(R.id.productPriceTV_productDetails);
        finalPrice = view.findViewById(R.id.productFinalPriceTV_productDetails);
        productCheap = view.findViewById(R.id.productCheapTV_productDetails);
        productPicture = view.findViewById(R.id.productIV_productDetails);
        shipp = view.findViewById(R.id.shippingTV_productDetails);

        rangeSpinner = view.findViewById(R.id.rangeSpinner_productDetails);

        // Product(String productName, String productColor, String companyName, String gender, int productPrice, double productCheap, int shipping ,String productPicture)

            productName.setText(prodName);
            productColor.setText(prodColor);
            companyName.setText(companyNam);
            gender.setText(gndr);
            productPrice.setText(prodPrice  + "₪");
            // if the product don't has cheap it's will disappear from the xml
            if ((prodCheap == 0.00 )){
                productCheap.setText("");
                finalPrice.setText("");
            }else {
                // this is the format for the double
                getFinalPrice(prodCheap, (double) prodPrice);
                productCheap.setText(String.format("הנחה ב %.2f", prodCheap));
                finalPrice.setText(String.format("%.2f", proFinalPrice));

               // getFinalPrice();
            //   finalPrice.setText((int) proFinalPrice);
            }
            if (proShipping == 0 ){
                shipp.setText(getString(R.string.freeShipping));
            }else {
                shipp.setText("משלוח ₪" + proShipping);
            }
            Bitmap bm = StringToBitmap(productPic); // the string we got from the  object
            productPicture.setImageBitmap(bm);




       final ArrayAdapter<Integer> adapter ;

       Integer[] ranges = new Integer[proRanges.size()];

       for (int i = 0; i< proRanges.size(); i++){
           Log.d("productInfoFragment", "the ranges is : " + proRanges.get(i).getRange());
           ranges[i] = proRanges.get(i).getRange();
       }

       adapter = new ArrayAdapter<>(context ,android.R.layout.simple_spinner_item, ranges);
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



    private void getFinalPrice(Double cheap, Double price) {
        Double correctPrice;
        correctPrice = 1.0 - (cheap) ;
        proFinalPrice = correctPrice * price;
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

                productRange = new Product(object.getInt("range"));

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
