package com.shoppproduct.dream_shops.model;

import java.math.BigDecimal;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Cart {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private BigDecimal totalAmount = BigDecimal.ZERO;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CartItem> cartItems;

    public void addCartItem(CartItem cartItem){
        this.cartItems.add(cartItem);
        cartItem.setCart(this);
        updateTotalAmount();
    }

    public void removeCartItem(CartItem cartItem){
        this.cartItems.remove(cartItem);
        cartItem.setCart(null);
        updateTotalAmount();
    }

    public void updateTotalAmount(){
        this.totalAmount = cartItems.stream().map(item -> {
            BigDecimal unitPrice = item.getUnitPrice();
            if (unitPrice == null){
                return BigDecimal.ZERO;
            }
            return unitPrice.multiply(BigDecimal.valueOf(item.getQuantity()));
        }).reduce(BigDecimal.ZERO, BigDecimal::add); // tính tổng hợp
    }

}
