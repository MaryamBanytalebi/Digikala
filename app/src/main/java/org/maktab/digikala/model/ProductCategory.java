package org.maktab.digikala.model;

import com.google.gson.annotations.SerializedName;

public class ProductCategory {

    @SerializedName("id")
    private int mId;
    @SerializedName("name")
    private String mName;
    @SerializedName("parent")
    private int mParent;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("image")
    private String mImage;
    @SerializedName("count")
    private int mCount;

    public ProductCategory(int id, String name, int parent, String description, String image, int count) {
        mId = id;
        mName = name;
        mParent = parent;
        mDescription = description;
        mImage = image;
        mCount = count;
    }

    public int getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public int getParent() {
        return mParent;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getImage() {
        return mImage;
    }

    public int getCount() {
        return mCount;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setName(String name) {
        mName = name;
    }

    public void setParent(int parent) {
        mParent = parent;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public void setCount(int count) {
        mCount = count;
    }
}
