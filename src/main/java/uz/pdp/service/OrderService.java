package uz.pdp.service;

import uz.pdp.baseAbstractions.BaseService;
import uz.pdp.model.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OrderService implements BaseService<Order> {
    private final ArrayList<Order> orders = new ArrayList<>();

    @Override
    public boolean add(Order order) {
        if (order != null && order.getId() != null) {
            orders.add(order);
            return true;
        }
        return false;
    }

    @Override
    public void update(UUID id, Order order) {}

    @Override
    public void delete(UUID id) {
        Order deletingOrder = getById(id);
        if (deletingOrder != null) {
            deletingOrder.setActive(false);
        }
    }

    @Override
    public List<Order> showAll() {
        return orders;
    }

    @Override
    public Order getById(UUID id) {
        for (Order order : orders) {
            if (order != null && order.getId().equals(id)) {
                return order;
            }
        }
        return null;
    }

    public ArrayList<Order> getOrdersByUserId(UUID userId) {
        ArrayList<Order> userOrders = new ArrayList<>();
        for (Order order : orders) {
            if (order != null && order.getUserId().equals(userId) && order.isActive()) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }
}
