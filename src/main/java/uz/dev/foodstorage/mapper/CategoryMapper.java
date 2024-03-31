package uz.dev.foodstorage.mapper;

import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;
import uz.dev.foodstorage.dao.CategoryRepository;
import uz.dev.foodstorage.domain.Category;
import uz.dev.foodstorage.dto.RequestCategory;
import uz.dev.foodstorage.dto.RequestCategoryWithParent;
import uz.dev.foodstorage.dto.ResponseCategory;
import uz.dev.foodstorage.exception.MyNotFoundException;

import java.util.Optional;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
@Component
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    ResponseCategory fromEntity(Category category);

    Category toEntity(RequestCategory requestCategory);

    @Mappings({
            @Mapping(target = "parent", expression = "java(getCategory(requestCategory.id(),categoryRepository))")
    })
    Category toEntity(RequestCategoryWithParent requestCategory, CategoryRepository categoryRepository);

    default Category getCategory(long categoryId, CategoryRepository categoryRepository) {
        Optional<Category> optional = categoryRepository.getId(categoryId);
        if (optional.isEmpty())
            throw new MyNotFoundException("category not found");
        return optional.get();
    }
}
