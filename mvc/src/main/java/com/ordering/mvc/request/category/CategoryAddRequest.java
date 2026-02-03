package com.ordering.mvc.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryAddRequest {
    private String categoryName;
    private String categoryDescription;
}
