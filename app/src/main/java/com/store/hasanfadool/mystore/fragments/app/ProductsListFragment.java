package com.store.hasanfadool.mystore.fragments.app;

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
import android.widget.ListView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.Product;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductsAsync;
import com.store.hasanfadool.mystore.ui.adapters.ProductAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;


public class ProductsListFragment extends Fragment implements AsyncResponse {
    private static final String TAG = "ProductsListFragment";


    ArrayList<Product> productList;
    ProductAdapter adapter;
    Context context;
    ListView listViewProduct;


    Product productInfo;



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

        listViewProduct = view.findViewById(R.id.productsList);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(context, productList);
        listViewProduct.setAdapter(adapter);



        SelectProductsAsync selectProductsAsync = new SelectProductsAsync();

        selectProductsAsync.execute();

        selectProductsAsync.delegate = this; // the listener




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

// Product(String productName, String productColor, String companyName, String gender, int productPrice, double productCheap, int shipping ,String productPicture)

                 productInfo = new Product(object.getString("productName"),
                        object.getString("productColor"),
                        object.getString("companyName"),object.getString("gender"),
                        object.getInt("productPrice"),
                        object.getDouble("cheap"),object.getInt("shipping"),object.getString("productPicture"));

             productList.add(mainListPro);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;


    }




//    public void initFragment(Fragment fragment){
//        FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.container, fragment);
//        fragmentTransaction.addToBackStack("fragment");
//        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        fragmentTransaction.commit();
//
//    }

}
