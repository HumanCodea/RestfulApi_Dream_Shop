package com.shoppproduct.dream_shops.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.model.OrderItem;

public interface IOrderService {
    
    Orders placeOrder(Long userId);
    BigDecimal calculateTotalAmount(List<OrderItem> orderItems);
    List<Orders> getUserOrders(Long userId);

}
