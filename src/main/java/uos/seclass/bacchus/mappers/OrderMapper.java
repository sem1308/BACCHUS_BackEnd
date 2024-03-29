package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertOrderDTO;
import uos.seclass.bacchus.dtos.UpdateDinnerDTO;
import uos.seclass.bacchus.dtos.UpdateOrderDTO;

;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(InsertOrderDTO orderDTO);

    InsertOrderDTO toDTO(Order order);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateOrderDTO dto, @MappingTarget Order entity);
}

