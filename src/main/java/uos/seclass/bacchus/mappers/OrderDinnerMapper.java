package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.OrderDinner;
import uos.seclass.bacchus.dtos.InsertOrderDinnerDTO;

@Mapper(componentModel = "spring")
public interface OrderDinnerMapper {

    OrderDinnerMapper INSTANCE = Mappers.getMapper(OrderDinnerMapper.class);

    OrderDinner toEntity(InsertOrderDinnerDTO orderDinnerDTO);

    InsertOrderDinnerDTO toDTO(OrderDinner orderDinnerDTO);
}

