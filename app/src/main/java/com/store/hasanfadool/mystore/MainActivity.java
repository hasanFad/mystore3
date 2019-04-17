package com.store.hasanfadool.mystore;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.store.hasanfadool.mystore.fragments.app.ProductsListFragment;
import com.store.hasanfadool.mystore.fragments.menu.ProductSearch;
import com.store.hasanfadool.mystore.fragments.menu.ShoppingCard;
import com.store.hasanfadool.mystore.fragments.user.SignInUser;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            setPointer();

    }


    private void setPointer() {

        ProductsListFragment fragment = new ProductsListFragment();
        initFragment(fragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

        case R.id.userLogin_appMenu:
            Toast.makeText(this, "userLogin_appMenu", Toast.LENGTH_SHORT).show();
            SignInUser signInUserFragment = new SignInUser();
            initFragment(signInUserFragment);
            return true;

            case R.id.shoppingCard_appMenu:
                Toast.makeText(this, "shoppingCard_appMenu", Toast.LENGTH_SHORT).show();
                ShoppingCard shoppingCardFragment = new ShoppingCard();
                initFragment(shoppingCardFragment);
                return true;

            case R.id.searching_appMenu:
                Toast.makeText(this, "searching_appMenu", Toast.LENGTH_SHORT).show();
                ProductSearch productSearch = new ProductSearch();
                initFragment(productSearch);
                return true;

            case R.id.userOrder_appMenu:
                Toast.makeText(this, "userOrder_appMenu", Toast.LENGTH_SHORT).show();

                return true;

        }
        return super.onOptionsItemSelected(item);

    }


                // it initializing the fragment
    private void initFragment(Fragment fragment){
        Log.d(TAG, "initFragment: ");
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack("fragment");
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        fragmentTransaction.commit();


    }





}
