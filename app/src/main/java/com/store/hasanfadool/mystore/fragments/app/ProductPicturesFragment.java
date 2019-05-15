package com.store.hasanfadool.mystore.fragments.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.interfaces.AsyncResponse;
import com.store.hasanfadool.mystore.models.Picture;
import com.store.hasanfadool.mystore.network.AsyncTasks.selects.SelectProductPicturesAsync;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ProductPicturesFragment extends Fragment implements AsyncResponse {

    Context context;
    FragmentManager fragmentManager;

    Bitmap b0,b1,b2,b3;
    ImageView smallPic1,smallPic2,smallPic3, bigPic;
    Button leftButton,rightButton;
    Picture myPictures;
    String mm ;

    @SuppressLint("InflateParams")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_pictures, null);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        context = getActivity();
        fragmentManager = getFragmentManager();
        Bundle getProductImage = getArguments();
        if (getProductImage != null){
            mm = getProductImage.getString("productPicture");
        }

            // get the pictures from the WS and use the listener
        SelectProductPicturesAsync selectProductPicturesAsync = new SelectProductPicturesAsync();
        selectProductPicturesAsync.execute();
        selectProductPicturesAsync.delegate = this;



        smallPic1 = view.findViewById(R.id.smallPic1_productPictures);
        smallPic2 = view.findViewById(R.id.smallPic2_productPictures);
        smallPic3 = view.findViewById(R.id.smallPic3_productPictures);
        bigPic = view.findViewById(R.id.bigPicture_productPictures);

        leftButton = view.findViewById(R.id.leftButton_productPictures);
        rightButton = view.findViewById(R.id.rightButton_productPictures);


        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // go to next picture

                Toast.makeText(context, "go to next image", Toast.LENGTH_SHORT).show();

                if (bigPic.getDrawable().toString().equals(b0.toString())){
                    bigPic.setImageBitmap(b1);

                    smallPic1.setImageBitmap(b2);
                    smallPic2.setImageBitmap(b3);
                    smallPic3.setImageBitmap(b0);

                }else if(bigPic.getDrawable().toString().equals(b1.toString())){
                    bigPic.setImageBitmap(b2);

                    smallPic1.setImageBitmap(b3);
                    smallPic2.setImageBitmap(b0);
                    smallPic3.setImageBitmap(b1);

                }else if (bigPic.getDrawable().toString().equals(b2.toString())){
                    bigPic.setImageBitmap(b3);
                    smallPic1.setImageBitmap(b0);
                    smallPic2.setImageBitmap(b1);
                    smallPic3.setImageBitmap(b2);

                }else {
                    bigPic.setImageBitmap(b0);
                    smallPic1.setImageBitmap(b1);
                    smallPic2.setImageBitmap(b2);
                    smallPic3.setImageBitmap(b3);
                }


            }
        });


        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    // go to back picture
                Toast.makeText(context, "go te back image", Toast.LENGTH_SHORT).show();
                if (bigPic.getDrawable().toString().equals(b0.toString())){
                    bigPic.setImageBitmap(b3);
                    smallPic1.setImageBitmap(b2);
                    smallPic2.setImageBitmap(b1);
                    smallPic3.setImageBitmap(b0);

                }else if (bigPic.getDrawable().toString().equals(myPictures.getPicture3())){
                    bigPic.setImageBitmap(b2);
                    smallPic1.setImageBitmap(b1);
                    smallPic2.setImageBitmap(b0);
                    smallPic3.setImageBitmap(b3);

                }else if (bigPic.getDrawable().toString().equals(myPictures.getPicture2())){
                    bigPic.setImageBitmap(b1);
                    smallPic1.setImageBitmap(b0);
                    smallPic2.setImageBitmap(b3);
                    smallPic3.setImageBitmap(b2);

                }else {
                    bigPic.setImageBitmap(b0);
                    smallPic1.setImageBitmap(b3);
                    smallPic2.setImageBitmap(b2);
                    smallPic3.setImageBitmap(b1);
                }
            }

        });
    }


    @Override
    public void processFinish(String outPut) { // the function from the listener
        readJson(outPut);
    }

    private void readJson(String result) { // set the pictures to object
        try {
            JSONArray array = new JSONArray(result);

            for (int i = 0; i<array.length(); i++){
                JSONObject object = array.getJSONObject(i);

                myPictures = new Picture(object.getString("productPicture1"),
                        object.getString("productPicture2"),
                        object.getString("productPicture3"));

                convert2Bitmap(myPictures); // convert the pictures to bitmap
                setPictures();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void convert2Bitmap(Picture strings) { // set the pictures at the imageView
        b0 = string2Bitmap(mm);
        b1 = string2Bitmap(strings.getPicture1());
        b2 = string2Bitmap(strings.getPicture2());
        b3 = string2Bitmap(strings.getPicture3());
    }

    private void setPictures(){
        bigPic.setImageBitmap(b0);
        smallPic1.setImageBitmap(b1);
        smallPic2.setImageBitmap(b2);
        smallPic3.setImageBitmap(b3);
    }


    private Bitmap string2Bitmap(String encodedString){ // convert the pictures from string to bitmap
        byte[] encodedByte = Base64.decode(encodedString, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodedByte, 0, encodedByte.length);
    }




}

