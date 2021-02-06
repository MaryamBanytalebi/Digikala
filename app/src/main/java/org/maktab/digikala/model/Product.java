package org.maktab.digikala.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Product {

    @SerializedName("id")
    private int mId;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("regular_price")
    private String mRegularPrice;
    @SerializedName("sale_price")
    private String mSalePrice;
    @SerializedName("date_on_sale_from")
    private String mDateOnSaleFrom;
    @SerializedName("date_on_sale_to")
    private String mDateOnSaleTo;
    @SerializedName("weight")
    private String mWeight;
    @SerializedName("length")
    private String mLength;
    @SerializedName("width")
    private String mWidth;
    @SerializedName("height")
    private String mHeight;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("short_description")
    private String mShortDescription;
    @SerializedName("average_rating")
    private String mAverageRating;
    @SerializedName("rating_count")
    private int mRatingCount;
    @SerializedName("total_sales")
    private int mTotal_sales;
    @SerializedName("images")
    private List<Images> mImages;

    public Product() {
    }

    public Product(int id, String title, String price, String regularPrice, String salePrice,
                   String weight, String length,
                   String width, String height, String description, String shortDescription,
                   String averageRating, int ratingCount, int total_sales, List<Images> images) {
        mId = id;
        mTitle = title;
        mPrice = price;
        mRegularPrice = regularPrice;
        mSalePrice = salePrice;
        //mDateOnSaleFrom = dateOnSaleFrom;
        //mDateOnSaleTo = dateOnSaleTo;
        mWeight = weight;
        mLength = length;
        mWidth = width;
        mHeight = height;
        mDescription = description;
        mShortDescription = shortDescription;
        mAverageRating = averageRating;
        mRatingCount = ratingCount;
        mTotal_sales = total_sales;
        mImages = images;
    }

    public int getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getPrice() {
        return mPrice;
    }

    public String getRegularPrice() {
        return mRegularPrice;
    }

    public String getSalePrice() {
        return mSalePrice;
    }

    public String getDateOnSaleFrom() {
        return mDateOnSaleFrom;
    }

    public String getDateOnSaleTo() {
        return mDateOnSaleTo;
    }

    public String getWeight() {
        return mWeight;
    }

    public String getLength() {
        return mLength;
    }

    public String getWidth() {
        return mWidth;
    }

    public String getHeight() {
        return mHeight;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getShortDescription() {
        return mShortDescription;
    }

    public String getAverageRating() {
        return mAverageRating;
    }

    public int getRatingCount() {
        return mRatingCount;
    }

    public List<Images> getImages() {
        return mImages;
    }

    public void setImages(List<Images> images) {
        mImages = images;
    }

    public void setId(int id) {
        mId = id;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public void setRegularPrice(String regularPrice) {
        mRegularPrice = regularPrice;
    }

    public void setSalePrice(String salePrice) {
        mSalePrice = salePrice;
    }

    public void setDateOnSaleFrom(String dateOnSaleFrom) {
        mDateOnSaleFrom = dateOnSaleFrom;
    }

    public void setDateOnSaleTo(String dateOnSaleTo) {
        mDateOnSaleTo = dateOnSaleTo;
    }

    public void setWeight(String weight) {
        mWeight = weight;
    }

    public void setLength(String length) {
        mLength = length;
    }

    public void setWidth(String width) {
        mWidth = width;
    }

    public void setHeight(String height) {
        mHeight = height;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public void setShortDescription(String shortDescription) {
        mShortDescription = shortDescription;
    }

    public void setAverageRating(String averageRating) {
        mAverageRating = averageRating;
    }

    public void setRatingCount(int ratingCount) {
        mRatingCount = ratingCount;
    }

    public void setAverage_rating(String average_rating) {
        mAverageRating = average_rating;
    }

    public void setRating_count(int rating_count) {
        mRatingCount = rating_count;
    }

    public int getTotal_sales() {
        return mTotal_sales;
    }

    public void setTotal_sales(int total_sales) {
        mTotal_sales = total_sales;
    }

}
