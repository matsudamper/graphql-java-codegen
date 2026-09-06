package io.github.kobylynskyi.product.repository;

import io.github.kobylynskyi.product.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
