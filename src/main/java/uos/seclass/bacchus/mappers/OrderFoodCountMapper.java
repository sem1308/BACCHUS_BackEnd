package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.OrderFoodCount;
import uos.seclass.bacchus.dtos.InsertOrderFoodCountDTO;

@Mapper(componentModel = "spring")
public interface OrderFoodCountMapper {

    OrderFoodCountMapper INSTANCE = Mappers.getMapper(OrderFoodCountMapper.class);

    OrderFoodCount toEntity(InsertOrderFoodCountDTO foodCountDTO);

    InsertOrderFoodCountDTO toDTO(OrderFoodCount foodCount);
}

