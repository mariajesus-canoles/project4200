package com.example.project4200;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomItemDecorator extends RecyclerView.ItemDecoration {

    private final int margin;

    public CustomItemDecorator(int margin) {
        this.margin = margin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //outRect.left = margin;
        //outRect.right = margin;
        outRect.bottom = margin;

        // Add top margin only for the first item to avoid double space between items
        /*if (parent.getChildAdapterPosition(view) == 0) {
            outRect.top = margin;
        } else {
            outRect.top = 0;
        }*/
    }
}
