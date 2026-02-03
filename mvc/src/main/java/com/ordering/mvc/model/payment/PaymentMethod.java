package com.ordering.mvc.model.payment;

public enum PaymentMethod {
    COD("Chuyển Khoản Trực Tiếp"),
    VNPAY("VNPAY"),
    MOMO("MOMO");
    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}

