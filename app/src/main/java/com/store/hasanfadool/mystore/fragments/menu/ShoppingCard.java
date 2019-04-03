package com.store.hasanfadool.mystore.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.ui.adapters.ProductAdapter;

import java.util.ArrayList;

public class ShoppingCard extends Fragment {

    ArrayList<Product> arrayList;
    ProductAdapter adapter;
    ListView listView;

    Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.shopping_card, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();

        listView = view.findViewById(R.id.productsList);
        arrayList = new ArrayList<>();
        adapter = new ProductAdapter(context, arrayList);
        listView.setAdapter(adapter);

        // send the product codes from local DB to WS to get the product runtime info




    }
}
