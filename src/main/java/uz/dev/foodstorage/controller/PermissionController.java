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
import uz.dev.foodstorage.GlobalURL.permissionURL.PermissionURL;
import uz.dev.foodstorage.domain.Permission;
import uz.dev.foodstorage.dto.PermissionRequest;
import uz.dev.foodstorage.dto.ResponseDto;
import uz.dev.foodstorage.service.PermissionService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(PermissionURL.permissionBaseUrl)
public class PermissionController {
    private final PermissionService permissionService;


    @GetMapping(PermissionURL.permissionGetAll)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public Page<Permission> getAll() {
        List<Permission> permissionList = permissionService.getAll();
        return new PageImpl<>(permissionList);
    }

    @GetMapping(PermissionURL.permissionGetById)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public Page<Permission> getId(@Valid @RequestParam(name = "id") long id) {
        Permission permission = permissionService.getId(id);
        return new PageImpl<>(Collections.singletonList(permission));
    }

    @PostMapping(PermissionURL.permissionAdd)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> add(@Valid @RequestBody PermissionRequest request) {
        ResponseDto dto = permissionService.add(request);
        return ResponseEntity.status(dto.check() ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(dto.message());
    }

    @PutMapping(PermissionURL.permissionUpdate)
    @PreAuthorize("hasAnyAuthority('UPDATE')")
    public ResponseEntity<?> update(@Valid @RequestParam(value = "permissionId") long id,
                                    @Valid @RequestBody PermissionRequest permissionRequest) {
        permissionService.update(id, permissionRequest);
        return ResponseEntity.status(HttpStatusCode.valueOf(204)).body("successfully updated");
    }

    @DeleteMapping(PermissionURL.permissionDelete)
    @PreAuthorize("hasAnyAuthority('DELETE')")
    public ResponseEntity<?> delete(@Valid @RequestParam(value = "permissionId") long id) {
        permissionService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("successfully deleted");
    }
}
