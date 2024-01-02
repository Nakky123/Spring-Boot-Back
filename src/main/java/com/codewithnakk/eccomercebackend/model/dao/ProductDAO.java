package com.codewithnakk.eccomercebackend.model.dao;

import com.codewithnakk.eccomercebackend.model.Product;
import org.springframework.data.repository.ListCrudRepository;

public interface ProductDAO extends ListCrudRepository<Product , Long> {
}
