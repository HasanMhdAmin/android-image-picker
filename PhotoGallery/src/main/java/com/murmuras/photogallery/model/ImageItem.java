package com.murmuras.photogallery.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Objects;

/**
 * Created by Hasan Mhd Amin on 04.01.21
 */
public class ImageItem implements Parcelable {

    String url;
    Boolean isSelected = false;

    public ImageItem(String url) {
        this.url = url;
    }

    public ImageItem(Parcel source) {
        this.url = source.readString();
        this.isSelected = source.readBoolean();
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ImageItem imageItem = (ImageItem) o;
        return Objects.equals(url, imageItem.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(url);
        dest.writeBoolean(isSelected);
    }

    public static final Creator<ImageItem> CREATOR = new Creator<ImageItem>() {
        public ImageItem createFromParcel(Parcel source) {
            return new ImageItem(source);
        }

        public ImageItem[] newArray(int size) {
            return new ImageItem[size];
        }
    };
}
