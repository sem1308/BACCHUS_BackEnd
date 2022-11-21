package uos.seclass.bacchus.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;
import uos.seclass.bacchus.domains.Style;
import uos.seclass.bacchus.dtos.InsertStyleDTO;
import uos.seclass.bacchus.dtos.UpdateStyleDTO;

@Mapper(componentModel = "spring")
public interface StyleMapper {

    StyleMapper INSTANCE = Mappers.getMapper(StyleMapper.class);

    Style toEntity(InsertStyleDTO styleDTO);

    InsertStyleDTO toDTO(Style style);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(UpdateStyleDTO dto, @MappingTarget Style entity);
}

