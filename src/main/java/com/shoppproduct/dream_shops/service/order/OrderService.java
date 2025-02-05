package com.shoppproduct.dream_shops.service.order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shoppproduct.dream_shops.exception.CartNotFoundException;
import com.shoppproduct.dream_shops.exception.InventoryOutException;
import com.shoppproduct.dream_shops.exception.OrderNotFoundException;
import com.shoppproduct.dream_shops.model.Cart;
import com.shoppproduct.dream_shops.model.Orders;
import com.shoppproduct.dream_shops.model.OrderItem;
import com.shoppproduct.dream_shops.model.Product;
import com.shoppproduct.dream_shops.repostitory.OrderRepository;
import com.shoppproduct.dream_shops.repostitory.ProductRepository;
import com.shoppproduct.dream_shops.service.cart.Imp.ICartService;
import com.shoppproduct.dream_shops.utils.dto.OrderDTO;
import com.shoppproduct.dream_shops.utils.enums.OrderStatus;

@Service
public class OrderService implements IOrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ICartService iCartService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Orders placeOrder(Long userId) {

        Cart cart = iCartService.getCartByUserId(userId);

        Orders order = createOrder(cart);

        List<OrderItem> orderItems = createOrderItem(order, cart);

        order.setOrderItems(new HashSet<>(orderItems));
        order.setTotalAmount(calculateTotalAmount(orderItems));
        Orders savedOrder = orderRepository.save(order);

        iCartService.clearCart(cart.getId());

        return savedOrder;
    }

    private Orders createOrder(Cart cart){
        Orders order = new Orders();
        order.setUser(cart.getUser());
        order.setOrderStatus(OrderStatus.PENDING);
        order.setOrderDate(LocalDate.now());

        return order;
    }

    private List<OrderItem> createOrderItem(Orders order, Cart cart){

        if(cart.getCartItems() == null){
            throw new CartNotFoundException("This cart doesn't contain any items. Please add items to your cart to order!");
        }

        return cart.getCartItems().stream().map(cartItem -> {
            Product product = cartItem.getProduct();
            // product bi tru di so luong
        
            if (product.getInventory() < cartItem.getQuantity()) {
                throw new InventoryOutException("The quantity of " + product.getProductName() + " has exceeded inventory. Please update smaller quantity!");
            }

            product.setInventory(product.getInventory() - cartItem.getQuantity());
            productRepository.save(product);
            return new OrderItem(
                    order,
                    product,
                    cartItem.getQuantity(),
                    cartItem.getUnitPrice());
        }).toList();
    }

    @Override
    public BigDecimal calculateTotalAmount(List<OrderItem> orderItems) {
        
        return orderItems
                .stream()
                .map(item -> item.getPrice()
                    .multiply(new BigDecimal(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal :: add);
    }
    
    @Override
    public List<OrderDTO> getUserOrders(Long userId){
        List<Orders> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrderNotFoundException("Not found order of user with id = " + userId);
        }
        return orders.stream().map(this :: convertToDTo).toList();
    }

    @Override
    public OrderDTO getOrder(Long orderId) {
        return orderRepository.findById(orderId)
            .map(this :: convertToDTo)
            .orElseThrow(() -> new OrderNotFoundException("Not found order with id = " + orderId));
    }

    @Override
    public OrderDTO convertToDTo(Orders orders){
        return modelMapper.map(orders, OrderDTO.class);
    }

}
