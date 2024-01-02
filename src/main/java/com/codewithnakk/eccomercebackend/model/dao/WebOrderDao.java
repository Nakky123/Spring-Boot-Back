package com.codewithnakk.eccomercebackend.model.dao;

import com.codewithnakk.eccomercebackend.model.LocalUser;
import com.codewithnakk.eccomercebackend.model.WebOrder;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface WebOrderDao extends ListCrudRepository<WebOrder, Long> {
    List<WebOrder> findByUser(LocalUser user);

}
