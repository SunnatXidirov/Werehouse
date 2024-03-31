package uz.dev.foodstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.dev.foodstorage.dao.PermissionRepository;
import uz.dev.foodstorage.domain.Permission;
import uz.dev.foodstorage.dto.PermissionRequest;
import uz.dev.foodstorage.dto.ResponseDto;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.mapper.PermissionMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PermissionService {
    private final PermissionMapper permissionMapper;
    private final PermissionRepository permissionRepository;

    public List<Permission> getAll() {
        List<Permission> list = permissionRepository.findAllBy();
        log.info("successfully get");
        return list;
    }

    public Permission getId(@NonNull long permissionId) {
        Optional<Permission> optional = permissionRepository.findId(permissionId);
        if (optional.isEmpty())
            throw new UsernameNotFoundException("permission not found by this id");
        Permission permission = optional.get();
        log.info("successfully get");
        return permission;
    }

    public ResponseDto add(@NonNull PermissionRequest request) {
        Optional<Permission> optional = permissionRepository.findByCode(request.code());
        if (optional.isPresent())
            return new ResponseDto("this permission code already exists", false);
        Permission permission = permissionMapper.toEntity(request);
        permissionRepository.save(permission);
        log.info("successfully saved");
        return new ResponseDto("successfully saved", true);
    }

    public void update(@NonNull long id, @NonNull PermissionRequest permissionRequest) {
        Optional<Permission> optional = permissionRepository.findId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("permission not found by this id");
        Permission permission = optional.get();
        permission.setName(permissionRequest.name());
        permission.setCode(permissionRequest.code());
        permissionRepository.save(permission);
        log.info("successfully updated");
    }

    public void delete(@NonNull long id) {
        Optional<Permission> optional = permissionRepository.findId(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("permission not found by this id");
        Permission permission = optional.get();
        permissionRepository.delete(permission);
        log.info("successfully deleted");
    }
}
