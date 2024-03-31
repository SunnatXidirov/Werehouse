package uz.dev.foodstorage.dao;


import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.dev.foodstorage.domain.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("select u from users u where u.id = :id")
    Optional<User> getId(@Param(value = "id") long id);

    @Query("select u from users u where u.username = :username")
    Optional<User> findByUsername(@Param(value = "username") String username);

    @Query("select u from users u where u.status='ACTIVE' order by u.name asc")
    List<User> getUsers(Pageable pageable);

    @Query("select u from users u where u.status='BLOCK' order by u.name asc")
    List<User> getBlockUsers(Pageable pageable);
}
