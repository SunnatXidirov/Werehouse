package uz.dev.foodstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.dev.foodstorage.dao.PermissionRepository;
import uz.dev.foodstorage.dao.RolesRepository;
import uz.dev.foodstorage.dao.UserRepository;
import uz.dev.foodstorage.domain.Permission;
import uz.dev.foodstorage.domain.Roles;
import uz.dev.foodstorage.domain.User;
import uz.dev.foodstorage.domain.enums.RoleName;
import uz.dev.foodstorage.domain.enums.UserStatus;
import uz.dev.foodstorage.dto.AdminRequest;
import uz.dev.foodstorage.dto.UserRequest;
import uz.dev.foodstorage.exception.MyConflictException;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.mapper.UserMapper;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RolesRepository rolesRepository;
    private final PermissionRepository permissionRepository;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional(rollbackFor = {MyNotFoundException.class, MyConflictException.class})
    public User registerUser(@NonNull UserRequest userRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(userRequest.username());
        if (optionalUser.isPresent())
            throw new MyConflictException("this username already exists or permission not found");
        User user = userMapper.toEntity(userRequest);
        Optional<Roles> code = rolesRepository.findCode(RoleName.USER.name());
        user.setRoles(Collections.singleton(code.get()));
        user.setStatus(UserStatus.ACTIVE);
        User save = userRepository.save(user);
        log.info("successfully saved");
        return save;
    }

    @Transactional(rollbackFor = {MyNotFoundException.class, MyConflictException.class})
    public User addAdmin(@NonNull AdminRequest adminRequest) {
        Optional<User> optionalUser = userRepository.findByUsername(adminRequest.username());
        if (optionalUser.isPresent())
            throw new MyConflictException("this username already exists or permission not found");
        Optional<Roles> optionalRoles = rolesRepository.findCode(adminRequest.roleCode());
        Optional<Permission> optionalPermission = permissionRepository.findByCode(adminRequest.permissionCode());
        if (optionalRoles.isEmpty() || optionalPermission.isEmpty())
            throw new MyNotFoundException("role or permission not found");
        Roles roles = optionalRoles.get();
        Permission permission = optionalPermission.get();
        User user = userMapper.toEntity(adminRequest);
        roles.setPermission(Collections.singleton(permission));
        user.setRoles(Collections.singleton(roles));
        user.setStatus(UserStatus.ACTIVE);
        User save = userRepository.save(user);
        return save;
    }

    public List<User> getUsers(@NonNull int size) {
        List<User> users = userRepository.getUsers(PageRequest.of(0, size));
        log.info("successfully get");
        return users;
    }

    public List<User> getBlockUsers(@NonNull int size) {
        List<User> users = userRepository.getBlockUsers(PageRequest.of(0, size));
        log.info("successfully get");
        return users;
    }

    public User getUser(@NonNull long id) {
        Optional<User> optional = userRepository.getId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("user not found by this id");
        User user = optional.get();
        String cacheKey = String.valueOf(user.getId());
        User redisUser = (User) redisTemplate.opsForValue().get(cacheKey);
        if (redisUser != null)
            return redisUser;
        log.info("successfully get");
        redisTemplate.opsForValue().set(cacheKey, user, 1, TimeUnit.DAYS);
        return user;
    }
}
