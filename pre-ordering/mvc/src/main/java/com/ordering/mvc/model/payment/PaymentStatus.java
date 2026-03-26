package com.ordering.mvc.model.payment;

public enum PaymentStatus {
    PENDING("Đang xử lý"),
    PAID("Đã thanh toán"),
    FAILED("Thanh toán thất bại");
    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
