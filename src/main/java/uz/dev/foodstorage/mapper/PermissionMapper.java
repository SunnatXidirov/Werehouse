package uz.dev.foodstorage.mapper;


import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.NonNull;

import uz.dev.foodstorage.domain.Permission;
import uz.dev.foodstorage.dto.PermissionRequest;

import java.util.List;


@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PermissionMapper {
    PermissionMapper INSTANCE = Mappers.getMapper(PermissionMapper.class);
    Permission toEntity(@NonNull PermissionRequest request);
    List<Permission> toEntities(@NonNull List<PermissionRequest> requestList);
}
