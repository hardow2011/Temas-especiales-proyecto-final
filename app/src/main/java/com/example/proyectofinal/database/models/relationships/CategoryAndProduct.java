package com.example.proyectofinal.database.models.relationships;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.proyectofinal.database.models.Category;
import com.example.proyectofinal.database.models.Product;

import java.io.Serializable;

public class CategoryAndProduct  implements Serializable {
    @Embedded
    public Category category;
    @Relation(
            parentColumn = "uid",
            entityColumn = "category"
    )
    public Product product;
}
