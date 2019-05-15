package com.store.hasanfadool.mystore.fragments.menu;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.models.Product;

import java.util.ArrayList;
import java.util.List;


public class ProductSearch extends Fragment {

    Context context;
    AutoCompleteTextView searchText;



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

        searchText = view.findViewById(R.id.searchET_searchProduct); // the AutoCompleteTextView

        final ArrayAdapter<String> adapter;


            String[] productsNames = new String[myProduct.size()]; // init the string array to product object length

        for (int i =0;i<myProduct.size(); i++){

            Log.d("product search", myProduct.get(i).getProName());

            productsNames[i] = myProduct.get(i).getProName();
        }


        adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, productsNames);
        searchText.setThreshold(1);
        searchText.setAdapter(adapter);

        searchText.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "my name is /" + adapter.getItem(position) , Toast.LENGTH_SHORT).show();
            }
        });


    }


    public void setProductsName(ArrayList<Product> myProduct){
        this.myProduct = myProduct;
    }



}
