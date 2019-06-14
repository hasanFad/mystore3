package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductRangeAsync;
import com.store.hasanfadool.mystore.network.AsyncTasks.updates.UpdatingQuantityAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductInfoFragment extends Fragment  implements AsyncResponseString {

    Context context;
            // from the xml
    TextView productName, productColor,companyName, gender, productPrice, productCheap, finalPrice, shipp;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;

    Double proFinalPrice; // the final price value
    List<String> proRanges; // the list will be into the spinner
    Product productRange, iProduct, p; // productRange is the object we get from the last class / iProduct the object we get from the WS

    String myRange;
    int myQuantity;


    FragmentManager fragmentManager;

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
        fragmentManager = getFragmentManager();

        SelectProductRangeAsync selectProductRangeAsync = new SelectProductRangeAsync(); // the select product range Async class
        selectProductRangeAsync.setProCode(iProduct.getProCode()); // send product code to method at the Async class
        selectProductRangeAsync.execute(); // do the Async
        selectProductRangeAsync.delegate = this; // the listener

        proRanges = new ArrayList<>(); // init the list
        proRanges.add("בחר");

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

                // set the product object elements
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

        ArrayAdapter<String> adapter = new ArrayAdapter<>(context ,android.R.layout.simple_spinner_dropdown_item, proRanges); //  adapter for the spinner
        rangeSpinner.setAdapter(adapter);

        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position != 0){
                    myRange = String.valueOf(parent.getItemAtPosition(position)); // save the value at the position
                    Log.d("hasan", "my range is: " + myRange);
                    Log.d("hasan", "my quantity is: " + myQuantity);


                    
                }

                if (myQuantity == 0){ //  check if don't have quantity will not choose this range
                    parent.setSelection(0);
                    Toast.makeText(context, R.string.noQuantity, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        productPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "the picture is clicked" + productName.getText().toString(), Toast.LENGTH_SHORT).show();

                ProductPicturesFragment productPicturesFragment = new ProductPicturesFragment();
                productPicturesFragment.setProductCodeToGetPictures(iProduct.getProCode());
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productPicturesFragment);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();

            }
        });


                // go to buy page
        goToBayPage = view.findViewById(R.id.goToBayPageButton_productDetails);
        goToBayPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        // go to page for pay will minos 1 from he quantity

                int newQuantity = productRange.getQuantity() - 1;
                Product newQuantit = new Product(productRange.getProCode(),Integer.parseInt(myRange),newQuantity);
                Toast.makeText(context, "הכמות שנשאר לאותו מידה היא: " + newQuantity, Toast.LENGTH_SHORT).show();

                UpdatingQuantityAsync updatingQuantityAsync = new UpdatingQuantityAsync();
                updatingQuantityAsync.setQuantity(newQuantit);
                updatingQuantityAsync.execute();

            }
        });
    }


        // method to calculate the final price if we have cheap and price
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
    public List<String> readRangeJson(String outPut) {

        try {
            JSONArray ary = new JSONArray(outPut);
            for (int i =0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);
                    // set the object at product object
                productRange = new Product(object.getString("productCode"),object.getInt("productRangeOfDimensions"),object.getInt("quantity"));
                    // set the product range we get to the list of ranges
                proRanges.add(String.valueOf(productRange.getRange())); // save the range
                myQuantity = productRange.getQuantity(); // save the quantity
            }
                setProRange(proRanges); //  method to set the ranges we have to proRanges list

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return proRanges;
    }
        //et the ranges we have to proRanges list
    private void setProRange(List<String> proRanges) {
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
        Log.d("productInfo", "my: " + iProduct.getProCode());
    }



}
