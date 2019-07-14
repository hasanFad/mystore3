package com.store.hasanfadool.mystore;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.store.hasanfadool.mystore.fragments.app.OrderInfo;
import com.store.hasanfadool.mystore.fragments.app.ProductsListFragment;
import com.store.hasanfadool.mystore.fragments.menu.ProductSearch;
import com.store.hasanfadool.mystore.fragments.menu.ShoppingCard;
import com.store.hasanfadool.mystore.fragments.user.SignInUser;
import com.store.hasanfadool.mystore.models.Product;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Context context;
    ProductsListFragment productsListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setPointer();


    }


    private void setPointer() {
        context =this;

        productsListFragment = new ProductsListFragment();
        initFragment(productsListFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");

        switch (item.getItemId()){

            // the user login
        case R.id.userLogin_appMenu:
            Log.d(TAG, "userLogin_appMenu");
            Toast.makeText(this, "userLogin_appMenu", Toast.LENGTH_SHORT).show();
            SignInUser signInUserFragment = new SignInUser();
            initFragment(signInUserFragment);
            return true;

            // the user shopping card
            case R.id.shoppingCard_appMenu:
                Log.d(TAG, "shoppingCard_appMenu");
                ShoppingCard shoppingCardFragment = new ShoppingCard();
                initFragment(shoppingCardFragment);
                return true;

                // search for a product
            case R.id.searching_appMenu:
                Log.d(TAG, "searching_appMenu");
                ProductSearch productSearch = new ProductSearch();
                productSearch.setProductsName((ArrayList<Product>) productsListFragment.getProductList());
                    // the search productsListFragment init
                    initFragment(productSearch);


                return true;

                // the user orders
            case R.id.userOrder_appMenu:
                Log.d(TAG, "userOrder_appMenu");
                OrderInfo orderInfo = new OrderInfo();
                initFragment(orderInfo);
                return true;

        }

        return super.onOptionsItemSelected(item);

    }



    // it initializing the productsListFragment
    private void initFragment(Fragment fragment){
        Log.d(TAG, "initFragment: ");
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("productsListFragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();


    }


}
