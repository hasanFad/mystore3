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

    TextView productName, productColor,companyName, gender, productPrice, productCheap, finalPrice, shipp;
    ImageView productPicture;
    Spinner rangeSpinner;
    Button goToBayPage;

    Product productRange;
    Product iProduct;

    Double proFinalPrice;
    List<Integer> proRanges;






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



        SelectProductRangeAsync selectProductRangeAsync = new SelectProductRangeAsync();
        selectProductRangeAsync.setProCode(iProduct.getProCode());
        selectProductRangeAsync.execute();
        selectProductRangeAsync.delegate = this;

        proRanges = new ArrayList<>();

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
                // this is the format for the double
                getFinalPrice(iProduct.getCheap(), (double) iProduct.getProPrice());
                productCheap.setText(String.format("הנחה ב %.2f", iProduct.getCheap()));
                finalPrice.setText(String.format("%.2f", proFinalPrice));

               // getFinalPrice();
            //   finalPrice.setText((int) proFinalPrice);
            }
            if (iProduct.getShipping() == 0 ){
                shipp.setText(getString(R.string.freeShipping));
            }else {
                shipp.setText("משלוח ₪" + iProduct.getShipping());
            }
            Bitmap bm = StringToBitmap(iProduct.getProPic()); // the string we got from the  object
            productPicture.setImageBitmap(bm);




        ArrayAdapter<Integer> adapter = new ArrayAdapter<>(context ,android.R.layout.simple_spinner_item, proRanges);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rangeSpinner.setAdapter(adapter);

        rangeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int index = rangeSpinner.getSelectedItemPosition();
                Log.d("hasan", "my select " + index);
                Toast.makeText(context, "my select " + index, Toast.LENGTH_SHORT).show();
                parent.setSelection(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                parent.getFirstVisiblePosition();
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





    private void getFinalPrice(Double cheap, Double price) {
        Double correctPrice;
        correctPrice = 1.0 - (cheap) ;
        proFinalPrice = correctPrice * price;
    }


    @Override
    public void processFinish(String outPut) {
        readRangeJson(outPut);
    }

    public List<Integer> readRangeJson(String outPut) {

        try {
            JSONArray ary = new JSONArray(outPut);
            for (int i =0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);

                productRange = new Product(object.getString("productCode1"),object.getInt("productRangeOfDimensions"),object.getInt("quantity"));
Log.d("hasan ", "my code :" + productRange.getProCode() + " productRangeOfDimensions " + productRange.getRange() + " quantity " + productRange.getQuantity());


                proRanges.add(productRange.getRange());


            }
                setProRange(proRanges);

        } catch (JSONException e) {
            e.printStackTrace();
        }
            return proRanges;
    }

    private void setProRange(List<Integer> proRanges) {
        this.proRanges = proRanges;
    }


    private Bitmap StringToBitmap(String encodedString){

        byte[] encodeByte = Base64.decode(encodedString,  Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
    }

    public void setiProduct(Product iProduct){
        this.iProduct = iProduct;
    }

}
