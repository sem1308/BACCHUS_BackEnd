package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

import uos.seclass.bacchus.domains.Customer;
import uos.seclass.bacchus.dtos.InsertCustomerDTO;;
import uos.seclass.bacchus.dtos.UpdateCustomerDTO;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    Customer toEntity(InsertCustomerDTO memberDTO);

    InsertCustomerDTO toDTO(Customer customer);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateCustomerDTO dto, @MappingTarget Customer entity);
}

