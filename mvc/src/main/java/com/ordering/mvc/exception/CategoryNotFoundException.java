package com.ordering.mvc.exception;

public class CategoryNotFoundException extends BaseException {
    public CategoryNotFoundException() {
        super("Hiện thông thấy danh mục phù hợp ", 404);
    }
}

