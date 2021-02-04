package org.maktab.digikala.repository;

import org.maktab.digikala.model.MapAddress;

import java.util.List;

public interface IAddressRepository {

    void updateAddress(MapAddress mapAddress);
    void insertAddress(MapAddress mapAddress);
    void insertAddresses(List<MapAddress> mapAddresses);
    void deleteAddress(MapAddress mapAddress);
    List<MapAddress> getMapAddresses();
    MapAddress getAddress();
    MapAddress getAddressWithId(long addressId);
}
