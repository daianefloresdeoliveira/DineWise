package com.example.finalproject.adapters;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

    private final int spanCount; // Number of columns
    private final int spacing; // Spacing in pixels
    private final boolean includeEdge;

    public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view); // Item position
        int column = position % spanCount; // Item column

        if (includeEdge) {
            outRect.left = spacing - column * spacing / spanCount; // Spacing on the left
            outRect.right = (column + 1) * spacing / spanCount; // Spacing on the right

            if (position < spanCount) { // Top edge
                outRect.top = spacing;
            }
            outRect.bottom = spacing; // Item bottom
        } else {
            outRect.left = column * spacing / spanCount; // Spacing on the left
            outRect.right = spacing - (column + 1) * spacing / spanCount; // Spacing on the right
            if (position >= spanCount) {
                outRect.top = spacing; // Item top
            }
        }
    }
}

