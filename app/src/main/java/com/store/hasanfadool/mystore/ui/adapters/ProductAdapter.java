package com.store.hasanfadool.mystore.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.DetailsFragment;
import com.store.hasanfadool.mystore.models.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private static final String TAG = "ProductsAdapter";

    Context context;
    List<Product> productList;

    private TextView proNameTV, proPriceTV, cheapTV, shippingTV;
    private ImageView proImg;
    private CheckBox favorite;
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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getting the product at the current position
        final Product currentProduct = productList.get(position);
        // inflating the cell layout from the xml file
        @SuppressLint("ViewHolder") View celPLayout = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        // get the xml views
         proNameTV = celPLayout.findViewById(R.id.productNameTV_productItem);
         proPriceTV = celPLayout.findViewById(R.id.productPriceTV_productItem);
         cheapTV = celPLayout.findViewById(R.id.productCheapTV_productItem);
         shippingTV = celPLayout.findViewById(R.id.shippingTV_productItem);
         favorite = celPLayout.findViewById(R.id.favoriteProductCB_productItem);
         proImg = celPLayout.findViewById(R.id.productImageView_productItem);

        // update the text and the img in the current pro
        proNameTV.setText(currentProduct.getProName());
        proPriceTV.setText(currentProduct.getProPrice() + " ₪");

        cheapTV.setText(currentProduct.getCheap() +" %");
        shippingTV.setText(currentProduct.getShipping() + " ₪");

        // the decode of the picture to base 64 at the Product class
        proImg.setImageBitmap(currentProduct.getProPic());




        return celPLayout;

    }
}
