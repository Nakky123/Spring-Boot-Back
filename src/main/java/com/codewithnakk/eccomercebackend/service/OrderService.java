package com.codewithnakk.eccomercebackend.service;

import com.codewithnakk.eccomercebackend.model.LocalUser;
import com.codewithnakk.eccomercebackend.model.WebOrder;
import com.codewithnakk.eccomercebackend.model.dao.WebOrderDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private WebOrderDao webOrderDao;

    public OrderService(WebOrderDao webOrderDao) {
        this.webOrderDao = webOrderDao;
    }

    public List<WebOrder> getOrders(LocalUser user){
        return webOrderDao.findByUser(user);
    }
}
