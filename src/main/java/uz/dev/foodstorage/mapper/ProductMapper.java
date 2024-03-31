package uz.dev.foodstorage.mapper;

import org.mapstruct.*;
import org.springframework.stereotype.Component;

import uz.dev.foodstorage.dao.CategoryRepository;
import uz.dev.foodstorage.dao.ProductRepository;
import uz.dev.foodstorage.domain.Category;
import uz.dev.foodstorage.domain.Product;
import uz.dev.foodstorage.dto.ProductRequest;
import uz.dev.foodstorage.exception.MyNotFoundException;

import java.util.Optional;


@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface ProductMapper {

    @Mappings({
            @Mapping(target = "category", expression = "java(getCategory(productRequest.categoryId(),categoryRepository))")
    })
    Product toEntity(ProductRequest productRequest, CategoryRepository categoryRepository);


    default Category getCategory(long categoryId, CategoryRepository categoryRepository) {
        Optional<Category> optional = categoryRepository.getId(categoryId);
        if (optional.isEmpty())
            throw new MyNotFoundException("category not found");
        return optional.get();
    }
}
