package uz.dev.foodstorage;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;
import uz.dev.foodstorage.dao.PermissionRepository;
import uz.dev.foodstorage.dao.RolesRepository;
import uz.dev.foodstorage.dao.UserRepository;
import uz.dev.foodstorage.domain.Permission;
import uz.dev.foodstorage.domain.Roles;
import uz.dev.foodstorage.domain.User;
import uz.dev.foodstorage.domain.enums.PermissionName;
import uz.dev.foodstorage.domain.enums.RoleName;
import uz.dev.foodstorage.domain.enums.UserStatus;

import java.util.HashSet;
import java.util.List;

@SpringBootApplication
@EnableJpaAuditing
public class FoodStorageApplication {

    public static void main(String[] args) {
        SpringApplication.run(FoodStorageApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder,
                             UserRepository userRepository,
                             RolesRepository roleRepository,
                             PermissionRepository permissionRepository) {
        return (args) -> {

            /// Permission generated
            Permission addPermission = Permission.builder()
                    .id(1l)
                    .name("ADD")
                    .code(PermissionName.ADD.name())
                    .build();

            Permission updatePermission = Permission.builder()
                    .id(2l)
                    .name("UPDATE")
                    .code(PermissionName.UPDATE.name())
                    .build();

            Permission showPermission = Permission.builder()
                    .id(3l)
                    .name("SHOW")
                    .code(PermissionName.SHOW.name())
                    .build();

            Permission deletePermission = Permission.builder()
                    .id(4l)
                    .name("DELETE")
                    .code(PermissionName.DELETE.name())
                    .build();
            permissionRepository.saveAll(List.of(
                    addPermission,
                    updatePermission,
                    showPermission,
                    deletePermission));

            Roles superAdmin = Roles.builder()
                    .id(1l)
                    .code(RoleName.SUPER_ADMIN.name())
                    .name("Admin")
                    .permission(new HashSet<>(List.of(
                            addPermission,
                            updatePermission,
                            showPermission,
                            deletePermission
                    )))
                    .build();


            /// Role generated

            roleRepository.save(superAdmin);

            /// Users generated
            User admin = User.builder()
                    .id(1l)
                    .name("Sunnat")
                    .username("xidi1ov")
                    .status(UserStatus.ACTIVE)
                    .password(passwordEncoder.encode("11"))
                    .roles(new HashSet<>(List.of(superAdmin)))
                    .build();
            userRepository.save(admin);
        };
    }


}
