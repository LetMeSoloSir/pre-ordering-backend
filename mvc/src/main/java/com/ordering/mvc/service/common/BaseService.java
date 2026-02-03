package com.ordering.mvc.service.common;

public interface BaseService <REQ, RES> {
    RES doProcess(REQ request);
}
