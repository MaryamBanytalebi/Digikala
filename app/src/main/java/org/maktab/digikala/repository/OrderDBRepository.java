package org.maktab.digikala.repository;

import android.content.Context;

import androidx.room.Room;

import org.maktab.digikala.model.Order;
import org.maktab.digikala.room.OrderDataBase;
import org.maktab.digikala.room.OrderDataBaseDao;

import java.util.List;

public class OrderDBRepository implements IRepository {

    private static OrderDBRepository sInstance;

    private OrderDataBaseDao mOrderDao;
    private Context mContext;
    private List<Order> mOrders;

    public static OrderDBRepository getInstance(Context context) {
        if (sInstance == null)
            sInstance = new OrderDBRepository(context);

        return sInstance;
    }

    private OrderDBRepository(Context context) {
        mContext = context.getApplicationContext();
        OrderDataBase orderDatabase = Room.databaseBuilder(mContext,
                OrderDataBase.class,
                "cart.db")
                .allowMainThreadQueries()
                .build();

        mOrderDao = orderDatabase.getOrderDatabaseDao();
    }


    @Override
    public void updateOrder(Order order) {

        mOrderDao.updateOrder(order);
    }

    @Override
    public void insertOrder(Order order) {

        mOrderDao.insertOrder(order);
    }

    @Override
    public void insertOrders(List<Order> orders) {

        mOrderDao.insertOrders(orders);

    }

    @Override
    public void deleteOrder(Order order) {

        mOrderDao.deleteTask(order);
    }

    @Override
    public void deleteAllOrder() {

        mOrderDao.deleteAllOrders();
    }

    @Override
    public List<Order> getOrders() {
        return mOrderDao.getOrders();
    }
}
