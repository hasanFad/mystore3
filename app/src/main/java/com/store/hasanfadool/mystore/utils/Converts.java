package com.store.hasanfadool.mystore.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class Converts {

    public Bitmap StringToBitmap(String encodingString){
        byte[] encodeByte = Base64.decode(encodingString,Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte,0,encodeByte.length);
    }
}
