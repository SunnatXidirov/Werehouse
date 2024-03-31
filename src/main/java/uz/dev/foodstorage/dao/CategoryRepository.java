package uz.dev.foodstorage.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.foodstorage.domain.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c from category c  where c.status='ACTIVE' and c.id= :id")
    Optional<Category> getId(@Param(value = "id")long id);

    @Query("SELECT c FROM category c WHERE c.status='ACTIVE' ORDER BY c.status DESC")
    List<Category> getAll(Pageable pageable);
    boolean existsByName(String name);

}