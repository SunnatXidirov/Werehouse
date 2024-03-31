package uz.dev.foodstorage.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.foodstorage.domain.Product;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select p from product p where p.status='ACTIVE' order by p.name asc")
    List<Product> getAllProduct(Pageable pageable);

    @Query("select p from product p where p.status='ACTIVE' and p.productId= :id")
    Optional<Product> getId(@Param(value = "id") long id);

    @Query("select sum(p.quantity) from product p where p.status='ACTIVE' and p.category.id= :categoryId")
    Long getProductCountByCategory(@Param(value = "categoryId") long categoryId);

    @Query("select p from product p where p.status='ACTIVE' and p.name like '%:name%' order by p.name asc ")
    List<Product> searchByName(@Param(value = "name") String name, Pageable pageable);

    @Query("select p from product p where p.status='ACTIVE' and p.quantity >= :quantity")
    List<Product> searchByQuantityUp(@Param(value = "quantity") double quantity,Pageable pageable);
    @Query("select p from product p where p.status='ACTIVE' and p.quantity <= :quantity")
    List<Product> searchByQuantityDown(@Param(value = "quantity") double quantity,Pageable pageable);

    @Query("select p from product p where p.status='ACTIVE' and p.category.id = :categoryId order by p.name asc")
    List<Product> searchByCategory(@Param(value = "categoryId") long categoryId,Pageable pageable);
}