package com.store.hasanfadool.mystore.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;

public class ProductSearch extends Fragment {

    Context context;
    AutoCompleteTextView searchText;

    String[] productName;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_product, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        searchText = view.findViewById(R.id.searchET_searchProduct); // the AutoCompleteTextView

        Bundle getProductData = getArguments(); // get the
        if (getProductData != null) {

            productName = getProductData.getStringArray("productName");
        }

            ArrayAdapter<String>adapter;
            if (productName != null) {
                adapter = new ArrayAdapter<>(context, android.R.layout.simple_dropdown_item_1line, productName);


            searchText.setThreshold(1);
            searchText.setAdapter(adapter);


        }







    }
}
