package com.ordering.mvc.model.category;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ordering.mvc.model.common.BaseEntity;
import com.ordering.mvc.model.product.ProductInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "category_id"))
public class CategoryInfo extends BaseEntity {
    @Column(name = "category_name")
    @JsonProperty("categoryName")
    private String categoryName;

    @Column(name = "category_description")
    @JsonProperty("categoryDescription")
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    private List<ProductInfo> products;

}
