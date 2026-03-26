package com.ordering.mvc.model.order;



public enum OrderStatus {
    PENDING("Đang xử lý"),
    CONFIRMED("Xác nhận"),
    PAID("Đã thanh toán"),
    PREPARING("Đang chuẩn bị"),
    DELIVERING("Đang giao hàng"),
    DELIVERED("Đã giao"),
    CANCELLED("Đã hủy");

    private final String description;

    OrderStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

