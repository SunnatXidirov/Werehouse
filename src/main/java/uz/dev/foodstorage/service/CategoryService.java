package uz.dev.foodstorage.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.dev.foodstorage.dao.CategoryRepository;
import uz.dev.foodstorage.dao.ProductRepository;
import uz.dev.foodstorage.domain.Category;
import uz.dev.foodstorage.domain.enums.Status;
import uz.dev.foodstorage.dto.RequestCategory;
import uz.dev.foodstorage.dto.RequestCategoryWithParent;
import uz.dev.foodstorage.dto.ResponseCategory;
import uz.dev.foodstorage.exception.MyConflictException;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryMapper categoryMapper;
    private final RedisTemplate<String, Object> redisTemplate;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository, ProductRepository productRepository,
                           CategoryMapper categoryMapper, RedisTemplate<String, Object> redisTemplate) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryMapper = categoryMapper;
        this.redisTemplate = redisTemplate;
    }


    public List<Category> getCategory(@NonNull int size) {
        List<Category> categories = categoryRepository.getAll(PageRequest.of(0, size));
        if (categories == null)
            throw new MyNotFoundException("category empty");
        return categories;
    }

    public ResponseCategory getId(@NonNull long id) {
        Optional<Category> optional = categoryRepository.getId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("category not found by this id");
        String cacheKey = "ResponseCategory";
        ResponseCategory categorys = (ResponseCategory) redisTemplate.opsForValue().get(cacheKey);
        if (categorys != null)
            return categorys;
        Category category = optional.get();
        Long productCount = productRepository.getProductCountByCategory(category.getId());
        ResponseCategory responseCategory = categoryMapper.fromEntity(category);
        responseCategory.setCategoryName(category.getParent().getName());
        responseCategory.setProductCount(productCount);
        redisTemplate.opsForValue().set(cacheKey, responseCategory, 1, TimeUnit.HOURS);
        return responseCategory;
    }

    public Category add(@NonNull RequestCategory requestCategory) {
        boolean exists = categoryRepository.existsByName(requestCategory.name());
        if (exists)
            throw new MyConflictException("this category already exists");
        Category category = categoryMapper.toEntity(requestCategory);
        Category save = categoryRepository.save(category);
        log.info("successfully saved");
        return save;
    }

    public Category addWithParent(@NonNull RequestCategoryWithParent requestCategory) {
        boolean exists = categoryRepository.existsByName(requestCategory.name());
        if (exists)
            throw new MyConflictException("this category already exists");
        Category category = categoryMapper.toEntity(requestCategory, categoryRepository);
        Category save = categoryRepository.save(category);
        log.info("successfully saved");
        return save;
    }

    public Category delete(@NonNull long id) {
        Optional<Category> optional = categoryRepository.getId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("category not found by this id");
        Category category = optional.get();
        category.setStatus(Status.INACTIVE);
        Category save = categoryRepository.save(category);
        log.info("successfully deleted");
        return save;
    }
}
