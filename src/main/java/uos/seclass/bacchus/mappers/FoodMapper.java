package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertFoodDTO;
import uos.seclass.bacchus.dtos.UpdateFoodDTO;

@Mapper(componentModel = "spring")
public interface FoodMapper {

    FoodMapper INSTANCE = Mappers.getMapper(FoodMapper.class);

    Food toEntity(InsertFoodDTO foodDTO);

    InsertFoodDTO toDTO(Food food);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateFoodDTO dto, @MappingTarget Food entity);
}

