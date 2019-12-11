package com.demo.controller.test;

import com.demo.pojo.Order;
import com.jdkhome.twiggy.core.authentication.res.ResAuth;
import com.jdkhome.twiggy.common.bean.Twiggy;
import com.jdkhome.twiggy.common.bean.TwiggyRes;
import com.jdkhome.twiggy.common.exception.TwiggyPermissionDeniedException;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * create by linkji.
 * create at 16:40 2019-12-10
 */
@RestController
@RequestMapping("/api/test/order")
public class OrderController {

    @Autowired
    ResAuth<Order> orderResAuth;

    /**
     * 通过订单号获取订单
     */
    @Data
    class GetOrderParams {
        String orderNo;
    }

    @Twiggy(value = "通过订单号获取订单", fun = "(merchant>0)||(user>0)||(superAdmin>0)")
    @RequestMapping("/get")
    public Order getOrder(@Valid GetOrderParams params) {

        Order order = new Order();
        order.setUserId(1);
        order.setValue("xxxx");
        order.setAccountNo("ACC0001");

        if (!orderResAuth.authOne(order)) {
            throw new TwiggyPermissionDeniedException();
        }

        return order;
    }


    /**
     * 获取订单列表
     */
    @Data
    class GetOrderListParams {

        @TwiggyRes
        Integer userId;

        @TwiggyRes
        String accountNo;
    }

    @Twiggy(value = "获取订单列表", fun = "(merchant>0)||(user>0)||(superAdmin>0)", res = "userId||accountNo")
    @RequestMapping("/list")
    public List<Order> getOrderList(@Valid GetOrderListParams params) {

        List<Order> orders = new ArrayList<>();

        orders.add(new Order(1, "ACC0001", "xxxx"));
        orders.add(new Order(2, "ACC0001", "xxxx"));
        orders.add(new Order(3, "ACC0001", "xxxx"));

        return orders;
    }

}
