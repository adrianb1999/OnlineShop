package com.adrian99.onlineShop.service.implementation;

import com.adrian99.onlineShop.model.Category;
import com.adrian99.onlineShop.model.Product;
import com.adrian99.onlineShop.model.QProduct;
import com.adrian99.onlineShop.repository.ProductRepository;
import com.adrian99.onlineShop.service.ProductService;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long aLong) {
        return productRepository.findById(aLong).orElse(null);
    }

    @Override
    public Product save(Product object) {
        return productRepository.save(object);
    }

    @Override
    public <S extends Product> Iterable<S> saveAll(Iterable<S> entities) {
        return productRepository.saveAll(entities);
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public Page<Product> findAllByCategory(Category category, int pageSize, int pageNum) {
        QProduct qProduct = QProduct.product;
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        booleanBuilder.and(qProduct.category.eq(category));

        return findAll(booleanBuilder, PageRequest.of(pageNum, pageSize));
    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {
    }

    //QUERY DSL QUERIES
    @Override
    public Optional<Product> findOne(Predicate predicate) {
        return Optional.empty();
    }

    @Override
    public Iterable<Product> findAll(Predicate predicate) {
        return null;
    }

    @Override
    public Iterable<Product> findAll(Predicate predicate, Sort sort) {
        return null;
    }

    @Override
    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        return productRepository.findAll(predicate, pageable);
    }

    @Override
    public long count(Predicate predicate) {
        return 0;
    }
}
