package com.store.hasanfadool.mystore.fragments.app;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductPicturesAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductPicturesFragment extends Fragment implements AsyncResponse {

    Context context;

    ImageView bigPicture,picture1,picture2,picture3;
    Button rightButton,leftButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_pictures, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bigPicture = view.findViewWithTag(R.id.bigPicture_productPictures);
        picture1 = view.findViewWithTag(R.id.smallPic1_productPictures);
        picture2 = view.findViewWithTag(R.id.smallPic2_productPictures);
        picture3 = view.findViewWithTag(R.id.smallPic3_productPictures);

        rightButton = view.findViewWithTag(R.id.rightButton_productPictures);
        leftButton = view.findViewWithTag(R.id.leftButton_productPictures);

        SelectProductPicturesAsync selectProductPicturesAsync = new SelectProductPicturesAsync();
        selectProductPicturesAsync.delegate = this;
        selectProductPicturesAsync.execute();

        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the right picture to the big picture
                Toast.makeText(context, "the right Button", Toast.LENGTH_SHORT).show();

            }
        });

        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // set the left picture to the big picture
                Toast.makeText(context, "the left Button", Toast.LENGTH_SHORT).show();
            }
        });



    }




    @Override
    public void processFinish(String result) {
        getPictures(result);
    }

    private void getPictures(String result) {
        try {
            JSONArray ary = new JSONArray(result);
            for (int i = 0; i <ary.length(); i++){
                JSONObject object = ary.getJSONObject(i);
                String  st1,st2,st3;

                    // set the result WS to string
                st1 = object.getString("productPicture1");
                st2 = object.getString("productPicture2");
                st3 = object.getString("productPicture3");

                    // convert and decode the strings to Base64
                byte[] by1,by2,by3;
                by1 = Base64.decode(st1, Base64.DEFAULT);
                by2 = Base64.decode(st2, Base64.DEFAULT);
                by3 = Base64.decode(st3, Base64.DEFAULT);

                Bitmap b1,b2,b3;
                    // set the Base64 to Bitmap
                b1 = BitmapFactory.decodeByteArray(by1,0, by1.length);
                b2 = BitmapFactory.decodeByteArray(by2,0, by2.length);
                b3 = BitmapFactory.decodeByteArray(by3,0, by3.length);

                picture1.setImageBitmap(b1);
                picture2.setImageBitmap(b2);
                picture3.setImageBitmap(b3);


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

