package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.FoodDinnerCount;
import uos.seclass.bacchus.domains.FoodOrderCount;
import uos.seclass.bacchus.dtos.InsertFoodDinnerCountDTO;

@Mapper(componentModel = "spring")
public interface FoodDinnerCountMapper {

    FoodDinnerCountMapper INSTANCE = Mappers.getMapper(FoodDinnerCountMapper.class);

    FoodDinnerCount toEntity(InsertFoodDinnerCountDTO foodCountDTO);

    InsertFoodDinnerCountDTO toDTO(FoodDinnerCount foodCount);
}

