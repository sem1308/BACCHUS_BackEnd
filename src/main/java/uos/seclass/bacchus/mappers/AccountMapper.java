package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Account;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertAccountDTO;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.dtos.UpdateAccountDTO;
import uos.seclass.bacchus.dtos.UpdateOrderDTO;

;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    Account toEntity(InsertAccountDTO orderDTO);

    InsertAccountDTO toDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateAccountDTO dto, @MappingTarget Account entity);
}

