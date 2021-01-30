package org.maktab.digikala.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomDatabase;
import androidx.room.Update;

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
    void deleteTask(Order order);

    @Query("DELETE FROM cart")
    void deleteAllOrders();

    @Query("SELECT * FROM cart")
    List<Order> getOrders();


}
