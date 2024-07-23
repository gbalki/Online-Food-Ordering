package com.balki.controller;

import com.balki.model.Order;
import com.balki.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class OrderAdminController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/order/restaurant/{id}")
    public ResponseEntity<List<Order>> getOrderHistory(@RequestParam(required = false) String orderStatus,
                                                       @PathVariable Long id) throws Exception {
        List<Order> orders = orderService.getRestaurantsOrder(id, orderStatus);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PutMapping("/order/{id}/{orderStatus}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable String orderStatus,
                                                         @PathVariable Long id) throws Exception {
        Order order = orderService.updateOrder(id, orderStatus);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
