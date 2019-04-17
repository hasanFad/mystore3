package com.store.hasanfadool.mystore.ui.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.store.hasanfadool.mystore.R;
import com.store.hasanfadool.mystore.fragments.app.ProductPicturesFragment;

import java.util.Objects;

public class ProductImagesAdapter extends BaseAdapter {
    Context context;




    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        final ProductPicturesFragment picturesFragment = new ProductPicturesFragment();

        ImageView picture1 = picturesFragment.getView().findViewById(R.id.smallPic1_productPictures);
        ImageView  picture2 = picturesFragment.getView().findViewById(R.id.smallPic2_productPictures);
        ImageView  picture3 = picturesFragment.getView().findViewById(R.id.smallPic3_productPictures);

        return null;
    }
}
