package com.decagon.demo1.model;

import com.decagon.demo1.dto.ProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@Entity
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Long quantity;
    private String category;
    private BigDecimal price;
    private String img;

    public Product(ProductDto productDto){
        this.category=productDto.getCategory();
        this.name=productDto.getName();
        this.price= productDto.getPrice();
        this.quantity= productDto.getQuantity();
        this.img= productDto.getImg();

    }
}

