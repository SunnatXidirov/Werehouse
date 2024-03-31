package uz.dev.foodstorage.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.foodstorage.domain.Roles;

import java.util.List;
import java.util.Optional;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long> {
    @Query("select r from roles r")
    Optional<List<Roles>> findAllBy();

    @Query("select r from roles r where r.id = :roleId")
    Optional<Roles> findById(@Param(value = "roleId") long id);

    @Query("select r from roles r where r.code = :code")
    Optional<Roles> findCode(@Param(value = "code") String code);
}