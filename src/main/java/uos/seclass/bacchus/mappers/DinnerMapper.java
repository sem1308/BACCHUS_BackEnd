package uos.seclass.bacchus.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Dinner;
import uos.seclass.bacchus.dtos.InsertDinnerDTO;

@Mapper(componentModel = "spring")
public interface DinnerMapper {

    DinnerMapper INSTANCE = Mappers.getMapper(DinnerMapper.class);

    Dinner toEntity(InsertDinnerDTO dinnerDTO);

    InsertDinnerDTO toDTO(Dinner dinner);
}

