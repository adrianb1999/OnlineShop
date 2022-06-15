package com.adrian99.onlineShop.service;

import com.adrian99.onlineShop.model.Category;
import com.adrian99.onlineShop.model.Product;
import org.springframework.data.domain.Page;

public interface ProductService extends CrudService<Product, Long>,
                                        QueryDslService<Product>{
    Page<Product> findAllByCategory(Category category, int pageSize, int pageNum);
}
