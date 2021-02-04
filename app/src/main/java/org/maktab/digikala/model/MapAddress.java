package org.maktab.digikala.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "address")
public class MapAddress {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "address_id")
    private long primaryId;

    @ColumnInfo(name = "address_name")
    private String addressName;

    @ColumnInfo(name = "address_lat")
    private double address_lat;

    @ColumnInfo(name = "address_lng")
    private double address_lng;

    public long getPrimaryId() {
        return primaryId;
    }

    public void setPrimaryId(long primaryId) {
        this.primaryId = primaryId;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public double getAddress_lat() {
        return address_lat;
    }

    public void setAddress_lat(double address_lat) {
        this.address_lat = address_lat;
    }

    public double getAddress_lng() {
        return address_lng;
    }

    public void setAddress_lng(double address_lng) {
        this.address_lng = address_lng;
    }

    public MapAddress(String addressName, double address_lat, double address_lng) {
        this.addressName = addressName;
        this.address_lat = address_lat;
        this.address_lng = address_lng;
    }
}
