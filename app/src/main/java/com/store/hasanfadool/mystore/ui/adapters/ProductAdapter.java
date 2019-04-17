package com.store.hasanfadool.mystore.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.ProductInfoFragment;
import com.store.hasanfadool.mystore.fragments.app.ProductsListFragment;
import com.store.hasanfadool.mystore.models.Product;

import java.util.List;
import java.util.Objects;

public class ProductAdapter extends BaseAdapter {

    private static final String TAG = "ProductsAdapter";

    Context context;
    List<Product> productList;


    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint({"SetTextI18n", "DefaultLocale"})
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        final Product currentProduct = productList.get(position);
        @SuppressLint("ViewHolder") View cellLayout = LayoutInflater.from(context).inflate(R.layout.product_item, parent,false);


        TextView productName = cellLayout.findViewById(R.id.productNameTV_productItem);
        TextView productPrice = cellLayout.findViewById(R.id.productPriceTV_productItem);
        TextView productCheap = cellLayout.findViewById(R.id.productCheapTV_productItem);
        TextView shipping = cellLayout.findViewById(R.id.shippingTV_productItem);
        ImageView productImage = cellLayout.findViewById(R.id.productImageView_productItem);

        productName.setText(currentProduct.getProName());
        productPrice.setText(currentProduct.getProPrice() + "₪");
                        // if the product don't has cheap it's will disappear from the xml
        if (currentProduct.getCheap()  == 0 ){
            productCheap.setText("");
        }else {
            // this is the format for the double
        productCheap.setText(String.format("הנחה ב %.2f", currentProduct.getCheap()));
        }
        shipping.setText(currentProduct.getShipping() + "₪");

        Bitmap bm = StringToBitmap(currentProduct.getProPic()); // the string we got from the json object
        productImage.setImageBitmap(bm); // function to encode the string

        cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "hii" + currentProduct.getProName(), Toast.LENGTH_SHORT).show();

                ProductInfoFragment productInfoFragment = new ProductInfoFragment();
                initFragment(productInfoFragment);
            }
        });


        return cellLayout;

}
            // encode the image(String) to base64
    @Nullable
    private Bitmap StringToBitmap(String encodedString){

            byte[] encodeByte = Base64.decode(encodedString,  Base64.DEFAULT);
            return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
}

    private void initFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = null;
        if (fragment.getFragmentManager() != null) {
            fragmentTransaction = fragment.getFragmentManager().beginTransaction();
        }
        assert fragmentTransaction != null;
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();

    }

}
