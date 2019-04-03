package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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


public class ProductsListFragment extends Fragment implements AsyncResponse {
    private static final String TAG = "ProductsListFragment";


    ArrayList<Product> productList;
    ProductAdapter adapter;
    Context context;
    ListView listViewProduct;


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

        selectProductsAsync.delegate = this; // the listener

         selectProductsAsync.execute();

         listViewProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                 Toast.makeText(context, productList.get(position).getProName(), Toast.LENGTH_SHORT).show();

                    // on product click
                 String value = productList.get(position).getProName();

                 DetailsFragment detailsFragment = new DetailsFragment();
                 initFragment(detailsFragment);
             }
         });


    }


    @Override
    public void processFinish(String outPutJson) {
        readProductsJson(outPutJson);
    }


    public ArrayList<Product> readProductsJson(String result){


        try {
            JSONArray ary = new JSONArray(result);

            productList.clear();

            productList = new ArrayList<>();
            for (int i = 0; i < ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);




                Product productMainList = new Product(object.getString("productName"),
                        object.getDouble("productCheap"),
                        object.getInt("productPrice"),object.getString("productImage"));

                Product productInfo = new Product(object.getString("productName"),
                        object.getString("productColor"),
                        object.getString("companyName"),
                        object.getString("gender"),
                        object.getInt("productPrice"),
                        object.getDouble("productCheap"),
                        object.getString("productPicture")
                );

                Product proName = new Product(object.getString("productName"));


                            // send the product details in the Bundle
                Bundle sendProductDetailsBundle = new Bundle();
                sendProductDetailsBundle.putStringArrayList("productDetails", productInfo);

                Bundle myProductName = new Bundle();
                myProductName.putStringArrayList("productName", proName); // save at static

             productList.add(productMainList);
            }
            adapter.notifyDataSetChanged();

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return productList;


    }



    private void initFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();
    }

}
