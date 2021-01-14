package com.murmuras.photogallery;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.murmuras.photogallery.model.ImageItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private final ArrayList<ImageItem> list;
    private final Boolean allowMultiSelection;
    private int lastSelectedItemIndex = -1;

    public ImageAdapter(ArrayList<ImageItem> list, Boolean allowMultiSelection) {
        this.list = list;
        this.allowMultiSelection = allowMultiSelection;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.image_item, viewGroup, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder viewHolder, int i) {
        final ImageItem item = list.get(i);
        Picasso.get().load(item.getUrl()).into(viewHolder.imageView);

        if (item.getSelected()) {
            viewHolder.container.setBackgroundColor(Color.parseColor("#F18383"));
        } else {
            viewHolder.container.setBackgroundColor(Color.parseColor("#00FFFFFF"));
        }

        viewHolder.imageView.setOnClickListener(view -> {
            item.setSelected(!item.getSelected());

            if (!allowMultiSelection) {
                if (lastSelectedItemIndex != -1) {
                    // remove the selection from the last item
                    ImageItem lastItem = list.get(lastSelectedItemIndex);
                    lastItem.setSelected(!lastItem.getSelected());
                }
                lastSelectedItemIndex = i;
            }
            notifyDataSetChanged();
        });
    }

    public ArrayList<ImageItem> getSelectedImage() {
        ArrayList<ImageItem> result = new ArrayList<>();
        for (ImageItem imageItem : list) {
            if (imageItem.getSelected())
                result.add(imageItem);
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        View container;

        public ImageViewHolder(View rootView) {
            super(rootView);
            container = rootView.findViewById(R.id.container);
            imageView = rootView.findViewById(R.id.image_cell);
        }
    }
}