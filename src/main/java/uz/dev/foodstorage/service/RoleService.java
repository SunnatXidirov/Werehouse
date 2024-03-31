package uz.dev.foodstorage.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import uz.dev.foodstorage.dao.RolesRepository;
import uz.dev.foodstorage.domain.Roles;
import uz.dev.foodstorage.dto.ResponseDto;
import uz.dev.foodstorage.dto.RoleRequest;
import uz.dev.foodstorage.exception.MyNotFoundException;
import uz.dev.foodstorage.mapper.RoleMapper;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoleService {

    private final RolesRepository rolesRepository;
    private final RoleMapper roleMapper;


    public List<Roles> getAll() {
        Optional<List<Roles>> optional = rolesRepository.findAllBy();
        if (optional.isEmpty())
            throw new MyNotFoundException("role table is empty");
        List<Roles> rolesList = optional.get();
        log.info("successfully get");
        return rolesList;
    }

    public Roles getId(@NonNull long id) {
        Optional<Roles> optional = rolesRepository.findById(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("role not found by this id");
        Roles roles = optional.get();
        log.info("successfully get");
        return roles;
    }

    public void add(@NonNull RoleRequest request) {
        Roles roles = roleMapper.toEntity(request);
        rolesRepository.save(roles);
        log.info("successfully saved");
    }

    public void update(@NonNull long id, @NonNull RoleRequest roleRequest) {
        Optional<Roles> optional = rolesRepository.findById(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("role not found by this id");
        Roles roles = optional.get();
        roles.setName(roleRequest.name());
        roles.setCode(roleRequest.code());
        rolesRepository.save(roles);
        log.info("successfully updated");
    }

    public void delete(@NonNull long id) {
        Optional<Roles> optional = rolesRepository.findById(id);
        if (optional.isEmpty())
            throw new MyNotFoundException("role not found by this id");
        Roles roles = optional.get();
        rolesRepository.delete(roles);
        log.info("successfully deleted");
    }
}
