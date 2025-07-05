package com.smartorders.productservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tag")
public class Tag {
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;
    @Column(name = "tag_name", nullable = false)
    private String tagName;
    @Column(name = "description")
    private String description; // Optional description for the tag
    @Column(name = "is_active", nullable = false)
    private Boolean isActive; // Indicates if the tag is currently active
    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "tags")
    private Set<Product> products = new HashSet<>();
}