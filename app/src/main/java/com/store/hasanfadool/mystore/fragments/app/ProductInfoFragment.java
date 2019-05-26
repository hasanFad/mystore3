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
import android.widget.AdapterView;
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

import java.util.ArrayList;
import java.util.List;


public class ProductInfoFragment extends Fragment  implements AsyncResponse {

    Context context;
            // from the xml
    TextView productName, productColor,companyName, gender, productPrice, productCheap, finalPrice, shipp;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;

    Double proFinalPrice; // the final price value
    List<Integer> proRanges; // the list will be into the spinner
    Product productRange, iProduct; // productRange for the object we get from the last class / iProduct the object we get from the WS


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

        SelectProductRangeAsync selectProductRangeAsync = new SelectProductRangeAsync(); // the select product range Async class
        selectProductRangeAsync.setProCode(iProduct.getProCode()); // send product code to method at the Async class
        selectProductRangeAsync.execute(); // do the Async
        selectProductRangeAsync.delegate = this; // the listener

        proRanges = new ArrayList<>(); // init the list
                // connected the view to the xml
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

        //  the object we get from the last class is:
        //  Product(String proCode,String productName, String productColor, String companyName, String gender,
        //  int productPrice, double productCheap, int shipping ,String productPicture)

                // set the product object elemints
            productName.setText(iProduct.getProName());
            productColor.setText(iProduct.getProColor());
            companyName.setText(iProduct.getCompName());
            gender.setText(iProduct.getGender());
            productPrice.setText(iProduct.getProPrice()  + "₪");
            // if the product don't has cheap it's will disappear from the xml
            if ((iProduct.getCheap() == 0.00 )){
                productCheap.setText("");
                finalPrice.setText("");
            }else {
                // if we have a cheap and price
                // the format of double "%.2f"
                getFinalPrice(iProduct.getCheap(), (double) iProduct.getProPrice()); // sent product cheap & price to considered a math operation to get the final price
                productCheap.setText(String.format("הנחה ב %.2f", iProduct.getCheap())); // set a text of cheap
                finalPrice.setText(String.format("%.2f", proFinalPrice)); // set the final price after math operation method

            }
            if (iProduct.getShipping() == 0 ){
                shipp.setText(getString(R.string.freeShipping));
            }else {
                shipp.setText("משלוח ₪" + iProduct.getShipping());
            }
            Bitmap bm = StringToBitmap(iProduct.getProPic()); // send the string we got from the object to encoding it at method
            productPicture.setImageBitmap(bm); // set the picture after encoded it

        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context ,android.R.layout.simple_spinner_item, proRanges); //  adapter for the spinner
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // set drop down of the spinner
        rangeSpinner.setAdapter(adapter);

        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rangeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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


        // method to calculate the final price if we have chap and price
    private void getFinalPrice(Double cheap, Double price){
        Double correctPrice;
        correctPrice = 1.0 - (cheap) ; // get the left after cheap
        proFinalPrice = correctPrice * price;
    }

        // the listener
    @Override
    public void processFinish(String outPut) {
        readRangeJson(outPut);
    }

            // set the ranges we get to list of integer
    public List<Integer> readRangeJson(String outPut) {

        try {
            JSONArray ary = new JSONArray(outPut);
            for (int i =0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);
                    // set the object at product object
                productRange = new Product(object.getString("productCode1"),object.getInt("productRangeOfDimensions"),object.getInt("quantity"));
                    // set the product range we get to the list of ranges
                proRanges.add(productRange.getRange());

            }
                setProRange(proRanges); //  method to set the ranges we have to proRanges list

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return proRanges;
    }
        //et the ranges we have to proRanges list
    private void setProRange(List<Integer> proRanges) {
        this.proRanges = proRanges;
    }


        // method to convert pictures from string to bitmap
    private Bitmap StringToBitmap(String encodedString){

        byte[] encodeByte = Base64.decode(encodedString,  Base64.DEFAULT);  // encode the string to base64
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length); // // set the string encode to bitmap
    }
        // method to get the object from the WS after we download from the WS with Async
    public void setiProduct(Product iProduct){
        this.iProduct = iProduct;
    }

}
