package uz.dev.foodstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.foodstorage.GlobalURL.productURL.ProductUrl;
import uz.dev.foodstorage.domain.Product;
import uz.dev.foodstorage.dto.ProductRequest;
import uz.dev.foodstorage.service.ProductService;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(ProductUrl.productBaseUrl)
public class ProductController {
    private final ProductService productService;

    @GetMapping(ProductUrl.productSearchByName)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> searchByName(@Valid @RequestParam(value = "productName") String name) {
        List<Product> products = productService.searchByName(name);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(products));
    }
    @GetMapping(ProductUrl.productSearchByCategory)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> searchByCategory(@Valid @RequestParam(value = "categoryId") long id) {
        List<Product> products = productService.searchByCategory(id);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(products));
    }
    @GetMapping(ProductUrl.productSearchByQuantityUp)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> searchByQuantityUp(@Valid @RequestParam(value = "quantity") double quantity) {
        List<Product> products = productService.searchByQuantityUp(quantity);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(products));
    }
    @GetMapping(ProductUrl.productSearchByQuantityDown)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> searchByQuantityDown(@Valid @RequestParam(value = "quantity") double quantity) {
        List<Product> products = productService.searchByQuantityDown(quantity);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(products));
    }


    @GetMapping(ProductUrl.productGetAll)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getAll(@Valid @RequestParam(value = "size") int size) {
        List<Product> productList = productService.getAll(size);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(productList));
    }

    @GetMapping(ProductUrl.productGetById)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getId(@Valid @RequestParam(value = "productId") long id) {
        Product product = productService.getId(id);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl(Collections.singletonList(product)));
    }

    @PostMapping(ProductUrl.productAdd)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> add(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.add(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PageImpl(Collections.singletonList(product)));
    }

    @DeleteMapping(ProductUrl.productDelete)
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public String delete(@Valid @RequestParam(value = "id") long id) {
        productService.delete(id);
        return null;
    }


}
