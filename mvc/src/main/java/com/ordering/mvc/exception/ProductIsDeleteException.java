package com.ordering.mvc.exception;

public class ProductIsDeleteException extends BaseException {
    public ProductIsDeleteException() {
        super("Sản phẩm hiện đã bị xóa, xin vui long thử lại sau",404);
    }
}
