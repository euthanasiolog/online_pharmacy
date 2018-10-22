package com.epam.pharmacy.model;

import com.epam.pharmacy.model.item.Drug;
import com.epam.pharmacy.model.item.Order;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.*;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Cart extends Entity {

    @NonNull
    @Getter
    private int clientId;

    private List<Order> orders = new ArrayList<>();

    public List<Order> getOrders () {
        return Collections.unmodifiableList(orders);
    }

    public void addOrder (Drug drug, int number) {
        orders.add(new Order(drug, number));
    }

    public void addOrder (Order order) {
        orders.add(order);
    }

    public void payment (Order order) {
        order.setPayment(true);
    }

    public void deleteOrder (Order order) {
        orders.remove(order);
    }

    public boolean isEmpty () {
        return orders.isEmpty();
    }

}
