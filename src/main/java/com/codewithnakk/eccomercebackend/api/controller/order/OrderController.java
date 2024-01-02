package com.codewithnakk.eccomercebackend.api.controller.order;

import com.codewithnakk.eccomercebackend.model.LocalUser;
import com.codewithnakk.eccomercebackend.model.WebOrder;
import com.codewithnakk.eccomercebackend.model.dao.LocalUserDAO;
import com.codewithnakk.eccomercebackend.service.OrderService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/order")
public class OrderController {

    private OrderService orderService;
    private LocalUserDAO localUserDAO;

    public OrderController(OrderService orderService , LocalUserDAO localUserDAO) {
        this.orderService = orderService;
        this.localUserDAO = localUserDAO;
    }

    @GetMapping("/{email}")
    public List<WebOrder> getOrdersByEmail(@PathVariable String email) {
        Optional<LocalUser> optionalUser = localUserDAO.findByEmailIgnoreCase(email);
        if (optionalUser.isPresent()) {
            LocalUser user = optionalUser.get();
            return orderService.getOrders(user);
        } else {
            // Handle case where user with given email is not found
            return Collections.emptyList();
        }
    }
}
