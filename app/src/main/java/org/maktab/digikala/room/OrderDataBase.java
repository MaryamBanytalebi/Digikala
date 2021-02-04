package org.maktab.digikala.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import org.maktab.digikala.model.MapAddress;
import org.maktab.digikala.model.Order;

@Database(entities = {Order.class , MapAddress.class}, version = 1)

public abstract class OrderDataBase extends RoomDatabase {

    public abstract OrderDataBaseDao getOrderDatabaseDao();

}
