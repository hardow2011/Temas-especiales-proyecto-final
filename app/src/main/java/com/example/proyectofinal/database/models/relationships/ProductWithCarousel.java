package com.example.proyectofinal.database.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectofinal.database.models.Carousel;
import com.example.proyectofinal.database.models.Product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProductWithCarousel implements Serializable {

    @Embedded
    public Product product;
    @Relation(
            parentColumn = "itemCode",
            entityColumn = "product",
            entity = Carousel.class
    )
    public List<Carousel> carousels;

    public ProductWithCarousel() {
        this.product = new Product();
        this.carousels = new ArrayList<>();
    }

    public ProductWithCarousel(Product product, List<Carousel> carousels) {
        this.product = product;
        this.carousels = carousels;
    }
}
