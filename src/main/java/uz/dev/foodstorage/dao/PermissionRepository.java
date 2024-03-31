package uz.dev.foodstorage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.foodstorage.domain.Permission;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

    @Query("select p from permissions p")
    List<Permission> findAllBy();

    @Query("select p from permissions p where p.id= :id")
    Optional<Permission> findId(@Param(value = "id")Long id);
    @Query("select p from permissions p where p.code= :code")
    Optional<Permission> findByCode(@Param(value = "code")String code);
}