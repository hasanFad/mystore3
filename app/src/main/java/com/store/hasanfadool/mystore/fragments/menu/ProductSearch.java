package com.store.hasanfadool.mystore.fragments.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.ProductInfoFragment;
import com.store.hasanfadool.mystore.models.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductSearch extends Fragment {

    Context context;
    AutoCompleteTextView searchText;

    FragmentManager fragmentManager;


    List<Product> myProduct; // the array of products


    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_product, null);
    }


    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();
        searchText = view.findViewById(R.id.searchET_searchProduct); // the AutoCompleteTextView

        final ArrayAdapter<String> adapter;


            final String[] productsNames = new String[myProduct.size()]; // init the string array to product object length

        for (int i =0;i<myProduct.size(); i++){

            productsNames[i] = myProduct.get(i).getProName(); // add product name from products object to string array
        }


        adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, productsNames);
        searchText.setThreshold(1); // length of search from products names e
        searchText.setAdapter(adapter);

        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() { // click at the request from search to get the product info
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "my name is /" + adapter.getItem(position) , Toast.LENGTH_SHORT).show();

                Bundle sendProductInBundle = new Bundle(); // bundle to sent the object
                sendProductInBundle.putSerializable("myPro", myProduct.get(position)); // set the product object into the bundle

                ProductInfoFragment productInfoFragment = new ProductInfoFragment(); // init the product information fragment
                productInfoFragment.setArguments(sendProductInBundle); // set the object at the fragment like a bundle

                    // init the productInfoFragment
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, productInfoFragment);
                fragmentTransaction.addToBackStack("fragment");
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.commit();
            }
        });


    }


    public void setProductsName(ArrayList<Product> myProduct){ // to set the products object from the product adapter
        this.myProduct = myProduct;
    }



}
