package org.maktab.digikala.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Customer {

    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("email")
    @Expose
    private String mEmail;
    @SerializedName("first_name")
    @Expose
    private String mFirst_name;
    @SerializedName("last_name")
    @Expose
    private String  mLast_name;
    @SerializedName("username")
    @Expose
    private String  mUsername;

    public Customer(int id, String email, String first_name, String last_name,
                    String username) {
        mId = id;
        mEmail = email;
        mFirst_name = first_name;
        mLast_name = last_name;
        mUsername = username;

    }

    public Customer(String email, String first_name, String last_name,
                    String username) {
        mEmail = email;
        mFirst_name = first_name;
        mLast_name = last_name;
        mUsername = username;

    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFirst_name() {
        return mFirst_name;
    }

    public void setFirst_name(String first_name) {
        mFirst_name = first_name;
    }

    public String getLast_name() {
        return mLast_name;
    }

    public void setLast_name(String last_name) {
        mLast_name = last_name;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }



    @Override
    public String toString() {
        return "Customer{" +
                "mId=" + mId +
                ", mEmail='" + mEmail + '\'' +
                ", mFirst_name='" + mFirst_name + '\'' +
                ", mLast_name='" + mLast_name + '\'' +
                ", mUsername='" + mUsername +
                '}';
    }
}
