package com.ordering.mvc.service.dashboard;

import com.ordering.mvc.repository.order.OrderRepository;
import com.ordering.mvc.repository.product.ProductRepository;
import com.ordering.mvc.repository.user.UserRepository;
import com.ordering.mvc.request.dashboard.DashboardRequest;
import com.ordering.mvc.response.dashboard.AdminDashboardResponse;
import com.ordering.mvc.service.common.BaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminDashboardService implements BaseService<DashboardRequest, AdminDashboardResponse> {

    private final OrderRepository orderRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepo;

    @Override
    public AdminDashboardResponse doProcess(DashboardRequest request) {

        AdminDashboardResponse res = new AdminDashboardResponse();

        res.setTotalOrders(orderRepo.countOrders());
        res.setRevenue(orderRepo.sumRevenue() == null ? 0 : orderRepo.sumRevenue());
        res.setTotalProducts(productRepo.count());
        res.setTotalEmployees(userRepo.countEmployee());

        return res;
    }
}
