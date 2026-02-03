package com.ordering.mvc.exception;

public class CategoryIsEmptyException extends BaseException {
    public CategoryIsEmptyException() {
        super("Hiện không có sản phẩm phù hợp, xin vui lòng thử lại sau", 404);
    }
}
