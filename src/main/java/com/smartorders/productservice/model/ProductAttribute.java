package com.smartorders.productservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_attribute")
public class ProductAttribute {

    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = "attr_name", nullable = false)
    private String name;

    @Column(name = "attr_value", nullable = false)
    private String value; // e.g., "Red", "Large", "Cotton"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "type", nullable = false)
    private String type; // e.g., "color", "size", "material"

    @Column(name = "unit", nullable = false)
    private String unit; // e.g., "cm", "kg", "pcs"

    @Column(name = "description")
    private String description; // Optional description for the attribute

}