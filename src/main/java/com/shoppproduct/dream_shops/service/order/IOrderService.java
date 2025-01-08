package com.shoppproduct.dream_shops.service.order;

import java.math.BigDecimal;
import java.util.List;

import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.dto.OrderDTO;
import com.shoppproduct.dream_shops.model.OrderItem;

public interface IOrderService {
    
    Orders placeOrder(Long userId);
    BigDecimal calculateTotalAmount(List<OrderItem> orderItems);
    OrderDTO getOrder(Long orderId);
    List<OrderDTO> getUserOrders(Long userId);

}
