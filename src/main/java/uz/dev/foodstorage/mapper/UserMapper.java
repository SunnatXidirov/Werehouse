package uz.dev.foodstorage.mapper;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import uz.dev.foodstorage.domain.User;
import uz.dev.foodstorage.dto.AdminRequest;
import uz.dev.foodstorage.dto.UserRequest;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.WARN,
        nullValueMapMappingStrategy = NullValueMappingStrategy.RETURN_NULL,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toEntity(@NonNull UserRequest userRequest);

    User toEntity(@NonNull AdminRequest adminRequest);

}
