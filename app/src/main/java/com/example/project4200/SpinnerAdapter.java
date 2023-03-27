package com.example.project4200;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SpinnerAdapter extends ArrayAdapter<String> {

    Context context;
    List<String> imgLists;

    public SpinnerAdapter(Context context, List<String> img_icons){
        super(context, R.layout.activity_edit, img_icons);
        this.context = context;
        this.imgLists = img_icons;
    }

    // Override these methods and instead return our custom view (with image and text)
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // Function to return our custom View (View with an image and text)
    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.dropdown, parent, false);

        TextView textView = row.findViewById(R.id.text);
        ImageView imageView = row.findViewById(R.id.img);

        Resources resources = context.getResources();
        String drawableName = imgLists.get(position);
        int resId = resources.getIdentifier(drawableName, "drawable", context.getPackageName());
        Drawable drawable = resources.getDrawable(resId);
        textView.setText(imgLists.get(position));
        imageView.setImageDrawable(drawable);

        return row;
    }

}
