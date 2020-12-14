package org.maktab.digikala.model;

import com.google.gson.annotations.SerializedName;

public class Images {

    @SerializedName("id")
    private int mId;
    @SerializedName("src")
    private String mSrc;

    public Images(int id, String src) {
        mId = id;
        mSrc = src;
    }

    public Images() {
    }

    public int getId() {
        return mId;
    }

    public String getSrc() {
        return mSrc;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setSrc(String src) {
        mSrc = src;
    }
}
