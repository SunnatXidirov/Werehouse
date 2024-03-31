package uz.dev.foodstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.foodstorage.GlobalURL.roleURL.RoleUrl;
import uz.dev.foodstorage.domain.Roles;
import uz.dev.foodstorage.dto.RoleRequest;
import uz.dev.foodstorage.service.RoleService;

import java.util.Collections;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(RoleUrl.roleBaseUrl)
public class RoleController {

    private final RoleService roleService;

    @GetMapping(RoleUrl.roleGetAll)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public Page<Roles> getAll() {
        List<Roles> rolesList = roleService.getAll();
        return new PageImpl<>(rolesList);
    }

    @GetMapping(RoleUrl.roleGetById)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public Page<Roles> getId(@Valid @RequestParam(value = "roleId") long id) {
        Roles roles = roleService.getId(id);
        return new PageImpl<>(Collections.singletonList(roles));
    }

    @PostMapping(RoleUrl.roleAdd)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> add(@Valid @RequestBody RoleRequest request) {
        roleService.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("successfully saved");
    }

    @PutMapping(RoleUrl.roleUpdate)
    @PreAuthorize("hasAnyAuthority('UPDATE')")
    public ResponseEntity<?> update(@Valid @RequestParam(value = "roleId") long id,
                                    @Valid @RequestBody RoleRequest roleRequest) {
        roleService.update(id, roleRequest);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successfully updated");
    }

    @DeleteMapping(RoleUrl.roleDelete)
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public ResponseEntity<?> delete(@Valid @RequestParam(value = "roleId") long id) {
        roleService.delete(id);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).build();
    }
}
