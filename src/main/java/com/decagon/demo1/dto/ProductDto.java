package com.decagon.demo1.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductDto {
        private Long id;
        private String name;
        private Long quantity;
        private String category;
        private BigDecimal price;
        private String img;

}