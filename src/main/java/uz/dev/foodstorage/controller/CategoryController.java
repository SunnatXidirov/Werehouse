package uz.dev.foodstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.foodstorage.GlobalURL.categoryURL.CategoryUrl;
import uz.dev.foodstorage.domain.Category;
import uz.dev.foodstorage.dto.RequestCategory;
import uz.dev.foodstorage.dto.RequestCategoryWithParent;
import uz.dev.foodstorage.dto.ResponseCategory;
import uz.dev.foodstorage.service.CategoryService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(CategoryUrl.categoryBaseUrl)
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping(CategoryUrl.categoryGetAll)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getAll(@Valid @RequestParam(value = "size") int size) {
        List<Category> categoryList = categoryService.getCategory(size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(categoryList));
    }

    @GetMapping(CategoryUrl.categoryGetById)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getId(@Valid @RequestParam(value = "id") long id) {
        ResponseCategory category = categoryService.getId(id);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(Collections.singletonList(category)));
    }

    @PostMapping(CategoryUrl.categoryAdd)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> add(@Valid @RequestBody RequestCategory requestCategory) {
        Category category = categoryService.add(requestCategory);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(Collections.singletonList(category)));
    }

    @PostMapping(CategoryUrl.categoryAddWithParent)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> addWithParent(@Valid @RequestBody RequestCategoryWithParent requestCategory) {
        Category category = categoryService.addWithParent(requestCategory);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(Collections.singletonList(category)));
    }

    @DeleteMapping(CategoryUrl.categoryDelete)
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public ResponseEntity<?> delete(@Valid @RequestParam(value = "id") long id) {
        Category category = categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(Collections.singletonList(category)));
    }

}
