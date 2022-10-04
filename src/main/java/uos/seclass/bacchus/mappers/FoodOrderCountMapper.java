package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.FoodOrderCount;
import uos.seclass.bacchus.dtos.InsertFoodDinnerCountDTO;
import uos.seclass.bacchus.dtos.InsertFoodOrderCountDTO;

@Mapper(componentModel = "spring")
public interface FoodOrderCountMapper {

    FoodOrderCountMapper INSTANCE = Mappers.getMapper(FoodOrderCountMapper.class);

    FoodOrderCount toEntity(InsertFoodOrderCountDTO foodCountDTO);

    InsertFoodOrderCountDTO toDTO(FoodOrderCount foodCount);
}

