package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Order;
import uos.seclass.bacchus.dtos.InsertOrderDTO;

;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    Order toEntity(InsertOrderDTO orderDTO);

    InsertOrderDTO toDTO(Order order);
}

