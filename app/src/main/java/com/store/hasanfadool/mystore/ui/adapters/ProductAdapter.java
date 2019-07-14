package com.store.hasanfadool.mystore.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.ProductInfoFragment;
import com.store.hasanfadool.mystore.fragments.app.ProductPicturesFragment;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.utils.Converts;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    private static final String TAG = "ProductsAdapter";

      private Context context;
      private List<Product> productList;
      private FragmentManager fragmentManager;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ProductAdapter(FragmentManager fragmentManager,Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.fragmentManager = fragmentManager;
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

        final TextView productName = cellLayout.findViewById(R.id.productNameTV_productItem);
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

        Converts converts = new Converts(); // to convert strings to bitmap
        final Bitmap bm = converts.StringToBitmap(currentProduct.getProPic()); // the string we got from the json object
        productImage.setImageBitmap(bm); // function to encode the string
            // on click from the image go to product images
        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "the picture is clicked " + productName.getText().toString(), Toast.LENGTH_SHORT).show();

                ProductPicturesFragment productPicturesFragment = new ProductPicturesFragment();
                productPicturesFragment.setProductCodeToGetPictures(currentProduct.getProCode(),bm);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productPicturesFragment);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });
            // click to go to productInfoFragment -> information about the product
        cellLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 // Product(String productName, String productColor, String companyName, String gender, int productPrice, double productCheap, int shipping ,String productPicture)
                ProductInfoFragment productInfoFragment = new ProductInfoFragment();
                productInfoFragment.setiProduct(currentProduct);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productInfoFragment);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });

        return cellLayout;

    }




}

