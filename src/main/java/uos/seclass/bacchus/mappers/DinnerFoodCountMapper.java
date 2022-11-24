package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.DinnerFoodCount;
import uos.seclass.bacchus.dtos.InsertDinnerFoodCountDTO;
import uos.seclass.bacchus.dtos.UpdateDinnerFoodCountDTO;

@Mapper(componentModel = "spring")
public interface DinnerFoodCountMapper {

    DinnerFoodCountMapper INSTANCE = Mappers.getMapper(DinnerFoodCountMapper.class);

    DinnerFoodCount toEntity(InsertDinnerFoodCountDTO foodCountDTO);

    InsertDinnerFoodCountDTO toDTO(DinnerFoodCount foodCount);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateDinnerFoodCountDTO dto, @MappingTarget DinnerFoodCount entity);
}

