package com.shoppproduct.dream_shops.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @NotBlank(message = "This name's category can be not blank")
    private String nameCategory;

    @OneToMany(mappedBy = "category")
    @JsonIgnore // kiểm soát quá trình tuần tự hóa dữ liệu.
    private List<Product> products;

    public Category(String nameCategory) {
        this.nameCategory = nameCategory;
    }

}
