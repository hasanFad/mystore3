package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductsAsync;
import com.store.hasanfadool.mystore.ui.adapters.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ProductsListFragment extends Fragment implements AsyncResponse {
    private static final String TAG = "ProductsListFragment";

    ArrayList<Product> productList; // the main array list
    ProductAdapter adapter; // adapter for main array list
    ListView listViewProduct;
    Context context;

    FragmentManager fragmentManager; // send with adapter for init fragment

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.products_list, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        context = getActivity();
        fragmentManager = getFragmentManager();



        listViewProduct = view.findViewById(R.id.productsList);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(fragmentManager,context, productList);
        listViewProduct.setAdapter(adapter);


        SelectProductsAsync selectProductsAsync = new SelectProductsAsync(); // class will connection to th WS

        selectProductsAsync.execute();

        selectProductsAsync.delegate = this;

    }


    @Override
    public void processFinish(String outPutJson) {
        readProductsJson(outPutJson);
    }


    public ArrayList<Product> readProductsJson(String result){

        try {
            JSONArray ary = new JSONArray(result);

            productList.clear();

            for (int i = 0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);


//    Product   (String productName, int productPrice, double productCheap, int shipping, String productImage)
                Product mainListPro = new Product(object.getString("productName"),
                        object.getInt("productPrice"),object.getDouble("cheap"),
                        object.getInt("shipping"), object.getString("productPicture"));

             productList.add(mainListPro);

            }

            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;

    }

    public List<Product> getProductList(){

        return this.productList;
    }


}





