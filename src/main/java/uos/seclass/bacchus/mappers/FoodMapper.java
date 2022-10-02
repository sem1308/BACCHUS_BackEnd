package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertFoodDTO;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodMapper INSTANCE = Mappers.getMapper(FoodMapper.class);

    Food toEntity(InsertFoodDTO foodDTO);

    InsertFoodDTO toDTO(Food food);
}

