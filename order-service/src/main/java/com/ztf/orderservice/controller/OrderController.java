package com.ztf.orderservice.controller;


import com.ztf.orderservice.OrderStatus;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrderController {

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/order")
    public String createOrder(@RequestParam String orderId,@RequestParam String username,@RequestParam String productId,@RequestParam OrderStatus status) {
        // 获取用户信息
        String userServiceUrl = "http://localhost:8081/user?username="+username;
        String userInfo = restTemplate.getForObject(userServiceUrl, String.class);
        // 获取产品信息
        String productServiceUrl = "http://localhost:8083/product?productId="+productId;
        String productInfo = restTemplate.getForObject(productServiceUrl, String.class);

        // 使用switch 表达式处理订单状态
        String orderStatusMessage = switch (status){
            case PENDING -> "您的订单正在等待处理。";

            case PROCESSING -> "您的订单正在处理中。";
            case COMPLETED -> "您的订单已完成。";
            case CANCELLED -> "您的订单已取消。";
        };
        return"订单 ID: "+orderId+"，下单人： "+userInfo+"，商品信息： "+productInfo+"，订单状态："+status+". "+orderStatusMessage;
    }
}
