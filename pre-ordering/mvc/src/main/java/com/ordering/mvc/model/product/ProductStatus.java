package com.ordering.mvc.model.product;

public enum ProductStatus {
    PUBLIC("Đang bán"),
    UNPUBLIC("Dừng bán"),
    DELETED("Đã xóa");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
