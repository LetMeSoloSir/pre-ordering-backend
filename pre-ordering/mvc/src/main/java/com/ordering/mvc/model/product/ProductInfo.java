package com.ordering.mvc.model.product;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordering.mvc.model.category.CategoryInfo;
import com.ordering.mvc.model.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
public class ProductInfo extends BaseEntity implements Serializable {
    @Column(name = "product_name")
    @JsonProperty("productName")
    private String productName;

    @Column(name = "product_description")
    @JsonProperty("productDescription")
    private String productDescription;

    @Column(name = "product_price")
    @JsonProperty("productPrice")
    private BigDecimal productPrice;

    @Column(name = "avatar_url")
    @JsonProperty("avatarUrl")
    private String avatarUrl;

    @Column(name = "quantity_per_unit")
    @JsonProperty("quantityPerUnit")
    private String quantityPerUnit;

    @Column(name = "units_on_order")
    @JsonProperty("unitsOnOrder")
    private int unitsOnOrder;

    @Column(name = "units_in_stock")
    @JsonProperty("unitsInStock")
    private int unitsInStock;

    @Column(name = "reorder_level")
    @JsonProperty("reorderLevel")
    private int reorderLevel;

    @Column(name = "discount", precision = 5, scale = 2)
    private BigDecimal discount = BigDecimal.ZERO;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private ProductStatus status = ProductStatus.UNPUBLIC;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryInfo category;


}
