package org.maktab.digikala.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import org.maktab.digikala.model.MapAddress;
import org.maktab.digikala.model.Order;

import java.util.List;

@Dao
public interface OrderDataBaseDao {

    @Update
    void updateOrder(Order order);

    @Insert
    void insertOrder(Order order);

    @Insert
    void insertOrders(List<Order> orders);

    @Delete
    void deleteOrder(Order order);

    @Query("DELETE FROM cart")
    void deleteAllOrders();

    @Query("SELECT * FROM cart")
    List<Order> getOrders();

    @Query("SELECT * FROM cart WHERE product_id=:productId")
    Order getOrder(int productId);

    @Update
    void updateAddress(MapAddress mapAddress);

    @Insert
    void insertAddress(MapAddress mapAddress);

    @Insert
    void insertAddresses(List<MapAddress> mapAddresses);

    @Delete
    void deleteAddress(MapAddress mapAddress);

    @Query("SELECT * FROM address")
    List<MapAddress> getAddresses();

    @Query("SELECT * FROM address WHERE selected_address=1")
    MapAddress getAddress();

    @Query("SELECT * FROM address WHERE primary_id=:addressId")
    MapAddress getAddressWithId(long addressId);
}
