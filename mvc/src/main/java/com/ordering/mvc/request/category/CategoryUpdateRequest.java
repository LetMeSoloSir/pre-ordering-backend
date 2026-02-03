package com.ordering.mvc.request.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {
    private UUID categoryId;
    private String categoryName;
    private String categoryDescription;
}
