package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.FoodDinnerCount;
import uos.seclass.bacchus.domains.FoodOrderCount;
import uos.seclass.bacchus.dtos.InsertFoodDinnerCountDTO;
import uos.seclass.bacchus.dtos.UpdateDinnerDTO;
import uos.seclass.bacchus.dtos.UpdateFoodDinnerCountDTO;

@Mapper(componentModel = "spring")
public interface FoodDinnerCountMapper {

    FoodDinnerCountMapper INSTANCE = Mappers.getMapper(FoodDinnerCountMapper.class);

    FoodDinnerCount toEntity(InsertFoodDinnerCountDTO foodCountDTO);

    InsertFoodDinnerCountDTO toDTO(FoodDinnerCount foodCount);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateFoodDinnerCountDTO dto, @MappingTarget FoodDinnerCount entity);
}

