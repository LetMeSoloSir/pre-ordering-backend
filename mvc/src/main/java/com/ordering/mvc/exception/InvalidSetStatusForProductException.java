package com.ordering.mvc.exception;

public class InvalidSetStatusForProductException extends BaseException {
    public InvalidSetStatusForProductException() {
        super("Có lỗi khi cài đặt trạng thái sản phẩm",404);
    }
}
