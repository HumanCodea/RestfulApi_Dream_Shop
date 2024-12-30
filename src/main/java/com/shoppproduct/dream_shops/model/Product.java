package com.shoppproduct.dream_shops.model;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int productId;

    @NotBlank(message = "This brand's product can be not blank")
    private String productBrand;

    @NotBlank(message = "This name's product can be not blank")
    private String productName;

    private BigDecimal productPrices;

    private int inventory;

    @NotBlank(message = "This description's product can be not blank")
    private String productDescription;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category category;

    //Khi bạn thêm, xóa, hoặc thay đổi danh sách images trong Product, 
    //các thao tác tương ứng sẽ được áp dụng trên cơ sở dữ liệu nhờ cascade và orphanRemoval.
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    public Product(String productBrand, String productName, BigDecimal productPrices, int inventory, String productDescription,
            Category category) {
        this.productBrand = productBrand;
        this.productName = productName;
        this.productPrices = productPrices;
        this.inventory = inventory;
        this.productDescription = productDescription;
        this.category = category;
    }

}
