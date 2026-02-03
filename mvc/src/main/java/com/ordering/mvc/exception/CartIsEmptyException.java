package com.ordering.mvc.exception;

public class CartIsEmptyException extends BaseException {
    public CartIsEmptyException() {

        super("Hiện đang có lỗi xảy ra, xin vui lòng thử lại sau", 404);

    }
}
