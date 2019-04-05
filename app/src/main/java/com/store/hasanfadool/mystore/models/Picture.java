package com.store.hasanfadool.mystore.models;


import android.content.Context;
import android.graphics.Bitmap;

public class Picture  {

    private Context context;
    private Bitmap bigPicture, picture1, picture2 ,picture3;


    public Picture(Bitmap bitmap){
        this.bigPicture = bitmap;
    }

    public Picture(Bitmap bigPicture,Bitmap picture1, Bitmap picture2, Bitmap picture3){
        this.bigPicture = bigPicture;
        this.picture1 = picture1;
        this.picture2 = picture2;
        this.picture3 = picture3;
    }

    public Bitmap getBigPicture() {
        return bigPicture;
    }

    public Bitmap getPicture1() {
        return picture1;
    }

    public Bitmap getPicture2() {
        return picture2;
    }

    public Bitmap getPicture3() {
        return picture3;
    }

    public Context getContext() {
        return context;
    }

    public void setBigPicture(Bitmap bigPicture) {
        this.bigPicture = bigPicture;
    }

    public void setPicture1(Bitmap picture1) {
        this.picture1 = picture1;
    }

    public void setPicture2(Bitmap picture2) {
        this.picture2 = picture2;
    }

    public void setPicture3(Bitmap picture3) {
        this.picture3 = picture3;
    }


}
