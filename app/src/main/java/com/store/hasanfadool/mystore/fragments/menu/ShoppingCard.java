package com.store.hasanfadool.mystore.fragments.menu;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponseString;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.ui.adapters.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShoppingCard  extends Fragment implements AsyncResponseString {

    Context context;
    FragmentManager fragmentManager;

    ArrayList<Product> productList;
    ProductAdapter adapter;
    ListView listViewProduct;
    Product myProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.products_list, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        context = getActivity();
        fragmentManager = getFragmentManager();

        listViewProduct = view.findViewById(R.id.productsList);
        productList = new ArrayList<>();
        adapter = new ProductAdapter(fragmentManager,context,productList);
        listViewProduct.setAdapter(adapter);

    }

    @Override
    public void processFinish(String outPut) {
       readShoppingCard(outPut);

    }

    public ArrayList<Product> readShoppingCard(String result){
        try{
            JSONArray ary = new JSONArray(result);
            productList.clear();

            for (int i = 0; i<ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);
                myProduct = new Product(object.getString("productCode"),object.getString("productName"),
                        object.getString("productColor"), object.getString("companyName"),object.getString("gender"),
                        object.getInt("productPrice"),object.getDouble("cheap"),object.getInt("shipping"),
                        object.getString("productPicture"));

                productList.add(myProduct);
            }
            adapter.notifyDataSetChanged();

        }catch (JSONException e){
            e.printStackTrace();
        }
        return productList;
    }

}
