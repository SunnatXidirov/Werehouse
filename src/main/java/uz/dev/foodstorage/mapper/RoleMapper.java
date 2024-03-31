package uz.dev.foodstorage.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import uz.dev.foodstorage.domain.Roles;
import uz.dev.foodstorage.dto.RoleRequest;
@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface RoleMapper {
    Roles toEntity(RoleRequest request);
}
