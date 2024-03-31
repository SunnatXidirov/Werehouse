package uz.dev.foodstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.foodstorage.dao.CategoryRepository;
import uz.dev.foodstorage.dao.ProductRepository;
import uz.dev.foodstorage.domain.Category;
import uz.dev.foodstorage.domain.Product;
import uz.dev.foodstorage.domain.enums.Status;
import uz.dev.foodstorage.dto.ProductRequest;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.mapper.ProductMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper,
                          CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.categoryRepository = categoryRepository;
    }

    public List<Product> getAll(@NonNull int size) {
        List<Product> productList = productRepository.getAllProduct(PageRequest.of(0, size));
        log.info("successfully get");
        return productList;
    }

    public Product getId(@NonNull long id) {
        Optional<Product> optional = productRepository.getId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("product not found");
        Product product = optional.get();
        log.info("successfully get");
        return product;
    }

    @Transactional
    public Product add(@NonNull ProductRequest productRequest) {
        Optional<Category> optional = categoryRepository.getId(productRequest.categoryId());
        if (optional.isEmpty())
            throw new MyNotFoundException("category not found by this id");
        Product product = productMapper.toEntity(productRequest, categoryRepository);
        product.setStatus(Status.ACTIVE);
        Product save = productRepository.save(product);
        log.info("successfully saved");
        return save;
    }

    public void delete(@NonNull long id) {
        Optional<Product> optional = productRepository.getId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("product not found");
        Product product = optional.get();
        productRepository.delete(product);
        log.info("successfully deleted");
    }

    public List<Product> searchByName(@NonNull String name) {
        List<Product> products = productRepository.searchByName(name, PageRequest.of(0, 50));
        log.info("successfully get");
        return products;
    }

    public List<Product> searchByQuantityUp(@NonNull double quantity) {
        List<Product> products = productRepository.searchByQuantityUp(quantity, PageRequest.of(0, 50));
        log.info("successfully get");
        return products;
    }

    public List<Product> searchByQuantityDown(@NonNull double quantity) {
        List<Product> products = productRepository.searchByQuantityDown(quantity, PageRequest.of(0, 50));
        log.info("successfully get");
        return products;
    }

    public List<Product> searchByCategory(@NonNull long id) {
        List<Product> products = productRepository.searchByCategory(id, PageRequest.of(0, 50));
        log.info("successfully get");
        return products;
    }
}
