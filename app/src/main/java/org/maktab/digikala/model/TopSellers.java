package org.maktab.digikala.model;

import com.google.gson.annotations.SerializedName;

public class TopSellers {

    @SerializedName("product_id")
    private int mId;

    @SerializedName("quantity")
    private int mQuantity;

    @SerializedName("title")
    private String mTitle;

    public TopSellers(int id, int quantity, String title) {
        mId = id;
        mQuantity = quantity;
        mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public int getQuantity() {
        return mQuantity;
    }

    public void setQuantity(int quantity) {
        mQuantity = quantity;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
