package com.adrian99.onlineShop.repository;

import com.adrian99.onlineShop.model.Product;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long>,
                                        QuerydslPredicateExecutor<Product> {
}
