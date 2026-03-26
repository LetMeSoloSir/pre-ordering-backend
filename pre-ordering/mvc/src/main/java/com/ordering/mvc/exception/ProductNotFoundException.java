package com.ordering.mvc.exception;


public class ProductNotFoundException extends BaseException {
    public ProductNotFoundException() {
        super("Có lỗi khi tìm kiếm sản phẩm", 404);
    }
}
