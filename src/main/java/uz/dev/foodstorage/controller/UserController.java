package uz.dev.foodstorage.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.dev.foodstorage.GlobalURL.userURL.UserUrl;
import uz.dev.foodstorage.domain.User;
import uz.dev.foodstorage.dto.AdminRequest;
import uz.dev.foodstorage.dto.UserRequest;
import uz.dev.foodstorage.service.UserService;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(UserUrl.baseUserUrl)
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping(UserUrl.getUsers)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<Page> getUsers(@Valid @RequestParam(value = "size") int size) {
        List<User> users = userService.getUsers(size);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new PageImpl<>(Collections.singletonList(users)));

    }

    @GetMapping(UserUrl.getBlockUser)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getBlockUsers(@Valid @RequestParam(value = "size") int size) {
        List<User> users = userService.getBlockUsers(size);
        if (users != null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new PageImpl<>(Collections.singletonList(users)));
        }
        return ResponseEntity.ok("There are no block users yet");
    }

    @GetMapping(UserUrl.getUser)
    @PreAuthorize("hasAnyAuthority('SHOW')")
    public ResponseEntity<?> getUser(@Valid @RequestParam(value = "userId") long id) {
        User user = userService.getUser(id);
        return ResponseEntity.status(HttpStatus.OK).body(new PageImpl<>(Collections.singletonList(user)));
    }


    @PostMapping(UserUrl.addUser)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserRequest userRequest) {
        User user = userService.registerUser(userRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PageImpl<>(Collections.singletonList(user)));
    }

    @PostMapping(UserUrl.addAdmin)
    @PreAuthorize("hasAnyAuthority('ADD')")
    public ResponseEntity<?> registerAdmin(@Valid @RequestBody AdminRequest adminRequest) {
        User user = userService.addAdmin(adminRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new PageImpl<>(Collections.singletonList(user)));
    }

}
