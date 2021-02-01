package org.maktab.digikala.repository;

import org.maktab.digikala.model.Order;

import java.util.List;

public interface IRepository {

    void updateOrder(Order order);
    void insertOrder(Order order);
    void insertOrders(List<Order> orders);
    void deleteOrder(Order order);
    void deleteAllOrder();
    List<Order> getOrders();
    Order getOrder(int productId);

}
