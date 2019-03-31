package com.store.hasanfadool.mystore.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.DetailsFragment;
import com.store.hasanfadool.mystore.models.Product;

import java.util.List;

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

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // getting the product at the current position
        final Product currentProduct = productList.get(position);
        // inflating the cell layout from the xml file
        @SuppressLint("ViewHolder") View celPLayout = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        // get the xml views
        TextView proNameTV = celPLayout.findViewById(R.id.productNameTV_productItem);
        TextView proPriceTV = celPLayout.findViewById(R.id.productPriceTV_productItem);
        ImageView proImg = celPLayout.findViewById(R.id.productImageView_productItem);

        // update the text and the img in the current pro
        proNameTV.setText(currentProduct.getProName());
        proPriceTV.setText(currentProduct.getProPrice() + " ₪");



        proImg.setImageResource(Integer.parseInt(currentProduct.getProPic()));

        celPLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // after click om the item...

                Intent toDetailsIntent = new Intent(context, DetailsFragment.class);
                toDetailsIntent.putExtra("","");
            }
        });


        return celPLayout;

    }
}
