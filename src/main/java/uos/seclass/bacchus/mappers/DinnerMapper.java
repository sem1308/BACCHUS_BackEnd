package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.domains.Food;
import uos.seclass.bacchus.dtos.InsertDinnerDTO;
import uos.seclass.bacchus.dtos.UpdateDinnerDTO;

@Mapper(componentModel = "spring")
public interface DinnerMapper {

    DinnerMapper INSTANCE = Mappers.getMapper(DinnerMapper.class);

    Dinner toEntity(InsertDinnerDTO dinnerDTO);

    InsertDinnerDTO toDTO(Dinner dinner);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateDinnerDTO dto, @MappingTarget Dinner entity);
}

